package IC.SmartPark.ParkingSpot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot,Integer> {
    public ParkingSpot findFirstByEmptyParkingSpotTrueAndBookedParkingSpotFalseOrderById();
    public ParkingSpot findByName(String name);
}
