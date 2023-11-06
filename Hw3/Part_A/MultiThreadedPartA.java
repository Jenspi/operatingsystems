/* RegEx Code distributed by Dr. James Wagner and modified by Jenny Spicer || CSCI4401 2023 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;
import java.util.Collections;

public class MultiThreadedPartA {
	public static void main(String [] args) {
		int number_threads = 1;
        for (int i = 0; i < number_threads; i++) {
            Thread t = new Thread(new MultiThreaded("WineReviews.csv"));
            t.start();

			try{
				t.join();
			}catch (Exception e) {
            	System.out.println(e+" exception is caught");
        	}
		}
	}//end main
}//end class

class MultiThreaded implements Runnable {
	private String pwd;
	Map <String, String> finalWords = Collections.synchronizedMap(new HashMap<String, String>());

	public MultiThreaded(String pwd){
		this.pwd = pwd;
	}

	@Override
	public void run(){
		try {
            //Debugging:
            // System.out.println("Thread "+Thread.currentThread().getId()+" is running");

			//Starting a thread with .start() will execute this .run() method, which will
			//be responsible for calling frequentWord()
			finalWords.putAll(frequentWord(pwd));
        }
        catch (Exception e) {
            System.out.println("Exception is caught");
        }
	}
	private  Map<String, String> frequentWord(String fileName){
		HashMap <String, Integer> temp = new HashMap<>();
		try{
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			String pattern = "[a-zA-Z]+";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(scanner.next().toLowerCase().trim());

			// find() looks for next pattern match
			// group() returns sequence of last pattern matched
			while (scanner.hasNext()) {
				if(m.find() && !(m.hitEnd())){
					if (temp.containsKey(m.group())){
						//if word already in hashmap, add one occurrence to value
						temp.put(m.group(), (temp.get(m.group())+1));
					}
					else if (!(temp.containsKey(m.group())) ){
						//if word not in hashmap yet, add key with the value of one occurrence
						temp.put(m.group(), 1);
					}
					//need a new matcher
					m = r.matcher(scanner.next().toLowerCase().trim());
				}
				else{
					m = r.matcher(scanner.next().toLowerCase().trim());
				}
			}

			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
		}

		//find and return most frequent word >= 7 from all in temp hashmap
		Integer maxValue = 0;
		String maxKey = "";
		for(Map.Entry<String, Integer> word : temp.entrySet()){
			if(word.getKey().length() >= 7){
				if(maxValue < word.getValue() ){
					maxValue = word.getValue();
					maxKey = word.getKey();
				}
			}
		}

		//returning	 <file_name, most_frequent_word>
		finalWords.put( fileName, maxKey );

		//print results... put here instead of main because I couldn't figure out how to get my hashmap back to main
		for(Map.Entry<String, String> word : finalWords.entrySet()){
			System.out.println(""+word.getKey() +":   "+ word.getValue());
		}

		return finalWords;
	}//end method
}//end class