import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Maze {

    private String path;
    private final Color white = new Color(255,255,255);
    private final Color black = new Color(0,0,0);
    private final Color green = new Color(0,255,0);
    private Graph g = new Graph();

    public Maze(String path){
        this.path = path;
    }

    public void findJunctions() throws IOException {
        File file = new File(path);
        BufferedImage img;

        try{
            img = ImageIO.read(file);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        for(int i = 1; i < img.getWidth()-1; i++)
        {
            for(int j = 1; j < img.getHeight()-1; j++)
            {
                if(img.getRGB(i,j) == white.getRGB())
                {
                    if((i == 1 || i == img.getWidth()-1) || (j == 1 || j == img.getHeight()-1))
                    {
                        g.setOpenings(i,j);
                    }
                    else if((img.getRGB(i+1, j) == white.getRGB() || img.getRGB(i-1,j) == white.getRGB()) &&
                            (img.getRGB(i, j+1) == white.getRGB() || img.getRGB(i,j-1) == white.getRGB()))
                    {
                        g.addNodes(i,j);
                    }
                }
                else
                {
                    g.addWalls(i,j);
                }
            }
        }

        for(LinkedList<Node> list : g.getAdjacencyList())
        {
            Node node = list.getFirst();
            img.setRGB(node.x,node.y, green.getRGB());
        }

        File nodes = new File("junctions.png");
        ImageIO.write(img, "png", nodes);
    }

    public void connectJunctions(){

        LinkedList<LinkedList<Node>> nodes = g.getAdjacencyList();

        for(LinkedList<Node> node: nodes)
        {
            Node source = node.getFirst();
            for(LinkedList<Node> others : nodes)
            {
                Node destination = others.getFirst();
                if(source.x == destination.x && source.y < destination.y)
                {
                    if(checkVertical(source, destination))
                    {
                        g.addEdge(source, destination);
                    }
                }
                else if(source.y == destination.y && source.x < destination.x)
                {
                    if(checkHorizontal(source, destination))
                    {
                        g.addEdge(source, destination);
                    }
                }
            }
        }
    }

    private boolean checkVertical(Node source, Node destination){

        for(Node wall : g.getWalls())
        {
            if(wall.x == source.x && wall.y > source.y && wall.y < destination.y)
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkHorizontal(Node source, Node destination){
        for(Node wall : g.getWalls())
        {
            if(wall.y == source.y && wall.x > source.x && wall.x < destination.x)
            {
                return false;
            }
        }
        return true;
    }

    public void findPath() throws IOException {
        File maze = new File(path);
        HashMap<Node, Node> path = g.BFS();
        BufferedImage img = ImageIO.read(maze);

        LinkedList<Node> shortestPath = new LinkedList<>();
        Node target = g.end;

       while(target != null)
       {
           shortestPath.add(target);
           target = path.get(target);
       }

       for(int i  = 0; i < shortestPath.size()-1; i++)
       {
           Node node1 = shortestPath.get(i);
           Node node2 = shortestPath.get(i+1);

           if(node1.x == node2.x)
           {
               if(node1.y > node2.y)
               {
                   for(int j = node1.y; j >= node2.y; j--)
                   {
                       img.setRGB(node1.x, j, new Color(255,0,0).getRGB());
                   }
               }
               else
               {
                   for(int j = node1.y; j <= node2.y; j++)
                   {
                       img.setRGB(node1.x, j, new Color(255,0,0).getRGB());
                   }
               }
           }
           else
           {
               if(node1.x > node2.x)
               {
                   for(int j = node1.x; j >= node2.x; j--)
                   {
                       img.setRGB(j, node1.y, new Color(255,0,0).getRGB());
                   }
               }
               else
               {
                   for(int j = node1.x; j <= node2.x; j++)
                   {
                       img.setRGB(j, node1.y, new Color(255,0,0).getRGB());
                   }
               }
           }
       }
        File solution = new File("solution.png");
        ImageIO.write(img, "png", solution);
    }
}

