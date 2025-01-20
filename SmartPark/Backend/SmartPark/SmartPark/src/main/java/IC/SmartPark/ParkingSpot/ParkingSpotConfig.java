package IC.SmartPark.ParkingSpot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ParkingSpotConfig{
    @Bean
    CommandLineRunner commandLineRunner1(ParkingSpotRepository parkingSpotRepository){
        return args -> {
            ParkingSpot parkingSpot1=new ParkingSpot("A1",false,false,false);
            ParkingSpot parkingSpot2=new ParkingSpot("A2",true,true,false);
            ParkingSpot parkingSpot3=new ParkingSpot("A3",true,false,false);
            ParkingSpot parkingSpot4=new ParkingSpot("A4",true,false,false);
            ParkingSpot parkingSpot5=new ParkingSpot("A5",true,false,false);
            ParkingSpot parkingSpot6=new ParkingSpot("A6",true,false,false);
            ParkingSpot parkingSpot7=new ParkingSpot("A7",true,false,false);
            ParkingSpot parkingSpot8=new ParkingSpot("A8",true,false,false);
            ParkingSpot parkingSpot9=new ParkingSpot("A9",true,false,false);
            ParkingSpot parkingSpot10=new ParkingSpot("A10",true,false,false);
            ParkingSpot parkingSpot11=new ParkingSpot("B1",true,false,false);
            ParkingSpot parkingSpot12=new ParkingSpot("B2",true,false,false);
            ParkingSpot parkingSpot13=new ParkingSpot("B3",true,false,false);
            ParkingSpot parkingSpot14=new ParkingSpot("B4",true,false,false);
            ParkingSpot parkingSpot15=new ParkingSpot("B5",true,false,false);
            ParkingSpot parkingSpot16=new ParkingSpot("B6",true,false,false);
            ParkingSpot parkingSpot17=new ParkingSpot("B7",true,false,false);
            ParkingSpot parkingSpot18=new ParkingSpot("B8",true,false,false);
            ParkingSpot parkingSpot19=new ParkingSpot("B9",true,false,false);
            ParkingSpot parkingSpot20=new ParkingSpot("B10",true,false,false);
            ParkingSpot parkingSpot21=new ParkingSpot("C1",true,false,false);
            ParkingSpot parkingSpot22=new ParkingSpot("C2",true,false,false);
            ParkingSpot parkingSpot23=new ParkingSpot("C3",true,false,false);
            ParkingSpot parkingSpot24=new ParkingSpot("C4",true,false,false);
            ParkingSpot parkingSpot25=new ParkingSpot("C5",true,false,false);
            ParkingSpot parkingSpot26=new ParkingSpot("C6",true,false,false);
            ParkingSpot parkingSpot27=new ParkingSpot("C7",true,false,false);
            ParkingSpot parkingSpot28=new ParkingSpot("C8",true,false,false);
            ParkingSpot parkingSpot29=new ParkingSpot("C9",true,false,false);
            ParkingSpot parkingSpot30=new ParkingSpot("C10",true,false,false);
            ParkingSpot parkingSpot31=new ParkingSpot("D1",true,false,false);
            ParkingSpot parkingSpot32=new ParkingSpot("D2",true,false,false);
            ParkingSpot parkingSpot33=new ParkingSpot("D3",true,false,false);
            ParkingSpot parkingSpot34=new ParkingSpot("D4",true,false,false);
            ParkingSpot parkingSpot35=new ParkingSpot("D5",true,false,false);
            ParkingSpot parkingSpot36=new ParkingSpot("D6",true,false,false);
            ParkingSpot parkingSpot37=new ParkingSpot("D7",true,false,false);
            ParkingSpot parkingSpot38=new ParkingSpot("D8",true,false,false);
            ParkingSpot parkingSpot39=new ParkingSpot("D9",true,false,false);
            ParkingSpot parkingSpot40=new ParkingSpot("D10",true,false,false);


            parkingSpotRepository.saveAll(List.of(parkingSpot1,parkingSpot2,parkingSpot3,parkingSpot4,parkingSpot5,parkingSpot6,parkingSpot7,parkingSpot8,parkingSpot9,parkingSpot10,parkingSpot11,parkingSpot12,parkingSpot13,parkingSpot14,parkingSpot15,parkingSpot16,parkingSpot17,parkingSpot18,parkingSpot19,parkingSpot20,parkingSpot21,parkingSpot22,parkingSpot23,parkingSpot24,parkingSpot25,parkingSpot26,parkingSpot27,parkingSpot28,parkingSpot29,parkingSpot30,parkingSpot31,parkingSpot32,parkingSpot33,parkingSpot34,parkingSpot35,parkingSpot36,parkingSpot37,parkingSpot38,parkingSpot39,parkingSpot40));
        };
    }
}

