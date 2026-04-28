package arcade;

/**
 * Customer.java
 * Stores customer data.
 */
public class Customer extends Person
{
    private String phone;
    private String email;
    private int membershipId;

    public Customer()
    {
        super();
        phone = "";
        email = "";
        membershipId = 0;
    }

    public Customer(int id, String firstName, String lastName, String phone,
                    String email, int membershipId)
    {
        super(id, firstName, lastName);
        this.phone = phone;
        this.email = email;
        this.membershipId = membershipId;
    }

    @Override
    public String getRole()
    {
        return "Customer";
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getMembershipId()
    {
        return membershipId;
    }

    public void setMembershipId(int membershipId)
    {
        this.membershipId = membershipId;
    }

    public String toFileString()
    {
        return id + "," + firstName + "," + lastName + "," + phone + ","
               + email + "," + membershipId;
    }

    public static Customer fromFileString(String line)
    {
        String[] parts = line.split(",");
        return new Customer(Integer.parseInt(parts[0]), parts[1], parts[2],
                parts[3], parts[4], Integer.parseInt(parts[5]));
    }

    @Override
    public String toString()
    {
        return id + " - " + getFullName() + " | " + phone + " | " + email;
    }
}
