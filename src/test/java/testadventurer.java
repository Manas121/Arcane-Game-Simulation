import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class testadventurer {
    @AfterEach
    void tearDown() {
        // Clean up any resources used by tests
    }

    @Test
    public void receiveDamage() {
        adventurer adventurerTest = new adventurer("test", 5, "default");
        adventurerTest.setHealth(0);
    }
    @Test
    public void testCreation(){
        adventurer adventurerTest = new adventurer("test", 5, "default");
        assertNotNull(adventurerTest);
    }

    @Test
    public void confirmName(){
        String expectedName = "test";
        adventurer adventurerTest = new adventurer(expectedName, 5, "default");
        assertEquals(expectedName, adventurerTest.getName());
    }

    @Test
    public void confirmHealth(){
        adventurer adventurerTest = new adventurer("test", 5, "default");
        adventurerTest.setHealth(3);
        assertEquals(3, adventurerTest.getHealth());
    }

}
