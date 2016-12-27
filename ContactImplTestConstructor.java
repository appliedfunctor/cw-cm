import org.junit.Test;
/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImplTestConstructor{

    private ContactImpl contact;

    @Test(expected=IllegalArgumentException.class)
    public void constructor3IDZero(){ contact = new ContactImpl(0, "", ""); }

    @Test(expected=IllegalArgumentException.class)
    public void constructor2IDZero(){
        contact = new ContactImpl(0, "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructor3IDNegative(){
        contact = new ContactImpl(-10000, "", "");
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructor2IDNegative(){
        contact = new ContactImpl(-10000, "");
    }

    @Test(expected=NullPointerException.class)
    public void constructor3NameNull(){
        contact = new ContactImpl(1, null, "");
    }

    @Test(expected=NullPointerException.class)
    public void constructor2NameNull(){
        contact = new ContactImpl(1, null);
    }

    @Test(expected=NullPointerException.class)
    public void constructor3NotesNull(){
        contact = new ContactImpl(1, "", null);
    }



}