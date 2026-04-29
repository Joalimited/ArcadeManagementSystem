package arcade;

/**
 * Payment.java
 */
public class Payment
{
    private int paymentId;
    private int customerId;
    private double amount;
    private String paymentMethod;
    private String payStatus;

    public Payment()
    {
        paymentId = 0;
        customerId = 0;
        amount = 0.0;
        paymentMethod = "";
        payStatus = "Pending";
    }

    public Payment(int paymentId, int customerId, double amount,
                   String paymentMethod, String payStatus)
    {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.payStatus = payStatus;
    }

    public int getPaymentId()
    {
        return paymentId;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public double getAmount()
    {
        return amount;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public String getPayStatus()
    {
        return payStatus;
    }

    public String toFileString()
    {
        return paymentId + "," + customerId + "," + amount + ","
               + paymentMethod + "," + payStatus;
    }

    public static Payment fromFileString(String line)
    {
        String[] parts = line.split(",");
        return new Payment(Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), Double.parseDouble(parts[2]),
                parts[3], parts[4]);
    }

    @Override
    public String toString()
    {
        return paymentId + " - Customer " + customerId + " | $" + amount
               + " | " + paymentMethod + " | " + payStatus;
    }
}
