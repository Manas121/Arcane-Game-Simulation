import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class testroom
{

    @Test
    public void testCreation() {
        room roomTest = new room("testRoom");
        assertNotNull(roomTest);
    }

    @Test
    public void confirmName() {
        String expectedName = "testRoom";
        room roomTest = new room(expectedName);
        assertEquals(expectedName, roomTest.getRoomName());
    }

    @Test
    public void roomConnected() {
        room room1 = new room("test");
        room room2 = new room("test1");
        room room3 = new room("test2");

        room1.setAdjRooms(room2,room3,null,null);

        assertNotNull( room1.getAdjRooms());
    }
}
