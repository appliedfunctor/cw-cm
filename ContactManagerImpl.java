import java.util.*;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImpl implements ContactManager{

    private int lastContactId;
    private Map<Integer, Contact> contacts;

    {
        lastContactId = 0;
        contacts = new HashMap<>();
    }

    public ContactManagerImpl(){
        //load data from file if exists
    }


    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        return 0;
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        return null;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        return null;
    }

    @Override
    public Meeting getMeeting(int id) {
        return null;
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        return null;
    }

    @Override
    public List<Meeting> getMeetingListOn(Calendar date) {
        return null;
    }

    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        return null;
    }

    @Override
    public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        return 0;
    }

    @Override
    public PastMeeting addMeetingNotes(int id, String text) {
        return null;
    }

    @Override
    public int addNewContact(String name, String notes) {
        int id = getNewContactId();
        System.out.println("id: "+id);
        contacts.put(id, new ContactImpl(id, name, notes));
        return id;
    }

    private int getNewContactId() {
        this.lastContactId++;
        return this.lastContactId;
    }

    @Override
    public Set<Contact> getContacts(String name) {
        return null;
    }

    @Override
    public Set<Contact> getContacts(int... ids) {
        return null;
    }

    @Override
    public void flush() {

    }
}
