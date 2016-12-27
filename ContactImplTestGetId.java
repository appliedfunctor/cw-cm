import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestGetId {

    private ContactImpl contact;

    @Test
    public void getIdTestMin(){
        int id = 1;
        contact = new ContactImpl(id, "");
        assertEquals(id, contact.getId());
    }

    @Test
    public void getIdTestMax(){
        int id = Integer.MAX_VALUE;
        contact = new ContactImpl(id, "");
        assertEquals(id, contact.getId());
    }
}
