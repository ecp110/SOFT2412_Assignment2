import java.util.*;
import java.io.*;

public abstract class Page {
    // Database locations
    private final String MOVIE_LOCATION;
    private final String CINEMAS_LOCATION;
    private final String CREDIT_CARD_LOCATION;
    private final String GIFT_CARD_LOCATION;
    private final String USERS_LOCATION;
    public String homePageString;

    // User information; stored in an arraylist of User objects
    private ArrayList<User> users = new ArrayList<User>();

    // Movie information; stored in an arralist of Movie objects
    private ArrayList<Movie> movies = new ArrayList<Movie>();

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
    private void parseUsers() {
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

                if (line[0] == null || line[0] == "") {
                    continue;
                } else if (line[1] == null || line[1] == "") {
                    continue;
                } else if (line[2] == null || line[2] == "") {
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
                this.users.add(new User(line[0], Integer.parseInt(line[1]), type));
            }

        } catch (FileNotFoundException e) {
            return;
        }
    }


    /** 
     * Parses movies into arraylist of users
     */
    private void parseMovies() {
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

                if (line[0] == null || line[0] == "") {
                    continue;
                } else if (line[1] == null || line[1] = "") {
                    continue;
                } else if (line[2] == null || line[2] = "") {
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
    private String parseTxt(String fileLoc, int lineSkip) {
        output = "";
        try {
            Scanner sc = new Scanner(new File(fileLoc));
            try {
                for (int i = 0; i < lineSkip; i++) {
                    sc.nextLine();
                }
            } catch (EOFException e) {}

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
                            this.MOVIE_LOCATION,this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
                            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, user
                            );
                    } else {
                        return new CustomerPage (
                            this.MOVIE_LOCATION,this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
                            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, user
                        );
                    }
                }
            }
        }

        // If user doesn't exist, then we will register them and take them to the customer page.
        User newUser = User(username, password, "customer");
        return new CustomerPage (
            this.MOVIE_LOCATION,this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, newUser
        );

    }

}