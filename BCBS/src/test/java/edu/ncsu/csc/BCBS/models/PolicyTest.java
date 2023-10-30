package edu.ncsu.csc.BCBS.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests the policy class
 */
@SpringBootTest
public class PolicyTest {

    @Test
    public void testPolicySetup() {
        User u = new User("uid-01", "John Cena", LocalDate.now(), "Male", null); // Owner of Policy
        User d = new User("uid-02", "Joey Fawkes", LocalDate.now(), "Male", null); // Dependent
        Policy p = new Policy("pid-01", Year.now(), true, u.getId(), new ArrayList<String>(), new ArrayList<Document>());

        assertEquals("pid-01", p.getId());
        assertEquals(Year.now(), p.getYear());
        assertEquals(true, p.isActive());
        assertEquals(0, p.getDependents().size());
        assertEquals(0, p.getDocuments().size());

        p.addDependent(d);
        p.addDependent(d); // No duplicate dependents
        assertEquals(1, p.getDependents().size());
        p.addDependent(u); // Owner cannot be a dependent of the same plan.
        assertEquals(1, p.getDependents().size());

        // Documents related to policy.
        Document subscriberDoc = new Document("id-01", "Coverage", "pid-01", "uid-01", LocalDate.now(), DocType.COVERAGE); // Subscriber doc.
        Document dependentDoc = new Document("id-02", "Explanation of Benefits", "pid-01", "uid-02", LocalDate.now(), DocType.EOB); // Dependent doc.
        Document unrelatedDoc = new Document("id-03", "Bill", "pid-01", "uid-03", LocalDate.now(), DocType.BILL); // Unrelated doc
        Document expiredDoc = new Document("id-03", "Bill", "pid-01", "uid-03", LocalDate.of(2019, 1, 1), DocType.BILL); // Unrelated doc
        Document nonPolicyDoc = new Document("id-04", "Coverage", "pid-02", "uid-01", LocalDate.now(), DocType.COVERAGE); // Unrelated doc.

        assertFalse(p.addDocument(expiredDoc)); // 2023 != 2019
        assertFalse(p.addDocument(unrelatedDoc)); // uid-03 doesn't exist.
        assertTrue(p.addDocument(dependentDoc)); // uid-02 is uid-01's dependent.
        assertTrue(p.addDocument(subscriberDoc)); // ok
        assertFalse(p.addDocument(subscriberDoc)); // Document already exists.
        assertFalse(p.addDocument(nonPolicyDoc)); // policy-id does not match.
        assertEquals(2, p.getDocuments().size());
    }
}
