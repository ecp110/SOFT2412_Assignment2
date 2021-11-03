package movie;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class PageTests{

    public Path currentPath = Paths.get(System.getProperty("user.dir"));
    public String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
    public String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
    public String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
    public String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
    public String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
    public String guestPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "guest").toString();

    @Test
    public void testGetMovies(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        gPage.parseAll();

        assertNotNull(gPage.getMovies());
    }

    @Test
    public void testGetUsers(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        gPage.parseAll();

        assertNotNull(gPage.getUsers());
    }

    @Test
    public void testAddUser(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        User user1 = new User("user1", "user1", "customer");

        gPage.addUser(user1);
        assertTrue(gPage.getUsers().contains(user1));

    }

    @Test
    public void testgetUserByUsername1(){ // positive
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        User user1 = new User("user1", "user1", "customer");

        gPage.addUser(user1);
        assertEquals(gPage.getUserByUsername("user1"), "user1");

    }

    @Test
    public void testgetUserByUsername2(){ // negative
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        
        assertNull(gPage.getUserByUsername("burbvrwefo"));

    }

    @Test
    public void testgetStaffByUsername1(){ // positive
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        User user1 = new User("user1", "user1", "staff");

        gPage.addUser(user1);
        assertEquals(gPage.getStaffByUsername("user1"), "user1");

    }

    @Test
    public void testgetStaffByUsername2(){ // negative
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        
        assertNull(gPage.getStaffByUsername("burbvrwefo"));

    }

    @Test
    public void testRemoveUser1(){ // positive

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        User user1 = new User("user1", "user1", "customer");

        gPage.addUser(user1);

        gPage.removeUser("user1");

        assertTrue(gPage.users.contains(user1));
    }

    @Test
    public void testRemoveUser2(){ // negative

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        int sizeBeforeRemoval = gPage.getUsers().size();

        gPage.removeUser("ungiwjfe");
        assertEquals(sizeBeforeRemoval, gPage.getUsers().size());
    }

    @Test
    public void testGetScreenByNum(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.getScreenByNum("0"), "Bronze Screen");
        assertEquals(gPage.getScreenByNum("1"), "Silver Screen");
        assertEquals(gPage.getScreenByNum("2"), "Gold Screen");
        assertEquals(gPage.getScreenByNum("7"), "invalid screen number"); // negative
        assertEquals(gPage.getScreenByNum(""), "invalid screen number"); //edge
    }

    @Test
    public void testGetMovieTime(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.getMovieTime("0"), "morning");
        assertEquals(gPage.getMovieTime("1"), "midday");
        assertEquals(gPage.getMovieTime("2"), "evening");
        assertEquals(gPage.getMovieTime("7"), "invalid movie time"); // negative
        assertEquals(gPage.getMovieTime(""), "invalid movie time"); //edge
    }



    @Test
    public void testGetMovieById(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.getMovieById("000").getTitle(), "Gladiator"); //positive
        assertNull(gPage.getMovieById("277")); //negative
        assertNull(gPage.getMovieById("")); //edge
    }

    @Test
    public void testFindMovieId(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.findMovieId("Gladiator"), "000");
    }



    @Test
    public void testRemoveMovie(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        Calendar cal = new Calendar(1, 1, 2021);
        ArrayList<String> cast = new ArrayList<String>();
        Movie movie1 = new Movie("movie", "yeet420", 69, "PG", cast, "peter", "1234", cal);

        assertFalse(gPage.removeMovie(movie1));
    }

    @Test
    public void testStoreViewings(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertNotNull(gPage.storeViewings());
    }

    @Test
    public void testStoreCinemas(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertNotNull(gPage.storeCinemas());
    }


    @Test
    public void testFilterViewings(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertNotNull(gPage.filterViewings("Bondi", "000", 0, 0, "Monday"));
    }




}