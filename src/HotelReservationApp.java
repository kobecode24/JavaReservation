import ReservationManagement.ReservationManager;
import RoomManagement.RoomManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class HotelReservationApp {

    private static final ReservationManager reservationManager = new ReservationManager();
    private static final RoomManager roomManager = new RoomManager();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        initializeDummyData();
        boolean running = true;
        System.out.println("Welcome to the Hotel Reservation System");

        while (running) {
            showMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    createReservation();
                    break;
                case 2:
                    modifyReservation();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    viewReservations();
                    break;
                case 5:
                    manageRooms();
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
        scanner.close();
    }

    public static void showMenu() {
        System.out.println("\nPlease choose an option:");
        System.out.println("1. Create Reservation");
        System.out.println("2. Modify Reservation");
        System.out.println("3. Cancel Reservation");
        System.out.println("4. View Reservations");
        System.out.println("5. Manage Rooms");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid choice. Please enter a number.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void createReservation() {
        System.out.println("Creating a new reservation...");
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();

        if (!roomManager.roomExists(roomNumber)) {
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }

        LocalDate checkInDate = getDateFromUser("Enter check-in date (YYYY-MM-DD): ");
        LocalDate checkOutDate = getDateFromUser("Enter check-out date (YYYY-MM-DD): ");

        if (!reservationManager.isRoomAvailable(roomNumber, checkInDate, checkOutDate)) {
            System.out.println("Room " + roomNumber + " is not available for the selected dates.");
            return;
        }

        reservationManager.createReservation(guestName, roomNumber, checkInDate, checkOutDate);
        System.out.println("Reservation created successfully.");
    }

    public static void modifyReservation() {
        System.out.println("Modifying an existing reservation...");
        System.out.print("Enter reservation ID to modify: ");
        UUID reservationId = UUID.fromString(scanner.nextLine());

        if (!reservationManager.reservationExists(reservationId)) {
            System.out.println("Reservation ID " + reservationId + " does not exist.");
            return;
        }

        System.out.print("Enter new guest name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter new room number: ");
        String roomNumber = scanner.nextLine();

        if (!roomManager.roomExists(roomNumber)) {
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }

        LocalDate checkInDate = getDateFromUser("Enter new check-in date (YYYY-MM-DD): ");
        LocalDate checkOutDate = getDateFromUser("Enter new check-out date (YYYY-MM-DD): ");

        if (!reservationManager.isRoomAvailable(roomNumber, checkInDate, checkOutDate)) {
            System.out.println("Room " + roomNumber + " is not available for the selected dates.");
            return;
        }

        reservationManager.modifyReservation(reservationId, guestName, roomNumber, checkInDate, checkOutDate);
        System.out.println("Reservation modified successfully.");
    }

    public static void cancelReservation() {
        System.out.println("Cancelling a reservation...");
        System.out.print("Enter reservation ID to cancel: ");
        try {
            UUID reservationId = UUID.fromString(scanner.nextLine());
            if (!reservationManager.reservationExists(reservationId)) {
                System.out.println("Reservation ID " + reservationId + " does not exist.");
                return;
            }

            System.out.print("Are you sure you want to cancel this reservation? (yes/no): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                reservationManager.cancelReservation(reservationId);
                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Reservation cancellation aborted.");
            }
        } catch (Exception e) {
            System.out.println("Invalid Reservation ID");
        }
    }

    public static void viewReservations() {
        System.out.println("Viewing all reservations...");
        reservationManager.viewReservations().forEach(reservation -> {
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Guest Name: " + reservation.getGuestName());
            System.out.println("Room Number: " + reservation.getRoomNumber());
            System.out.println("Check-In Date: " + reservation.getCheckInDate());
            System.out.println("Check-Out Date: " + reservation.getCheckOutDate());
            System.out.println("----------------------------------------");
        });
    }

    public static void checkRoomAvailability() {
        System.out.println("Checking room availability...");
        System.out.print("Enter room number to check availability: ");
        String roomNumber = scanner.nextLine();
        if (!roomManager.roomExists(roomNumber)) {
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }
        LocalDate checkInDate = getDateFromUser("Enter check-in date (YYYY-MM-DD): ");
        LocalDate checkOutDate = getDateFromUser("Enter check-out date (YYYY-MM-DD): ");

        if (reservationManager.isRoomAvailable(roomNumber, checkInDate, checkOutDate)) {
            System.out.println("Room " + roomNumber + " is available for the selected dates.");
        } else {
            System.out.println("Room " + roomNumber + " is not available for the selected dates.");
        }
    }

    public static void manageRooms() {
        boolean managing = true;
        while (managing) {
            System.out.println("\nRoom Management:");
            System.out.println("1. Add Room");
            System.out.println("2. Remove Room");
            System.out.println("3. View All Rooms");
            System.out.println("4. Check Room Availability");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getChoice();
            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    removeRoom();
                    break;
                case 3:
                    viewAllRooms();
                    break;
                case 4:
                    checkRoomAvailability();
                    break;
                case 5:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void addRoom() {
        System.out.print("Enter room number to add: ");
        String roomNumber = scanner.nextLine();
        if (roomManager.addRoom(roomNumber)) {
            System.out.println("Room " + roomNumber + " added successfully.");
        } else {
            System.out.println("Room " + roomNumber + " already exists.");
        }
    }

    private static void removeRoom() {
        boolean removing = true;

        while (removing){

            System.out.println("\nRoom Removing:");
            System.out.println("1. By ID");
            System.out.println("2. By Room number");
            System.out.println("3.Return");
            System.out.print("Enter your choice: ");

            int choice = getChoice();
            switch (choice){
                case 1:
                    System.out.print("Enter room ID: ");
                    UUID roomId = UUID.fromString(scanner.nextLine());
                    if (roomManager.removeRoomByID(roomId)){
                    System.out.println("Room with ID " + roomId + " deleted successfully.");
                    } else {
                    System.out.println("Room with the ID " + roomId + " doesn't exists.");
                    }
                    break;
                case 2:
                    System.out.print("Enter room Number: ");
                    String roomNumber = scanner.nextLine();
                    if (roomManager.removeRoomByNumber(roomNumber)){
                        System.out.println("Room with Number " + roomNumber + " deleted successfully.");
                    } else {
                        System.out.println("Room with the Number " + roomNumber + " doesn't exists.");
                    }
                    break;
                case 3:
                    removing=false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");


            }
        }
    }

    private static void viewAllRooms() {
        System.out.println("All Rooms:");
        roomManager.printAllRooms();
        System.out.println("Total rooms : " + roomManager.getTotalRooms());

    }

    private static LocalDate getDateFromUser(String prompt) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                date = LocalDate.parse(line, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }

    private static void initializeDummyData() {
        roomManager.addRoom("101");
        roomManager.addRoom("102");
        roomManager.addRoom("103");
        roomManager.addRoom("201");
        roomManager.addRoom("202");
        roomManager.addRoom("203");
        roomManager.addRoom("301");
        roomManager.addRoom("302");
        roomManager.addRoom("303");
        roomManager.addRoom("401");
        roomManager.addRoom("402");
        roomManager.addRoom("403");

        LocalDate today = LocalDate.now();
        reservationManager.createReservation("John Doe", "101", today, today.plusDays(3));
        reservationManager.createReservation("Jane Smith", "102", today.plusDays(1), today.plusDays(5));
        reservationManager.createReservation("Bob Johnson", "201", today.plusDays(2), today.plusDays(4));

        System.out.println("Dummy data initialized successfully.");
    }
}
