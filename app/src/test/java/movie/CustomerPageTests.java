package movie;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerPageTests {

    @Test
    public void displayInitialTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String customerPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "customer").toString();
        
        CustomerPage cPage = new CustomerPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            customerPagePath,
            new User("user", "user", "customer"));
        String assertString = "Welcome back,\n\nWe hope that you can enjoy one of the listed movies below today! \n\nIf you\'d like to select a movie, just type the number that is next to that movie listing.\n\nIf you\'d like to filter the movies, press the \'f\' key and hit enter, and you\'ll be prompted to enter either a cinema or movie.";

        assertNotNull(cPage.displayInitial());
    }

    @Test
    public void cancelTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String customerPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "customer").toString();

        CustomerPage cPage = new CustomerPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            customerPagePath,
            new User("user", "user", "customer"));
        String homePageString = "Hello! Welcome to XYZ Cinemas! We hope you are having a great day.\nIf you would like to log-in, just type \"y\".\nIf you would like to register, just type \"r\".\nIf you would like to continue as a guest, type \"g\".\n\nRemember, if at any point in your process you would like to cancel and return to this main page, just type \"c\" and hit enter.";

        HomePage retPage = cPage.cancel();

        assertNotNull(retPage.cancel());
    }

    @Test
    public void testDisplayBookingConfirmation(){

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String customerPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "customer").toString();

        CustomerPage cPage = new CustomerPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            customerPagePath,
            new User("user", "user", "customer"));

        assertNotNull(cPage.displayBookingConfirmation("test"));

    }

}