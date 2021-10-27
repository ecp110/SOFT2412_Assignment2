package movie;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HomePageTests {

    @Test
    public void displayInitialTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String homePagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "init").toString();
        
        HomePage hPage = new HomePage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            homePagePath
            ); 
        String homePageString = "Hello! Welcome to XYZ Cinemas! We hope you are having a great day.\nIf you would like to log-in, just type \"y\".\nIf you would like to register, just type \"r\".\nIf you would like to continue as a guest, type \"g\".\n\nRemember, if at any point in your process you would like to cancel and return to this main page, just type \"c\" and hit enter.";

        assertEquals(hPage.displayInitial(), homePageString);
    }

    @Test
    public void displayLogInTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String homePagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "init").toString();
        HomePage hPage = new HomePage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            homePagePath
            );
        
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
    }

    @Test
    public void logInTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String homePagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "init").toString();
        HomePage hPage = new HomePage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            homePagePath
            );

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