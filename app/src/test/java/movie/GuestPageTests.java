package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GuestPageTests {

    @Test
    public void displayInitialTest() {
        GuestPage gPage = new GuestPage(
            "app/src/main/java/movie/Databases/Movies.json", 
            "app/src/main/java/movie/Databases/Locations.json", 
            "app/src/main/java/movie/Databases/credit_cards.json", 
            "app/src/main/java/movie/Databases/gift_cards.json", 
            "app/src/main/java/movie/Databases/members.json", 
            "app/src/main/java/movie/pages/guest/"
            );
        String assertString = "GUEST OUTPUT";

        assertEquals(gPage.displayInitial(), assertString);
    }

    @Test
    public void cancelTest() {
        GuestPage gPage = new GuestPage(
            "app/src/main/java/movie/Databases/Movies.json", 
            "app/src/main/java/movie/Databases/Locations.json", 
            "app/src/main/java/movie/Databases/credit_cards.json", 
            "app/src/main/java/movie/Databases/gift_cards.json", 
            "app/src/main/java/movie/Databases/members.json", 
            "app/src/main/java/movie/pages/guest/"
            );
        String homePageString = "Hello! Welcome to XYZ Cinemas! We hope you are having a great day.\nIf you would like to log-in, just type \"y\".\nIf you would like to register, just type \"r\".\nIf you would like to continue as a guest, type \"g\".\n\nRemember, if at any point in your process you would like to cancel and return to this main page, just type \"c\" and hit enter.";

        HomePage retPage = gPage.cancel();

        assertEquals(retPage.displayInitial(), homePageString);
        assertNull(gPage.getUsers());
    }

    @Test
    public void registerTest() {
        GuestPage gPage = new GuestPage(
            "app/src/main/java/movie/Databases/Movies.json", 
            "app/src/main/java/movie/Databases/Locations.json", 
            "app/src/main/java/movie/Databases/credit_cards.json", 
            "app/src/main/java/movie/Databases/gift_cards.json", 
            "app/src/main/java/movie/Databases/members.json", 
            "app/src/main/java/movie/pages/guest/"
            );

        // Positive case
        assertEquals(gPage.register("KanyeWest123", "Smithy123"), 1);
        for (User user : gPage.getUsers()) {
            if (user.getName() == "KanyeWest123") {
                assertEquals(user.getPassword(), "Smith123");
                break;
            }
        }

        // Negative case (already exists)
        assertEquals(gPage.register("Charles", "first2345"), 0);

        // Edge case
        assertEquals(gPage.register(null, null), -1);
        assertEquals(gPage.register("", null), -1);
        assertEquals(gPage.register(null, ""), -1);
        assertEquals(gPage.register("", ""), -1);
        
        //TODO : ADD TO DATABASE
    }
}