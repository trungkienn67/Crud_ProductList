package thidk.codelean.jdbc;

public class Maintenance {
    private int MaintenanceID;
    private String CarId;
    private final String Description;
    private String Cost;
    private String Maintenance;

    // Constructor no có MaintenanceID
    public Maintenance(String CarId, String Description, String Cost, String Maintenance) {
        this.CarId = CarId;
        this.Description = Description;
        this.Cost = Cost;
        this.Maintenance = Maintenance;
    }


    // Constructor có MaintenanceID
    public Maintenance(int MaintenanceID, String CarId, String Description, String Cost, String Maintenance) {
        this.MaintenanceID = MaintenanceID;
        this.CarId = CarId;
        this.Description = Description;
        this.Cost = Cost;
        this.Maintenance = Maintenance;
    }

    // Getter và Setter cho MaintenanceID
    public int getMaintenanceID() {
        return MaintenanceID;
    }

    public void setMaintenanceID(int MaintenanceID) {
        this.MaintenanceID = MaintenanceID;
    }

    // Getter và Setter cho CarId
    public String getCarId() {
        return CarId;
    }

    public void setCarId(String CarId) {
        this.CarId = CarId;
    }

    // Getter cho Description (final nên no cần setter)
    public String getDescription() {
        return Description;
    }

    // Getter và Setter cho Cost
    public String getCost() {
        return Cost;
    }

    public void setCost(String Cost) {
        this.Cost = Cost;
    }

    // Getter và Setter cho Maintenance
    public String getMaintenance() {
        return Maintenance;
    }

    public void setMaintenance(String Maintenance) {
        this.Maintenance = Maintenance;
    }

    @Override
    public String toString() {
        return "Maintenance [MaintenanceID=" + MaintenanceID + ", CarId=" + CarId + ", Description=" + Description + ", Cost=" + Cost + ", Maintenance=" + Maintenance + "]";
    }
}
