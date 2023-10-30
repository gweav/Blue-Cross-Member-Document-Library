package edu.ncsu.csc.BCBS.controllers;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import edu.ncsu.csc.BCBS.models.Document;
import edu.ncsu.csc.BCBS.models.Policy;
import edu.ncsu.csc.BCBS.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the UserController class
 *
 * @author Gabe Weaver
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ElasticsearchClient client;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUser() throws Exception {

        System.out.println("\n----testGetUser----\n");

        // Create User

        String str = "First";
        User user = new User("id-1", str, LocalDate.now(), "Male", null);
        Policy policy = new Policy("pid-1", Year.now(), true, user.getId(), new ArrayList<String>(), new ArrayList<Document>());
        user.addPolicy(policy);

        // POST User to index.
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(i -> i
                    .index("users")
                    .id(user.getId())
                    .document(user)
            );
        } catch (Exception e) {
            System.out.println("Elasticsearch Index failed: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
        assertEquals("created", indexResponse.result().jsonValue());
        System.out.println("Index Response: " + indexResponse);

        // GET User from Mock MVC
        this.mockMvc.perform(get("/api/user/id-1")).andDo(print()).andExpect(status().isOk());

        // GET Nonexistent User
        this.mockMvc.perform(get("/api/user/id-56")).andDo(print()).andExpect(status().isNotFound());

        // GET from Elasticsearch client
        GetResponse<User> getResponse = null;
        try {
            getResponse = client.get(g -> g
                            .index("users")
                            .id("id-1"),
                    User.class
            );

            System.out.println("Get Response: " + getResponse.toString());
        } catch (Exception e) {
            System.out.println("Elasticsearch GET failed: " + e.getMessage());
            fail();
        }  

        // Check User's Policy
        User getUser = null;
        if (getResponse.found()) {
            getUser = getResponse.source();
        } else {
            System.out.println("User not found");
            fail();
        }
        assertEquals("pid-1", getUser.getPolicies().get(0).getId());

        // DELETE User
        DeleteResponse deleteResponse = client.delete(d -> d
                .index("users")
                .id("id-1")
        );
        assertEquals("deleted", deleteResponse.result().jsonValue().toString());
        System.out.println("Delete Response: "+ deleteResponse.toString());


        // DELETE Index
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index("users").build();
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);
        System.out.println("Delete Index Response: " + deleteIndexResponse.toString());

    }

    @Test
    public void populateTest() throws Exception {

        System.out.println("----populateTest----");

        // Populates Data
        this.mockMvc.perform(get("/api/populate")).andDo(print()).andExpect(status().isOk());

        System.out.println("Jacob's Documents");

        // Jacob's documents
        this.mockMvc.perform(get("/api/user/intj123456789/documents")).andDo(print()).andExpect(status().isOk());


    }
}
