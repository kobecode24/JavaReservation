package RoomManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoomManager {

    private final Map<UUID, Room> rooms;

    public RoomManager() {
        this.rooms = new HashMap<>();
    }

    public boolean addRoom(String roomNumber) {
        UUID id = UUID.randomUUID();
        if (rooms.values().stream().noneMatch(room -> room.getRoomNumber().equals(roomNumber))) {
            rooms.put(id, new Room(roomNumber));
            return true;
        }
        return false;
    }

    public Room getRoom(String roomNumber) {
        for (Room room : rooms.values()){
            if (room.getRoomNumber().equals(roomNumber)){
                return room;
            }
        }
        return null;
    }


    public boolean roomExists(String roomNumber) {
        return rooms.values().stream()
                .anyMatch(room -> room.getRoomNumber().equals(roomNumber));
    }

    public boolean removeRoomByNumber(String roomNumber) {
        UUID roomToRemove = null;
        for (Map.Entry<UUID, Room> entry : rooms.entrySet()) {
            if (entry.getValue().getRoomNumber().equals(roomNumber)) {
                roomToRemove = entry.getKey();
                break;
            }
        }
        if (roomToRemove != null) {
            rooms.remove(roomToRemove);
            return true;
        }
        return false;
    }


    public boolean removeRoomByID(UUID roomId) {
        if (rooms.containsKey(roomId)){
            rooms.remove(roomId);
            return true;
        }
        return false;
    }

    public int getTotalRooms() {
        return rooms.size();
    }

    public void printAllRooms() {
        for (Map.Entry<UUID,Room> entry : rooms.entrySet()){
            UUID roomId = entry.getKey();
            String roomNumber = entry.getValue().getRoomNumber();

            System.out.println("Room ID: " + roomId + " Room number: " + roomNumber);
        }
    }


}
