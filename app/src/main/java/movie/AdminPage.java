package movie;

import java.util.*;
import java.io.*;

public class AdminPage extends Page {

    // User details
    private User user;

    public AdminPage(String movieLocation, String cinemasLocation, String creditCardLocation, String giftCardLocation, String usersLocation, String pagePath, User user) {
        super(movieLocation, cinemasLocation, creditCardLocation, giftCardLocation, usersLocation, pagePath);
        this.user = user;
        this.parseAll();
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

        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        output += this.parseTxt("/main.txt", 1);

        return output;
    }

    public HomePage cancel() {
        this.user = null;
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, this.PAGE_PATH
            );
    }

}