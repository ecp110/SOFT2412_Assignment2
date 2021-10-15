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
        String output = ""

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

    public Page cancel() {
        this.user = null;
        return new HomePage(this.movieLocation, this.cinemasLocation, this.creditCardLocation, this.giftCardLocation, this.usersLocation);
    }


}