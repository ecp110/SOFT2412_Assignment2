package movie;

import java.util.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.*;

public class CustomerPage extends Page {

    // User details
    private User user;
    
    public CustomerPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String pagePath, User user) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation, pagePath);
        this.user = user;
    }

    public String displayInitial() {
        String output = "";
        String fileLoc = this.PAGE_PATH + "/main.txt";

        try {
            Scanner sc = new Scanner(new File(fileLoc));

            // Personalisation at start to user logged in
            output += sc.nextLine();
            output += " ";
            output += user.getName();
            output += "!";
            output += "\n";

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        output += this.parseTxt("/init.txt", 1);
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
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, this.PAGE_PATH
            );
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