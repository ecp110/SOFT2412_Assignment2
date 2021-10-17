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
    public abstract Page cancel();

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

                // Adds the user as a movie object to the page
                this.movies.add(new Movie(line[0], "DESCRIPTION TBD", Integer.parseInt(line[2]), line[1], null));
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

    /**
     * Logs anyone in
     * If they are a customer, returns 0
     * If they are a staff, returns 1
     * If they are a manager, returns 2
     * If they are not a recorded user / other error, returns -1
     */

     public int logIn(String username, String password) {
        if (username == null) {
            return -1;
        } else if (username.equals("")) {
            return -1;
        }

        if (password == null) {
            return -1;
        } else if (password.equals("")) {
            return -1;
        }

        // Checks for existing User
        User foundUser = null;
        for (User user : this.users) {
            if (user.getName().equals(username)) {
                if (user.getPassword().equals(password)) {
                    foundUser = user;
                    break;
                }
            }
        }

        // If user doesn't exist, terminate with error
        if (foundUser == null) {
            return -1;
        }

        // If user does exist, return right person type code
        String userType = user.getType().toLowerCase();
        if (userType.equals("customer")) {
            return 0;
        } else if (userType.equals("staff")) {
            return 1;
        } else if (userType.equals("manager")) {
            return 2;
        }

        return -1;
     }

     /**
      * Registers a new user
      * If user alreay exists, return 0
      * If user created, return 1
      * If error, return -1
      * @param username
      * @param password
      * @return
      */

     public int register(String username, String password) {
        // Error checking
        if (username == null) {
            return -1;
        } else if (username.equals("")) {
            return -1;
        }

        if (password == null) {
            return -1;
        } else if (password.equals("")) {
            return -1;
        }

        // Checks doesn't exist already
        for (User user : this.users) {
            if (user.getName().equals(username)) {
                return 0;
            }
        }
        
        // Creates new user and adds to page list
        User newUser = new User(username, password, "customer");
        this.users.add(newUser);

        // Adds new user to database
        try {
            Object o = jsonParser.parse(new FileReader(this.USERS_LOCATION));
            JSONArray jsonArray = (JSONArray) o;
            JSONObject nUser = new JSONObject();
            nUser.put("username", "password", "type");
            nUser.put(username, password, "customer");
            jsonArray.add(nUser);
            FileWriter f = new FileWriter(this.USERS_LOCATION);
            f.write(jsonArray.toJSONString());
            f.flush();
            f.close();
            return 1;
        } catch (ParseException e) {
            return -1;
        } catch (IOException e) {
            return -1;
        }

        return -1;
     }

}