package movie;

import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.Console;

public class Engine {

    public static void main(String[] args) {

        // database filepaths

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();
        String cancellationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "cancellations.csv").toString();

        String adminPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "admin").toString();
        String customerPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "customer").toString();
        String homePagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "init").toString();
        String guestPagePath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "pages", "guest").toString();

        //create home page
        
        HomePage home = new HomePage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, homePagePath);


        System.out.println(home.displayInitial());

        Scanner scan = new Scanner(System.in);
        boolean endProgram = false;
        boolean loginComplete = false;
        User currentUser = null;
        boolean running = true;

        // prompt for login

        while (!loginComplete){
            String response = scan.nextLine();

            if (response.toLowerCase().equals("l")){
                loginComplete = true;

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
                    continue;
                }
                else{
                    loginComplete = true;
                }
                


            }

            else if (response.toLowerCase().equals("g")){
                loginComplete = true;
                //proceed as guest
            }
            else if (response.toLowerCase().equals("c")){
                System.out.println("Quitting. Goodbye!");
                Engine.logCancellation(null, "user cancellation", cancellationPath);
                running = false;
                break;
            }

            else{
                System.out.println(home.displayInitial());;
            }

        }


        
        while (running){
            if (currentUser == null){ //GUEST EXPERIENCE
                GuestPage guest = new GuestPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, guestPagePath);
                //System.out.println(guest.displayInitial());
    
                Movie selectedMovie = null;
                boolean bookingComplete = false;
                while (!bookingComplete) {
                    
                    System.out.println(guest.displayInitial());
                    String response = scan.nextLine();
            
                    if(response.toLowerCase().equals("f")){
                        //System.out.println("Write filter code here!");
                        bookingComplete = true;

                        //filtering
                        try{

                        while (true){
                            guest.displayFilterMessage();
                            ArrayList<String> filteredInput = guest.readFilterInput();
                            if (filteredInput == null){
                                System.out.println("Would you like to filter again?\nPress y to filter again or any key to quit filter");
                                //reads user filter input
                                Scanner nullInputScan = new Scanner(System.in);
                                String option = nullInputScan.nextLine();
                                if (option.toLowerCase().equals("y")){
                                    continue;
                                }
                                else{
                                    break;
                                }
                            }
                            else if (filteredInput != null){
                                guest.filterMovies(filteredInput.get(0), filteredInput.get(1), filteredInput.get(2));
                                System.out.println("If you would like to book a movie, please exit the filter page and login.\n\n");
                            }
                        }
                        }catch (Exception e){
                            System.out.println("something went wrong in engine guest filter");
                        }
                        System.out.println("ended filter");
                        continue;
                    }
                    if (response.toLowerCase().equals("r")){

                        boolean registered = false;

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
                                    guest.addUser(new User(username,password,"customer"));
                                    registered = true;
                                    
                                } else {
                                    System.out.println("Passwords do not match. Please try again.");
                                }
                                
                            } else {
                                System.out.println("Username is already taken.");
                            }
                            
                        }
                        continue;
                    }
    
                    if(response.toLowerCase().equals("b")) {
                        //guest registration here and then booking
                        
                        bookingComplete = true;
                        if (selectedMovie != null) {
                            //register() and book()
                            System.out.println(
                                "To book tickets to "+selectedMovie.getTitle()+", you must have an account."+
                                "\n if you would like to proceed and register, type \"r\"." +"\nOtherwise if you would like to cancel, type anything.");
                            bookingComplete = true;
                        } else {
                            System.out.println("No movie selected, please select a movie and try again!");
                        }
                    }
                    else {
                        selectedMovie = guest.getMovieById(response);
                        if (selectedMovie != null){
                            System.out.println("Selected Movie: "+selectedMovie.getTitle()+"\n");
                            System.out.println(selectedMovie.getSynopsis());
                            System.out.println("\nIf you would like to book this movie, type \"b\".\nTo view another movie, type in its ID.");
    
                        } else {
                            System.out.println("Movie not found! Please try again");
                        }
    
                    }
                }
            }
            else if (currentUser.getType().equals("customer")){
                CustomerPage customer = new CustomerPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, customerPagePath, currentUser);

                System.out.println(customer.displayInitial());

                boolean bookingComplete = false;
                Movie selectedMovie = null;
                while (!bookingComplete) {
            
                    String response = scan.nextLine();
            
                    if(response.toLowerCase().equals("f")){
                        //System.out.println("Write filter code here!");
                        bookingComplete = true;
                        //filtering
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
                                    else{
                                        break;
                                    }
                                }
                                else if (filteredInput != null){
                                    customer.filterMovies(filteredInput.get(0), filteredInput.get(1), filteredInput.get(2));
                                    System.out.println("If you would like to book a movie, please exit the filter page and login.\n\n");
                                }
                            }
                        }catch (Exception e){
                            System.out.println("something went wrong in engine guest filter");
                        }
                        continue;
                    }

                    if(response.toLowerCase().equals("b")) {
                        //movie booking shit here
                        if (selectedMovie != null) {
                            //book()
                            System.out.println("Booking "+selectedMovie.getTitle());
                            bookingComplete = true;
                        } else {
                            System.out.println("No movie selected, please select a movie and try again!");
                        }
                        
                    }
                    else {
                        selectedMovie = customer.getMovieById(response);
                        if (selectedMovie != null){
                            System.out.println("Selected Movie: "+selectedMovie.getTitle());
                            System.out.println("");
                            System.out.println(selectedMovie.getSynopsis());
                            System.out.println("\nIf you would like to book this movie, type \"b\".\nTo view another movie, type in its ID.");

                        } else {
                            System.out.println("Movie not found! Please try again");
                        }

                    }
                }
            }
            //ADMIN and STAFF EXPERIENCE
            else if(currentUser.getType().equals("staff") || currentUser.getType().equals("manager")){
                AdminPage admin = new AdminPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, adminPagePath, currentUser);
                System.out.println(admin.displayInitial());

                
                boolean functionChosen = false;
                while (!functionChosen) {
                    String response = scan.nextLine();
                    if (response.equals("1")) {
                        functionChosen = true;
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
                            functionChosen = true;
                            break;
                        }

                    } else if (response.equals("2")) {
                        functionChosen = true;
                        System.out.println(admin.displayMovieEditPrompt());
                        System.out.println(admin.displayMovies());
                        System.out.println();
                        String movieID = scan.nextLine();

                        if (movieID.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            functionChosen = true;
                            break;
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
                                functionChosen = true;
                                break;
                            }

                            editString += ",";

                            System.out.print("ID: ");
                            editString += scan.nextLine().replaceAll("\n", "");

                            if (editString.toLowerCase().equals("c")) {
                                System.out.println("Cancelling, goodbye!");
                                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                                functionChosen = true;
                                break;
                            }

                            editString += ",";

                            System.out.print("Classification: ");
                            editString += scan.nextLine().replaceAll("\n", "");

                            if (editString.toLowerCase().equals("c")) {
                                System.out.println("Cancelling, goodbye!");
                                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                                functionChosen = true;
                                break;
                            }

                            editString += ",";

                            System.out.print("Runtime: ");
                            editString += scan.nextLine().replaceAll("\n", "");

                            if (editString.toLowerCase().equals("c")) {
                                System.out.println("Cancelling, goodbye!");
                                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                                functionChosen = true;
                                break;
                            }

                                    editString += ",";

                            System.out.print("Director: ");
                            editString += scan.nextLine().replaceAll("\n", "");

                            if (editString.toLowerCase().equals("c")) {
                                System.out.println("Cancelling, goodbye!");
                                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                                functionChosen = true;
                                break;
                            }

                            editString += ",";

                            //Cast = TODO
                            editString += ",";

                            System.out.print("Release Date (ddmmyyyy): ");
                            editString += scan.nextLine().replaceAll("\n", "");

                            if (editString.toLowerCase().equals("c")) {
                                System.out.println("Cancelling, goodbye!");
                                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                                functionChosen = true;
                                break;
                            }

                            editString += ",";

                            System.out.print("Synopsis: ");
                            editString += scan.nextLine().replaceAll("\n", "");

                            if (editString.toLowerCase().equals("c")) {
                                System.out.println("Cancelling, goodbye!");
                                Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                                functionChosen = true;
                                break;
                            }

                            if (admin.editMovie(toEdit, editString)) {
                                System.out.println(admin.displayCompletedEdit()); 
                            } else {
                                System.out.println("ERROR");
                            }
                        }


                    } else if (response.equals("4") && currentUser.getType().equals("manager")) {
                        functionChosen = true;
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
                            continue;
                        } else if (response.equals("2")) {
                            boolean validUsername = false;
                            while(!validUsername) {
                                System.out.println("Please type in the username of the staff account you wish to remove: ");
                                String username = scan.nextLine();
                                if(admin.getStaffByUsername(username) != null){
                                    admin.removeUser(username);
                                    validUsername = true;
                                } else {
                                    System.out.println("Error, user doesn't exist, or is not a staff user.");
                                }
                            }
                        } else if (response.toLowerCase().equals("c")) {
                            System.out.println("Cancelling, goodbye!");
                            Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                            functionChosen = true;
                            break;
                        }
                        
                    } else if (response.equals("4") && currentUser.getType().equals("staff")) {
                        System.out.println("You do not have access to this function.");
                    } else if (response.equals("c")) {
                        System.out.println("Cancelling, goodbye!");
                        Engine.logCancellation(currentUser, "user cancellation", cancellationPath);
                        functionChosen = true;
                    }
                }
            }
            running = false;
        }
    }

    public static boolean logCancellation(User user, String reason, String cancellationPath) {
        String userName;

        if (user == null) {
            userName = "anon";
        } else {
            userName = user.getName();
        }
        String datetime = java.util.Calendar.getInstance().getTime().toString();
        String cancellationString = "\n" + datetime + "," + userName + "," + reason;

        try {
            FileWriter fw = new FileWriter(cancellationPath, true);
            fw.append(cancellationString);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
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

