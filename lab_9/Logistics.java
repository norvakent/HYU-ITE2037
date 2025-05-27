package lab_9;

import java.util.ArrayList;
import java.util.Scanner;

public class Logistics {
  public static void main(String[] args) {
    String[] new_gyeonggi_boxes = {"SuWon#1","SeongNam#1","YongIn#1","GoSeong#1","GaPyeong#1"};
    String[] new_gangwon_boxes = {"Gangneung#1","Wonju#1"};
    ArrayList<Gyeonggi> gyList = new ArrayList<>();
    ArrayList<Gangwon> gaList = new ArrayList<>();

    Scanner scan = new Scanner(System.in);
    int index;

    ServiceManagement.packageBoxes(new_gyeonggi_boxes, Gyeonggi.class, gyList);
    ServiceManagement.packageBoxes(new_gangwon_boxes, Gangwon.class, gaList);

    System.out.println("*** Oh, delivery price has been increased!! ***");
    ServiceManagement.raiseAll(Gyeonggi.class, gyList, 1.05);
    ServiceManagement.raiseAll(Gangwon.class, gaList, 1.05);

    System.out.println("Which box is important in Gangwon-area?");
    index = scan.nextInt();
    scan.nextLine();
    index = ServiceManagement.findIndexByNum(gaList, index);

    if(index == -1) {
      System.out.println("nothing");
    } else {
      System.out.println("The box \""+gaList.get(index).getDescription()+"\" is important! be careful!\n");
      ServiceManagement.raisePerBox(gaList.get(index), 1.2);
    }

    System.out.println("Which box has the wrong area in Gyeonggi-area?");
    index = scan.nextInt();
    scan.nextLine();
    index = ServiceManagement.findIndexByNum(gyList, index);

    if(index == -1) {
      System.out.println("nothing");
    } else {
      System.out.println("The box \"" + gyList.get(index).getDescription() + "\" is actually has to go to Gangwon! late! hurry up!\n");
      ServiceManagement.changeHub(gyList, index, Gangwon.class, gaList);
      ServiceManagement.raisePerBox(gaList.getLast(), 0.9);
    }

    System.out.println("-------- Delivery List for Gyeonggi --------");
    for(Gyeonggi g : gyList) {
      System.out.println(g+"\n");
    }

    System.out.println("-------- Delivery List for Gangwon --------");
    for(Gangwon j : gaList) {
      System.out.println(j+"\n");
    }

    scan.close();
  }
}
