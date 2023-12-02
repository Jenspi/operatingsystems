import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            String outputname = args[0].replace("input", "output");
            FileWriter outputFile = new FileWriter(outputname);
        
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(" ");
                String process = columns[0].trim();
                String action = columns[1].trim();
                String resource = columns[2].trim();
                
                Node processNode = graph.newNode(process, true);
                Node resourceNode = graph.newNode(resource, false);

                if(action.toLowerCase().equals("w")){
                    // wants-- two outcomes: process successfully allocated, or process must wait on another process taking the resource
                    outputFile.write("Process "+process+" wants resource "+resource+" – ");
                    
                    outputFile.write(graph.requestEdge(processNode, resourceNode));
                }
                else if(action.toLowerCase().equals("r")){
                    // releases-- one outcome: resource released
                    outputFile.write("Process "+process+" releases resource "+resource+" – ");
                    graph.removeEdge(processNode, resourceNode);
                    resourceNode.setBusy(false);
                    outputFile.write("Resource "+resource+" is now free.\n");
                }
            }
            //print of there was a deadlock or not:
            outputFile.write( graph.deadlockPresence() );
            
            outputFile.close();
            System.out.println("Output created: "+ outputname);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + args[0]);
        }
        catch (IOException e) {
            System.out.println("I/O Exception!");
        }
        catch (java.lang.ArrayIndexOutOfBoundsException a) {
            System.out.println("No file specified!");
        }
    }
}//end Deadlock class

class RAG {
    //here lies code to implement your Resource Allocation Graph data structure class ...
    // https://www.geeksforgeeks.org/resource-allocation-graph-rag-in-operating-system/
    //Your program will track resource requests, allocations, and releases
    public ArrayList<Node> edge = new ArrayList<Node>();

    public Node newNode(String name, boolean isResource){
        Node node = new Node(name, isResource);
        edge.add(node);
        return node;
    }

    //edges:
    public String requestEdge(Node process, Node resource){
        if(resource.nodeList.isEmpty()){
        //if(!resource.isBusy()){

            //if resource is available
            addEdge(resource, process);
            // DEBUGGING:
            // System.out.println("EMPTY resource list: ");
            // resource.printList();
            ////////////////////////////////////////////////
            resource.setBusy(true);
            return "Resource "+resource.name+" is allocated to process "+process.name+".\n";
            
        }
        else if(!resource.nodeList.isEmpty()){
        //else if(resource.isBusy()){

            //if resource pre-occupied
            // DEBUGGING:
            // System.out.println("FULL resource list: ");
            // resource.printList();
            ////////////////////////////////////////////////
            return "Process "+process.name+" must wait.\n";
        }
        return "";
    }
    public void addEdge(Node current, Node next){
        //need directed egdes!!
        //current.addNode(next) == current -> next
        //next would not point to current unless we added current to be next's edge
        current.addNode(next);
    }
    public void removeEdge(Node current, Node next){
        current.removeNode(next);
        edge.remove(next);
    }
    
    //deadlocks:
    public String deadlockPresence(){
        //note: not necessary to check for deadlock after a resource release
        ArrayList<Node> visited = new ArrayList<Node>();
        ArrayList<Node> visited2 = new ArrayList<Node>();

        for(Node node : edge){
            if(!visited.contains(node)){
                if(deadlockCycle(node, visited, visited2)){
                    //true; deadlock detected when entering this block
                    return "DEADLOCK DETECTED";
                    //return true;
                }
            }
        }
        //false
        return "EXECUTION COMPLETED: No deadlock encountered.";
        //return false;
    }
    public boolean deadlockCycle(Node node, ArrayList<Node> visited, ArrayList<Node> visited2){
        //recursive helper function for deadlockPresence
        visited.add(node);
        visited2.add(node);
            for(Node next : node.nodeList){
            if(!visited.contains(next)){
                if(deadlockCycle(next, visited, visited2)){
                    return true;
                }
            }
            else if(visited.contains(next)){
                //CYCLE
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
    public ArrayList<Node> nodeList = new ArrayList<Node>();
    boolean isResource, isBusy;

    public Node(String name, boolean isResource){
        this.name = name;
        this.isResource = isResource;
        if(isResource){
            this.isBusy = false;
        }
        else{this.isBusy=true;}
    }

    public void addNode(Node node){
        nodeList.add(node);
    }
    public void removeNode(Node node){
        nodeList.remove(node);
    }
    public void printList(){
        for(Node node : nodeList){
            System.out.print(node.name+" ");
        }
        System.out.println("\n");
    }
    public ArrayList<Node> getList(Node node){
        return node.nodeList;
    }
    public boolean isBusy(){
        //ONLY FOR RESOURCES
        return this.isBusy;
    }
    public void setBusy(boolean bool){
        this.isBusy = bool;
    }
}//end Node class
    