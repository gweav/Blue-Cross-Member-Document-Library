package edu.ncsu.csc.BCBS.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the user class
 */
@SpringBootTest
public class UserTest {

    @Test
    public void testUser() {
        User u = new User("uid-01", "Jacob Johnson", LocalDate.now(), "Non-binary", null);

        assertEquals("uid-01", u.getId());
        assertEquals("Jacob Johnson", u.getName());
        assertEquals(LocalDate.now().getYear(), u.getDob().getYear());
        assertEquals("Non-binary", u.getGender());
        assertEquals(0, u.getPolicies().size());

        Policy p = new Policy("pid-01", Year.now(), false, u.getId(), null, null);
        u.addPolicy(p);
        assertEquals(1, u.getPolicies().size());

    }
}
