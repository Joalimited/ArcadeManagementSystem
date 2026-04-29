package arcade;

/**
 * Person.java
 * */
public abstract class Person
{
    protected int id;
    protected String firstName;
    protected String lastName;

    public Person()
    {
        id = 0;
        firstName = "";
        lastName = "";
    }

    public Person(int id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public abstract String getRole();

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFullName()
    {
        return firstName + " " + lastName;
    }
}
