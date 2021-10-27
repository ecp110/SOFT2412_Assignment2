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
        String assertString = "Greetings, user!\n\nWhat would you like to do today?\n(1) Display booking log\n(2) Edit movie information\n(3) ASD";

        assertEquals(aPage.displayInitial(), assertString);
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
        String assertString = "What location would you like to see bookings for?\n(1) George St\n(2) Chatswood\n(3) Bondi\n(4) Hurstville";

        assertEquals(aPage.displayBookingLogPrompt(), assertString);
    }

    @Test
    public void displayBookingLogTest() {
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
        String bondiString = "Displaying results for: Bondi\n\n634 (Madagascar 3: Europe's Most Wanted) | Stacey booked for 3 in the Morning with card 12345 in the Gold screen.\n000 (Gladiator) | Jimmy booked for 3 in the Midday with card 12345 in the Bronze screen.\n234 (John Wick) | Alex booked for 3 in the Evening with card 12345 in the Silver screen.\n999 (The Wolf of Wall Street) | Charles booked for 3 in the Midday with card 12345 in the Gold screen.\n394 (How to Train your Dragon) | Ethan booked for 3 in the Evening with card 12345 in the Gold screen.\n243 (Star Wars: A New Hope) | Scrum Master booked for 3 in the Morning with card 12345 in the Gold screen.";
        String gsString = "Displaying results for: George Street\n\n634 (Madagascar 3: Europe's Most Wanted) | Stacey booked for 1 in the Morning with card 12345 in the Gold screen.\n000 (Gladiator) | Jimmy booked for 1 in the Midday with card 12345 in the Bronze screen.\n234 (John Wick) | Alex booked for 1 in the Evening with card 12345 in the Silver screen.\n999 (The Wolf of Wall Street) | Charles booked for 1 in the Midday with card 12345 in the Gold screen.\n394 (How to Train your Dragon) | Ethan booked for 1 in the Evening with card 12345 in the Gold screen.\n243 (Star Wars: A New Hope) | Scrum Master booked for 1 in the Morning with card 12345 in the Gold screen.";
        String chattyString = "Displaying results for: Chatswood\n\n634 (Madagascar 3: Europe's Most Wanted) | Stacey booked for 3 in the Morning with card 12345 in the Gold screen.\n000 (Gladiator) | Jimmy booked for 3 in the Midday with card 12345 in the Bronze screen.\n234 (John Wick) | Alex booked for 2 in the Evening with card 12345 in the Silver screen.\n999 (The Wolf of Wall Street) | Charles booked for 2 in the Midday with card 12345 in the Gold screen.\n394 (How to Train your Dragon) | Ethan booked for 2 in the Evening with card 12345 in the Gold screen.\n243 (Star Wars: A New Hope) | Scrum Master booked for 2 in the Morning with card 12345 in the Gold screen.";
        String hustvilleString = "Displaying results for: Hurstville\n\n634 (Madagascar 3: Europe's Most Wanted) | Stacey booked for 3 in the Morning with card 12345 in the Gold screen.\n000 (Gladiator) | Jimmy booked for 3 in the Midday with card 12345 in the Bronze screen.\n234 (John Wick) | Alex booked for 3 in the Evening with card 12345 in the Silver screen.\n999 (The Wolf of Wall Street) | Charles booked for 3 in the Midday with card 12345 in the Gold screen.\n394 (How to Train your Dragon) | Ethan booked for 4 in the Evening with card 12345 in the Gold screen.\n243 (Star Wars: A New Hope) | Scrum Master booked for 4 in the Morning with card 12345 in the Gold screen.";

        assertEquals(aPage.displayBookingReciepts("Bondi"), bondiString);
        assertEquals(aPage.displayBookingReciepts("George Street"), gsString);
        assertEquals(aPage.displayBookingReciepts("Chatswood"), chattyString);
        assertEquals(aPage.displayBookingReciepts("Hurstville"), hustvilleString);

        assertNull(aPage.displayBookingReciepts(""));
        assertNull(aPage.displayBookingReciepts(null));
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
            new User("user", "user", "admin"));
        String assertString = "Thank you! The edit has been complete and is now in the database.";

        assertEquals(aPage.displayCompletedEdit(), assertString);
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

        assertEquals(retPage.displayInitial(), homePageString);
        assertNull(aPage.getUsers());
    }
}