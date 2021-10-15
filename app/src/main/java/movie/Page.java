import java.util.*;
import java.io.*;

public abstract class Page {
    // Boolean status
    private boolean loggedIn = false;
    private boolean admin = false;

    // Database locations
    private String movieLocation;
    private String cinemasLocation;
    private String creditCardLocation;
    private String giftCardLocation;
    private String usersLocation;
    public String homePageString;

    // User information; stored in an arraylist of User objects
    private ArrayList<User> users = new ArrayList<User>();

    public Page(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation) {
        this.movieLocation = movieLocation;
        this.cinemasLocation = cinemasLocation;
        this.creditCardLocation = creditCardLocation;
        this.giftCardLocation = giftCardLocation;
        this.usersLocation = usersLocation;

        this.parseUsers();
    }

    /**
     * Displays the home / initial page which a user lands on for the given page
     */
    public abstract String displayInitial();

    /**
     * What to do & display if at any time the user cancels the current operation
     */
    public abstract String cancel();

    /** 
     * Parses users into arraylist of users
     */
    private void parseUsers() {
        // Initiates scanner for users file
        try {
            Scanner sc = new Scanner(new File("this.usersLocation"));
            String[] line = new String[3];

            // Skips the initial line with column names
            sc.nextLine();
            
            boolean admin;
            // Reads in each of the lines into User objects and stores them in the page
            while (sc.hasNextLine()) {
                admin = false;
                line = sc.nextLine().split(",");

                if (line[0] == null || line[0] == "") {
                    break;
                } else if (line[1] == null) {
                    break;
                } else if (line[2] == null) {
                    break;
                }

                if (!(line[2].equals("customer"))) {
                    admin = true;
                }
                this.users.add(new User(line[0], Integer.parseInt(line[1]), admin));
            }

        } catch (FileNotFoundException e) {
            return;
        }
    }

    /**
     * Logs anyone in
     * If they are a customer, take them to customer page
     * If they are an admin, take them to admin page
     */
    public boolean logIn(String username, String password) {
        if (username == null || password == null) {
            return false;
        } else if (username.equals("") || password.equals("")) {
            return false;
        }
        
        
        return true;
    }

    public void logOut() {
        this.loggedIn = false;
        this.admin = false;
    }

    // GETTERS

    /**
     * Returns if any user is logged in or not
     */
    public boolean loggedIn() {
        return this.loggedIn;
    }

    /**
     * Returns if this is an admin page or not
     */
    public boolean isAdmin() {
        return this.admin;
    }


}