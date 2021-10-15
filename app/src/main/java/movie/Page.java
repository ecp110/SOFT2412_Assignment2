import java.util.*;
import java.io.*;

public abstract class Page {
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
            
            String type;
            // Reads in each of the lines into User objects and stores them in the page
            while (sc.hasNextLine()) {
                type = null;
                line = sc.nextLine().split(",");

                if (line[0] == null || line[0] == "") {
                    continue;
                } else if (line[1] == null) {
                    continue;
                } else if (line[2] == null) {
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

                this.users.add(new User(line[0], Integer.parseInt(line[1]), type));
            }

        } catch (FileNotFoundException e) {
            return;
        }
    }

    /**
     * Logs anyone in
     * If they are a customer, take them to customer page
     * If they are an admin, take them to admin page
     * If they are not a recorded user, create them in the database and take them to the customer page
     */
    public Page logIn(String username, int password) {
        Page retPage;

        if (username == null || password == null) {
            return false;
        } else if (username.equals("")) {
            return false;
        }
        
        // Iterates through all users in the userbase, and if they exist, then returns the page with their details
        for (User user : this.users) {
            if (user.getName().equals(username)) {
                if (user.getPassword() == password) {
                    if (user.isAdmin()) {
                        return new AdminPage (
                            this.movieLocation,this.cinemasLocation, this.creditCardLocation,
                            this.giftCardLocation, this.usersLocation, user
                            );
                    } else {
                        return new CustomerPage (
                            this.movieLocation,this.cinemasLocation, this.creditCardLocation,
                            this.giftCardLocation, this.usersLocation, user
                        );
                    }
                }
            }
        }

        // If user doesn't exist, then we will register them and take them to the customer page.
        User newUser = User(username, password, "customer");
        return new CustomerPage (
            this.movieLocation,this.cinemasLocation, this.creditCardLocation,
            this.giftCardLocation, this.usersLocation, newUser
        );

    }
}