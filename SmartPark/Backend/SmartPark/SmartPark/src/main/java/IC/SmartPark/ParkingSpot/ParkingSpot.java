package IC.SmartPark.ParkingSpot;

import jakarta.persistence.*;

@Entity
@Table(name="parking_space")
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean emptyParkingSpot;
    private boolean bookedParkingSpot;
    private boolean paidParkingSpot;

    public ParkingSpot() {
    }

    public ParkingSpot(String name, boolean EmptyParkingSpot, boolean bookedparkingspot, boolean PaidParkingSpot) {
        this.name = name;
        this.emptyParkingSpot = EmptyParkingSpot;
        this.bookedParkingSpot = bookedparkingspot;
        this.paidParkingSpot = PaidParkingSpot;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmptyParkingSpot() {
        return emptyParkingSpot;
    }

    public void setEmptyParkingSpot(boolean emptyParkingSpot) {
        this.emptyParkingSpot = emptyParkingSpot;
    }

    public boolean isBookedparkingspot() {
        return bookedParkingSpot;
    }

    public void setBookedparkingspot(boolean bookedparkingspot) {
        this.bookedParkingSpot = bookedparkingspot;
    }

    public boolean isPaidParkingSpot() {
        return paidParkingSpot;
    }

    public void setPaidParkingSpot(boolean paidParkingSpot) {
        this.paidParkingSpot = paidParkingSpot;
    }

    public void clearParkingSpot(){
        emptyParkingSpot =true;
        bookedParkingSpot =false;
        paidParkingSpot =false;
    }

    public boolean isAvailable(){
        if(emptyParkingSpot && !bookedParkingSpot){
            return true;
        }
        return false;
    }
}
