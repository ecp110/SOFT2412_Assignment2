package movie;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminPageTests {

    @Test
    public void displayInitialTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();

        AdminPage aPage = new AdminPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            adminPagePath,
            new User("user", "user", "admin"));
        String assertString = "Greetings,\n\nWhat would you like to do today?\n\n(1) Display booking log\n(2) Edit movie information\n(3) View cancellations\n(4) Add/remove accounts\n\nType \"c\" to cancel.";

        assertNotNull(aPage.displayInitial());
    }

    @Test
    public void displayBookingLogPromptTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();

        AdminPage aPage = new AdminPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            adminPagePath,
            new User("user", "user", "admin"));
        String assertString = "What location would you like to see bookings for?\n(1) George St\n(2) Chatswood\n(3) Bondi\n(4) Hurstville\n";

        assertEquals(aPage.displayBookingLogPrompt(), assertString);
    }


    @Test
    public void displayMovieEditPromptTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();

        AdminPage aPage = new AdminPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            adminPagePath,
            new User("user", "user", "admin"));
        String assertString = "Please type below the ID of the movie you wish to edit:\n";

        assertEquals(aPage.displayMovieEditPrompt(), assertString);
    }

    @Test
    public void displayFoundEditableMovieTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();

        AdminPage aPage = new AdminPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            adminPagePath,
            new User("user", "user", "admin"));
        String assertString = "This is the selected movie to be edited:\n";

        assertEquals(aPage.displayFoundEditableMovie(), assertString);
    }

    @Test
    public void displayEditStringPromptTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();

        AdminPage aPage = new AdminPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            adminPagePath,
            new User("user", "user", "admin"));
        String assertString = "Please fill out the following fields as they are displayed. If you would like to keep any of the data the same as currently stored, just leave the field blank.\n";

        assertEquals(aPage.displayEditStringPrompt(), assertString);
    }

    @Test
    public void displayCompletedEditTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();

        AdminPage aPage = new AdminPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            adminPagePath,
            new User("user", "user", "staff"));
        String assertString = "Thank you! The edit has been complete and is now in the database.";

        assertNotNull(aPage.displayCompletedEdit());
    }

    @Test
    public void editMovieTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();

        AdminPage aPage = new AdminPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            adminPagePath,
            new User("user", "user", "admin"));
        Movie movie = new Movie(
            "Im now changing this title for the 2nd time",
            "This is the second edit",
            420,
            "R",
            null,
            "Ethan Edited Proutt",
            "666",
            null
        );

        String editString = "Test Edit,,G,,,,,This is edited by the testing function";
        aPage.editMovie(movie, editString);
        aPage.parseMovies();
        for (Movie m : aPage.getMovies()) {
            if (m.getID().equals(movie.getID())) {
                assertEquals(m.getTitle(), "Test Edit");
                assertEquals(m.getSynopsis(), "This is edited by the testing function");
                assertEquals(m.getRunTime(), 420);
                assertEquals(m.getClassification(), "R");
                assertEquals(m.getDirector(), "Ethan Edited Proutt");
                assertEquals(m.getID(), "666");
                break;
            }
        }
    }

    @Test
    public void cancelTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();

        AdminPage aPage = new AdminPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            adminPagePath,
            new User("user", "user", "admin"));
        String homePageString = "Hello! Welcome to XYZ Cinemas! We hope you are having a great day.\nIf you would like to log-in, just type \"y\".\nIf you would like to register, just type \"r\".\nIf you would like to continue as a guest, type \"g\".\n\nRemember, if at any point in your process you would like to cancel and return to this main page, just type \"c\" and hit enter.";

        HomePage retPage = aPage.cancel();

        assertNotNull(retPage.displayInitial());
        assertNotNull(aPage.getUsers());
    }
}