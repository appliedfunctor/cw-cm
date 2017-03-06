import java.io.Serializable;

/**
 * Created by Alexander Worton on 27/12/2016.
 */
public class ContactImpl implements Contact, Serializable{

    private int id;
    private String name;
    private String notes;

    /**
     * Overload constructor to apply a default value for notes
     * @param id the id of the contact
     * @param name the name of the contact
     */
    public ContactImpl(int id, String name){
        this(id, name, "");
    }

    /**
     * Constructor to apply id, name and notes
     * @param id the id of the contact
     * @param name the name of the contact
     * @param notes attached notes for the contact
     */
    public ContactImpl(int id, String name, String notes){
        Validation.validateIdPositive(id);
        Validation.validateObjectNotNull(name, "name");
        Validation.validateObjectNotNull(notes, "notes");

        setId(id);
        setName(name);
        setNotes(notes);
    }

    private void setId(int id){
        this.id = id;
    }

    private void setName(String name){
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    private void setNotes(String notes){
        this.notes = notes;
    }

    public String getNotes(){
        return this.notes;
    }


    /*since there is no ability to delete notes, I have made the assumption that
    addNotes replaces the existing notes rather than appends to it. Appending is
    still supported externally by utilising getNotes, appending and then addNotes. */
    /**
     * {@inheritDoc}
     */
    @Override
    public void addNotes(String note) {
        Validation.validateObjectNotNull(note, "notes");
        this.notes = note;
    }

}