import csci.ooad.layout.IMaze;
import csci.ooad.layout.IMazeSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class maze implements IMazeSubject, IObserver {

    private final Logger logger = LoggerFactory.getLogger(arcane.class);
    private final audibleObserver audio;

    private ArrayList <adventurer> mazeAdventurers;
    private ArrayList <creature> mazeCreatures;
    private ArrayList <String> mazeFood;
    private ArrayList <room> mazeRooms;

    //make sure to return a copy for encapsulation purposes
    public ArrayList<adventurer> getMazeAdventurers() {
        return this.mazeAdventurers;
    }
    public ArrayList<creature> getMazeCreatures() {
        return this.mazeCreatures;
    }
    public ArrayList<room> getMazeRooms() {
        return this.mazeRooms;
    }

    maze(){
        EventBus.getInstance().attach(this, EventType.GameOver);
        audio = new audibleObserver(this, 1);
    }

    public static builder newbuilder(adventurerFactory adventurer_factory, creatureFactory creature_factory, foodFactory food_factory) {
        return new builder(adventurer_factory, creature_factory, food_factory);
    }


    @Override
    public IMaze getMaze() {

        return new MazeAdapter(this);

    }

    @Override
    public void update(String eventDescription) {

        if (eventDescription.contains("GameOver")) {
            logger.info("ARCANE received a notification: " + eventDescription);
            // Additional logic for handling game over event

            //Check who won
            int adventurerhealth = 0;
            for (adventurer adventurer : this.getMazeAdventurers()) {
                if (adventurer.getHealth() > 0) {
                    adventurerhealth++;
                }
            }

            int creaturehealth = 0;

            for (creature creature : this.getMazeCreatures()) {
                if (creature.getHealth() > 0) {
                    creaturehealth++;
                }
            }

            if (adventurerhealth > creaturehealth) {
                for (adventurer adventurer : this.getMazeAdventurers()) {
                    if (adventurer.getHealth() > 0) {
                        logger.info("Adventurer " + adventurer.getName() + " is still alive");
                    }
                }
            }
            else {
                for (creature creature : this.getMazeCreatures()) {
                    if (creature.getHealth() > 0) {
                        logger.info("Creature " + creature.getName() + " is still alive");
                    }
                }
            }


            audio.update((eventDescription));
        }

    }

    public static class builder {
        private adventurerFactory adventurerFactory;
        private creatureFactory creatureFactory;
        private foodFactory foodFactory;

        private ArrayList <adventurer> inner_mazeAdventurers = new ArrayList<adventurer>();
        private ArrayList <creature> inner_mazeCreatures = new ArrayList <creature>();
        private ArrayList <String> inner_mazeFood = new ArrayList<>();
        private ArrayList <room> inner_mazeRooms = new ArrayList<room>();

        builder(adventurerFactory adventurer_factory, creatureFactory creature_factory, foodFactory food_factory) {
            this.adventurerFactory = adventurer_factory;
            this.creatureFactory = creature_factory;
            this.foodFactory = food_factory;
        }

        public maze build(){
            maze thisMaze  = new maze();
            thisMaze.mazeAdventurers = inner_mazeAdventurers;
            thisMaze.mazeCreatures = inner_mazeCreatures;
            thisMaze.mazeFood = inner_mazeFood;
            thisMaze.mazeRooms = inner_mazeRooms;

            return thisMaze;
        }

        public builder addToRoom(room roomName, String foodToAdd) {
            roomName.assignRoomFood(foodToAdd);
            return this;
        }

        public builder addToRoom(room roomName, adventurer adventurerToAdd) {
            roomName.assignRoomAdventurers(adventurerToAdd);
            return this;

        }

        public builder addToRoom(room roomName, creature creatureToAdd) {
            roomName.assignRoomCreatures(creatureToAdd);
            return this;
        }

        public builder addAdventurers(adventurer adventurersToAdd) {
            this.inner_mazeAdventurers.add(adventurersToAdd);
            return this;
        }

        public builder createAndAddCowards(String[] cowardsToAdd) {
            for(String cowardName : cowardsToAdd){
                coward newCoward = (coward) adventurerFactory.create(cowardName,"coward");
                this.addAdventurers(newCoward);
            }
            return this;
        }

        public builder createAndAddGluttons(String[] gluttonsToAdd) {
            for(String name : gluttonsToAdd){
                glutton newGlutton = (glutton) adventurerFactory.create(name,"glutton");
                this.addAdventurers(newGlutton);
            }
            return this;
        }

        public builder createAndAddKnights(String[] knightsToAdd) {
            for(String name : knightsToAdd){
                knight newKnight = (knight) adventurerFactory.create(name,"knight");
                this.addAdventurers(newKnight);
            }
            return this;
        }

        public builder createAndAddAdventurers(String[] adventurersToAdd) {
            for (String name: adventurersToAdd) {
                adventurer newAdventurer = adventurerFactory.create(name, "default");
                this.addAdventurers(newAdventurer);
            }
            return this;
        }

        public builder createAndAddFoodItems(String[] foodItemsToAdd) {
            for (String name: foodItemsToAdd) {
                food newfood = foodFactory.create(name);
                this.inner_mazeFood.add(newfood.getName());
            }
            return this;

        }

        public builder addCreatures(creature creatureToAdd) {
            this.inner_mazeCreatures.add(creatureToAdd);
            return this;
        }

        public builder createAndAddDemons(String[] demonsToAdd) {
            for(String name : demonsToAdd){
                demon newDemon = (demon) creatureFactory.create(name, "demon");
                this.addCreatures(newDemon);
            }
            return this;
        }

        public builder createAndAddCreatures(String[] creaturesToAdd) {
            for(String name : creaturesToAdd){
                creature newCreature = creatureFactory.create(name, "default");
                this.addCreatures(newCreature);
            }
            return this;
        }



        public builder create3x3grid() {
            room center = new room("Center");
            room north = new room("North");
            room east = new room("East");
            room south = new room("South");
            room west = new room("West");
            room northWest = new room("Northwest");
            room northEast = new room("Northeast");
            room southWest = new room ("Southwest");
            room southEast = new room("Southeast");
            //room[] maze = {northWest, north, northEast, west, center, east, southWest, south, southEast};
            this.inner_mazeRooms.add(center);
            this.inner_mazeRooms.add(north);
            this.inner_mazeRooms.add(east);
            this.inner_mazeRooms.add(south);
            this.inner_mazeRooms.add(west);
            this.inner_mazeRooms.add(northWest);
            this.inner_mazeRooms.add(northEast);
            this.inner_mazeRooms.add(southWest);
            this.inner_mazeRooms.add(southEast);

            northWest.setAdjRooms(north, west, null, null);
            northEast.setAdjRooms(north,east, null, null);
            southWest.setAdjRooms(south, west, null, null);
            southEast.setAdjRooms(south,east, null, null);
            center.setAdjRooms(north,south,east,west);
            north.setAdjRooms(northWest,northEast, center,null);
            west.setAdjRooms(northWest,center,southWest, null);
            east.setAdjRooms(northEast,center,southEast,null);
            south.setAdjRooms(southWest,center,southEast,null);

            return this;
        }


        public builder distributeRandomly() {

            //Distribute a random number of creatures, adventurers, and food randomly in the maze

            //Create a 3x3 maze
            builder ourMaze = this.create3x3grid();

            //create and add a random number of adventurers/creatures in the maze (the types of adventurers/creatures should be random as well)

            int randomNumberOfDefaultAdventurers = (int)(Math.random() * (10 - 1 + 1)) + 1;
            int randomNumberOfDefaultCreatures = (int)(Math.random() * (10 - 1 + 1)) + 1;
            int randomNumberOfFood =  (int)(Math.random() * (10 - 1 + 1)) + 1;

            int randomNumberOfGluttons = (int)(Math.random() * (randomNumberOfDefaultAdventurers - 1 + 1)) + 1;
            int randomNumberOfKnights = (int)(Math.random() * (randomNumberOfDefaultAdventurers - 1 + 1)) + 1;
            int randomNumberOfCowards = (int)(Math.random() * (randomNumberOfDefaultAdventurers - 1 + 1)) + 1;
            int randomNumberOfDemons = (int)(Math.random() * (randomNumberOfDefaultCreatures - 1 + 1)) + 1;

            //Create random number of adventurers, creatures, and food based on randomNumberOfAdventurers
            String[] adventurersToAdd = new String[randomNumberOfDefaultAdventurers];
            for (int i = 0; i < randomNumberOfDefaultAdventurers; i++){
                adventurersToAdd[i] = ("Adventurer " + i);
            }
            this.createAndAddAdventurers(adventurersToAdd);

            String[] creaturesToAdd = new String[randomNumberOfDefaultCreatures];
            for (int i = 0; i < randomNumberOfDefaultCreatures; i++){
                creaturesToAdd[i] = ("Creature " + i);
            }
            this.createAndAddCreatures(creaturesToAdd);

            String[] gluttonsToAdd = new String[randomNumberOfGluttons];
            for (int i = 0; i < randomNumberOfGluttons; i++) {
                gluttonsToAdd[i] = "Glutton " + i;
            }
            this.createAndAddGluttons(gluttonsToAdd);

            String[] knightsToAdd = new String[randomNumberOfKnights];
            for (int i = 0; i < randomNumberOfKnights; i++) {
                knightsToAdd[i] = "Knight " + i;
            }
            this.createAndAddKnights(knightsToAdd);

            String[] cowardsToAdd = new String[randomNumberOfCowards];
            for (int i = 0; i < randomNumberOfCowards; i++) {
                cowardsToAdd[i] = "Coward " + i;
            }
            this.createAndAddCowards(cowardsToAdd);

            String[] demonsToAdd = new String[randomNumberOfDemons];
            for (int i = 0; i < randomNumberOfDemons; i++) {
                demonsToAdd[i] = "Demon " + i;
            }
            this.createAndAddDemons(demonsToAdd);

            String[] foodToAdd = new String[randomNumberOfFood];
            for (int i = 0; i < randomNumberOfFood; i++) {
                foodToAdd[i] = "Food " + i;
            }
            this.createAndAddFoodItems(foodToAdd);

            //Following adds the adventurers (including subtypes), creatures (including subtypes), and foods to specific rooms - fix it to use the builder methods
            int min = 0;
            int max = this.inner_mazeRooms.size() - 1;

            for (int i = 0; i < this.inner_mazeAdventurers.size() ; i++) {
                int random_num = (int) (Math.random() * (max - min + 1)) + min;
                adventurer currentAdventurer = this.inner_mazeAdventurers.get(i);
                this.addToRoom(this.inner_mazeRooms.get(random_num), currentAdventurer);
            }
            for (int i = 0; i < this.inner_mazeCreatures.size(); i++){
                int random_num = (int)(Math.random() * (max - min + 1)) + min;
                creature creatureCurrent = this.inner_mazeCreatures.get(i);
                this.addToRoom(this.inner_mazeRooms.get(random_num), creatureCurrent);
            }
            for (int i = 0; i < randomNumberOfFood; i++){
                int random_num = (int)(Math.random() * (max - min + 1)) + min;
                this.addToRoom(this.inner_mazeRooms.get(random_num), this.inner_mazeFood.get(i));
            }
            return this;
        }
    }

}
