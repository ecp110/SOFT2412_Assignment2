package movie;
import java.util.*;
import java.io.*;
import org.json.*;

public abstract class Page {
    // Database locations
    protected final String MOVIE_LOCATION;
    protected final String CINEMAS_LOCATION;
    protected final String CREDIT_CARD_LOCATION;
    protected final String GIFT_CARD_LOCATION;
    protected final String USERS_LOCATION;
    protected String homePageString;

    // JSON Objects
    private JSONObject json;


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
        this.parseMovies(this.json);
    }

    /**
     * Displays the home / initial page which a user lands on for the given page
     */
    public abstract String displayInitial();

    /**
     * Page to return to & display if at any time the user cancels the current operation
     */
    public abstract HomePage cancel();

    public void JSONObjectCreator(String folder, String filename) throws FileNotFoundException {
        System.err.println(filename);
        json = new JSONObject(new JSONTokener(new FileReader(folder + "/" + filename)));
    }

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


    protected void parseMovies(JSONObject json) { 
        JSONArray JSONmovies = json.getJSONArray("movies");
        for (int i = 0; i < JSONmovies.length(); i++) {
            JSONObject movie = JSONmovies.getJSONObject(i);
            String releaseDate = movie.getString("release date");
            int day = Integer.parseInt(releaseDate.substring(0,2));
            int month = Integer.parseInt(releaseDate.substring(2,4));
            int year = Integer.parseInt(releaseDate.substring(4,8));
            Calendar relDate = new Calendar(day, month, year);
            JSONArray JSONcast = movie.getJSONArray("cast");
            ArrayList<String> castList = new ArrayList<String>();
            if (JSONcast != null) {
                for (int j = 0; j < JSONcast; j++) {
                    castList.add(JSONcast.getString(i));
                }
            }
            this.movies.add(new Movie(movie.getString("name"), movie.getString("synopsis"), movie.getInt("runtime"), movie.getString("classification"), castList, movie.getString("director"), movie.getString("id"), relDate));
        }
        System.out.println(this.movies);
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