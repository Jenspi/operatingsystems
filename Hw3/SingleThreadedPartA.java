/* RegEx Code distributed by Dr. James Wagner and modified by Jenny Spicer || CSCI4401 2023 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

public class SingleThreadedPartA {
	public static void main(String [] args) {
		//storing	 <file_name, most_frequent_word>
		HashMap <String, String> finalWords = new HashMap<>();
		
		//Manually importing files:
		finalWords.putAll(frequentWord("TimeUse.csv"));
		finalWords.putAll(frequentWord("SMS_Spam.txt"));
		finalWords.putAll(frequentWord("UFOReports.txt"));
		finalWords.putAll(frequentWord("IMDB.csv"));
		finalWords.putAll(frequentWord("WineReviews.csv"));
		finalWords.putAll(frequentWord("Resume.csv"));
		finalWords.putAll(frequentWord("FakeNews.csv"));

		//printing everything:
		for(Map.Entry<String, String> word : finalWords.entrySet()){
			System.out.println(""+word.getKey() +":   "+ word.getValue());
		}
	}
	public static Map<String, String> frequentWord(String fileName){
		HashMap <String, Integer> temp = new HashMap<>();
		HashMap <String, String> output = new HashMap<>();
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
		output.put( fileName, maxKey );
		return output;
	}//end method
}//end class