package movie;

import java.util.*;
import java.io.*;

public class AdminPage extends Page {

    // User details
    private User user;

    public AdminPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, User user) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation);
        this.user = user;
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

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        output += this.parseTxt(this.homePageString, 1);

        return output;
    }

    public Page cancel() {
        this.user = null;
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION
            );
    }

}