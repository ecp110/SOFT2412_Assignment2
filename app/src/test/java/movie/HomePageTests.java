package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HomePageTests {

    @Test
    public void displayInitialTest() {
        HomePage hPage = new HomePage("app/src/main/java/movie/Databases/Movies.json", "app/src/main/java/movie/Databases/Locations.json", "app/src/main/java/movie/Databases/credit_cards.json", "app/src/main/java/movie/Databases/gift_cards.json", "app/src/main/java/movie/Databases/members.json");
        String homePageString = "Hello! Welcome to XYZ Cinemas! We hope you are having a great day.\nIf you would like to log-in, just type \"y\".\nIf you would like to register, just type \"r\".\nIf you would like to continue as a guest, type \"g\".\n\nRemember, if at any point in your process you would like to cancel and return to this main page, just type \"c\" and hit enter."

        assertEquals(hPage.displayInitial(), homePageString);
    }

    @Test
    public void displayLogInTest() {
        HomePage hPage = new HomePage("app/src/main/java/movie/Databases/Movies.json", "app/src/main/java/movie/Databases/Locations.json", "app/src/main/java/movie/Databases/credit_cards.json", "app/src/main/java/movie/Databases/gift_cards.json", "app/src/main/java/movie/Databases/members.json");
        
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
    }

    @Test
    public void logInTest() {
        HomePage hPage = new HomePage("app/src/main/java/movie/Databases/Movies.json", "app/src/main/java/movie/Databases/Locations.json", "app/src/main/java/movie/Databases/credit_cards.json", "app/src/main/java/movie/Databases/gift_cards.json", "app/src/main/java/movie/Databases/members.json");
        
        User user1 = hPage.logIn("Charles", "first2345"); // manager
        User user2 = hPage.logIn("Sergio", "it234"); // staff
        User user3 = hPage.logIn("Vincent", "viral346"); // customer
        User user4 = hPage.logIn("Kanye West", "JIK"); // doesnn't exist

        // Manager
        assertEquals(user1.getName(), "Charles");
        assertEquals(user1.getPassword(), "first2345");
        assertEquals(user1.getType(), "manager");
        assertTrue(user1.isAdmin());

        // Staff
        assertEquals(user2.getName(), "Sergio");
        assertEquals(user2.getPassword(), "it234");
        assertEquals(user2.getType(), "staff");
        assertTrue(user2.isAdmin());

        // Customer
        assertEquals(user3.getName(), "Vincent");
        assertEquals(user3.getPassword(), "viral346");
        assertEquals(user3.getType(), "customer");
        assertFalse(user3.isAdmin());

        // Not exist
        assertNull(user4);
    }
}