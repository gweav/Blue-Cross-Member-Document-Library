package edu.ncsu.csc.BCBS.controllers;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import edu.ncsu.csc.BCBS.models.Address;
import edu.ncsu.csc.BCBS.models.DocType;
import edu.ncsu.csc.BCBS.models.Document;
import edu.ncsu.csc.BCBS.models.Policy;
import edu.ncsu.csc.BCBS.models.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DocumentControllerTest {
    
    @Autowired
    private ElasticsearchClient esc;

    @Autowired
    private MockMvc mvc;

    /**
     * Tests Basic Retrieval of Documents based on ID.
     * @throws Exception on critical error.
     */
    @Test
    public void testElasticSearchDocumentId() throws Exception {
        System.out.println("\n----testElasticSearchDocumentId----\n");

        // POST Document to Index Storage.
        Document d = new Document("id-1", "Explanation of Benefits", "pid-1", "uid-1", LocalDate.now(), DocType.EOB);

        IndexResponse iResponse = null;

        try {
            iResponse = esc.index(i -> i.index("documents")
                                        .id(d.getId()).document(d));
            System.out.println("Index Response: " + iResponse.toString());           
        } catch (IOException e) {
            System.out.println("Index failed: " + e.getMessage());
            fail();
        }

        // GET a Document that doesn't exist.
        this.mvc.perform(get("/api/documents/id-6")).andDo(print()).andExpect(status().isNotFound());
        
        // GET Document.
        this.mvc.perform(get("/api/documents/id-1")).andDo(print()).andExpect(status().isOk());

        // DELETE Document
        DeleteResponse dResponse = esc.delete(del -> del.index("documents").id("id-1"));
        System.out.println("Delete Response: " + dResponse.toString());
    
        // DELETE Index
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index("documents").build();
        DeleteIndexResponse deleteIndexResponse = esc.indices().delete(deleteIndexRequest);
        System.out.println("Delete Index Response: " + deleteIndexResponse.toString());
    }

    /**
     * Tests Document Querying based on users.
     * @throws Exception on critical mass.
     */
    @Test
    public void testESDocumentByUser() throws Exception {
        System.out.println("\n----testESDocumentByUser----\n");

        Address a = new Address("Example", "Example", "Example", "Example");
        User u = new User("uid-1", "Jacob Johnson", LocalDate.now(), "Non-binary", a);
        Policy p = new Policy("pid-1", Year.now(), true, u.getId(), null, null);
        Document d = new Document("id-1", "Explanation of Benefits", "pid-1", "uid-1",LocalDate.now(), DocType.EOB);
        Document d2 = new Document("id-2", "Bill", "pid-1", "uid-2", LocalDate.now(), DocType.BILL);
        p.addDocument(d);
        p.addDocument(d2); // Shouldn't be added -> Unrelated document.
        u.addPolicy(p);
        
        // Posts Users / Policies / Documents
        try {
            // POST User
            IndexResponse iResponse = esc.index(i -> i.index("users").id(u.getId()).document(u));
            System.out.println("Index Response (user): " + iResponse.toString());
        } catch (IOException e) {
            System.out.println("Index failed: " + e.getMessage());
            fail();
        }

        // GET Document based on User ID
        this.mvc.perform(get("/api/user/uid-1/documents")).andDo(print()).andExpect(status().isOk());

        // TEST that the user's document index is created with the document
        GetResponse<Document> getResponse = null;
        try {
            getResponse = esc.get(g -> g
                            .index(u.getId() + "-documents")
                            .id("id-1"),
                    Document.class
            );
        } catch (Exception e) {
            System.out.println("Elasticsearch GET failed: " + e.getMessage());
            fail();
        }

        Document response = getResponse.source();
        assertEquals(d.getId(), response.getId());
        assertEquals(d.getName(), response.getName());

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index("users").build();
        DeleteIndexResponse deleteIndexResponse = esc.indices().delete(deleteIndexRequest);
        System.out.println("Delete Index Response: " + deleteIndexResponse.toString());

        DeleteIndexRequest deleteIndexRequestUserDocuments = new DeleteIndexRequest.Builder().index(u.getId() + "-documents").build();
        DeleteIndexResponse deleteIndexResponseUserDocuments = esc.indices().delete(deleteIndexRequestUserDocuments);
        System.out.println("Delete User Document Index Response: " + deleteIndexResponseUserDocuments.toString());
    }

    @Test
    public void testSearchDocuments() throws Exception {
        System.out.println("\n----testSearchDocuments----\n");

        Address address = new Address("city", "state", "address", "zip");
        User subscriber = new User("uid-1", "First Last", LocalDate.now(), "Male", address);
        User dependent = new User("uid-2", "First Last", LocalDate.now(), "Male", address);
        Policy policy = new Policy("pid-1", Year.now(), true, subscriber.getId(), null, null);

        // Subscriber's documents
        Document d1 = new Document("id-1", "Explanation of Benefits", "pid-1", "uid-1", LocalDate.now(), DocType.EOB);
        Document d2 = new Document("id-2", "Bill", "pid-1", "uid-1", LocalDate.now(), DocType.BILL);
        Document d3 = new Document("id-3", "Letter", "pid-1", "uid-1", LocalDate.now(), DocType.LETTER);

        // Dependent's documents
        Document d4 = new Document("id-4", "Coverage", "pid-1", "uid-2", LocalDate.now(), DocType.COVERAGE);
        Document d5 = new Document("id-5", "ID Card", "pid-1", "uid-2", LocalDate.now(), DocType.ID_CARD);

        policy.addDependent(dependent);
        policy.addDocument(d1);
        policy.addDocument(d2);
        policy.addDocument(d3);
        policy.addDocument(d4);
        policy.addDocument(d5);
        assertEquals(5, policy.getDocuments().size());
        subscriber.addPolicy(policy);
        dependent.addPolicy(policy);

        // Subscriber Test
        System.out.println("\n-----SUBSCRIBER-----");

        // POST User to the index
        IndexResponse indexResponse = null;
        try {
            indexResponse = esc.index(i -> i
                    .index("users")
                    .id(subscriber.getId())
                    .document(subscriber)
            );
        } catch (Exception e) {
            System.out.println("Elasticsearch Index failed: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
        assertEquals("created", indexResponse.result().jsonValue());

        // GET documents by user
        this.mvc.perform(get("/api/user/uid-1/documents")).andDo(print()).andExpect(status().isOk());

        // GET from the index created for uid-1
        GetResponse<Document> getResponse = null;
        try {
            getResponse = esc.get(g -> g
                            .index(subscriber.getId() + "-documents")
                            .id("id-2"),
                    Document.class
            );
            
        } catch (Exception e) {
            System.out.println("Elasticsearch GET failed: " + e.getMessage());
            fail();
        }

        Document res = getResponse.source();
        assertEquals("id-2", res.getId());
        assertEquals("Bill", res.getName());
        System.out.println("Attempt to retrieve Document id-2 (Bill) from index uid-1-documents: " + res.getId());


        // Search for BILL in uid-1-documents
        TimeUnit.SECONDS.sleep(2);
        this.mvc.perform(get("/api/user/" + subscriber.getId() + "/documents/" + "Bill")).andDo(print()).andExpect(status().isOk());

        // Dependent Test
        System.out.println("\n-----DEPENDENT-----");

        // POST dependent User into index
        try {
            indexResponse = esc.index(i -> i
                    .index("users")
                    .id(dependent.getId())
                    .document(dependent)
            );
        } catch (Exception e) {
            System.out.println("Elasticsearch Index failed: " + e.getMessage());
            e.printStackTrace();
            fail();
        }

        // Get Dependent Documents
        this.mvc.perform(get("/api/user/uid-2/documents")).andDo(print()).andExpect(status().isOk());

        // Search Request for Dependent
        TimeUnit.SECONDS.sleep(2);
        this.mvc.perform(get("/api/user/" + dependent.getId() + "/documents/" + "Coverage")).andDo(print()).andExpect(status().isOk());


        // Delete indices
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index("users").build();
        DeleteIndexResponse deleteIndexResponse = esc.indices().delete(deleteIndexRequest);
        System.out.println("Delete Index Response: " + deleteIndexResponse.toString());

        DeleteIndexRequest deleteIndexRequestUserDocuments = new DeleteIndexRequest.Builder().index(subscriber.getId() + "-documents").build();
        DeleteIndexResponse deleteIndexResponseUserDocuments = esc.indices().delete(deleteIndexRequestUserDocuments);
        System.out.println("Delete User Document Index Response: " + deleteIndexResponseUserDocuments.toString());
    }
}
