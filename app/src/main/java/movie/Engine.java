package movie;

import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.Console;

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

    private IdleTimer idler = new IdleTimer();
    private User currentUser = null;

    public static void main(String[] args) {
        Engine e = new Engine();

        e.reboot = false;

        while (true) {
            e.mainLoop();

            if (!e.running) {
                e.idler.stopTimer();
                return;
            }

            if (e.reboot) {
                e.idler.stopTimer();
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

        System.out.println(home.displayLogIn());
        String username = scan.nextLine();

        // masking password
        System.out.println(home.displayLogIn());

        /*EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";

        try {
            password = in.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        et.stopMasking();*/

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
            System.out.println(guest.lineBreak());
            
            response = scan.nextLine();

            // BLANK CHECKS
            if (response == null || response.equals("")) {
                System.out.println(guest.displayInvalidInput());
                continue;
            }
            
            // FILTER FUNCTIONALITY
            if (response.toLowerCase().equals("f")) {   
                try {
                    while ( true ) {
                        guest.displayFilterMessage();
                        ArrayList<String> filteredInput = guest.readFilterInput();
                        if (filteredInput == null){
                            System.out.println("Would you like to filter again?\nPress y to filter again or any key to quit filter");
                            //reads user filter input
                            Scanner nullInputScan = new Scanner(System.in);
                            String option = nullInputScan.nextLine();

                            if (option.equals("") || option == null) {
                                System.out.println(guest.displayInvalidInput());
                                continue;
                            }
                            if (option.toLowerCase().equals("y")){
                                continue;
                            }
                            else{
                                exit = false;
                                break;
                            }
                        }
                        else if (filteredInput != null){
                            guest.filterMovies(filteredInput.get(0), filteredInput.get(1), filteredInput.get(2));
                            System.out.println("If you would like to book a movie, please exit the filter page and login.\n\n");
                        }
                    }
                } catch (Exception e){
                    System.out.println("something went wrong in engine guest filter");
                }
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
                    //register() and book()
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
                    System.out.println(selectedMovie.getSynopsis());
                    System.out.println(
                        "\nIf you would like to book this movie, type \"b\".\n"+
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
                exit = true;
                try{

                    while (true){
                        customer.displayFilterMessage();
                        ArrayList<String> filteredInput = customer.readFilterInput();
                        if (filteredInput == null){
                            System.out.println("Would you like to filter again?\nPress y to filter again or any key to quit filter");
                            //reads user filter input
                            Scanner nullInputScan = new Scanner(System.in);
                            String option = nullInputScan.nextLine();
                            if (option.toLowerCase().equals("y")){
                                continue;
                            }
                            else {
                                break;
                            }
                        }
                        else if (filteredInput != null){
                            customer.filterMovies(filteredInput.get(0), filteredInput.get(1), filteredInput.get(2));
                            System.out.println("If you would like to book a movie, please exit the filter page and login.\n\n");
                        }
                    }
                } catch (Exception e){
                    System.out.println("something went wrong in engine guest filter");
                }
            }
            else if(response.toLowerCase().equals("h")){
                clearConsole();
                System.out.println(customer.lineBreak());
                System.out.println(customer.displayInitial());
            }
            // BOOKING - TBD
            else if(response.toLowerCase().equals("b")) {
                //movie booking shit here
                if (selectedMovie != null) {
                    //book()
                    System.out.println("Booking "+selectedMovie.getTitle());
                    exit = true;
                } else {
                    System.out.println("No movie selected, please select a movie and try again!");
                }
                
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
                    System.out.println("");
                    System.out.println(selectedMovie.getSynopsis());
                    System.out.println(
                        "\nIf you would like to book this movie, type \"b\".\n"+
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

            // CANCELLATION
            } else if (response.equals("c")) {
                System.out.println("Cancelling, goodbye!");
                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                exit = true;
                this.reboot = true;
            }
        }
    }  
}

class IdleTimer {
    public Timer timer;
    public IdleTimer () {
        this.timer = new java.util.Timer();
        timer.cancel();
    }

    public void startTimer(){
        timer.schedule( 
            new java.util.TimerTask() {
                @Override
                public void run() {
                    System.out.println("You have been logged in for 5 seconds");
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

