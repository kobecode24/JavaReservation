package ReservationManagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationManager {

    private final List<Reservation> reservations;


    public ReservationManager() {
        this.reservations = new ArrayList<>();
    }

    public void createReservation(String guestName, String roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation reservation = new Reservation(guestName,roomNumber,checkInDate,checkOutDate);
        reservations.add(reservation);
    }

    public void modifyReservation(UUID reservationId , String guestName, String roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Reservation reservation : reservations){
            if (reservation.getReservationId().equals(reservationId)){
                reservation.setGuestName(guestName);
                reservation.setRoomNumber(roomNumber);
                reservation.setCheckInDate(checkInDate);
                reservation.setCheckOutDate(checkOutDate);
            }
        }
    }

    public void cancelReservation(UUID reservationId) {
        reservations.removeIf(reservation -> reservation.getReservationId().equals(reservationId));




    }

    public Reservation viewReservation(UUID reservationId) {
        for (Reservation reservation : reservations){
            if (reservation.getReservationId().equals(reservationId)){
                return reservation;
            }
        }
        return null;
    }

    public List<Reservation> viewReservations() {
        return reservations;
    }

    public boolean roomExists(String roomNumber) {
        return reservations.stream().anyMatch(reservation -> reservation.getRoomNumber().equals(roomNumber));
    }

    public boolean isRoomAvailable(String roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoomNumber().equals(roomNumber) &&
                    checkInDate.isBefore(reservation.getCheckOutDate()) &&
                    checkOutDate.isAfter(reservation.getCheckInDate())) {
                return false;
            }
        }
        return true;
    }

    public boolean reservationExists(UUID reservationId) {
        return reservations.stream().anyMatch(reservation -> reservation.getReservationId().equals(reservationId));
    }
}
