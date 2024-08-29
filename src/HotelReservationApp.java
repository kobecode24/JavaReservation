import java.util.Scanner;

public class HotelReservationApp {


    public static void main(String[] args){

        boolean running = true;


        while (running){
            showMenu();
        }
    }

    public static void showMenu(){
        System.out.println("Welcome to the Hotel Reservation System");
        System.out.println("Please choose an option:");
        System.out.println("1. Create Reservation");
        System.out.println("2. Modify Reservation");
        System.out.println("3. Cancel Reservation");
        System.out.println("4. View Reservations");
        System.out.println("5. Check Room Availability");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice(){
        Scanner value = new Scanner(System.in);
        return value.nextInt();
    }

    public static void createReservation(){
        Reser
    }
}




