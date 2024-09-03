import org.junit.jupiter.api.Test;

public class testGameConfigurator {
    @Test
    public void testGame() {
        String[] args = new String[]{
                "--numberOfRooms", "",
                "--numberOfAdventurers", "",
                "--numberOfCreatures", "",
                "--numberOfFoodItems", ""
        };
        gameConfigurator.main(args);


    }
}
