package IC.SmartPark.ParkingSpot;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


@Service
public class ParkingSpotService {
    @Autowired
    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public ParkingSpot getAvalableParkingSpot(){
        ParkingSpot p=parkingSpotRepository.findFirstByEmptyParkingSpotTrueAndBookedParkingSpotFalseOrderById();
        if(p!=null){
            return p;
        }
        else return null;
        //return null;
    }

    public List<Map<String, Boolean>> getAllParkingSpotsInformation(){
        List<ParkingSpot> parkingSpots=parkingSpotRepository.findAll();
        List<Map<String,Boolean>> parkingSpotsInformation=parkingSpots.stream().sorted(Comparator.comparingInt(ParkingSpot::getId))
                        .map(parkingSpot -> Map.ofEntries(Map.entry("emptyParkingSpot",parkingSpot.isEmptyParkingSpot()),
                                                          Map.entry("bookedParkingSpot",parkingSpot.isBookedparkingspot()))).toList();
        return parkingSpotsInformation;
    }

    public void modifyEmptyParkingSpot(String parkingSpotName){
        ParkingSpot parkingSpot=parkingSpotRepository.findByName(parkingSpotName);
        if(parkingSpot!=null){
            if(parkingSpot.isEmptyParkingSpot()==false){
                if(parkingSpot.isPaidParkingSpot()){
                    parkingSpot.setEmptyParkingSpot(true);
                }
            }else{
                parkingSpot.setEmptyParkingSpot(false);
            }
        }
        parkingSpotRepository.save(parkingSpot);
    }

    public String modifyBookedParkingSpot(String parkingSpotName){
        ParkingSpot parkingSpot=parkingSpotRepository.findByName(parkingSpotName);
        if(parkingSpot!=null){
            if(parkingSpot.isEmptyParkingSpot()==false){
                return "Parking spot unavailable!";
            }else{
                parkingSpot.setBookedparkingspot(true);
            }
        }
        parkingSpotRepository.save(parkingSpot);
        return "succes";
    }
}
