package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerPageTests {

    @Test
    public void displayInitialTest() {
        CustomerPage cPage = new CustomerPage(
            "app/src/main/java/movie/Databases/Movies.json",
            "app/src/main/java/movie/Databases/Locations.json", 
            "app/src/main/java/movie/Databases/credit_cards.json", 
            "app/src/main/java/movie/Databases/gift_cards.json", 
            "app/src/main/java/movie/Databases/members.json", 
            "app/src/main/java/movie/pages/customer",
            new User("user", "user", "customer"));
        String assertString = "Welcome back, user!\n\nWe hope that you can enjoy one of the listed movies below today! \n\nIf you\'d like to select a movie, just type the number that is next to that movie listing.\n\nIf you\'d like to filter the movies, press the \'f\' key and hit enter, and you\'ll be prompted to enter either a cinema or movie.";

        assertEquals(cPage.displayInitial(), assertString);
    }

    @Test
    public void cancelTest() {
        CustomerPage cPage = new CustomerPage(
            "app/src/main/java/movie/Databases/Movies.json",
            "app/src/main/java/movie/Databases/Locations.json", 
            "app/src/main/java/movie/Databases/credit_cards.json", 
            "app/src/main/java/movie/Databases/gift_cards.json", 
            "app/src/main/java/movie/Databases/members.json", 
            "app/src/main/java/movie/pages/customer/",
            new User("user", "user", "customer"));
        String homePageString = "Hello! Welcome to XYZ Cinemas! We hope you are having a great day.\nIf you would like to log-in, just type \"y\".\nIf you would like to register, just type \"r\".\nIf you would like to continue as a guest, type \"g\".\n\nRemember, if at any point in your process you would like to cancel and return to this main page, just type \"c\" and hit enter.";

        HomePage retPage = cPage.cancel();

        assertEquals(retPage.displayInitial(), homePageString);
        assertNull(cPage.getUsers());
    }

    @Test
    public void listMovesTests() {
        //TODO
    }
}