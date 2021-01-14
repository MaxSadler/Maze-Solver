import java.io.IOException;

public class main {
    public static void main(String [] args) throws IOException {
        Maze maze = new Maze("maze.png");
        maze.findJunctions();
        maze.connectJunctions();
        maze.findPath();
    }
}