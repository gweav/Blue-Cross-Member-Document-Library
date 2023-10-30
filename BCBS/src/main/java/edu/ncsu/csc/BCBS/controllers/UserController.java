package edu.ncsu.csc.BCBS.controllers;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import edu.ncsu.csc.BCBS.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;

/**
 * A REST controller class for user CRUD operations
 *
 * @author Gabe Weaver
 */
@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * A client for communicating with ElasticSearch
     */
    @Autowired
    ElasticsearchClient client;

    /**
     * User GET endpoint using the path "/api/user/{id}"
     *
     * @param id the id of the user to get
     * @return the user with the associated ID
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        GetResponse<User> response = null;

        try {
            response = client.get(g -> g
                    .index("users")
                    .id(id.toLowerCase()),
                    User.class
            );
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User u = response.source();
        if (u == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // No user in Elasticsearch.

        User res = new User(u.getId(), u.getName(), u.getDob(), u.getGender(), u.getAddr());
        for (Policy p : u.getPolicies()) {
            res.addPolicy(new Policy(p.getId(), p.getYear(), p.isActive(), p.getSubscriberId(), p.getDependents(),null));
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/populate")
    public ResponseEntity<String> populate() {
        Address address = new Address("city", "state", "address", "zip");

        // Jacob w/ 2 dependents
        User jacob = new User("INTJ123456789", "Jacob Johnson", LocalDate.of(1983, 1, 31), "Male", address);
        User joseph = new User("ESTP987654321", "Joseph Johnson", LocalDate.of(2004, 1, 1), "Male", address);
        User zander = new User("ISTP112233445", "Zander Johnson", LocalDate.of(2006, 1, 1), "Male", address);

        // **FIRST POLICY**

        // Policy for Jacob + Dependent
        Policy policy = new Policy("NCSU123456789", Year.of(2022), true, jacob.getId(), null, null);
        policy.addDependent(joseph);
        policy.addDependent(zander);

        // Policy Documents
        policy.addDocument(new Document("id-1", "Explanation of Benefits", "NCSU123456789", "INTJ123456789", LocalDate.of(2022,12,31), DocType.EOB));
        policy.addDocument(new Document("id-2", "Summary of Benefits & Coverage (2022)", "NCSU123456789", "INTJ123456789", LocalDate.of(2022,12,13), DocType.COVERAGE));
        policy.addDocument(new Document("id-3", "Blue Cross NC Insurance-ID Cards (2022)", "NCSU123456789", "INTJ123456789", LocalDate.of(2022,8,12), DocType.LETTER));
        policy.addDocument(new Document("id-4", "Plan Renewal - Rate Notice (2022)", "NCSU123456789", "INTJ123456789", LocalDate.of(2022,10,11), DocType.ENROLLMENT));
        policy.addDocument(new Document("id-5", "Plan Bill - (Medical) (January)", "NCSU123456789", "ESTP987654321", LocalDate.of(2022,12,28), DocType.BILL));
        policy.addDocument(new Document("id-6", "Important Coverage Notice: Prompt Pay", "NCSU123456789", "ESTP987654321", LocalDate.of(2022,4,1), DocType.LETTER));
        policy.addDocument(new Document("id-7", "Explanation of Benefits", "NCSU123456789", "ESTP987654321", LocalDate.of(2022,11,30), DocType.EOB));
        policy.addDocument(new Document("id-8", "Blue Cross NC Insurance - ID Cards", "NCSU123456789", "ESTP987654321", LocalDate.of(2022,11,21), DocType.ID_CARD));
        policy.addDocument(new Document("id-9", "Plan Renewal - Rate Notice (2022)", "NCSU123456789", "ISTP112233445", LocalDate.of(2022,11,18), DocType.ENROLLMENT));
        policy.addDocument(new Document("id-10", "Plan Bill - (Medical) (October)", "NCSU123456789", "ISTP112233445", LocalDate.of(2022,9,8), DocType.BILL));
        policy.addDocument(new Document("id-11", "Plan Bill - (Medical) (June)", "NCSU123456789", "ISTP112233445", LocalDate.of(2022,5,12), DocType.BILL));
        policy.addDocument(new Document("id-12", "Summary of Benefits & Coverage (2022)", "NCSU123456789", "ISTP112233445", LocalDate.of(2022,12,11), DocType.COVERAGE));
        policy.addDocument(new Document("id-13", "Explanation of Benefits", "NCSU123456789", "ISTP112233445", LocalDate.of(2022,12,15), DocType.EOB));

        // Add policy to Jacob + Dependents
        jacob.addPolicy(policy);
        joseph.addPolicy(policy);
        zander.addPolicy(policy);

        // **SECOND POLICY**

        // Jacob's 3rd Dependent under a policy last last year.
        User jose = new User("ENTP123454321", "Jose Garcia", LocalDate.of(1999, 10, 10), "Male", address);
        Policy pLastYear = new Policy("LAST432190491", Year.of(2021), false, jacob.getId(), null, null);
        pLastYear.addDependent(jose);

        // Last Year's Policy Documents
        pLastYear.addDocument(new Document("id-27", "Explanation of Benefits", "LAST432190491", "INTJ123456789", LocalDate.of(2021,12,31), DocType.EOB));
        pLastYear.addDocument(new Document("id-28", "Summary of Benefits & Coverage (2022)", "LAST432190491", "INTJ123456789", LocalDate.of(2021,12,13), DocType.COVERAGE));
        pLastYear.addDocument(new Document("id-29", "Blue Cross NC Insurance-ID Cards (2022)", "LAST432190491", "INTJ123456789", LocalDate.of(2021,8,12), DocType.LETTER));
        pLastYear.addDocument(new Document("id-30", "Plan Renewal - Rate Notice (2022)", "LAST432190491", "ENTP123454321", LocalDate.of(2021,10,11), DocType.ENROLLMENT));
        pLastYear.addDocument(new Document("id-31", "Plan Bill - (Medical) (January)", "LAST432190491", "ENTP123454321", LocalDate.of(2021,12,28), DocType.BILL));
        pLastYear.addDocument(new Document("id-32", "Important Coverage Notice: Prompt Pay", "LAST432190491", "ENTP123454321", LocalDate.of(2021,4,1), DocType.LETTER));

        // Add Policy to Jacob + Jose.
        jacob.addPolicy(pLastYear);
        jose.addPolicy(pLastYear);

        // Pagliacci Clown
        User clown = new User("ISFJ998877665", "Pagliacci Clown", LocalDate.of(1977, 1, 1), "Male", address);
        Policy pClown = new Policy("ABLE987654321", Year.of(2022),true, clown.getId(), null, null);

        // Pagliacci Documents
        pClown.addDocument(new Document("id-14", "Summary of Benefits & Coverage (2023)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022,12,31), DocType.COVERAGE));
        pClown.addDocument(new Document("id-15", "ID Card", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 8, 1), DocType.ID_CARD));
        pClown.addDocument(new Document("id-16", "Enrollment", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 10, 1), DocType.ENROLLMENT));
        pClown.addDocument(new Document("id-17", "Plan Bill - (Medical) (February)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 2, 15), DocType.BILL));
        pClown.addDocument(new Document("id-18", "Plan Bill - (Medical) (April)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 4, 15), DocType.BILL));
        pClown.addDocument(new Document("id-19", "Plan Bill - (Medical) (June)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 6, 10), DocType.BILL));
        pClown.addDocument(new Document("id-20", "Plan Bill - (Medical) (June)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 6, 15), DocType.BILL));
        pClown.addDocument(new Document("id-21", "Enrollment", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 11, 30), DocType.ENROLLMENT));
        pClown.addDocument(new Document("id-22", "Plan Bill - (Medical) (September)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 9, 1), DocType.BILL));
        pClown.addDocument(new Document("id-23", "Plan Bill - (Medical) (July)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 7, 31), DocType.BILL));
        pClown.addDocument(new Document("id-24", "Plan Bill - (Medical) (January)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 1, 1), DocType.BILL));
        pClown.addDocument(new Document("id-25", "Plan Bill - (Medical) (November)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 11, 30), DocType.BILL));
        pClown.addDocument(new Document("id-26", "Summary of Benefits and Coverage (2022)", "ABLE987654321", "ISFJ998877665", LocalDate.of(2022, 12, 1), DocType.COVERAGE));

        // Add policy to clown
        clown.addPolicy(pClown);

        // POST Users
        try {
            // Jacob (40)
            client.index(i -> i
                    .index("users")
                    .id(jacob.getId().toLowerCase())
                    .document(jacob)
            );

            // Joseph (19)
            client.index(i -> i
                    .index("users")
                    .id(joseph.getId().toLowerCase())
                    .document(joseph)
            );

            // Zander (17)
            client.index(i -> i
                    .index("users")
                    .id(zander.getId().toLowerCase())
                    .document(zander)
            );

            // Jose (23)
            client.index(i -> i
                    .index("users")
                    .id(jose.getId().toLowerCase())
                    .document(jose)
            );

            // Pagliacci
            client.index(i -> i
                    .index("users")
                    .id(clown.getId().toLowerCase())
                    .document(clown)
            );
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to index users", HttpStatus.BAD_REQUEST);
        }
        // HttpHeaders headers = new HttpHeaders();

        // headers.add("Access-Control-Allow-Origin", "*");
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
