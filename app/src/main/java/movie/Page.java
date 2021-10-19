package movie;
import java.util.*;
import java.io.*;
import org.json.simple.*;

public abstract class Page {
    // Database locations
    protected final String MOVIE_LOCATION;
    protected final String CINEMAS_LOCATION;
    protected final String CREDIT_CARD_LOCATION;
    protected final String GIFT_CARD_LOCATION;
    protected final String USERS_LOCATION;
    protected String homePageString;

    // User information; stored in an arraylist of User objects
    protected ArrayList<User> users = new ArrayList<User>();

    // Movie information; stored in an arralist of Movie objects
    protected ArrayList<Movie> movies = new ArrayList<Movie>();

    public Page(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation) {
        this.MOVIE_LOCATION = movieLocation;
        this.CINEMAS_LOCATION = cinemasLocation;
        this.CREDIT_CARD_LOCATION = creditCardLocation;
        this.GIFT_CARD_LOCATION = giftCardLocation;
        this.USERS_LOCATION = usersLocation;

        this.parseUsers();
        this.parseMovies();
    }

    /**
     * Displays the home / initial page which a user lands on for the given page
     */
    public abstract String displayInitial();

    /**
     * Page to return to & display if at any time the user cancels the current operation
     */
    public abstract HomePage cancel();

    /** 
     * Parses users into arraylist of users
     */
    protected void parseUsers() {
        // Initiates scanner for users file
        try {
            Scanner sc = new Scanner(new File(this.USERS_LOCATION));
            String[] line = new String[3];

            // Skips the initial line with column names
            sc.nextLine();
            
            String type;
            // Reads in each of the lines into User objects and stores them in the page
            while (sc.hasNextLine()) {
                type = null;
                line = sc.nextLine().split(",");

                // validity checks
                if (line.length != 3) {
                    continue;
                }

                if (line[0] == null || line[0].equals("")) {
                    continue;
                } else if (line[1] == null || line[1].equals("")) {
                    continue;
                } else if (line[2] == null || line[2].equals("")) {
                    continue;
                }

                if (line[2].toLowerCase().equals("customer")) {
                    type = "customer";
                } else if (line[2].toLowerCase().equals("manager")) {
                    type = "manager";
                } else if (line[2].toLowerCase().equals("staff")) {
                    type = "staff";
                } else {
                    continue;
                }

                // Adds the user as a user object to the page
                this.users.add(new User(line[0], line[1], type));
            }

        } catch (FileNotFoundException e) {
            return;
        }
    }


    /** 
     * Parses movies into arraylist of users
     */
    protected void parseMovies() {
        // Initiates scanner for movies file
        try {
            Scanner sc = new Scanner(new File(this.MOVIE_LOCATION));
            String[] line = new String[3];

            // Skips the initial line with column names
            sc.nextLine();
            
            String type;
            Movie toAdd;
            // Reads in each of the lines into movie objects and stores them in the page
            while (sc.hasNextLine()) {
                type = null;
                line = sc.nextLine().split(",");

                // validity checks
                if (line.length != 3) {
                    continue;
                }

                if (line[0] == null || line[0].equals("")) {
                    continue;
                } else if (line[1] == null || line[1].equals("")) {
                    continue;
                } else if (line[2] == null || line[2].equals("")) {
                    continue;
                }
                
                // Adds new movie to the this.movies ararylist
                toAdd = new Movie(line[0], "DESCRIPTION TBD", Integer.parseInt(line[2]), line[1], null, null, null, null);
                this.movies.add(toAdd);
            }

        } catch (FileNotFoundException e) {
            return;
        }
    }

    /**
     * Parses a txt file into a String which it then returns
     */
    protected String parseTxt(String fileLoc, int lineSkip) {
        String output = "";
        try {
            Scanner sc = new Scanner(new File(fileLoc));
            for (int i = 0; i < lineSkip; i++) {
                sc.nextLine();
            }

            while (sc.hasNextLine()) {
                output += sc.nextLine();
                output += "\n";
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        return output;
    }

}