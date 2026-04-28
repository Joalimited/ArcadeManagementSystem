package arcade;

public class ArcadeMachine
{
    private int machineId;
    private String gameName;
    private String machineType;
    private String status;
    private double costPerPlay;

    public ArcadeMachine()
    {
        machineId = 0;
        gameName = "";
        machineType = "";
        status = "Working";
        costPerPlay = 0.0;
    }

    public ArcadeMachine(int machineId, String gameName, String machineType,
                         String status, double costPerPlay)
    {
        this.machineId = machineId;
        this.gameName = gameName;
        this.machineType = machineType;
        this.status = status;
        this.costPerPlay = costPerPlay;
    }

    public int getMachineId()
    {
        return machineId;
    }

    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public String getMachineType()
    {
        return machineType;
    }

    public void setMachineType(String machineType)
    {
        this.machineType = machineType;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public double getCostPerPlay()
    {
        return costPerPlay;
    }

    public void setCostPerPlay(double costPerPlay)
    {
        this.costPerPlay = costPerPlay;
    }

    public String toFileString()
    {
        return machineId + "," + gameName + "," + machineType + ","
               + status + "," + costPerPlay;
    }

    public static ArcadeMachine fromFileString(String line)
    {
        String[] parts = line.split(",");
        return new ArcadeMachine(Integer.parseInt(parts[0]), parts[1],
                parts[2], parts[3], Double.parseDouble(parts[4]));
    }

    @Override
    public String toString()
    {
        return machineId + " - " + gameName + " | " + machineType
               + " | " + status + " | $" + costPerPlay;
    }
}
