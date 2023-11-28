import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadlock{
    public static void main( String [] args ) {
        //here lies code to process the input file and simulate resource allocations ...
        /*
        Input to your program will consist of lines like the following, read from a file:
            1 W 1
            2 W 2
            3 W 6
        */
        try{
            File file = new File(args[0]);
            Scanner scanner = new Scanner(file);
            RAG graph = new RAG();
        
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(" ");
                String process = columns[0].trim();
                String action = columns[1].trim();
                String resource = columns[2].trim();
                
                Node processNode = graph.newNode(process);
                Node resourceNode = graph.newNode(resource);

                if(action.toLowerCase().equals("w")){
                    //wants-- two outcomes: process successfully allocated, or process must wait on another process taking the resource
                    System.out.print("Process "+process+" wants resource "+resource+" – ");
                    graph.requestEdge(processNode, resourceNode);
                    // if(resourceNode.nextNode.isEmpty()){
                        
                    //     System.out.println("Resource "+resource+" is allocated to process "+process+".");
                    // }
                    // else{
                    //     System.out.println("Process "+process+" must wait.");
                    // }

                }
                else if(action.toLowerCase().equals("r")){
                    //releases-- one outcome: resource released
                    System.out.print("Process "+process+" releases resource "+resource+" –");
                    graph.removeEdge(processNode, resourceNode);
                    System.out.println("Resource "+resource+" is now free.");
                }
            }
            graph.deadlockPresence();
            // if(graph.deadlockPresence()){
            //     System.out.println("DEADLOCK DETECTED: ");//Processes 2, 3, 4, and Resources 2, 3, 6, are found in a cycle.");
            // }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + args[0]);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException a) {
            System.out.println("No file specified!");
        }
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
        edge.add(node);
        //add node to Node class so we can check later if that nextNode list is empty
        //node.addNode(node);
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
    public void requestEdge(Node process, Node resource){
        if(resource.nextNode.isEmpty()){
            //if resource is available
            resource.addNode(process);
            System.out.println("Resource "+resource.name+" is allocated to process "+process.name+".");
        }
        else if(!resource.nextNode.isEmpty()){
            //if resource pre-occupied   
            //process.addNode(resource);
            System.out.println("Process "+process.name+" must wait.");
        }
    }
    public boolean deadlockPresence(){
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
                    return true;
                }
            }
        }
        //false
        System.out.println("EXECUTION COMPLETED: No deadlock encountered.");
        return false;
    }
    public boolean deadlockCycle(Node node, ArrayList<Node> visited, ArrayList<Node> visited2){
        //helper function for deadlockPresence
        visited.add(node);
        visited2.add(node);
            for(Node next : node.nextNode){
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

        visited2.remove(node);
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
    