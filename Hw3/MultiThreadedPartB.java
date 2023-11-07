/* RegEx Code distributed by Dr. James Wagner and modified by Jenny Spicer || CSCI4401 2023 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

public class MultiThreadedPartB {
	public static void main(String [] args) {
		//storing	 <file_name, most_frequent_word>
		HashMap <String, String> finalWords = new HashMap<>();
		
		//Finding pathway to directory with all files:
		File directoryPath = new File("/Users/jenspi/src/operatingsystems/Hw3/14_FILES");
		String fileNames[] = directoryPath.list();

		//debugging:
		// for (String file : fileNames){
		// 	System.out.println(file);	
		// }

		//time:
		long start = System.nanoTime();
		int number_threads = 1;
        for (int i = 0; i < number_threads; i++) {
            Thread t = new Thread(new MultiThreadedB(fileNames));
            t.start();

			try{
				t.join();
			}catch (Exception e) {
            	System.out.println(e+" exception is caught");
        	}
		}
		long end = System.nanoTime();
		long total = (end-start);
		System.out.println("Time: "+(total/1000000000)+" seconds ("+(total/1000000)+" ms)");
	}
	
}//end class

class MultiThreadedB implements Runnable {
	private String[] pwd;
	Map <String, String> finalWords = Collections.synchronizedMap(new HashMap<String, String>());

	public MultiThreadedB(String[] pwd){
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
	public static Map<String, String> frequentWord(String[] fileNames){
		HashMap <String, String> output = new HashMap<>();
		for(int i=0; i<fileNames.length; i++){
			HashMap <String, Integer> temp = new HashMap<>();
			//debugging:
			//System.out.println("entered for loop");
			try{
				
					File file = new File(fileNames[i]);
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
				System.out.println("File not found: " + fileNames[i]);
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
			output.put( fileNames[i], maxKey );
		}//end for-loop

		//print results... put here instead of main because I couldn't figure out how to get my hashmap back to main
		for(Map.Entry<String, String> word : output.entrySet()){
			System.out.println(""+word.getKey() +":   "+ word.getValue());
		}

		return output;
	}//end method
}