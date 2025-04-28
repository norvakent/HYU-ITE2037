package lab_7;

import java.time.LocalDate;

public class CarFactory {
  public static void main(String[] args) {
    Car protoICE = new Ice();
    Car protoBEV = new Bev();
    System.out.println(protoICE);
    System.out.println(protoBEV);

    Ice protoType1 = new Ice("Test1", LocalDate.of(1886, 01, 29),1);System.out.println(protoType1);
    Ice newICE = new Ice("ICE1", LocalDate.now(), 800000);System.out.println(newICE);
    Ice addICE = new Ice("ICE1", LocalDate.now(), 200000);System.out.println(addICE);

    System.out.println(protoType1.equals(newICE));
    System.out.println(newICE.equals(addICE));

    Bev protoType2 = new Bev("Test2", LocalDate.of(1832, 01,01),1);System.out.println(protoType2);
    Bev newBEV = new Bev("BEV1", LocalDate.now(), 1000000);System.out.println(newBEV);
    Bev addBEV = new Bev("BEV1", LocalDate.now(), 300000);System.out.println(addBEV);
    Bev BEVplusplus = new Bev("BEV1++", LocalDate.now(), 100000);System.out.println(BEVplusplus);

    System.out.println(newBEV.equals(addBEV));
    System.out.println(addBEV.equals(BEVplusplus));

    System.out.println(protoICE.totalCO2());
    System.out.println(protoBEV.totalCO2());
  }
}
