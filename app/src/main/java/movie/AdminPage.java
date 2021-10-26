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

    public String displayBookingLogPrompt() {
        return this.parseTxt("/askForLocation.txt", 0);
    }

    public String displayBookingReciepts(String loc) {
        if (loc == null || loc.equals("")) {
            return null;
        }

        String time;
        String customerName;
        String cardNum;
        String numSeats;
        String movieId;
        String screenType;

        String retString = "Displaying results for: " + loc + "\n\n";

        try {
            Scanner sc = new Scanner(new File(this.CINEMAS_LOCATION + "/" + loc + "/bookingLog.csv"));
            sc.nextLine(); // Skips first line in csv
            while (sc.hasNextLine()) {
                String[] vals = sc.nextLine().split(",");

                time = vals[0];
                if (time.equals("0")) {
                    time = "Morning";
                } else if (time.equals("1")) {
                    time = "Midday";
                } else if (time.equals("2")) {
                    time = "Evening";
                }

                customerName = vals[1];
                cardNum = vals[2];
                numSeats = vals[3];
                movieId = vals[4];
                screenType = vals[5];

                retString += this.formatLog(time, customerName, cardNum, numSeats, movieId, screenType);
                retString += "\n";
            } 
        } catch (IOException e) {
            return "FILE ERROR: " + this.CINEMAS_LOCATION + "/" + loc;
        }

        return retString;
    }

    private String formatLog(String time, String customerName, String cardNum, String numSeats, String movieId, String screenType) {
        String retString = "";
        // Format: [ID] (MovName): [Name] booked for [numSeats] in the [time] with card [cardNum] in the [screenType] screen.

        retString += movieId;
        for (Movie movie : this.movies) {
            if (movie.getID().equals(movieId)) {
                retString += " (" + movie.getTitle() + ") ";
                break;
            }
        }
        retString += "| " + customerName + " booked for " + numSeats + " in the " + time + " with card " + cardNum + " in the ";

        if (screenType.equals("0")) {
            retString += "Bronze screen.";
        } else if (screenType.equals("1")) {
            retString += "Silver screen.";
        } else if (screenType.equals("2")) {
            retString += "Gold screen.";
        }

        return retString;    
    }

    public HomePage cancel() {
        this.user = null;
        return new HomePage(
            this.MOVIE_LOCATION, this.CINEMAS_LOCATION, this.CREDIT_CARD_LOCATION,
            this.GIFT_CARD_LOCATION, this.USERS_LOCATION, this.PAGE_PATH
            );
    }

}