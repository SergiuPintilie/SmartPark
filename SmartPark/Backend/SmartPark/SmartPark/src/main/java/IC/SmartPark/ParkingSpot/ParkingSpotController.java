package IC.SmartPark.ParkingSpot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ParkingSpotController {
    @Autowired
    public final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping("/getAvailableParkingSpot")
    public String getAvailableParkingSpot() {
        return parkingSpotService.getAvalableParkingSpot().getName();
    }

    @GetMapping("/getParkingInfo")
    public List<Map<String, Boolean>> getParkingInfo() {
        return parkingSpotService.getAllParkingSpotsInformation();
    }

    @PostMapping("/modifyEmptyParking")
    public void modifyEmptyParking(@RequestParam String parkingSpotName) {
        parkingSpotService.modifyEmptyParkingSpot(parkingSpotName);
    }

    @PostMapping("/modifyBookedParking")
    public String modifyBookedParking(@RequestParam String parkingSpotName) {
        return parkingSpotService.modifyBookedParkingSpot(parkingSpotName);
    }
}
