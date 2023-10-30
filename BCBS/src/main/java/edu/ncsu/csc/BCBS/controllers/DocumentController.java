package edu.ncsu.csc.BCBS.controllers;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import edu.ncsu.csc.BCBS.models.Document;
import edu.ncsu.csc.BCBS.models.Policy;
import edu.ncsu.csc.BCBS.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.S3Exception;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DocumentController {
    
    @Autowired
    /** A client for communicating with Elasticsearch */
    private ElasticsearchClient esc;

    /**
     * A client for communicating with S3
     */
    @Autowired
    private S3Client s3Client;

    /**
     * Retrieves the specific document with associated ID.
     */
    @GetMapping("/documents/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable String id) {
        GetResponse<Document> response = null;

        try {
            response = esc.get(g -> g.index("documents").id(id), 
                                Document.class);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Document d = response.source();
        if (d == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    /**
     * Retrives the specified document from S3 using the provided ID
     */
    @GetMapping("/S3/documents/{id}")
    public ResponseEntity<byte[]> getS3Document(@PathVariable String id) {

        try {
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(id + ".pdf")
                    .bucket("bcbs9-documents")
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
            byte[] data = objectBytes.asByteArray();

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(data, responseHeaders, HttpStatus.OK);

        } catch (S3Exception e) {
            System.out.println(e.awsErrorDetails().errorMessage());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves the list of documents with associated user.
     * This may require the addition of a user field in Document class.
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/user/{userId}/documents")
    public ResponseEntity<List<Document>> getDocumentsByUser(@PathVariable String userId) {

        User user = null;
        try {
            // Retrieve the user.
            user = esc.get(g -> g.index("users").id(userId.toLowerCase()),
                                User.class).source(); // Source returns the Object User.

            if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            // This is the list of documents pertaining to the user.
            List<Document> userDocuments = new ArrayList<>();

            // Search thru the list of policies associated with the user.
            for (Policy p : user.getPolicies()) {
                for (Document d : p.getDocuments()) {
                    // Are you the subscriber OR does this document belong to you (Dependent)?
                    if (p.getSubscriberId().toLowerCase().equals(userId.toLowerCase()) || d.getUserId().toLowerCase().equals(userId.toLowerCase())) {
                        userDocuments.add(d);
                        // Add relevant documents to the user's personal index
                        esc.index(i -> i
                                .index(userId.toLowerCase() + "-documents")
                                .id(d.getId())
                                .document(d)
                        );
                    }
                }
            }
            
            return new ResponseEntity<>(userDocuments, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}/documents/{query}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Document>> getDocumentByQuery(@PathVariable String userId, @PathVariable String query) {
        SearchResponse<Document> response = null;

        try {
            response = esc.search(s -> s
                            .index(userId + "-documents")
                            .query(q -> q
                                    .match(t -> t
                                            .field("name")
                                            .query(query)
                                    )
                            ),
                    Document.class
            );
        } catch (IOException e) {
            System.out.println("Document search failed: " + e.getMessage());
            return null;
        }

        List<Hit<Document>> hits = response.hits().hits();

        List<Document> documents = new ArrayList<Document>();
        for (Hit<Document> hit: hits) {
            documents.add(hit.source());
        }
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

}
