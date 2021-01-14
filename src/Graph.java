import java.util.HashMap;
import java.util.LinkedList;

public class Graph {

    Node start;
    Node end;

    private LinkedList<LinkedList<Node>> adjacencyList = new LinkedList<>();
    private LinkedList<Node> walls = new LinkedList<>();

    public void addNodes(int i, int j) {
        LinkedList<Node> list = new LinkedList<>();
        list.add(new Node(i,j));
        adjacencyList.add(list);
    }

    public void addEdge(Node a, Node b){
        for(LinkedList<Node> list : adjacencyList)
        {
            if(list.getFirst() == a)
            {
                list.add(b);
            }
            else if(list.getFirst() == b)
            {
                list.add(a);
            }
        }
    }

    public void addWalls(int i, int j){
        walls.add(new Node(i,j));
    }

    public LinkedList<Node> getWalls(){
        return walls;
    }

    public LinkedList<LinkedList<Node>> getAdjacencyList() {
        return adjacencyList;
    }

    public void setOpenings(int i, int j) {
        Node node = new Node(i,j);
        if(start == null)
        {
            this.start = node;
        }
        else
        {
            this.end = node;
        }
        LinkedList<Node> list = new LinkedList<>();
        list.add(node);
        adjacencyList.add(list);
    }

    public HashMap<Node, Node> BFS(){
        Queue q = new Queue(adjacencyList.size());
        HashMap<Node, Node> path = new HashMap<>();

        q.enQueue(start);
        start.visited = true;

        while (!q.isEmpty())
        {
            Node curr = q.deQueue();

            for(LinkedList<Node> list : adjacencyList)
            {
                if(list.getFirst() == curr)
                {
                    for(Node node : list)
                    {
                        if(!node.visited)
                        {
                            q.enQueue(node);
                            node.visited = true;
                            path.put(node, curr);
                        }
                    }
                }
            }
        }
       return path;
    }
}

class Node{
    int x;
    int y;
    boolean visited = false;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }
}
