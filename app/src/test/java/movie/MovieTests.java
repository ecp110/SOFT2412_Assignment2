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

        String returned = "Name: " + movie1.getTitle() + "Run time: " + movie1.getRunTime() + "Rating: " + movie1.getClassification();

        assertEquals(movie1.toString(), returned);

    }

}