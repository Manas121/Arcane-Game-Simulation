import csci.ooad.layout.IMazeObserver;
import csci.ooad.layout.MazeObserver;

public class gameConfigurator {
    public static void main(String[] args) {
        adventurerFactory adventurerFactory = new adventurerFactory();
        creatureFactory creatureFactory = new creatureFactory();
        foodFactory foodFactory = new foodFactory();
        maze ourMaze = maze.newbuilder(adventurerFactory,creatureFactory,foodFactory).distributeRandomly().build();
        arcane game = new arcane();



        IMazeObserver mazeObserver = MazeObserver.getNewBuilder("Arcane Game")
                .useRadialLayoutStrategy()
                .setDelayInSecondsAfterUpdate(5)
                .build();

        ourMaze.attach(mazeObserver);



        game.play(ourMaze);
    }
}
