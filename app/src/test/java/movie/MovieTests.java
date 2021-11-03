package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class MovieTests {
    
    @Test
    public void validInsertActor(){

        ArrayList<String> cast = new ArrayList<String>();
        ArrayList<String> cast2 = new ArrayList<String>();
        cast2.add("David");

        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "yeet420", 69, "PG", cast, "peter", "1234", cal);
        Movie movie2 = new Movie("movie2", "yeet69", 69, "PG", cast2, "peter", "1234", cal);

        movie1.insertActor("Daniel");
        movie2.insertActor("Goliath");
    
        ArrayList<String> test = new ArrayList<String>();
        test.add("Daniel");

        ArrayList<String> test2 = new ArrayList<String>();
        test2.add("David");
        test2.add("Goliath");

        assertEquals(movie1.getCast(), test);
        assertEquals(movie2.getCast(), test2);

        assertEquals(movie1.insertActor("Eddie"), "Actor added to cast!");
        assertEquals(movie2.insertActor("Ethan"), "Actor added to cast!");
    }

    @Test 
    public void InvalidInsertActor(){

        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "yeet420", 69, "PG", cast, "peter", "1234", cal);


        assertEquals(movie1.insertActor("Daniel"), "Error! This actor is already included in the movie cast");
    }

    @Test
    public void validAppendSynopsis(){

        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "69", 69, "PG", cast, "peter", "1234", cal);

        movie1.appendSynopsis("is a good number");

        assertEquals(movie1.getSynopsis(), "69 is a good number");
    }

    @Test
    public void validToString(){

        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "69", 69, "PG", cast, "peter", "1234", cal);

        String returned = "(" + movie1.getID() + ") > " + movie1.getTitle() + " (" + movie1.getDirector() + "). Run time: " + movie1.getRunTime() + ". Rating: " + movie1.getClassification();

        assertEquals(movie1.toString(), returned);

    }

    @Test
    public void getDirector(){
        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "69", 69, "PG", cast, "peter", "1234", cal);

        assertEquals(movie1.getDirector(), "peter");
    }

    @Test
    public void testGetID(){

        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "69", 69, "PG", cast, "peter", "1234", cal);

        assertEquals(movie1.getID(), "1234");
    }

    @Test
    public void testGetReleaseDate(){

        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "69", 69, "PG", cast, "peter", "1234", cal);

        assertEquals(movie1.getReleaseDate(), cal);
    }

    @Test
    public void testSetCast(){

        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "69", 69, "PG", cast, "peter", "1234", cal);

        assertEquals(movie1.getCast(), cast);

        ArrayList<String> cast2 = new ArrayList<String>();
        movie1.setCast(cast2);
        assertEquals(movie1.getCast(), cast2);
    }

    @Test
    public void testSetClassification(){

        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "69", 69, "PG", cast, "peter", "1234", cal);

        assertEquals(movie1.getClassification(), "PG");

        ArrayList<String> cast2 = new ArrayList<String>();
        movie1.setClassification("M");
        assertEquals(movie1.getClassification(), "M");
    }

    @Test
    public void testSetters(){
        ArrayList<String> cast = new ArrayList<String>();
        cast.add("Daniel");
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie1 = new Movie("movie", "69", 69, "PG", cast, "peter", "1234", cal);

        movie1.setDirector("Pierre");
        movie1.setID("50");
        Calendar cal2 = new Calendar(2, 1, 2021);
        movie1.setReleaseDate(cal2);
        movie1.setRunTime(50);
        movie1.setSynopsis("50");
        movie1.setTitle("test");

        assertEquals(movie1.getDirector(), "Pierre");
        assertEquals(movie1.getID(), "50");
        assertEquals(movie1.getReleaseDate(), cal2);
        assertEquals(movie1.getRunTime(), 50);
        assertEquals(movie1.getSynopsis(), "50");
        assertEquals(movie1.getTitle(), "test");
    }
    

}