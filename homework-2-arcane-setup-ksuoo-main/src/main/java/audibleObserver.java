import jdk.jfr.Event;

import java.beans.EventSetDescriptor;
import java.io.IOException;
import java.util.List;

public class audibleObserver implements IObserver{
    private final IObserver arcaneGame;
    private final Integer delayInSeconds;

    public audibleObserver(IObserver arcaneGame, Integer delayInSeconds) {
        this.arcaneGame = arcaneGame;
        this.delayInSeconds = delayInSeconds;
    }

    private void say(String eventDescription) throws IOException {
        String[] cmd = {"say", eventDescription};
        Runtime.getRuntime().exec(cmd);
    }

    @Override
    public void update(String event) {
//        if (event.contains("GameStart") | event.contains("GameOver") | event.contains("killed")) {
//            try {
//                say(event);
//                Thread.sleep(delayInSeconds * 1000);
//            } catch (InterruptedException | IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
        try {
            say(event);
            Thread.sleep(delayInSeconds * 1000);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
