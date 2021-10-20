package movie;
import java.util.*;
import java.io.*;
import org.json.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Page {
    // Database locations
    protected final String MOVIE_LOCATION;
    protected final String CINEMAS_LOCATION;
    protected final String CREDIT_CARD_LOCATION;
    protected final String GIFT_CARD_LOCATION;
    protected final String USERS_LOCATION;
    protected final String PAGE_PATH;

    // User information; stored in an arraylist of User objects
    protected ArrayList<User> users = new ArrayList<User>();

    // Movie information; stored in an arralist of Movie objects
    protected ArrayList<Movie> movies = new ArrayList<Movie>();

    public Page(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String pagePath) {
        this.MOVIE_LOCATION = movieLocation;
        this.CINEMAS_LOCATION = cinemasLocation;
        this.CREDIT_CARD_LOCATION = creditCardLocation;
        this.GIFT_CARD_LOCATION = giftCardLocation;
        this.USERS_LOCATION = usersLocation;
        this.PAGE_PATH = pagePath;

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

    //public void JSONObjectCreator(String folder, String filename) throws FileNotFoundException {
       // System.err.println(filename);
        //json = new JSONObject(new JSONTokener(new FileReader(folder + "/" + filename)));
    //}

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

    protected void parseMovies(){
            JSONParser parser = new JSONParser();
            ArrayList<Movie> readMovies = new ArrayList<Movie>();
    
            try {
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(this.MOVIE_LOCATION));
                JSONArray movies = (JSONArray) jsonObject.get("movies");
    
                String title;
                String id;
                String classification;
                int runtime;
                String director;
                ArrayList<String> cast = null;
                Calendar release = null;
                String synopsis;
                Movie mFull;
                
                for (Object movie : movies) {
                    JSONObject m = (JSONObject) movie;
    
                    title = (String) m.get("name");
                    id = String.valueOf(m.get("id"));
                    classification = (String) m.get("classification");
                    runtime = (int) (long) m.get("runtime");
                    director = (String) m.get("director");
                    synopsis = (String) m.get("synopsis");
    
                    mFull = new Movie(title, synopsis, runtime, classification, cast, id, director, release);
                    readMovies.add(mFull);
                    mFull = null;
                }
    
            } catch (FileNotFoundException e) {
                System.out.println("NO FILE");
            } catch (IOException e) {
                System.out.println("ERROR");
            } catch (ParseException e) {
                System.out.println("ERROR");
            }
    
            for (Movie m : readMovies) {
                System.out.println(m.toString());
            }
        
    }

    /**
     * Parses a txt file into a String which it then returns
     */
    protected String parseTxt(String fileExtension, int lineSkip) {
        String output = "";
        String fileLoc = this.PAGE_PATH + fileExtension;
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

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

}