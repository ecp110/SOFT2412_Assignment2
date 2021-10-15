import java.util.*;
import java.io.*;

public class HomePage extends Page {

    public Page() {
        super(page)
    }

    public String displayInitial() {
        String output = "";
        try {
            Scanner sc = new Scanner(new File(Page.homePageString));
            while (sc.hasNextLine()) {
                output += sc.nextLine();
                output += "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("NO FILE FOUND");
        }

        return output;
    }
    
}