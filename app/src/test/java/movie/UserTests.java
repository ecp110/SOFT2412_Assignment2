package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {
    @Test
    public void userGetters() {
        User user1 = new User("user1", "user1", "customer");
        User user2 = new User("UsEr2", "USer2", "cUsToMeR");
        User user3 = new User("user3", "user3", "staff");
        User user4 = new User("user4", "user4", "manager");

        // GetName
        assertEquals(user1.getName(), "user1"); // Positive Case
        assertEquals(user2.getName(), "UsEr2"); // Edge Case

        // GetName
        assertEquals(user1.getPassword(), "user1"); // Positive Case
        assertEquals(user2.getPassword(), "USer2"); // Edge Case

        // GetName
        assertEquals(user1.getType(), "customer"); // Positive Case
        assertEquals(user3.getType(), "staff"); // Positive Case
        assertEquals(user4.getType(), "manager"); // Positive Case
        assertEquals(user2.getType(), "cUsToMeRx"); // Edge Case
    }

    @Test
    public void isAdminTest() {
        User user1 = new User("user1", "user1", "customer");
        User user2 = new User("UsEr2", "USer2", "cUsToMeR");
        User user3 = new User("user3", "user3", "staff");
        User user4 = new User("user4", "user4", "manager");

        // Positive cases
        assertTrue(user3.isAdmin());
        assertTrue(user4.isAdmin());

        // Negative cases
        assertFalse(user1.isAdmin());
        assertFalse(user2.isAdmin());
    }
}
