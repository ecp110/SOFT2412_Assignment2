package movie;

import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.Console;
import java.util.Random;

public class Engine {

    private Path currentPath = Paths.get(System.getProperty("user.dir"));
    private String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
    private String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
    private String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
    private String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
    private String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
    private String cancellationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "cancellations.csv").toString();

    private String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();
    private String customerPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "customer").toString();
    private String homePagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "init").toString();
    private String guestPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "guest").toString();

    private boolean reboot = false;
    private boolean running = true;

    private User currentUser = null;

    public static void main(String[] args) {
        Engine e = new Engine();

        e.reboot = false;

        while (true) {
            e.mainLoop();

            if (!e.running) {

                return;
            }

            if (e.reboot) {

                e = null;
                e = new Engine();
                e.reboot = false;
                clearConsole();
            }
        }       
    }

    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printLineBreak(){
        System.out.print("------------------------------------------------------------------------" + "\n");
    }
    

    public static void logCancellation(User user, String reason, String cancellationPath) {
        String userName;

        if (user == null) {
            userName = "anon";
        } else {
            userName = user.getName();
        }
        String datetime = java.util.Calendar.getInstance().getTime().toString();
        String cancellationString = "\n" + datetime + "," + userName + "," + reason;

        Scanner scan = new Scanner(System.in);
        boolean endProgram = false;
        boolean loginComplete = false;
        User currentUser = null;
        boolean running = true;

        try {
            FileWriter fw = new FileWriter(cancellationPath, true);
            fw.append(cancellationString);
            fw.flush();
            fw.close();
        } catch (IOException e) {}
    }

    public void mainLoop() {
        HomePage home = new HomePage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, homePagePath);

        // DISPLAY INITIAL HOME PAGE
        clearConsole();
        System.out.println(home.displayInitial());

        
        // INITIALISE USER, SCANNER FOR USER INPUT AND INITIAL RESPONSE
        Scanner scan = new Scanner(System.in);
        String response = scan.nextLine();

        // QUIT ON HOME PAGE
        if (response.toLowerCase().equals("q")){
            System.out.println("Quitting. Goodbye!");
            Engine.logCancellation(null, "user cancellation", cancellationPath);
            this.running = false;
            return;
        
        // LOGIN FUNCTIONALITY
        } else if (response.toLowerCase().equals("l")) {
            currentUser = this.login(home, scan);
        }

        // GUEST EXPERIENCE
        if (currentUser == null && response.toLowerCase().equals("g")){ //GUEST EXPERIENCE
            this.guestExperience(scan);
        }

        // REBOOT IF NO VALID INPUT
        else if (currentUser == null) {
            this.reboot = true;
            return;
        }

        // CUSTOMER EXPERIENCE
        else if (currentUser.getType().equals("customer")){
            this.customerExperience(scan);
        }

        //ADMIN & STAFF EXPERIENCE
        else if(currentUser.getType().equals("staff") || currentUser.getType().equals("manager")){
            this.staffExperience(scan);
        }

        // REBOOT ONCE THE ABOVE HAS COMPLETED
        this.reboot = true;
    }

    /**
     * LOGIN CAPABILITY
     */
    public User login(HomePage home, Scanner scan) {
        clearConsole();
        printLineBreak();
        System.out.println("\n"+home.displayLogIn());
        printLineBreak();
        String username = scan.nextLine();

        // masking password
        clearConsole();
        printLineBreak();
        System.out.println("\n"+home.displayLogIn());
        printLineBreak();

        String password = PasswordField.readPassword();
        
        currentUser = home.logIn(username, password);

        if (currentUser == null){
            System.out.println("Specified user doesn't exist. Continuing as guest...\n");
        } 
        return currentUser;            
    }

    /**
     * GUEST EXPERIENCE
     */
    public void guestExperience(Scanner scan) {
        GuestPage guest = new GuestPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, guestPagePath);
        String response = "";
        Movie selectedMovie = null;
        boolean exit = false;

        // CLEAR CONSOLE
        clearConsole();
        System.out.println(guest.displayInitial());

        while (!exit) {
            // DISPLAY INITIAL HOME PAGE

            
            response = scan.nextLine();

            // BLANK CHECKS
            if (response == null || response.equals("")) {
                System.out.println(guest.displayInvalidInput());
                continue;
            }
            
            // FILTER FUNCTIONALITY
            if (response.toLowerCase().equals("f")) {
                boolean correctMovie = false;
                String movieID = "";
                while(!correctMovie) {
                    System.out.println("Please enter the movie ID you wish to filter by (enter \'n\' for no preference): ");
                    System.out.println(guest.lineBreak());
                    movieID = scan.next();
                    clearConsole();
                    selectedMovie = guest.getMovieById(movieID);
                    if((selectedMovie != null) || (movieID.equals("n"))) {
                        correctMovie = true;
                        response = movieID;
                        if (movieID.equals("n")){
                            movieID = "";
                        }
                        
                    } else {
                        System.out.println("Invalid movie ID.");
                    }
                }

                
                System.out.println("Please enter one of the following locations (enter anything else for no preference): ");
                System.out.println(guest.lineBreak());
                System.out.print(guest.listLocations());
                System.out.println(guest.lineBreak());
                String location = scan.next();
                if(!( 
                    (location.toLowerCase().equals("bondi"))||
                    (location.toLowerCase().equals("chatswood"))||
                    (location.toLowerCase().equals("georgestreet"))||
                    (location.toLowerCase().equals("hurstville"))  
                )) {
                    location = "";
                }
                clearConsole();

                
                System.out.println("Please enter the the number corresponding to your preferred screen size (enter anything else for no preference): ");
                System.out.println(guest.lineBreak());
                System.out.println("(0) Bronze\n(1) Silver\n(2) Gold");
                System.out.println(guest.lineBreak());
                int screenSize = -1;
                try {
                    screenSize = scan.nextInt();
                    assert ( (screenSize == 0) || (screenSize == 1) || (screenSize == 2) );
                } catch (Exception e) {
                    screenSize = -1;
                }
                ArrayList<Viewing> results = guest.filterViewings(location,movieID,screenSize,-1,"");
                clearConsole();


                System.out.println(guest.lineBreak());
                for (Viewing result : results) {
                    System.out.println(result);
                }
                if(results.size()== 0) {
                    System.out.println("No matching sessions found! Type \'f\' to search again.");
                }
                System.out.println(guest.lineBreak());
                System.out.println("As a guest, you cannot book a ticket, and must first register an account.\nType \'r\' to register!");
                response = scan.nextLine();
            }
            else if (response.toLowerCase().equals("h")) {
                clearConsole();
                System.out.println(guest.displayInitial());
            }
            // REGISTRATION FUNCTIONALITY 
            else if (response.toLowerCase().equals("r")){

                boolean registered = false;
                User newUser = null;

                while (!registered) {
                    System.out.println("Please enter a username (Must be alphanumeric with no spaces): ");
                    String username = scan.nextLine();

                    if (guest.getUserByUsername(username) == null) {
                        System.out.println("Valid username! Please enter a password: ");
                        String password = PasswordField.readPassword();
                        System.out.println("Please re-enter your password: ");
                        String password2 = PasswordField.readPassword();
                        if (password2.equals(password)) {
                            System.out.println("Passwords match!");
                            newUser = new User(username,password,"customer");
                            guest.addUser(newUser);
                            registered = true;
                            
                        } else {
                            System.out.println("Passwords do not match. Please try again.");
                        }
                        
                    } else {
                        System.out.println("Username is already taken.");
                    }
                    
                }

                if (registered) {
                    this.currentUser = newUser;
                    this.customerExperience(scan);
                    return;
                }
    
            }

            // BOOKING FUNCTIONALITY - TODO
            else if(response.toLowerCase().equals("b")) {
               
                if (selectedMovie != null) {
                    System.out.println(
                        "To book tickets to "+selectedMovie.getTitle()+", you must have an account."+
                        "\n if you would like to proceed and register, type \"r\"." +
                        "\nOtherwise if you would like to cancel, type anything.");
                    exit = true;
                } else {
                    System.out.println("No movie selected, please select a movie and try again!");
                }
            }

            else if (response.toLowerCase().equals("l")) {
                User u = this.login(new HomePage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, homePagePath), scan);
                this.currentUser = u;

                if (u.isAdmin()) {
                    this.staffExperience(scan);
                } else {
                    this.customerExperience(scan);
                }
                return;
            }
            
            // CANCELLATION FUNCTIONALITY
            else if (response.toLowerCase().equals("c")) {
                System.out.println("Quitting. Goodbye!");
                Engine.logCancellation(null, "user cancellation", cancellationPath);
                this.reboot = true;
                exit = true;
            }

            // LOOKUP MOVIE FROM PROVIDED RESPONSE
            else {
                selectedMovie = guest.getMovieById(response);
                if (selectedMovie != null){
                    clearConsole();
                    System.out.println("Selected Movie: "+selectedMovie.getTitle()+"\n");
                    printLineBreak();
                    System.out.println(selectedMovie.getSynopsis());
                    printLineBreak();
                    System.out.println(
                        "To view another movie, type in its ID.\n"+
                        "To return to the home page, type \"h\".");
                } else {
                    System.out.println("Movie not found! Please try again");
                }

            }
        }
    }

    /**
     * CUSTOMER EXPERIENCE
     */
    public void customerExperience(Scanner scan) {
        CustomerPage customer = new CustomerPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, customerPagePath, currentUser);
        boolean exit = false;
        Movie selectedMovie = null;
        String response = "";

        // CLEAR CONSOLE
        clearConsole();
        System.out.println(customer.lineBreak());
        System.out.println(customer.displayInitial());
        
        while (!exit) {
            
            response = scan.nextLine();
            
            
            // FILTERING
            if (response.toLowerCase().equals("f")) {
                boolean correctMovie = false;
                String movieID = "";
                while(!correctMovie) {
                    System.out.println("Please enter the movie ID you wish to filter by (enter \"n\" for no preference): ");
                    customer.lineBreak();
                    movieID = scan.next();
                    if(movieID.equals("c")){
                        System.out.println("Cancelling, goodbye!");
                        Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                        this.reboot = true;
                        return;
                    }
                    clearConsole();
                    selectedMovie = customer.getMovieById(movieID);
                    if((selectedMovie != null) || (movieID.equals("n"))) {
                        correctMovie = true;
                        response = movieID;
                        if (movieID.equals("n")){
                            movieID = "";
                        }
                        
                    } else {
                        System.out.println("Invalid movie ID.");
                    }
                }

                
                System.out.println("Please enter one of the following locations (enter anything else for no preference): ");
                System.out.println(customer.lineBreak());
                System.out.print(customer.listLocations());
                System.out.println(customer.lineBreak());
                String location = scan.next();
                if(location.equals("c")){
                    System.out.println("Cancelling, goodbye!");
                    Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                    this.reboot = true;
                    return;
                }
                if(!( 
                    (location.toLowerCase().equals("bondi"))||
                    (location.toLowerCase().equals("chatswood"))||
                    (location.toLowerCase().equals("georgestreet"))||
                    (location.toLowerCase().equals("hurstville"))  
                )) {
                    location = "";
                }
                clearConsole();

                
                System.out.println("Please enter the the number corresponding to your preferred screen size (enter anything else for no preference): ");
                System.out.println(customer.lineBreak());
                System.out.println("(0) Bronze\n(1) Silver\n(2) Gold");
                System.out.println(customer.lineBreak());
                int screenSize = -1;
                try {
                    screenSize = scan.nextInt();

                    assert ( (screenSize == 0) || (screenSize == 1) || (screenSize == 2) );
                } catch (Exception e) {
                    screenSize = -1;
                }
                ArrayList<Viewing> results = customer.filterViewings(location,movieID,screenSize,-1,"");
                clearConsole();


                System.out.println(customer.lineBreak());
                int index = 1;
                for (Viewing result : results) {
                    System.out.println("("+index+") "+result);
                    index++;
                }
                if(results.size() == 0) {
                    System.out.println("No matching sessions found! Type \'f\' to search again.");
                }
                System.out.println(customer.lineBreak());
                System.out.println("To book one of these sessions, enter its corresponding number.");
                boolean correctNumber = false;
                String number = "";
                Scanner bookingScanner = new Scanner(System.in);
                while(!correctNumber){
                    number = bookingScanner.nextLine();
                    if(number.equals("c")){
                        System.out.println("Cancelling, goodbye!");
                        Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                        this.reboot = true;
                        return;
                    }
                    try {
                        int numberInt = Integer.valueOf(number);
                        if ((numberInt <= 0) || (numberInt >= index)) {
                            System.out.println("Invalid number");
                            continue;
                        } else {
                            correctNumber = true;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid number");
                        continue;
                    }

                    
                }
                System.out.println("Selected session: ");
                Viewing selectedSession = results.get(Integer.valueOf(number)-1);
                System.out.println(selectedSession);
                System.out.println(customer.lineBreak());

                System.out.println("How many tickets do you require? ");
                correctNumber = false;
                int numberInt = 0;
                while(!correctNumber){
                    
                    number = bookingScanner.nextLine();
                    if(number.equals("c")){
                        System.out.println("Cancelling, goodbye!");
                        Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                        this.reboot = true;
                        return;
                    }

                    try {
                        numberInt = Integer.valueOf(number);
                        if ((numberInt <= 0) || (numberInt > 10)){
                            throw new IllegalArgumentException("Invalid number of tickets");
                        }
                    } catch (IllegalArgumentException it) {
                        System.out.println("Please select an amount of tickets between 1 and 10.");
                        continue;
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        continue;
                    }
                    correctNumber = true;
                }
                correctNumber = false;
                int totalTickets = numberInt;
                System.out.println("You have selected "+ totalTickets + " tickets.");
                System.out.println(customer.lineBreak());
                System.out.println("How many adult tickets do you require?");
                int selectedTickets = 0;
                int adultTicketsInt = 0;
                while(!correctNumber){
                    String adultTickets = bookingScanner.nextLine();
                    if(adultTickets.equals("c")){
                        System.out.println("Cancelling, goodbye!");
                        Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                        this.reboot = true;
                        return;
                    }
                    
                    try {
                        adultTicketsInt = Integer.valueOf(adultTickets);
                        if ((adultTicketsInt < 0) || (adultTicketsInt > (totalTickets-selectedTickets))){
                            throw new IllegalArgumentException("Invalid number of tickets");
                        }
                    } catch (IllegalArgumentException it) {
                        System.out.println("Please select an amount of tickets between 0 and "+(totalTickets-selectedTickets));
                        continue;
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        continue;
                    }
                    correctNumber = true;
                    selectedTickets += adultTicketsInt;
                }
                int childTicketsInt = 0;
                
                if(totalTickets > selectedTickets) {
                    System.out.println(customer.lineBreak());
                    System.out.println("How many child tickets do you require?");
                    correctNumber = false;
                    while(!correctNumber){
                        String childTickets = bookingScanner.nextLine();
                        if(childTickets.equals("c")){
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            this.reboot = true;
                            return;
                        }
                        
                        try {
                            childTicketsInt = Integer.valueOf(childTickets);
                            if ((childTicketsInt < 0) || (childTicketsInt > (totalTickets-selectedTickets))){
                                throw new IllegalArgumentException("Invalid number of tickets");
                            }
                        } catch (IllegalArgumentException it) {
                            System.out.println("Please select an amount of tickets between 0 and "+(totalTickets-selectedTickets));
                            continue;
                        } catch (Exception e) {
                            System.out.println("Invalid input.");
                            continue;
                        }
                        correctNumber = true;
                        selectedTickets += childTicketsInt;
                    }  
                }
                int studentTicketsInt = 0;
                
                if(totalTickets > selectedTickets) {
                    System.out.println(customer.lineBreak());
                    System.out.println("How many student tickets do you require?");
                    correctNumber = false;
                    while(!correctNumber){
                        String studentTickets = bookingScanner.nextLine();
                        if(studentTickets.equals("c")){
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            this.reboot = true;
                            return;
                        }
                        
                        try {
                            studentTicketsInt = Integer.valueOf(studentTickets);
                            if ((studentTicketsInt < 0) || (studentTicketsInt > (totalTickets-selectedTickets))){
                                throw new IllegalArgumentException("Invalid number of tickets");
                            }
                        } catch (IllegalArgumentException it) {
                            System.out.println("Please select an amount of tickets between 0 and "+(totalTickets-selectedTickets));
                            continue;
                        } catch (Exception e) {
                            System.out.println("Invalid input.");
                            continue;
                        }
                        correctNumber = true;
                        selectedTickets += studentTicketsInt;
                    }  
                }
                int pensionerTicketsInt = 0;
                
                if(totalTickets > selectedTickets) {
                    System.out.println(customer.lineBreak());
                    System.out.println("How many pensioner tickets do you require?");
                    correctNumber = false;
                    while(!correctNumber){
                        String pensionerTickets = bookingScanner.nextLine();
                        if(pensionerTickets.equals("c")){
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            this.reboot = true;
                            return;
                        }
                        
                        try {
                            pensionerTicketsInt = Integer.valueOf(pensionerTickets);
                            if (pensionerTicketsInt != (totalTickets-selectedTickets)){
                                throw new IllegalArgumentException("Invalid number of tickets");
                            }
                        } catch (IllegalArgumentException it) {
                            System.out.println("Please select an amount of tickets between "+(totalTickets-selectedTickets)+" and "+(totalTickets-selectedTickets));
                            continue;
                        } catch (Exception e) {
                            System.out.println("Invalid input.");
                            continue;
                        }
                        correctNumber = true;
                        selectedTickets += pensionerTicketsInt;
                    }  
                }

                String seatingPosition = "";
                System.out.println(customer.lineBreak());
                System.out.println("Would you like to sit in the front, middle, or back?");
                boolean correctSelection = false;
                while (!correctSelection){
                    seatingPosition = bookingScanner.nextLine().toLowerCase();
                    if(seatingPosition.equals("c")){
                        System.out.println("Cancelling, goodbye!");
                        Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                        this.reboot = true;
                        return;
                    }
                    if (
                        (seatingPosition.equals("front")) ||
                        (seatingPosition.equals("middle")) ||
                        (seatingPosition.equals("back"))
                    ) {
                        correctSelection = true;
                    } else {
                        System.out.println("Please enter either \'front\', \'middle\', or \'back\'");
                        continue;
                    }
                }

                double adultTicketsAmount = customer.getBookingPrice("adult",selectedSession.getScreenName(),adultTicketsInt);
                double childTicketsAmount = customer.getBookingPrice("child",selectedSession.getScreenName(),childTicketsInt);
                double studentTicketsAmount = customer.getBookingPrice("student",selectedSession.getScreenName(),studentTicketsInt);
                double pensionerTicketsAmount = customer.getBookingPrice("pensioner",selectedSession.getScreenName(),pensionerTicketsInt);
                double totalAmount = adultTicketsAmount+childTicketsAmount+studentTicketsAmount+pensionerTicketsAmount;
                
                clearConsole();
                System.out.println(customer.lineBreak());
                System.out.println(
                    "Booking Summary: \n\n"+
                    selectedSession+"\n\n"+
                    "Adult Tickets: "+adultTicketsInt+" ("+customer.formatCurrencyString(adultTicketsAmount)+")\n"+
                    "Child Tickets: "+childTicketsInt+" ("+customer.formatCurrencyString(childTicketsAmount)+")\n"+
                    "Student Tickets: "+studentTicketsInt+" ("+customer.formatCurrencyString(studentTicketsAmount)+")\n"+
                    "Pensioner Tickets: "+pensionerTicketsInt+" ("+customer.formatCurrencyString(pensionerTicketsAmount)+")\n"+
                    "Seating Location: "+seatingPosition+"\n"+
                    customer.lineBreak()+"\n"+
                    "Total: "+customer.formatCurrencyString(totalAmount)
                    );
                System.out.println(customer.lineBreak());
                System.out.println(
                    "\nHow would you like to pay?\n"+
                    "Enter \'e\' to pay by EFT\n"+
                    "Enter \'g\' to pay by gift card\n"+
                    "Enter \'c\' to cancel this transaction and return to the main page.\n"+
                    customer.lineBreak()
                    );
                boolean correctInput = false;
                String input = "";
                boolean paymentSuccessful = false;
                String referenceCardNumber = "";
                while (!correctInput){
                    input = bookingScanner.nextLine().toLowerCase();
                    if ( (input.equals("c")) || (input.equals("e")) || (input.equals("g"))) {
                        correctInput = true;
                    } else {
                        System.out.println("Invalid input.");
                        continue;
                    }
                }
                if(input.equals("c")){
                    System.out.println("Cancelling, goodbye!");
                    Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                    this.reboot = true;
                    return;

                } else if (input.equals("e")) {
                    correctInput = false;
                    String cardHolderName = "";
                    String creditCardNumber = "";
                    clearConsole();
                    System.out.println(customer.lineBreak());
                    System.out.println("SECURE CHECKOUT");
                    System.out.println(customer.lineBreak());
                    while(!correctInput){
                        System.out.println("Please enter the name on your credit card: ");
                        cardHolderName = bookingScanner.nextLine();
                        if(cardHolderName.equals("c")){
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            this.reboot = true;
                            return;
                        }

                        System.out.println("Please your credit card number:");
                        //creditCardNumber = bookingScanner.nextLine();
                        creditCardNumber = PasswordField.readPassword();
                        if(creditCardNumber.equals("c")){
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            this.reboot = true;
                            return;
                        }
                        if (customer.verifyCreditCard(creditCardNumber,cardHolderName)) {
                            correctInput = true;
                        } else {
                            System.out.println("Invalid card details. Please try again.");
                            System.out.println(customer.lineBreak());
                            continue;
                        }
                    }
                    System.out.println("Charging "+customer.formatCurrencyString(totalAmount)+" to your credit card.");
                    paymentSuccessful = true;
                    referenceCardNumber = creditCardNumber;
                } else if (input.equals("g")) {
                    correctInput = false;
                    String giftCardNumber = "";
                    clearConsole();
                    System.out.println(customer.lineBreak());
                    System.out.println("SECURE CHECKOUT");
                    System.out.println(customer.lineBreak());
                    while(!correctInput){

                        System.out.println("Please enter your gift card number: ");
                        giftCardNumber = bookingScanner.nextLine();
                        if(seatingPosition.equals("c")){
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            this.reboot = true;
                            return;
                        }
                        if (customer.verifyGiftCard(giftCardNumber)) {
                            correctInput = true;
                        } else {
                            System.out.println("Gift card number is incorrect, or gift card has been used already.\nPlease try again.");
                            System.out.println(customer.lineBreak());
                            continue;
                        }
                    }
                    customer.useGiftCard(giftCardNumber);
                    System.out.println("Your gift card has now been used.");
                    paymentSuccessful = true;
                    referenceCardNumber = giftCardNumber;
                }
                if(paymentSuccessful){
                    customer.book(selectedSession,totalTickets,currentUser,referenceCardNumber);
                }
                clearConsole();
                System.out.println(customer.lineBreak());
                System.out.println("Thank you for your booking!");
                System.out.println(customer.lineBreak());
                Random rnd = new Random();
                int receiptNum = 100000000 + rnd.nextInt(900000000);
                System.out.println("Receipt Number: "+receiptNum);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ie) {}
                this.reboot = true;
                return;

                
                
            }
            else if(response.toLowerCase().equals("h")){
                clearConsole();
                System.out.println(customer.lineBreak());
                System.out.println(customer.displayInitial());
            }
            // CANCELLATION
            else if (response.toLowerCase().equals("c")) {
                System.out.println("Quitting. Goodbye!");
                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                this.reboot = true;
                exit = true;
            }
            // LOOKUP MOVIE FROM PROVIDED RESPONSE
            else {
                    selectedMovie = customer.getMovieById(response);
                if (selectedMovie != null){
                    clearConsole();
                    
                    System.out.println("Selected Movie: "+selectedMovie.getTitle());
                    printLineBreak();
                    System.out.println(selectedMovie.getSynopsis());
                    printLineBreak();
                    System.out.println(
                        "To view another movie, type in its ID.\n"+
                        "To return to the home page, type \"h\".");

                } else {
                    System.out.println("Movie not found! Please try again");
                }

            }
        }
    }

    /**
     * STAFF & MANAGER EXPERIENCE
     */
    public void staffExperience(Scanner scan) {
        AdminPage admin = new AdminPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, adminPagePath, currentUser);
        // CLEAR CONSOLE
        clearConsole();

        boolean exit = false;
        String response = "";

        while (!exit) {
            System.out.println(admin.lineBreak());
            System.out.println(admin.displayInitial());

            response = scan.nextLine();

            // DISPLAY BOOKING LOG
            if (response.equals("1")) {
                System.out.println(admin.displayBookingLogPrompt());
                response = scan.nextLine();
                if (response.equals("1")) {
                    System.out.println(admin.displayBookingReciepts("George Street"));
                } else if (response.equals("2")) {
                    System.out.println(admin.displayBookingReciepts("Chatswood"));
                } else if (response.equals("3")) {
                    System.out.println(admin.displayBookingReciepts("Bondi"));
                } else if (response.equals("4")) {
                    System.out.println(admin.displayBookingReciepts("Hurstville"));
                } else if (response.equals("c")) {
                    System.out.println("Cancelling, goodbye!");
                    Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                    exit = true;
                    this.reboot = true;
                    return;
                }

            // EDIT MOVIE INFORMATION
            } else if (response.equals("2")) {
                System.out.println(admin.displayMovieModificationPrompt());
                response = scan.nextLine().toLowerCase();
                if (response.equals("c")) {
                    System.out.println("Cancelling, goodbye!");
                    Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                    exit = true;
                    this.reboot = true;
                    return;

                // ADD NEW MOVIE
                } else if (response.equals("1")) {
                    String title;
                    String id;
                    String synop;
                    int runtime;
                    String classification;
                    String director;
                    Calendar releaseDate;

                    System.out.println("What's the name of the movie you'd like to add:");
                    title = scan.nextLine();
                    

                    System.out.println("ID:");
                    id = scan.nextLine();

                    System.out.println("What's the synopsis:");
                    synop = scan.nextLine();

                    while (true) {
                        try {
                            System.out.println("How long does it run for (in minutes):");
                            runtime = Integer.valueOf(scan.nextLine());
                            break;
                        } catch (Exception e) {
                            System.out.println("Sorry, invalid input. Please try again.");
                            continue;
                        }
                    }

                    System.out.println("What's its rating (classification):");
                    classification = scan.nextLine();

                    System.out.println("Who's the director:");
                    director = scan.nextLine();

                    while (true) {
                        try {
                            System.out.println("When was it released (ddmmyyyy):");
                            char[] date = scan.nextLine().toCharArray();
                            if (date.length != 8) {
                                System.out.println("Invalid input. Please try again.");
                                continue;
                            }

                            int day = Integer.valueOf(String.valueOf(date[0]) + String.valueOf(date[1]));
                            int month = Integer.valueOf(String.valueOf(date[2]) + String.valueOf(date[3]));
                            int year = Integer.valueOf(String.valueOf(date[4]) + String.valueOf(date[5]) + String.valueOf(date[6]) + String.valueOf(date[7]));

                            releaseDate = new Calendar(day, month, year);

                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please try again.");
                            continue;
                        }
                    }

                    System.out.println("Who is in the cast? If there are no more to add, please just hit enter.");
                    String actor;
                    ArrayList<String> cast = new ArrayList<String>();

                    while (true) {
                        actor = scan.nextLine();
                        if (actor == null || actor.equals("")) {
                            break;
                        } else {
                            cast.add(actor);
                        }
                    }

                    Movie newMovie = new Movie(title, synop, runtime, classification, cast, director, id, releaseDate);

                    if (!admin.addMovie(newMovie)) {
                        System.out.println("ERROR");
                    }

                // REMOVE MOVIE
                } else if (response.equals("2")) {
                    boolean removed = false;
                    while (!removed) {
                        System.out.println("Which movie would you like to delete (ID)?");
                        System.out.println(admin.displayMovies());
                        System.out.println("");

                        response = scan.nextLine().toLowerCase();

                        if (response.equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            exit = true;
                            this.reboot = true;
                            return;
                        }

                        Movie foundMovie = admin.getMovieById(response);

                        if (foundMovie == null) {
                            System.out.println("No movie found with that ID. Please try again.");
                            continue;
                        } else {
                            System.out.println("Deleting movie " + foundMovie.getTitle() + "...");
                            if (admin.removeMovie(foundMovie)) {
                                removed = true;
                            } else {
                                System.out.println("ERROR");
                            }
                        }
                    }

                // EDIT EXISTING MOVIE 
                } else if (response.equals("3")) {
                    System.out.println(admin.displayMovieEditPrompt());
                    System.out.println(admin.displayMovies());                

                    System.out.println();
                    String movieID = scan.nextLine();

                    if (movieID.toLowerCase().equals("c")) {
                        System.out.println("Cancelling, goodbye!");
                        Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                        exit = true;
                        this.reboot = true;
                        return;
                    }

                    Movie toEdit = null;
                    for (Movie movie : admin.getMovies()) {
                        if (movie.getID().equals(movieID)) {
                            toEdit = movie;
                            System.out.println(admin.displayFoundEditableMovie());
                            System.out.println(movie.toString());
                        }
                    }
                    if (toEdit == null) {
                        System.out.println("Sorry, that is not a valid movie ID.");
                    } else {
                        System.out.println(admin.displayEditStringPrompt());
                        String editString = "";

                        System.out.print("Title: ");
                        editString += scan.nextLine().replaceAll("\n", "");

                        if (editString.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            exit = true;
                            this.reboot = true;
                            return;
                        }

                        editString += ",";

                        System.out.print("ID: ");
                        editString += scan.nextLine().replaceAll("\n", "");

                        if (editString.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            exit = true;
                            this.reboot = true;
                            return;
                        }

                        editString += ",";

                        System.out.print("Classification: ");
                        editString += scan.nextLine().replaceAll("\n", "");

                        if (editString.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            exit = true;
                            this.reboot = true;
                            return;
                        }

                        editString += ",";

                        System.out.print("Runtime: ");
                        editString += scan.nextLine().replaceAll("\n", "");

                        if (editString.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            exit = true;
                            this.reboot = true;
                            return;
                        }

                                editString += ",";

                        System.out.print("Director: ");
                        editString += scan.nextLine().replaceAll("\n", "");

                        if (editString.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            exit = true;
                            this.reboot = true;
                            return;
                        }

                        editString += ",";

                        //Cast = TODO
                        editString += ",";

                        System.out.print("Release Date (ddmmyyyy): ");
                        editString += scan.nextLine().replaceAll("\n", "");

                        if (editString.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            exit = true;
                            this.reboot = true;
                            return;
                        }

                        editString += ",";

                        System.out.print("Synopsis: ");
                        editString += scan.nextLine().replaceAll("\n", "");

                        if (editString.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            exit = true;
                            this.reboot = true;
                            return;
                        }

                        if (admin.editMovie(toEdit, editString)) {
                            System.out.println(admin.displayCompletedEdit()); 
                        } else {
                            System.out.println("ERROR");
                        }
                    }
                }
            
            // VIEW CANCELLATIONS
            } else if (response.equals("3")) {
                System.out.println(admin.displayCancellations());

            // ADD / REMOVE ACCOUNTS
            } else if (response.equals("4") && currentUser.getType().equals("manager")) {
                System.out.println(admin.displayManageStaffPrompt());
                response = scan.nextLine();
                if (response.equals("1")){
                    boolean registered = false;

                    while (!registered) {
                        System.out.println("Please enter a username (Must be alphanumeric with no spaces): ");
                        String username = scan.nextLine();

                        if (admin.getUserByUsername(username) == null) {
                            System.out.println("Valid username! Please enter a password: ");
                            String password = PasswordField.readPassword();
                            System.out.println("Please re-enter your password: ");
                            String password2 = PasswordField.readPassword();
                            if (password2.equals(password)) {
                                System.out.println("Passwords match!");
                                admin.addUser(new User(username,password,"staff"));
                                registered = true;
                                
                            } else {
                                System.out.println("Passwords do not match. Please try again.");
                            }
                            
                        } else {
                            System.out.println("Username is already taken.");
                        }
                        
                    }
                    
                } else if (response.equals("2")) {
                    boolean validUsername = false;
                    while(!validUsername) {
                        System.out.println("Please type in the username of the staff account you wish to remove: ");
                        String username = scan.nextLine();
                        if (admin.getStaffByUsername(username) != null){
                            admin.removeUser(username);
                            validUsername = true;
                        } else {
                            System.out.println("Error, user doesn't exist, or is not a staff user.");
                        }
                    }
                } else if (response.toLowerCase().equals("c")) {
                    System.out.println("Cancelling, goodbye!");
                    Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                    exit = true;
                    this.reboot = true;
                    return;
                }
            
            // ADD / REMOVE ACCOUNTS BUT NOT MANAGER
            } else if (response.equals("4") && currentUser.getType().equals("staff")) {
                System.out.println("You do not have access to this function.");

            // EDIT TIMETABLING
            } else if (response.equals("5")) {

                System.out.println(admin.displayTimetablingMenu());
                response = scan.nextLine();


                // EDIT TIMETABLE
                if (response.equals("1")) {
                    System.out.println(admin.displayInitialTimetableEdit());

                    String loc = "";
                    boolean invalid = true;

                    while (invalid){
                        response = scan.nextLine();
                        if (response.equals("1")) {
                            loc = "Bondi";
                            invalid = false;
                        } else if (response.equals("2")) {
                            loc = "GeorgeStreet";
                            invalid = false;
                        } else if (response.equals("3")) {
                            loc = "Chatswood";
                            invalid = false;
                        } else if (response.equals("4")) {
                            loc = "Hurstville";
                            invalid = false;
                        } else if (response.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            invalid = false;
                            this.reboot = true;
                            return;
                        } else {
                            System.out.println("Invalid input: try again.");
                        }
                    }

                    Cinema cThis = null;

                    ArrayList<Cinema> cinemas = admin.storeCinemas();
                    for (Cinema cinema : cinemas) {
                        if (cinema.getName().equals(loc)) {
                            cThis = cinema;
                            break;
                        }
                    }

                    int i = 1;
                    int dayNum = 0;
                    String[] dayName = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                    ArrayList<Viewing> allViewings = new ArrayList<Viewing>();
                    for (ArrayList<Viewing> day : cThis.viewings) {
                        System.out.println(dayName[dayNum]);
                        for (Viewing viewing : day) {
                            allViewings.add(viewing);
                            System.out.print("(");
                            System.out.print(i);
                            System.out.print(") ");
                            System.out.println(viewing.toString());
                            i += 1;
                        }
                        System.out.println(admin.lineBreak());
                        dayNum += 1;
                    }
                    i -= 1;

                    System.out.println("If you would like to edit a specific viewing, please type the number associated with it");
                    int checkNum = -1;
                    while (true){
                        response = scan.nextLine();
                        checkNum = -1;
                        try {
                            checkNum = Integer.valueOf(response);
                            if (checkNum > i) {
                                System.out.println("Invalid input. Please try again.");
                            } else if (checkNum < 0) {
                                System.out.println("Invalid input. Please try again.");
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please try again.");
                        }
                    }

                    Viewing editingViewing = allViewings.get(checkNum - 1);

                    String existingViewingString = "";
                    existingViewingString += editingViewing.getTimeOfDay();
                    existingViewingString += ",";
                    existingViewingString += editingViewing.getDay();
                    existingViewingString += ",";
                    existingViewingString += editingViewing.getMovie().getID();
                    existingViewingString += ",";
                    existingViewingString += editingViewing.getScreenType();

                    System.out.println();
                    System.out.print("You have selected: ");
                    System.out.println(editingViewing.toString());
                    
                
                    boolean finished = false;
                    int time = -1;
                    Movie mov = null;
                    int screen = -1;
                    while (!finished){
                        System.out.println(admin.displayViewingSpecificEditPrompt());
                        response = scan.nextLine().toLowerCase();

                        if (response.equals("1")) {
                            
                            System.out.println(admin.displayMovies());
                            System.out.println("Which movie would you like to change to? (ID)");    

                            while (true) {
                                response = scan.nextLine();

                                mov = admin.getMovieById(response);
                                if (mov == null) {
                                    System.out.println("Invalid movie selection.");
                                    continue;
                                } else {
                                    break;
                                }
                            }
                            
                        } else if (response.equals("2")) {
                            System.out.println("What time would you like to change it to?");
                            System.out.println(admin.displayTimeChecks());
                            
                            while (true) {
                                response = scan.nextLine();
                                if (response.equals("1")) {
                                    time = 0;
                                    break;
                                } else if (response.equals("2")) {
                                    time = 1;
                                    break;
                                } else if (response.equals("3")) {
                                    time = 2;
                                    break;
                                } else if (response.toLowerCase().equals("c")) {
                                    System.out.println("Cancelling, goodbye!");
                                    Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                                    this.reboot = true;
                                    return;
                                } else {
                                    System.out.println("Invalid input. Please try again.");
                                    continue;
                                }
                            }

                        } else if (response.equals("3")) {
                            System.out.println("What screen would you like to change it to?");
                            System.out.println(admin.displayScreenChecks());
                            
                            while (true) {
                                response = scan.nextLine();
                                if (response.equals("1")) {
                                    screen = 0;
                                    break;
                                } else if (response.equals("2")) {
                                    screen = 1;
                                    break;
                                } else if (response.equals("3")) {
                                    screen = 2;
                                    break;
                                } else if (response.toLowerCase().equals("c")) {
                                    System.out.println("Cancelling, goodbye!");
                                    Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                                    this.reboot = true;
                                    return;
                                } else {
                                    System.out.println("Invalid input. Please try again.");
                                    continue;
                                }
                            }

                        } else if (response.equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            finished = true;
                            this.reboot = true;
                            return;

                        } else if (response.equals("r")) {
                            finished = true;
                            System.out.println("CONFIRMATION - Changes to be made are: ");

                            System.out.print("Movie: ");
                            try {
                                System.out.println(mov.getTitle());
                            } catch (NullPointerException e) {
                                System.out.println("No change");
                            }

                            System.out.print("Time: ");
                            if (time == 0) {
                                System.out.println("Morning");
                            } else if (time == 1) {
                                System.out.println("Midday");
                            } else if (time == 2) {
                                System.out.println("Evening");
                            } else {
                                System.out.println("No change");
                            }

                            System.out.print("Screen: ");
                            if (screen == 0) {
                                System.out.println("Bronze");
                            } else if (time == 1) {
                                System.out.println("Silver");
                            } else if (time == 2) {
                                System.out.println("Gold");
                            } else {
                                System.out.println("No change");
                            }

                            // MAKE ALL CHANGES IN DATABASE & CHECK IF VALID
                            String locPath = Paths.get(this.currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", loc, "Timetable.csv").toString();
                            if (time >= 0 && time <= 2) {
                            editingViewing.setTimeOfDay(time);
                            }

                            if (screen >= 0 && screen <= 2) {
                                editingViewing.setScreen(screen);
                            }

                            if (mov != null) {
                                editingViewing.setMovie(mov);
                            }

                            String timetableString = "";
                            timetableString += editingViewing.getTimeOfDay();
                            timetableString += ",";
                            timetableString += editingViewing.getDay();
                            timetableString += ",";
                            timetableString += editingViewing.getMovie().getID();
                            timetableString += ",";
                            timetableString += editingViewing.getScreenType();

                            try {
                                Scanner sc = new Scanner(new File(locPath));
                                sc.nextLine();
                                String next;
                                String checkTime;
                                String checkDay;
                                String checkScreen;
                                boolean addition = true;
                                while (sc.hasNextLine()) {
                                    next = sc.nextLine();

                                    if (next.equals(existingViewingString)) {
                                        continue;
                                    }

                                    checkTime = next.split(",")[0];
                                    checkDay = next.split(",")[1];
                                    checkScreen = next.split(",")[3];

                                    if (checkTime.equals(String.valueOf(editingViewing.getTimeOfDay())) &&
                                        checkDay.equals(editingViewing.getDay()) &&
                                        checkScreen.equals(String.valueOf(editingViewing.getScreenType()))) {
                                        
                                            addition = false;
                                            break;

                                    }
                                }

                                if (!addition) {
                                    System.out.println("Sorry, this viewing conflicts with an existing viewing. Please try again.");
                                } else {
                                    try {
                                        String csv = "";
                                        try {
                                            Scanner sc2 = new Scanner(new File(locPath));
                                            String line;
                                            while (sc2.hasNextLine()) {
                                                line = sc2.nextLine().trim();
                                                if (!(line.equals(existingViewingString))) {
                                                    csv += line;
                                                    csv += "\n";
                                                }
                                            }
                                            csv += timetableString;
                                            csv += "\n";
                                        } catch (IOException e) {
                                            System.out.println(e);
                                        }

                                        FileWriter fw = new FileWriter(locPath, false);
                                        fw.append(csv);
                                        fw.flush();
                                        fw.close();
                                        admin.parseAll();
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
                                }


                            } catch (IOException e) {
                                System.out.println(e);
                            }

                        } else {
                            System.out.println("Sorry, not a valid input. Please try again.");
                            continue;
                        }
                    }

                    // ADD NEW MOVIE
                } else if (response.equals("2")) {
                    
                    
                    System.out.println(admin.displayInitialTimetableEdit());

                    String loc = "";
                    boolean invalid = true;

                    while (invalid){
                        response = scan.nextLine();
                        if (response.equals("1")) {
                            loc = "Bondi";
                            invalid = false;
                        } else if (response.equals("2")) {
                            loc = "GeorgeStreet";
                            invalid = false;
                        } else if (response.equals("3")) {
                            loc = "Chatswood";
                            invalid = false;
                        } else if (response.equals("4")) {
                            loc = "Hurstville";
                            invalid = false;
                        } else if (response.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            invalid = false;
                            this.reboot = true;
                            return;
                        } else {
                            System.out.println("Invalid input: try again.");
                        }
                    }

                    Cinema cThis = null;

                    ArrayList<Cinema> cinemas = admin.storeCinemas();
                    for (Cinema cinema : cinemas) {
                        if (cinema.getName().equals(loc)) {
                            cThis = cinema;
                            break;
                        }
                    }

                    int i = 1;
                    int dayNum = 0;
                    String[] dayName = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                    ArrayList<Viewing> allViewings = new ArrayList<Viewing>();
                    for (ArrayList<Viewing> day : cThis.viewings) {
                        System.out.println(dayName[dayNum]);
                        for (Viewing viewing : day) {
                            allViewings.add(viewing);
                            System.out.print("(");
                            System.out.print(i);
                            System.out.print(") ");
                            System.out.println(viewing.toString());
                            i += 1;
                        }
                        System.out.println(admin.lineBreak());
                        dayNum += 1;
                    }
                    i -= 1;

                    System.out.println();

                    String id;
                    String time;
                    String day;
                    String screen;

                    int timeInt;
                    int screenInt;

                    Movie movThis;

                    while (true) {
                        id = null;
                        time = null;
                        day = null;
                        screen = null;
                        timeInt = -1;
                        screenInt = -1;
                        movThis = null;

                        System.out.println("What movie would you like to add? (ID)");
                        id = scan.nextLine();

                        System.out.println("When would you like this movie to screen? (Morning, Midday or Evening)");
                        time = scan.nextLine();

                        System.out.println("What day would you like this to screen on? (Monday, Tuesday etc.)");
                        day = scan.nextLine();

                        System.out.println("What screen would you like this to screen on? (Bronze, Silver, Gold)?");
                        screen = scan.nextLine();

                        if (time.toLowerCase().equals("morning")) {
                            timeInt = 0;
                        } else if (time.toLowerCase().equals("midday")) {
                            timeInt = 1;
                        } else if (time.toLowerCase().equals("evening")) {
                            timeInt = 2;
                        } else {
                            System.out.println("Not a valid input. Try again.");
                            continue;
                        }

                        if (screen.toLowerCase().equals("bronze")) {
                            screenInt = 0;
                        } else if (screen.toLowerCase().equals("silver")) {
                            screenInt = 1;
                        } else if (screen.toLowerCase().equals("gold")) {
                            screenInt = 2;
                        } else {
                            System.out.println("Not a valid input. Try again.");
                            continue;
                        }

                        for (Movie movie : admin.movies) {
                            if (movie.getID().equals(id)) {
                                movThis = movie;
                                break;
                            }
                        }

                        if (movThis == null) {
                            System.out.println("Movie not found. Try again.");
                            continue;
                        }

                        if (id.equals("") || time.equals("") || day.equals("") || screen.equals("")) {
                            System.out.println("Not a valid input. Try again.");
                            continue;
                        } else {
                            break;
                        }
                    }

                    Viewing newViewing = new Viewing(cThis, movThis, screenInt, timeInt, day);

                    String newViewingString = "";
                    newViewingString += newViewing.getTimeOfDay();
                    newViewingString += ",";
                    newViewingString += newViewing.getDay();
                    newViewingString += ",";
                    newViewingString += newViewing.getMovie().getID();
                    newViewingString += ",";
                    newViewingString += newViewing.getScreenType();

                    System.out.println();
                    System.out.print("You are going to add: ");
                    System.out.println(newViewing.toString());
                    
                    // MAKE ALL CHANGES IN DATABASE & CHECK IF VALID
                    String locPath = Paths.get(this.currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", loc, "Timetable.csv").toString();

                    try {
                        Scanner sc = new Scanner(new File(locPath));
                        sc.nextLine();
                        String next;
                        String checkTime;
                        String checkDay;
                        String checkScreen;
                        boolean addition = true;
                        while (sc.hasNextLine()) {
                            next = sc.nextLine();

                            if (next.equals(newViewingString)) {
                                continue;
                            }

                            checkTime = next.split(",")[0];
                            checkDay = next.split(",")[1];
                            checkScreen = next.split(",")[3];

                            if (checkTime.equals(String.valueOf(newViewing.getTimeOfDay())) &&
                                checkDay.equals(newViewing.getDay()) &&
                                checkScreen.equals(String.valueOf(newViewing.getScreenType()))) {
                                    addition = false;
                                    break;

                            }
                        }

                        if (!addition) {
                            System.out.println("Sorry, this viewing conflicts with an existing viewing. Please try again.");
                        } else {
                            try {
                                String csv = "";
                                try {
                                    Scanner sc2 = new Scanner(new File(locPath));
                                    String line;
                                    while (sc2.hasNextLine()) {
                                        line = sc2.nextLine().trim();
                                        if (!(line.equals(newViewingString))) {
                                            csv += line;
                                            csv += "\n";
                                        }
                                    }
                                    csv += newViewingString;
                                    csv += "\n";
                                } catch (IOException e) {
                                    System.out.println(e);
                                }

                                FileWriter fw = new FileWriter(locPath, false);
                                fw.append(csv);
                                fw.flush();
                                fw.close();
                                admin.parseAll();
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                        }


                    } catch (IOException e) {
                        System.out.println(e);
                    }


                } else if (response.equals("3")) {

                    System.out.println(admin.displayInitialTimetableEdit());

                    String loc = "";
                    boolean invalid = true;

                    while (invalid){
                        response = scan.nextLine();
                        if (response.equals("1")) {
                            loc = "Bondi";
                            invalid = false;
                        } else if (response.equals("2")) {
                            loc = "GeorgeStreet";
                            invalid = false;
                        } else if (response.equals("3")) {
                            loc = "Chatswood";
                            invalid = false;
                        } else if (response.equals("4")) {
                            loc = "Hurstville";
                            invalid = false;
                        } else if (response.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            invalid = false;
                            this.reboot = true;
                            return;
                        } else {
                            System.out.println("Invalid input: try again.");
                        }
                    }

                    Cinema cThis = null;

                    ArrayList<Cinema> cinemas = admin.storeCinemas();
                    for (Cinema cinema : cinemas) {
                        if (cinema.getName().equals(loc)) {
                            cThis = cinema;
                            break;
                        }
                    }

                    int i = 1;
                    int dayNum = 0;
                    String[] dayName = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                    ArrayList<Viewing> allViewings = new ArrayList<Viewing>();
                    for (ArrayList<Viewing> day : cThis.viewings) {
                        System.out.println(dayName[dayNum]);
                        for (Viewing viewing : day) {
                            allViewings.add(viewing);
                            System.out.print("(");
                            System.out.print(i);
                            System.out.print(") ");
                            System.out.println(viewing.toString());
                            i += 1;
                        }
                        System.out.println(admin.lineBreak());
                        dayNum += 1;
                    }
                    i -= 1;

                    System.out.println("Type the number of the viewing you would like to remove");
                    int checkNum = -1;
                    while (true){
                        response = scan.nextLine();
                        checkNum = -1;
                        try {
                            checkNum = Integer.valueOf(response);
                            if (checkNum > i) {
                                System.out.println("Invalid input. Please try again.");
                            } else if (checkNum < 0) {
                                System.out.println("Invalid input. Please try again.");
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please try again.");
                        }
                    }

                    Viewing editingViewing = allViewings.get(checkNum - 1);

                    String existingViewingString = "";
                    existingViewingString += editingViewing.getTimeOfDay();
                    existingViewingString += ",";
                    existingViewingString += editingViewing.getDay();
                    existingViewingString += ",";
                    existingViewingString += editingViewing.getMovie().getID();
                    existingViewingString += ",";
                    existingViewingString += editingViewing.getScreenType();
                    String locPath = Paths.get(this.currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations", loc, "Timetable.csv").toString();

                    try {
                        String csv = "";
                        try {
                            Scanner sc2 = new Scanner(new File(locPath));
                            String line;
                            while (sc2.hasNextLine()) {
                                line = sc2.nextLine().trim();
                                if (!(line.equals(existingViewingString))) {
                                    csv += line;
                                    csv += "\n";
                                }
                            }
                        } catch (IOException e) {
                            System.out.println(e);
                        }

                        System.out.print("Removing ");
                        System.out.print(editingViewing.toString());
                        System.out.println("...");

                        FileWriter fw = new FileWriter(locPath, false);
                        fw.append(csv);
                        fw.flush();
                        fw.close();
                        admin.parseAll();
                    } catch (IOException e) {
                        System.out.println(e);
                    }


                } else if (response.toLowerCase().equals("c")) {
                    System.out.println("Cancelling, goodbye!");
                    Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                    this.reboot = true;
                    return;
                }

            // CANCELLATION
            } else if (response.equals("c")) {
                System.out.println("Cancelling, goodbye!");
                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                exit = true;
                this.reboot = true;
                return;
            }
        }
    }  
}

class IdleTimer {
    public Timer timer;
    public IdleTimer () {
        this.timer = new java.util.Timer();
    }

    public void startTimer(){
        timer.schedule( 
            new java.util.TimerTask() {
                @Override
                public void run() {

                    timer.cancel();
                }
            }, 
            5000
        );
    }

    public void stopTimer(){
        timer.cancel();
    }

    public void resetTimer(){
        stopTimer();
        startTimer();
    }
}


class PasswordField {

    /**
     *@param prompt The prompt to display to the user
     *@return The password as entered by the user
     */
    public static String readPassword() {
       EraserThread et = new EraserThread("");
       Thread mask = new Thread(et);
       mask.start();
 
       BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
       String password = "";
 
       try {
          password = in.readLine();
       } catch (IOException ioe) {
         ioe.printStackTrace();
       }
       // stop masking
       et.stopMasking();
       // return the password entered by the user
       return password;
    }
 }

class EraserThread implements Runnable {



    private boolean stop;
  
    /**
     *@param The prompt displayed to the user
     */
    public EraserThread(String prompt) {
        System.out.print(prompt);
    }
 
    /**
     * Begin masking...display asterisks (*)
     */
    public void run () {
       stop = true;
       while (stop) {
          System.out.print("\010*");
      try {
         Thread.currentThread().sleep(1);
          } catch(InterruptedException ie) {
             ie.printStackTrace();
          }
       }
    }
 
    /**
     * Instruct the thread to stop masking
     */
    public void stopMasking() {
       this.stop = false;
    }
 }

