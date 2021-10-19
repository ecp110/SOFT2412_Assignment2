package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminPageTests {

    @Test
    public void displayInitialTest() {
        AdminPage aPage = new AdminPage("", "", "", "", "", new User("user", "user", "customer"));
        String assertString = "Greetings, user!\n\nWhat would you like to do today?\n(1) ASD\n(2) ASD\n(3) ASD";

        assertEquals(aPage.displayInitial(), assertString);
    }

    @Test
    public void cancelTest() {
        AdminPage aPage = new AdminPage("", "", "", "", "", new User("user", "user", "customer"));
        String homePageString = "Hello! Welcome to XYZ Cinemas! We hope you are having a great day.\nIf you would like to log-in, just type \"y\".\nIf you would like to register, just type \"r\".\nIf you would like to continue as a guest, type \"g\".\n\nRemember, if at any point in your process you would like to cancel and return to this main page, just type \"c\" and hit enter."

        HomePage retPage = aPage.cancel();

        assertEquals(retPage.displayInitial(), homePageString);
        assertNull(aPage.getUser());
    }
}