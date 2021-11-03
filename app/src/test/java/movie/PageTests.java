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
    public void testConvertScreenToNum(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.convertScreenToNum("bronze"), "0");
        assertEquals(gPage.convertScreenToNum("silver"), "1");
        assertEquals(gPage.convertScreenToNum("gold"), "2");
        assertEquals(gPage.convertScreenToNum("yeet"), "invalid screen name"); // negative
        assertEquals(gPage.convertScreenToNum(""), "invalid screen name"); // edge
    }

    @Test
    public void testConvertLocationFormat(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.convertLocationFormat("bondi"), "Bondi");
        assertEquals(gPage.convertLocationFormat("hurstville"), "Hurstville");
        assertEquals(gPage.convertLocationFormat("george street"), "George Street");
        assertEquals(gPage.convertLocationFormat("chatswood"), "Chatswood");
        assertEquals(gPage.convertLocationFormat("Mosman"), "invalid location name:Mosman"); // negative
        assertEquals(gPage.convertLocationFormat(""), "invalid location name:"); // edge

    }

    @Test
    public void testFormatFilteredMovie(){
        Calendar cal = new Calendar(1, 1, 2021);
        ArrayList<String> cast = new ArrayList<String>();
        Movie movie1 = new Movie("movie", "12", 12, "PG", cast, "peter", "1234", cal);

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.formatFilteredMovie(movie1, "Bronze", "Morning"), "(1234);movie;(peter);Run Time: 12;Rating: PG\n;Bronze > ;Morning\n");
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
    public void testUnformatHashMapValue(){

        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.unformatHashMapValue("hello;world"), "hello world");
    }

    @Test
    public void testFindAllMoviesGivenLocation(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.findAllMoviesGivenLocation("Bondi").get("location"), "Bondi");
        assertNotNull(gPage.findAllMoviesGivenLocation("Bondi").get("1"));

        assertNull(gPage.findAllMoviesGivenLocation("Mosman").get("1")); // negative
    }

    @Test
    public void testFindAllMoviesGivenLocationAndScreen(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.findAllMoviesGivenLocationAndScreen("Bondi", "0").get("location"), "Bondi");
        assertNotNull(gPage.findAllMoviesGivenLocationAndScreen("Bondi", "0").get("1"));

        assertNull(gPage.findAllMoviesGivenLocationAndScreen("Bondi", "7").get("1")); // negative
    }

    @Test
    public void testFindAllMoviesGivenLocationAndMovie(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.findAllMoviesGivenLocationAndMovie("000","Bondi").get("location"), "Bondi");
        assertNotNull(gPage.findAllMoviesGivenLocationAndMovie("000","Bondi").get("1"));

        assertNull(gPage.findAllMoviesGivenLocationAndMovie("0182323", "Bondi").get("1")); // negative
    }

    @Test
    public void testFindAllMoviesGivenAllFilters(){
        GuestPage gPage = new GuestPage(
            movieLocationPath,
            cinemasLocationPath,
            creditCardLocationPath,
            giftCardLocationPath,
            usersLocationPath,
            guestPagePath
            );

        assertEquals(gPage.findAllMoviesGivenAllFilters("000","Bondi", "0").get("location"), "Bondi");
        assertNotNull(gPage.findAllMoviesGivenAllFilters("000","Bondi", "1").get("1"));

        gPage.filterMovies("000", "Bondi", "0");
        gPage.filterMovies("000", "Bondi", "");
        gPage.filterMovies("", "Bondi", "0");

        assertNull(gPage.findAllMoviesGivenAllFilters("0182323", "Bondi", "0").get("1")); // negative
        assertNull(gPage.findAllMoviesGivenAllFilters("000", "Bondi", "7").get("1")); // negative
    }


    




}