package arcade;

/**
 * Membership.java
 */
public class Membership
{
    private int membershipId;
    private int customerId;
    private String membershipType;
    private int points;
    private double discountRate;

    public Membership()
    {
        membershipId = 0;
        customerId = 0;
        membershipType = "Basic";
        points = 0;
        discountRate = 0.0;
    }

    public Membership(int membershipId, int customerId, String membershipType,
                      int points, double discountRate)
    {
        this.membershipId = membershipId;
        this.customerId = customerId;
        this.membershipType = membershipType;
        this.points = points;
        this.discountRate = discountRate;
    }

    public int getMembershipId()
    {
        return membershipId;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public String getMembershipType()
    {
        return membershipType;
    }

    public int getPoints()
    {
        return points;
    }

    public double getDiscountRate()
    {
        return discountRate;
    }

    public void addPoints(int points)
    {
        this.points += points;
    }

    public String toFileString()
    {
        return membershipId + "," + customerId + "," + membershipType + ","
               + points + "," + discountRate;
    }

    public static Membership fromFileString(String line)
    {
        String[] parts = line.split(",");
        return new Membership(Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), parts[2],
                Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
    }

    @Override
    public String toString()
    {
        return membershipId + " - Customer " + customerId + " | "
               + membershipType + " | Discount: " + discountRate;
    }
}
