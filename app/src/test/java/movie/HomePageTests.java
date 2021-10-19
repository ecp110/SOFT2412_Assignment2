package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HomePageTests {

    @Test
    public void displayInitialTest() {
        HomePage hPage = new HomePage("", "", "", "", "");
        String homePageString = "Hello! Welcome to XYZ Cinemas! We hope you are having a great day.\nIf you would like to log-in, just type \"y\".\nIf you would like to register, just type \"r\".\nIf you would like to continue as a guest, type \"g\".\n\nRemember, if at any point in your process you would like to cancel and return to this main page, just type \"c\" and hit enter."

        assertEquals(hPage.displayInitial(), homePageString);
    }

    @Test
    public void displayLogInTest() {
        HomePage hPage = new HomePage("", "", "", "", "");
        
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
        assertEquals(hPage.displayLogIn(), "Please input your username:");
        assertEquals(hPage.displayLogIn(), "Please enter your password. Remember, you won't be able to see it on the screen for security sake, but be assured that we are still keeping track!");
    }

    @Test
    public void logInTest() {
        
    }
}