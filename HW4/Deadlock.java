import java.io.File;
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
    
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] columns = line.split(" ");
            //if (columns.length >= 4) {
            String process = columns[0].trim();
            String action = columns[1].trim();
            String resource = columns[2].trim();
            //data.put(custID, custName);
            //}
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
    public boolean deadlockPresence(){
        //note: not necessary to check for deadlock after a resource release
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
    