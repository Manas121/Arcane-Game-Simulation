import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public abstract class entity implements Comparable<entity>, IObserver {

    private final Logger logger = LoggerFactory.getLogger(arcane.class);
    private final audibleObserver audio;

    // high cohesion - everything in the class is related to information about the entity
    //inheritance - superclass for adventurer and creature
    public final String name;
    public double health;

    entity(String name, double health) {
        this.name = name;
        this.health = health;


        // Observer pattern - attach the entity to the EventBus
        EventBus.getInstance().attach( this, EventType.Death);
        EventBus.getInstance().attach( this, EventType.AteSomething);
        EventBus.getInstance().attach( this, EventType.FightOutcome);

        audio = new audibleObserver(this, 1);
    }

    //getters
    public double getHealth() {
        return this.health;
    }
    public String getName() {
        return this.name;
    }

    //setters
    public void setHealth(double health) {
        if (health <= 0) {
            EventBus.getInstance().postMessage(EventType.Death, this.name + " was killed");
            this.health = 0;
        } else {
            this.health = health;
        }
    }

    // polymorphism - implemented differently for adventurer and creature
    public abstract String printStatus();

    //Implement compareTo method from Comparable interface - use of polymorphism
    @Override
    public int compareTo(entity otherEntity) {
        // Compare creatures based on their health in descending order
        return Double.compare(otherEntity.getHealth(), this.health);
    }

    @Override
    public void update(String eventDescription) {
        // Handle the Death event
        if (eventDescription.contains(this.name) && eventDescription.contains("kill")) {
            logger.info(this.name + " received a death notification: " + eventDescription);
            // Additional logic for handling death event
            audio.update(eventDescription);

        }

        // Handle the AteSomething event
        if (eventDescription.contains(this.name) && eventDescription.contains("ate")) {
            logger.info(this.name + " received a notification: " + eventDescription);
            // Additional logic for handling death event

        }

        // Handle the FightOutcome event

        if (eventDescription.contains(this.name) && (eventDescription.contains("beat") || eventDescription.contains("lost to") || eventDescription.contains("drew against")) ) {

            logger.info(this.name + " received a notification: " + eventDescription);
            // Additional logic for handling death event

        }


    }
}