import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private static EventBus instance;
    private Map<EventType, List<IObserver>> observers;

    private EventBus() {
        observers = new HashMap<>();
        for (EventType type : EventType.values()) {
            observers.put(type, new ArrayList<>());
        }
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void attach(IObserver observer, EventType eventType) {
        observers.get(eventType).add(observer);
    }

    public void postMessage(EventType eventType, String eventDescription) {

        List<IObserver> eventObservers = observers.get(eventType);
        //System.out.println(eventObservers.size());
        for (IObserver observer : eventObservers) {
            observer.update(eventDescription);
        }
    }



}
