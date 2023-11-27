import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadlock{
    public static void main( String [] args ) {
        //here lies code to process the input file and simulate resource allocations ...
        //the operations (AddNode, AddEdge, RemoveEdge, etc.) will be required.

        /*
        Input to your program will consist of lines like the following, read from a file:
            1 W 1
            2 W 2
            3 W 6
        */
        File file = new File(args[0]);
        Scanner scanner = new Scanner(file);
        RAG graph = new RAG();
    
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] columns = line.split(" ");
            //if (columns.length >= 4) {
            String process = columns[0].trim();
            String action = columns[1].trim();
            String resource = columns[2].trim();
            //data.put(custID, custName);
            //}
            Node processNode = rag.newNode(process);
            Node resourceNode = rag.newNode(resource);

            if(action.toLowerCase().equals("w")){
                //wants
                System.out.print("Process 1 wants resource 1 – ");
                if(deadlockCycle(next, visited, visited2)){
                    System.out.println("Process 1 must wait.");
                }
                else{
                    System.out.println("Resource 1 is allocated to process 1.");
                }

            }
            else if(action.toLowerCase().equals("r")){
                //releases
                System.out.print("Process 1 releases resource 1 –");
                if(deadlockCycle(next, visited, visited2)){
                    System.out.println("Resource 1 is allocated to process 5.");
                }
                else{
                    System.out.println("Resource 1 is now free.");
                }

            }
        }
        
        scanner.close();
    }
}//end Deadlock class

class RAG {
    //here lies code to implement your Resource Allocation Graph data structure class ...
    //https://www.geeksforgeeks.org/resource-allocation-graph-rag-in-operating-system/
    //Your program will track resource requests, allocations, and releases
    public ArrayList<Node> edge = new ArrayList<Node>();

    public Node newNode(String name){
        Node node = new Node(name);
        //add to edges so deadlock helper functions can access it
        edge.add(next);
        return node;
    }
    public void addEdge(Node current, Node next){
        //need directed egdes!!
        //current.addNode(next) == current -> next
        //next would not point to current unless we added current to be next's edge
        current.addNode(next);
    }
    public void removeEdge(Node current, Node next){
        current.removeNode(next);
    }
    //public void assignEdge(){}
    public void requestEdge(Node process, Node resource){
        if(resource.nextNode.isEmpty()){
            //if resource is available
            resource.addNode(process);
        }
        else if(!resource.nextNode.isEmpty()){
            //if resource pre-occupied   
            process.addNode(resource);
        }
    }
    public void deadlockPresence(){
        //note: not necessary to check for deadlock after a resource release
        ArrayList<Node> visited = new ArrayList<Node>();
        ArrayList<Node> visited2 = new ArrayList<Node>();

        for(Node node : edge){
            if(!visited.contains(node)){
                //means
                if(deadlockCycle(node, visited, visited2)){
                    //true

                    //go through all processes

                    System.out.println("DEADLOCK");
                }
            }
        }
        //false
        System.out.println("NO DEADLOCK");
    }
    public boolean deadlockCycle(Node node, ArrayList<Node> visited, ArrayList<Node> visited2){
        //helper function for deadlockPresence
        visited.add(node);
        visited2.add(node);
            for(Node next : n.nextNode){
            if(!visited.contains(next)){
                //means
                if(deadlockCycle(next, visited, visited2)){
                    //true
                    return true;
                }
            }
            else if(visited.contains(next)){
                //true
                return true;
            }
        }

        visited2.remove();
        return false;
    }

}//end RAG class

class Node {
    //here lies the code for a node object, ...
    public String name;
    public ArrayList<Node> nextNode = new ArrayList<Node>();

    public Node(String name){
        this.name = name;
    }

    public void addNode(Node node){
        nextNode.add(node);
    }
    public void removeNode(Node node){
        nextNode.remove(node);
    }
    
}//end Node class
    