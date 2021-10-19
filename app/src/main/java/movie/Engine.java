package movie;

import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Engine {

    public static void main(String[] args) {

        // database filepaths

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String movieLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie","Databases", "Movies.json").toString();
        String cinemasLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie","Databases", "Locations").toString();
        String creditCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie","Databases", "credit_cards.json").toString();
        String giftCardLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie","Databases", "gift_cards.json").toString();
        String usersLocationPath = Paths.get(currentPath.toString(), "src", "main", "java", "movie","Databases", "members.json").toString();

        //create home page

        HomePage home = new HomePage(movieLocationPath, cinemasLocationPath, creditCardLocationPath, giftCardLocationPath, usersLocationPath);
        //home.displayInitial();

        Scanner scan = new Scanner(System.in);
        boolean endProgram = false;
        boolean loginComplete = false;

        // prompt for login

        while (!loginComplete){
            System.out.println("Welcome to Fancy Cinemas! Would you like to login or proceed as a guest? \n (L) Login    (P) Proceed as Guest");
            String response = scan.nextLine();

            if (response.toLowerCase().equals("l")){
                loginComplete = true;
                System.out.println("Please enter your username: ");
                String username = scan.nextLine();

                // masking password
                String prompt = "Please enter your password: ";
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
                


            }
            else if (response.trim().equals("")){
                System.out.println("\nPlease press Y to login or press P to proceed as a guest!\n");
            }

            else if (response.toLowerCase().equals("p")){
                loginComplete = true;
                //proceed as guest
            }
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