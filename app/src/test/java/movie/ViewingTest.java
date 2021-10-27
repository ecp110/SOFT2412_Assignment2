package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class ViewingTest{

    @Test
    public void testGetMovie(){
        ArrayList<String> cast = new ArrayList<String>();
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie = new Movie("movie", "yeet420", 70, "PG", cast, "peter", "1234", cal);

        Viewing test = new Viewing(movie, 0, 0, "Monday");

        assertEquals(test.getMovie(), movie);
    }

    @Test
    public void testGetScreenType(){
        ArrayList<String> cast = new ArrayList<String>();
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie = new Movie("movie", "yeet420", 70, "PG", cast, "peter", "1234", cal);

        Viewing test = new Viewing(movie, 0, 0, "Monday");

        assertEquals(test.getScreenType(), 0);
    }

    @Test
    public void testGetScreenName(){
        ArrayList<String> cast = new ArrayList<String>();
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie = new Movie("movie", "yeet420", 70, "PG", cast, "peter", "1234", cal);

        Viewing test = new Viewing(movie, 0, 0, "Monday");
        assertEquals(test.getScreenName(), "Bronze");

        Viewing test2 = new Viewing(movie, 1, 0, "Monday");
        assertEquals(test2.getScreenName(), "Silver");

        Viewing test3 = new Viewing(movie, 2, 0, "Monday");
        assertEquals(test3.getScreenName(), "Gold");

        Viewing test4 = new Viewing(movie, -1, 0, "Monday");
        assertEquals(test4.getScreenName(), null);
    }

    @Test
    public void testGetTimeOfDay(){
        ArrayList<String> cast = new ArrayList<String>();
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie = new Movie("movie", "yeet420", 70, "PG", cast, "peter", "1234", cal);

        Viewing test = new Viewing(movie, 0, 0, "Monday");

        assertEquals(test.getTimeOfDay(), 0);
    }

    @Test
    public void testGetTimeOfDayName(){
        ArrayList<String> cast = new ArrayList<String>();
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie = new Movie("movie", "yeet420", 70, "PG", cast, "peter", "1234", cal);

        Viewing test = new Viewing(movie, 0, 0, "Monday");
        assertEquals(test.getTimeOfDayName(), "Morning");

        Viewing test2 = new Viewing(movie, 0, 1, "Monday");
        assertEquals(test2.getTimeOfDayName(), "Midday");

        Viewing test3 = new Viewing(movie, 0, 2, "Monday");
        assertEquals(test3.getTimeOfDayName(), "Evening");

        Viewing test4 = new Viewing(movie, 0, -1, "Monday");
        assertEquals(test4.getTimeOfDayName(), null);
        
    }

    @Test
    public void testGetDay(){
        ArrayList<String> cast = new ArrayList<String>();
        Calendar cal = new Calendar(1, 1, 2021);
        Movie movie = new Movie("movie", "yeet420", 70, "PG", cast, "peter", "1234", cal);

        Viewing test = new Viewing(movie, 0, 0, "Monday");

        assertEquals(test.getDay(), "Monday");
    }
}