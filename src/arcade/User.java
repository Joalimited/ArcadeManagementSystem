package arcade;

/**
 * User.java
 * Stores login account information.
 */
public class User
{
    private static int userCount = 0;

    private int userId;
    private String email;
    private String encryptedPassword;
    private String role;

    public User()
    {
        userId = 0;
        email = "";
        encryptedPassword = "";
        role = "Employee";
        userCount++;
    }

    public User(int userId, String email, String encryptedPassword, String role)
    {
        this.userId = userId;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.role = role;
        userCount++;
    }

    public static int getUserCount()
    {
        return userCount;
    }

    public int getUserId()
    {
        return userId;
    }

    public String getEmail()
    {
        return email;
    }

    public String getEncryptedPassword()
    {
        return encryptedPassword;
    }

    public String getRole()
    {
        return role;
    }

    public String toFileString()
    {
        return userId + "," + email + "," + encryptedPassword + "," + role;
    }

    public static User fromFileString(String line)
    {
        String[] parts = line.split(",");
        return new User(Integer.parseInt(parts[0]), parts[1], parts[2],
                parts[3]);
    }

    @Override
    public String toString()
    {
        return userId + " - " + email + " - " + role;
    }
}
