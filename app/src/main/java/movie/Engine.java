package movie;

import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Engine {

    public static void main(String[] args) {

        // database filepaths

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie", "Databases", "members.json").toString();

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

        // prompt for login

        while (!loginComplete){
            String response = scan.nextLine();

            if (response.toLowerCase().equals("l")){
                loginComplete = true;

                System.out.println(home.displayLogIn());
                String username = scan.nextLine();

                // masking password
                String prompt = home.displayLogIn();
                EraserThread et = new EraserThread(prompt);
                Thread mask = new Thread(et);
                mask.start();

                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String password = "";

                try {
                    password = in.readLine();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                et.stopMasking();
                
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
            else if (response.toLowerCase().equals("q")){
                System.out.println("Quitting. Goodbye!");
                break;
            }

            else{
                System.out.println(home.displayInitial());;
            }

        }


        boolean running = true;
        while (running){
            if (currentUser == null){ //GUEST EXPERIENCE
                GuestPage guest = new GuestPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, guestPagePath);
                System.out.println(guest.displayInitial());
    
                Movie selectedMovie = null;
                boolean bookingComplete = false;
                while (!bookingComplete) {
            
                    String response = scan.nextLine();
            
                    if(response.toLowerCase().equals("f")){
                        System.out.println("Write filter code here!");
                        bookingComplete = true;
    
    
                        
                        //filter()
                    }
    
                    if(response.toLowerCase().equals("b")) {
                        //guest registration here and then booking
                        
                        bookingComplete = true;
                        if (selectedMovie != null) {
                            //register() and book()
                            System.out.println("To book tickets to "+selectedMovie.getTitle()+", you must have an account.");
                            bookingComplete = true;
                        } else {
                            System.out.println("No movie selected, please select a movie and try again!");
                        }
                    }
                    else {
                        selectedMovie = guest.getMovieById(response);
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
            else if (currentUser.getType().equals("customer")){
                CustomerPage customer = new CustomerPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, customerPagePath, currentUser);

                System.out.println(customer.displayInitial());

                boolean bookingComplete = false;
                Movie selectedMovie = null;
                while (!bookingComplete) {
            
                    String response = scan.nextLine();
            
                    if(response.toLowerCase().equals("f")){
                        System.out.println("Write filter code here!");
                        bookingComplete = true;
                        //filter()
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

            else if(currentUser.getType().equals("staff") || currentUser.getType().equals("manager")){
                AdminPage admin = new AdminPage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath, adminPagePath, currentUser);
                System.out.println(admin.displayInitial());
            }


            running = false;


        }


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