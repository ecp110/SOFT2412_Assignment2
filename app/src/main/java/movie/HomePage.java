import java.util.*;
import java.io.*;

public class HomePage extends Page {
    private boolean hasUsername = false;

    public HomePage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation);
        this.homePageString = "../pages/init/main.txt";
    }

    /**
     * Prints the inital screen which a user lands on
     * Asks whether they want to login or not
     */
    public String displayInitial() {
        String output = "";
        try {
            Scanner sc = new Scanner(new File(this.homePageString));
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
     * Displays the login screen
     * If username has been displayed previously, then  call  asks for password
     * If nothing has been called, then call will ask for username.
     */
    public String displayLogIn() {
        String username = "../pages/init/username.txt";
        String password = "../pages/init/password.txt";
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

    public String cancel() {
        this.hasUsername = false;
        return "";
    }
    
}