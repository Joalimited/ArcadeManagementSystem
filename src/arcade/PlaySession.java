package arcade;

/**
 * PlaySession.java
 */
public class PlaySession
{
    private int sessionId;
    private int customerId;
    private int machineId;
    private int plays;
    private double totalCost;

    public PlaySession()
    {
        sessionId = 0;
        customerId = 0;
        machineId = 0;
        plays = 0;
        totalCost = 0.0;
    }

    public PlaySession(int sessionId, int customerId, int machineId,
                       int plays, double totalCost)
    {
        this.sessionId = sessionId;
        this.customerId = customerId;
        this.machineId = machineId;
        this.plays = plays;
        this.totalCost = totalCost;
    }

    public int getSessionId()
    {
        return sessionId;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public int getMachineId()
    {
        return machineId;
    }

    public int getPlays()
    {
        return plays;
    }

    public double getTotalCost()
    {
        return totalCost;
    }

    public String toFileString()
    {
        return sessionId + "," + customerId + "," + machineId + ","
               + plays + "," + totalCost;
    }

    public static PlaySession fromFileString(String line)
    {
        String[] parts = line.split(",");
        return new PlaySession(Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
    }

    @Override
    public String toString()
    {
        return sessionId + " - Customer " + customerId + " used Machine "
               + machineId + " | Plays: " + plays + " | $" + totalCost;
    }
}
