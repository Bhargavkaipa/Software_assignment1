package CW1;
/**
 * The `Name` class represents a person's name, including their first name and last name.
 */
public class Name {
    private String firstName;
    private String lastName;
    /**
     * Constructor for the `Name` class.
     *
     * @param firstName The first name of the person.
     * @param lastName  The last name of the person.
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /**
     * Get the first name of the person.
     *
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Get the last name of the person.
     *
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Get the initial of the last name (first character of the last name).
     *
     * @return The initial of the last name or a space if the last name is empty.
     */
    public char getInitPeriodLast() {
        if (lastName.length() > 0) {
            return lastName.charAt(0);
        } else {
            return ' '; 
        }
    }
}
