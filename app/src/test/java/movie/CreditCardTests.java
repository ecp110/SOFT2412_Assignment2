package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class CreditCardTests{

    @Test
    public void testGetName(){
        CreditCard card = new CreditCard("Genkins", "0000000");
        assertEquals(card.getName(), "Genkins");
    }

    @Test
    public void testGetNumber(){
        CreditCard card = new CreditCard("Genkins", "0000000");
        assertEquals(card.getNumber(), "0000000");
    }
}