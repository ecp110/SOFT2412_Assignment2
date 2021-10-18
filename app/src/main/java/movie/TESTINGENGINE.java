package movie;

import java.util.*;
import java.io.*;

public class TESTINGENGINE {

    public static void main (String[] args) {
        HomePage landing = new HomePage("./database/movies.csv", "a", "b", "c", "./database/users.csv");
        String input;

        Scanner sc = new Scanner(System.in);

        System.out.println(landing.displayInitial());

        input = sc.nextLine();

        if (input.equals("y")) {
            System.out.println(landing.displayLogIn());
            String username = sc.nextLine();
            System.out.println(landing.displayLogIn());
            String password = sc.nextLine();

            //Page nextPage = landing.logIn(username, password);

            //System.out.println(nextPage.displayInitial());
        }
    }
}