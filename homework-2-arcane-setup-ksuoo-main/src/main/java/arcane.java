import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

//Every class in java implicitly extends java.lang.object - example of inheritance

//Maze will be updated here as the state of the game changes
public class arcane implements IObserver{
    private final Logger logger = LoggerFactory.getLogger(arcane.class);
    private final audibleObserver audio;

    arcane() {
        // Observer pattern - attach arcane to the EventBus

        EventBus.getInstance().attach( this, EventType.TurnEnded);
        EventBus.getInstance().attach( this, EventType.GameStart);
        audio = new audibleObserver(this, 1);
    }

    public int diceRoll() {
        int min = 1;
        int max = 6;
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public void fight(adventurer fighting_adventurer, creature fighting_creature) {
        int adventurerRoll =  diceRoll();
        logger.info("Rolled a " + adventurerRoll);
        int creatureRoll = diceRoll();
        logger.info("Rolled a " + creatureRoll);

        logger.info(fighting_adventurer.printStatus() + " fought " + fighting_creature.printStatus());

        fighting_adventurer.setHealth(fighting_adventurer.getHealth() - 0.5);
        fighting_creature.setHealth(fighting_creature.getHealth() - 0.5);


        if (adventurerRoll > creatureRoll) {
            int diff = adventurerRoll - creatureRoll;
            fighting_creature.setHealth(fighting_creature.getHealth() - diff);
            //logger.info(fighting_adventurer.printStatus() + " beat " + fighting_creature.printStatus());
            EventBus.getInstance().postMessage(EventType.FightOutcome, fighting_adventurer.printStatus() + " beat " + fighting_creature.printStatus());
        } else if (adventurerRoll<creatureRoll) {
            int diff = creatureRoll - adventurerRoll;
            fighting_adventurer.setHealth(fighting_adventurer.getHealth() - diff);
            //logger.info(fighting_adventurer.printStatus() + " lost to " + fighting_creature.printStatus());
            EventBus.getInstance().postMessage(EventType.FightOutcome, fighting_adventurer.printStatus() + " lost to " + fighting_creature.printStatus());
        } else {
            //logger.info(fighting_adventurer.printStatus() + " drew against " + fighting_creature.printStatus());
            EventBus.getInstance().postMessage(EventType.FightOutcome, fighting_adventurer.printStatus() + " drew against " + fighting_creature.printStatus());
        }
    }

    public void play(maze ourMaze) {
        //logger.info("Starting play...");
        EventBus.getInstance().postMessage(EventType.GameStart, "GameStart");
        int turn = 0;
        //every turn
        boolean adventurers_alive = true;
        boolean creatures_alive = true;
        List<room> maze = ourMaze.getMazeRooms();
        List<adventurer> adventurers = ourMaze.getMazeAdventurers();
        List<creature> creatures = ourMaze.getMazeCreatures();
        while (adventurers_alive && creatures_alive) {
            logger.info("ARCANE MAZE: turn " + turn);
            for (room element : maze) {
                List<adventurer> room_adventurers = element.getRoomAdventurers();
                List<creature> room_creatures = element.getRoomCreatures();
                List<String> room_food = element.getRoomFood();

                String log_adventurers = "";
                String log_creatures = "";
                String log_food = "";

                int count = 0;

                for (int i = 0; i < room_adventurers.size(); i++) {
                    if (count > 0){
                        log_adventurers += ", " + room_adventurers.get(i).printStatus();
                    } else {
                        log_adventurers += room_adventurers.get(i).printStatus();
                        count++;
                    }
                }
                count = 0;
                for (int i = 0; i < room_creatures.size(); i++) {
                    if (count > 0){
                        log_creatures += ", "+ room_creatures.get(i).printStatus();
                    } else {
                        log_creatures += room_creatures.get(i).printStatus();
                        count++;
                    }
                }
                count = 0;
                for (int i = 0; i < room_food.size(); i++) {
                    if (count > 0){
                        log_food += ", "+ room_food.get(i);
                    } else {
                        log_food += room_food.get(i);
                        count++;
                    }
                }

                logger.info("\t" + element.getRoomName() + ":");
                logger.info("\t \t Adventurers: " + log_adventurers);
                logger.info("\t \t Creatures: " + log_creatures);
                logger.info("\t \t Food: " + log_food);
            }

            for (room element : maze) {
                List<adventurer> room_adventurers = element.getRoomAdventurersSorted();
                List<creature> room_creatures = element.getRoomCreaturesSorted();
                List<String> room_food = element.getRoomFood();

                if (!room_adventurers.isEmpty() && !room_creatures.isEmpty()) {
                    for (adventurer current : room_adventurers){
                        List<adventurer> updated_adventurers = element.getRoomAdventurersSorted();
                        List<creature> updated_creatures = element.getRoomCreaturesSorted();
                        List<String> updated_food = element.getRoomFood();
                        if (!updated_adventurers.isEmpty() && !updated_creatures.isEmpty()) {
                            creature healthiest_creature = updated_creatures.get(0);
                            adventurer healthiest_adventurer = updated_adventurers.get(0);
                            if (current instanceof glutton && !updated_food.isEmpty() && !(healthiest_creature instanceof demon)) {
                                String eat = updated_food.get(0);
                                current.setHealth(healthiest_adventurer.getHealth() + 1);
                                element.removeRoomFood(eat);
                                //logger.info(healthiest_adventurer.printStatus() + " just ate a " + eat);
                                EventBus.getInstance().postMessage(EventType.AteSomething, healthiest_adventurer.printStatus() + " just ate a " + eat);

                            } else if (current instanceof coward && !(healthiest_creature instanceof demon)) {
                                element.removeRoomAdventurer(current);
                                room neighbor = element.getRandomNeighbor(element);
                                neighbor.assignRoomAdventurers(current);
                                current.setHealth(current.getHealth() - 0.5);
                                logger.info(current.printStatus() + " moved from " + element.getRoomName() + " to " + neighbor.getRoomName());
                            } else if (current instanceof knight && healthiest_adventurer != current) {
                                fight (current, healthiest_creature);
                            }
                            fight(healthiest_adventurer, healthiest_creature);
                        }
                    }
                } else if (!room_adventurers.isEmpty() && !room_food.isEmpty()) {
                    String eat = room_food.get(0);
                    adventurer healthiest_adventurer = room_adventurers.get(0);
                    healthiest_adventurer.setHealth(healthiest_adventurer.getHealth() + 1);
                    element.removeRoomFood(eat);
                    //logger.info(healthiest_adventurer.printStatus() + " just ate a " + eat);
                    EventBus.getInstance().postMessage(EventType.AteSomething, healthiest_adventurer.printStatus() + " just ate a " + eat);
                } else if (!room_adventurers.isEmpty()) {
                    for (adventurer current : room_adventurers) {
                        element.removeRoomAdventurer(current);
                        room neighbor = element.getRandomNeighbor(element);
                        neighbor.assignRoomAdventurers(current);
                        logger.info(current.printStatus() + " moved from " + element.getRoomName() + " to " + neighbor.getRoomName());
                    }
                }
            }
            adventurers_alive = false;
            for (adventurer current : adventurers) {
                if (current.getHealth() > 0) {
                    adventurers_alive = true;
                }
            }
            creatures_alive = false;
            for (creature current : creatures) {
                if (current.getHealth() > 0) {
                    creatures_alive = true;
                }
            }
            ourMaze.notifyObservers("Turn " + turn + " just ended");

            turn++;

        }

        EventBus.getInstance().postMessage(EventType.GameOver, "GameOver");
        if (adventurers_alive) {
            logger.info("The adventurers won!");
            audio.update("The adventurers won");
        } else {
            logger.info("Boo, the creatures won");
            audio.update("The creatures won");
        }
    }


    @Override
    public void update(String eventDescription) {

        // Handle the TurnEnded event

        if (eventDescription.contains("TurnEnded")) {
            logger.info("ARCANE received a notification: " + eventDescription);
            // Additional logic for handling turn ended event
        }



        // Handle the GameStart event

        if (eventDescription.contains("GameStart")) {
            logger.info("ARCANE received a notification: " + eventDescription);
            // Additional logic for handling game start event
            audio.update((eventDescription));
        }




    }
}

