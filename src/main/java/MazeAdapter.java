import csci.ooad.layout.IMaze;

import java.util.ArrayList;
import java.util.List;

public class MazeAdapter implements IMaze {
    private final maze ourMaze;

    public MazeAdapter(maze ourMaze) {
        this.ourMaze = ourMaze;
    }

    @Override
    public List<String> getRooms() {
        List<String> roomNames = new ArrayList<>();
        for (room room : ourMaze.getMazeRooms()) {
            roomNames.add(room.getRoomName());
        }
        return roomNames;
    }

    @Override
    public List<String> getNeighborsOf(String room) {
        List<String> neighborNames = new ArrayList<>();
        for (room neighbor : ourMaze.getMazeRooms()) {
            if (neighbor.getRoomName().equals(room)) {
                for (room adjRoom : neighbor.getAdjRooms()) {
                    if (adjRoom != null) {
                        neighborNames.add(adjRoom.getRoomName());
                    }
                }
                break;
            }
        }
        return neighborNames;
    }

    @Override
    public List<String> getContents(String room) {
        List<String> contents = new ArrayList<>();
        for (room currentRoom : ourMaze.getMazeRooms()) {
            if (currentRoom.getRoomName().equals(room)) {
                for (adventurer adv : currentRoom.getRoomAdventurers()) {
                    contents.add(adv.printStatus());
                }
                for (creature crit : currentRoom.getRoomCreatures()) {
                    contents.add(crit.printStatus());
                }
                contents.addAll(currentRoom.getRoomFood());
                break;
            }
        }
        return contents;
    }
}
