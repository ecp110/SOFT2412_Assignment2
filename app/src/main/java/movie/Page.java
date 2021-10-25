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

    // CreditCard information; stored in an arralist of CreditCard objects
    protected ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();

    // GiftCard information; stored in an arralist of GiftCard objects
    protected ArrayList<GiftCard> giftCards = new ArrayList<GiftCard>();

    public Page(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String pagePath) {
        this.MOVIE_LOCATION = movieLocation;
        this.CINEMAS_LOCATION = cinemasLocation;
        this.CREDIT_CARD_LOCATION = creditCardLocation;
        this.GIFT_CARD_LOCATION = giftCardLocation;
        this.USERS_LOCATION = usersLocation;
        this.PAGE_PATH = pagePath;

        this.parseAll();
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

    private void parseAll() {
        this.parseUsers();
        this.parseMovies();
        this.parseCreditCards();
        this.parseGiftCards();
    }

    /** 
     * Parses users into arraylist of users
     */
    protected void parseUsers() {
        // Initiates scanner for users file
        JSONParser parser = new JSONParser();
    
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(this.USERS_LOCATION));
            JSONArray users = (JSONArray) jsonObject.get("users");

            String name;
            String password;
            String status;
            User uFull;
            
            for (Object user : users) {
                JSONObject u = (JSONObject) user;

                name = (String) u.get("name");
                password = (String) u.get("password");
                status = (String) u.get("status");

                uFull = new User(name, password, status);
                this.users.add(uFull);
                uFull = null;
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } catch (ParseException e) {
            System.out.println("ERROR");
        }
  
    }

    protected void parseMovies(){
            JSONParser parser = new JSONParser();
    
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
                    this.movies.add(mFull);
                    mFull = null;
                }
    
            } catch (FileNotFoundException e) {
                System.out.println("NO FILE");
            } catch (IOException e) {
                System.out.println("ERROR");
            } catch (ParseException e) {
                System.out.println("ERROR");
            }
 
    }

    protected void parseCreditCards(){
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(this.CREDIT_CARD_LOCATION));
            JSONArray creditCards = (JSONArray) jsonObject.get("creditcards");

            String name;
            String number;
            CreditCard cFull;
            
            for (Object card : creditCards) {
                JSONObject c = (JSONObject) card;

                name = (String) c.get("name");
                number = (String) c.get("number");

                cFull = new CreditCard(name, number);
                this.creditCards.add(cFull);
                cFull = null;
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } catch (ParseException e) {
            System.out.println("ERROR");
        }

    }

    protected void parseGiftCards(){
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(this.GIFT_CARD_LOCATION));
            JSONArray giftCards = (JSONArray) jsonObject.get("giftcards");

            String number;
            boolean redeemed = false;

            String cParsing;
            int day;
            int month;
            int year;

            Calendar issue;
            Calendar expiry;
            GiftCard gFull;
            
            for (Object card : giftCards) {
                JSONObject g = (JSONObject) card;

                number = (String) g.get("number");
                if ((int) (long) g.get("redeemed") == 1) {
                    redeemed = true;
                }
                cParsing = (String) g.get("issue");
                day = Integer.valueOf(cParsing.substring(0, 2));
                month = Integer.valueOf(cParsing.substring(2, 4));
                year = Integer.valueOf(cParsing.substring(4));
                issue = new Calendar(day, month, year);

                cParsing = (String) g.get("expiry");
                day = Integer.valueOf(cParsing.substring(0, 2));
                month = Integer.valueOf(cParsing.substring(2, 4));
                year = Integer.valueOf(cParsing.substring(4));
                expiry = new Calendar(day, month, year);

                gFull = new GiftCard(number, redeemed, issue, expiry);
                this.giftCards.add(gFull);
                gFull = null;
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE");
        } catch (IOException e) {
            System.out.println("ERROR");
        } catch (ParseException e) {
            System.out.println("ERROR");
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

    /**
     * Returns string with all movies, one movie per line
     */
    protected String displayMovies() {
        String retString = "";
        for (Movie movie : this.movies) {
            retString += movie.toString();
            retString += "\n";
        }
        return retString;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

}