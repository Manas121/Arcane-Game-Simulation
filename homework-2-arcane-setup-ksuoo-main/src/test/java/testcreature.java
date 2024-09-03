import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class testcreature {

    @Test
    public void testCreation(){
        creature monster = new creature("test", 5, "default");
        assertNotNull(monster);
    }

    @Test
    public void confirmName(){
        String expectedName = "monster";
        creature monster = new creature("monster", 5, "default");
        assertEquals(expectedName, monster.getName());
    }

    @Test
    public void confirmHealth(){
        creature monster = new creature("test", 5, "default");
        monster.setHealth(2);
        assertEquals(2, monster.getHealth());
    }


}
