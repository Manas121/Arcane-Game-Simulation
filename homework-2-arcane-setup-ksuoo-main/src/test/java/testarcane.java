import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class testarcane {
    @Test
    public void confirmDice() {
        arcane game = new arcane();
        int dice = game.diceRoll();
        assertTrue(dice > 0 && dice < 7);


    }

    @Test
    public void confirmFight() {
        adventurer adventurerTest = new adventurer("test", 5, "default");
        creature creatureTest = new creature("monster",5,"default");
        arcane game = new arcane();
        game.fight(adventurerTest,creatureTest);
    }
}
