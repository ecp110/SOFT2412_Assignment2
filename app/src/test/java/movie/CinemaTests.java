package movie;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CinemaTests{


    @Test
    public void testGetName(){
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String chatswoodLoc = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", "Chatswood").toString();

        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", chatswoodLoc, movies);

        assertEquals(test.getName(), "George Street");
    }

    @Test
    public void testGetLocationPath(){
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String chatswoodLoc = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", "Chatswood", "timetable.csv").toString();

        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", chatswoodLoc, movies);

        assertEquals(test.getLocationPath(), currentPath);
    }

    @Test
    public void testGetMovies(){
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String chatswoodLoc = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", "Chatswood", "timetable.csv").toString();
        
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", chatswoodLoc, movies);

        assertEquals(test.getMovies(), movies);
    }

    @Test
    public void testParseViewings(){
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String chatswoodLoc = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", "Chatswood", "timetable.csv").toString();

        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", chatswoodLoc, movies);
        
        for (Viewing v : test.getMonday()) {
            assertTrue(v != null);
        }

        for (Viewing v : test.getTuesday()) {
            assertTrue(v != null);
        }

        for (Viewing v : test.getWednesday()) {
            assertTrue(v != null);
        }

        for (Viewing v : test.getThursday()) {
            assertTrue(v != null);
        }

        for (Viewing v : test.getFriday()) {
            assertTrue(v != null);
        }

        for (Viewing v : test.getSaturday()) {
            assertTrue(v != null);
        }

        for (Viewing v : test.getSunday()) {
            assertTrue(v != null);
        }
    }

    @Test
    public void dayToStringTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String chatswoodLoc = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", "Chatswood", "timetable.csv").toString();

        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cinema test = new Cinema("George Street", chatswoodLoc, movies);
        
        assertTrue(test.dayToString("Monday") != null);
        assertTrue(test.dayToString("Tuesday") != null);
        assertTrue(test.dayToString("Wednesday") != null);
        assertTrue(test.dayToString("Thursday") != null);
        assertTrue(test.dayToString("Friday") != null);
        assertTrue(test.dayToString("Saturday") != null);
        assertTrue(test.dayToString("Sunday") != null);

        assertNull(test.dayToString(""));
        assertNull(test.dayToString(null));

        

    }

    
}