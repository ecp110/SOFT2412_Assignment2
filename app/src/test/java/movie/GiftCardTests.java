package movie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class GiftCardTests{

    @Test
    public void testIsRedeemed(){
        Calendar cal = new Calendar(1, 1, 2021);
        GiftCard card = new GiftCard("0000", false, cal, cal);

        assertEquals(card.isRedeemed(), false);
    }

    @Test
    public void testGetNumber(){
        Calendar cal = new Calendar(1, 1, 2021);
        GiftCard card = new GiftCard("0000", false, cal, cal);

        assertEquals(card.getNumber(), "0000");
    }

    @Test
    public void testGetExpiry(){
        Calendar cal = new Calendar(1, 1, 2021);
        GiftCard card = new GiftCard("0000", false, cal, cal);

        assertEquals(card.getExpiry(), cal);
    }

    @Test
    public void testGetIssue(){
        Calendar cal = new Calendar(1, 1, 2021);
        GiftCard card = new GiftCard("0000", false, cal, cal);

        assertEquals(card.getIssue(), cal);
    }
}