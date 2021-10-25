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

        output += this.parseTxt("/main.txt", 1);
        output += this.displayMovies();
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

    public String book(int seats, User user, Viewing viewing, Cinema cinema) {

        String recieptString = ""; // Reciept string of form "[movie name] at [time] at [location] in the [screen type] screen for [seats]"
        String databaseString = ""; // String to add to databse log of all bookings
        String logLocation = cinema.getLocationPath() + "/bookingLog.csv";

        recieptString += viewing.getMovie().getTitle() + "in the ";
        recieptString += viewing.getTimeOfDayName() + " at ";
        recieptString += cinema.getName() + " in the ";
        recieptString += viewing.getScreenName() + " screen for ";
        recieptString += String.valueOf(seats) + " people.";

        databaseString += String.valueOf(viewing.getTimeOfDay()) + ",";
        databaseString += user.getName() + ",";
        databaseString += String.valueOf(seats) + ",";
        databaseString += viewing.getMovie().getID() + ",";
        databaseString += viewing.getScreenName() + "\n";
        
        try {
            FileWriter fw = new FileWriter(logLocation, true);
            fw.append(databaseString);
            fw.flush();
            fw.close();
        } catch (IOException e) {}

        return this.displayBookingConfirmation(recieptString);
    }

    private String displayBookingConfirmation(String recieptString) {
        String output = "";
        String fileLoc = this.PAGE_PATH + "/bookingConfirmation.txt";

        try {
            Scanner sc = new Scanner(new File(fileLoc));

            // Personalisation at start to user logged in
            output += sc.nextLine();
            output += recieptString;
            output += this.parseTxt(recieptString, 1);
        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        output += this.parseTxt("/bookingConfirmation.txt", 1);
        output += this.displayMovies();
        return output;
    }

    



}