package movie;

import java.util.*;
import java.io.*;



public class HomePage extends Page {
    private boolean hasUsername = false;

    public HomePage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String filePath) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation, filePath);
        this.parseAll();
    }

    /**
     * Prints the inital screen which a user lands on
     * Asks whether they want to login or not
     */
    public String displayInitial() {
        return this.parseTxt("/main.txt", 0);
    }

    /**
     * Displays the login screen
     * If username has been displayed previously, then call asks for password
     * If nothing has been called, then call will ask for username.
     */
    public String displayLogIn() {
        String username = this.PAGE_PATH + "/username.txt";
        String password = this.PAGE_PATH + "/password.txt";
        String display;

        if (this.hasUsername) {
            display = password;
        } else {
            display = username;
        }

        String output = "";
        try {
            Scanner sc = new Scanner(new File(display));
            while (sc.hasNextLine()) {
                output += sc.nextLine();
                output += "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        this.hasUsername = !this.hasUsername;

        return output;
    }

    /**
     * Logs anyone in
     * Returns the user if exist; null if not
     */
    public User logIn(String username, String password) {
        if (username == null) {
            return null;
        } else if (username.equals("")) {
            return null;
        }

        if (password == null) {
            return null;
        } else if (password.equals("")) {
            return null;
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

        return foundUser;
     }

    public HomePage cancel() {
        this.hasUsername = false;
        return this;
    }

    public boolean hasUsername() {
        return this.hasUsername;
    }

    
    
}

