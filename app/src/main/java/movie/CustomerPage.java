package movie;

import java.util.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.*;

public class CustomerPage extends Page {

    // User details
    private User user;
    
    public CustomerPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, User user) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation);
        this.user = user;
        this.homePageString = "../pages/customer/main.txt";
    }

    public String displayInitial() {
        String output = "";

        try {
            Scanner sc = new Scanner(new File(this.homePageString));

            // Personalisation at start to user logged in
            output += sc.nextLine();
            output += " ";
            output += user.getName();
            output += "!";
            output += "\n";

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        output += this.parseTxt(this.homePageString, 1);
        output += this.listMovies();
        return output;
    }

    /**
     * Required abstract function
     * returns HomePage for when page gets cancelled
     */

    public HomePage cancel() {
        this.user = null;
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION);
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
            String json = new String(Files.readAllBytes(Paths.get(this.USERS_LOCATION)));
            json.replace("]", "");

            json += ",\n{\n\"name\":\"";
            json += username;
            json += "\"";
            json += ",\n";

            json += "\"password\":\"";
            json += password;
            json += "\"";
            json += ",\n";
            
            json += "\"status\":\"customer\"";
            json += ",\n";
            json += "}";

            json += "\n";
            json += "]";

            File userOut = new File(this.USERS_LOCATION);
            FileWriter fileOut = new FileWriter(userOut);

            fileOut.write(json);
            fileOut.flush();
            fileOut.close();


        } catch (IOException e) {
            return -1;
        }

        return -1;
     }

    /**
     * Lists all the movies for the customer to see
     * @return
     */
    public String listMovies() {
        String output = "";

        for (Movie movie : this.movies) {
            output += movie.toString();
            output += "\n";
        }

        return output;
    }



}