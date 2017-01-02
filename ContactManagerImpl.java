import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactManagerImpl implements ContactManager{

    private int lastContactId;
    private Map<Integer, Contact> contacts;

    private int lastMeetingId;
    private Map<Integer, Meeting> meetings;

    {
        lastContactId = 0;
        lastMeetingId = 0;
        contacts = new HashMap<>();
        meetings = new HashMap<>();
    }

    public ContactManagerImpl(){
        //load data from file if exists
    }


    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        validateAddNewFutureMeeting(contacts, date);
        return createNewFutureMeeting(contacts, date);
    }

    private void validateAddNewFutureMeeting(Set<Contact> contacts, Calendar date) {
        Validation.validateObjectNotNull(contacts, "Contacts");
        Validation.validateSetPopulated(contacts, "Contacts");
        Validation.validateObjectNotNull(date, "Date");
        Validation.validateDateInFuture(date);
        Validation.validateAllContactsKnown(contacts, this.contacts); //last as computationally intensive O(n)
    }

    private int createNewFutureMeeting(Set<Contact> contacts, Calendar date){
        int id = getNewMeetingId();
        Meeting meeting = new FutureMeetingImpl(id, date, contacts);
        this.meetings.put(id, meeting);
        return id;
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        Meeting meeting = this.meetings.get(id);
        if(meeting == null) return null;
        Validation.validateStateInPast(meeting.getDate());

        if(!meeting.getClass().equals(PastMeetingImpl.class)) //enforces the event must have occurred and had notes added
            return null;

        return (PastMeeting)meeting;
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Meeting meeting = this.meetings.get(id);
        if(meeting != null)
            Validation.validateStateInFuture(meeting.getDate());

        return (FutureMeeting)meeting;
    }

    @Override
    public Meeting getMeeting(int id) {
        return meetings.get(id);
    }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        Validation.validateObjectNotNull(contact, "Contact");
        Validation.validateContactKnown(contact, this.contacts); //last as computationally intensive O(n)
        return getElementsFromMapAsList(this.meetings, (k,v) -> v.getContacts().contains(contact));
    }


    @Override
    public List<Meeting> getMeetingListOn(Calendar date) {
        Validation.validateObjectNotNull(date);
        List<Meeting> meetingList = getElementsFromMapAsList(this.meetings, (k,v) -> v.getDate().equals(date));
        return meetingList;
    }

    @Override
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        return null;
    }

    @Override
    public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        validateAddNewPastMeeting(contacts, date, text);
        return createNewPastMeeting(contacts, date, text);
    }

    private void validateAddNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
        Validation.validateObjectNotNull(contacts, "Contacts");
        Validation.validateObjectNotNull(date, "Date");
        Validation.validateDateInPast(date);
        Validation.validateObjectNotNull(text, "Text");
        Validation.validateAllContactsKnown(contacts, this.contacts); //last as computationally intensive O(n)
    }

    private int createNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        int id = getNewMeetingId();
        Meeting meeting = new PastMeetingImpl(id, date, contacts, text);
        this.meetings.put(id, meeting);
        return id;
    }

    @Override
    public PastMeeting addMeetingNotes(int id, String text) {
        Validation.validateObjectNotNull(text, "Text");
        Meeting meeting = meetings.get(id);
        Validation.validateArgumentNotNull(meeting, "Meeting");
        Validation.validateStateInPast(meeting.getDate());
        return addNotesToPastMeeting(meeting, text);
    }

    private PastMeeting addNotesToPastMeeting(Meeting meeting, String text) {
        PastMeeting meetingWithNotes = new PastMeetingImpl(meeting.getId(), meeting.getDate(), meeting.getContacts(), text);
        this.meetings.put(meetingWithNotes.getId(), meetingWithNotes); //overwrite previous meeting without notes
        return meetingWithNotes;
    }

    @Override
    public int addNewContact(String name, String notes) {
        Validation.validateStringNotNullOrEmpty(name, "name");
        Validation.validateStringNotNullOrEmpty(notes, "notes");

        int id = getNewContactId();
        this.contacts.put(id, new ContactImpl(id, name, notes));
        return id;
    }

    private int getNewContactId() {
        return ++this.lastContactId;
    }

    private int getNewMeetingId() {
        return ++this.lastMeetingId;
    }

    @Override
    public Set<Contact> getContacts(String name) {
        Validation.validateObjectNotNull(name, "Name");
        if(name.equals(""))
            return getContactsAsSet();

        return getElementsFromMapAsSet(this.contacts, (k, v) -> v.getName().equals(name));
    }

    private Set<Contact> getContactsAsSet() {
        return contacts.values().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Contact> getContacts(int... ids) {
        Validation.validateSetPopulated(ids, "Contact Ids array");
        Set<Contact> result = getElementsFromMapAsSet(this.contacts, (k, v) -> IntStream.of(ids).anyMatch(i -> i == k));
        Validation.validateArgumentSizeMatch(ids.length, result.size());
        return result;
    }

    private <T> Set<T> getElementsFromMapAsSet(Map<Integer, T> map, BiPredicate<Integer, T> predicate) {
        return map.entrySet().stream()
                .filter(e -> predicate.test(e.getKey(), e.getValue()))
                .map(e -> e.getValue())
                .collect(Collectors.toSet());
    }

    private <T> List<T> getElementsFromMapAsList(Map<Integer, T> map, BiPredicate<Integer, T> predicate) {
        return map.entrySet().stream()
                .filter(e -> predicate.test(e.getKey(), e.getValue()))
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public void flush() {

    }
}
