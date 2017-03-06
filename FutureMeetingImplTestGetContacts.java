import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class FutureMeetingImplTestGetContacts {

    private FutureMeeting meeting;
    private final Calendar date;
    private final Set<Contact> populatedSet;

    {
        date = Calendar.getInstance();
        populatedSet = new HashSet<>();
    }

    @Test
    public void getContactsTestSingle(){
        ContactImpl contact = new ContactImpl(1, "Name Of");
        populatedSet.add(contact);

        int id = 1;
        meeting = new FutureMeetingImpl(id, date, populatedSet);

        assertEquals(1, meeting.getContacts().size());
        assertTrue(meeting.getContacts().contains(contact));
    }

    @Test
    public void getContactsTestMultiple(){
        //do we want to internally create a copy of all elements to prevent mutability?
        int numContacts = 8;
        Set<Contact> verifySet = generateContacts(numContacts);

        int id = 1;
        meeting = new FutureMeetingImpl(id, date, populatedSet);

        assertEquals(numContacts, meeting.getContacts().size());
        assertTrue(meeting.getContacts().containsAll(verifySet));
    }

    private Set<Contact> generateContacts(int number){
        return IntStream.range(1,number+1)
                .mapToObj(i -> new ContactImpl(i, "Name"))
                //side effect populating the class scoped set with the same elements
                .peek(populatedSet::add)
                .collect(Collectors.toSet());
    }
}