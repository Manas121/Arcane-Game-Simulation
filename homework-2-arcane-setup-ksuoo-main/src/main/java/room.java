import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class room {
    // a list of adventurers and creatures in every room - multiple adventures and creatures in each room
    private ArrayList <adventurer> roomAdventurers = new ArrayList<adventurer>();
    private ArrayList <creature> roomCreatures = new ArrayList <creature>();
    private ArrayList <String> roomFood = new ArrayList<>();

    //Phased out
    //private creature roomCreature = null;
    //private adventurer roomAdventurer = null;
    private String roomName;
    private room[] adjRooms = new room[4];

    room(String name){
        this.roomName = name;
    }

    public void assignRoomAdventurers(adventurer newAdventurer) {
        this.roomAdventurers.add(newAdventurer);
    }

    public void removeRoomAdventurer(adventurer removeAdventurer){
        this.roomAdventurers.remove(removeAdventurer);
    }

    public void assignRoomCreatures(creature newCreature) {
        this.roomCreatures.add(newCreature);
    }

    public void assignRoomFood(String newFood){
        this.roomFood.add(newFood);
    }



    public void removeRoomFood(String removeFood){
        this.roomFood.remove(removeFood);
    }

    public room getRandomNeighbor(room current) {
        room[] neighbors = current.getAdjRooms();
        int min = 0;
        int max = -1;
        for (int i = 0; i < neighbors.length; i++){
            if (neighbors[i] != null){
                max++;
            }
        }
        int random = (int)(Math.random() * (max - min + 1)) + min;
        return neighbors[random];
    }

    public String getRoomName() {
        return roomName;
    }

    public void setAdjRooms(room room1, room room2, room room3, room room4){
        this.adjRooms[0] = room1;
        this.adjRooms[1] = room2;
        this.adjRooms[2] = room3;
        this.adjRooms[3] = room4;
    }

    /*public void setAdjRooms(room neighbor)
    {
        this.adjRooms.add(neighbor);
    }*/
    public List<creature> getRoomCreatures() {
         return List.copyOf(this.roomCreatures);
    }

    public List<adventurer>  getRoomAdventurers() {
        return List.copyOf(this.roomAdventurers);
    }

    public List<String> getRoomFood(){
        return List.copyOf(this.roomFood);
    }

    public List <creature> getRoomCreaturesSorted() {
        List<creature> aliveCreatures = new ArrayList<>();
        for (int i = 0; i < this.getRoomCreatures().size(); i++){
            if (this.getRoomCreatures().get(i).getHealth() > 0){
                aliveCreatures.add(this.getRoomCreatures().get(i));
            }
        }
        return aliveCreatures.stream().sorted().collect(Collectors.toList());
    }

    public List <adventurer> getRoomAdventurersSorted() {
        List<adventurer> aliveAdventurers = new ArrayList<>();
        for (int i = 0; i < this.getRoomAdventurers().size(); i++){
            if (this.getRoomAdventurers().get(i).getHealth() > 0){
                aliveAdventurers.add(this.getRoomAdventurers().get(i));
            }
        }
        return aliveAdventurers.stream().sorted().collect(Collectors.toList());
    }

    public room[] getAdjRooms() {
        return this.adjRooms;
    }


}




