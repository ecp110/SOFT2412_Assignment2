package movie;

import java.util.*;
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

    public Page cancel() {
        this.user = null;
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION);
    }

    public String listMovies() {
        String output = "";

        for (Movie movie : this.movies) {
            output += movie.toString();
            output += "\n";
        }

        return output;
    }



}