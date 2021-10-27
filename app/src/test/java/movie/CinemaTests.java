package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class CinemaTests{


    @Test
    public void testGetName(){

        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", "path", movies);

        assertEquals(test.getName(), "George Street");
    }

    @Test
    public void testGetLocationPath(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", "path", movies);

        assertEquals(test.getLocationPath(), "path");
    }

    @Test
    public void testGetMovies(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", "path", movies);

        assertEquals(test.getMovies(), movies);
    }

    @Test
    public void testDayToString(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", "path", movies);

        

        assertEquals(test.getMonday(), movies);
    }

    
}