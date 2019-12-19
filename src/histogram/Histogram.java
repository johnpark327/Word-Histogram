package histogram;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Histogram {

	public static void main(String[] args) {
		
		StringBuilder inputString = new StringBuilder();

		try {
		File input = new File("input.txt");
		Scanner reader = new Scanner(input);
		while(reader.hasNextLine()) {
			inputString.append(reader.nextLine());
		}
		} catch(FileNotFoundException e) {
			System.out.println("File not Found");
			e.printStackTrace();
		}//Try catch block for file io
		
		//System.out.println(inputString);
		String s = inputString.toString();
		
		if(s.length()==0) {
			System.out.println("Input text box is empty");
			System.exit(0);
		}//Check if empty
		
		String[] sa = s.split("\\W+");
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();//Hashmap for having word keys with the value of occurance
		
		for(int i=0;i<sa.length;i++) {
			String w = sa[i].toLowerCase();
			if (map.get(w)==null) {
				map.put(w, 1);
			}
			else {
				map.replace(w, map.get(w)+1);
			}
		}//Loop through the string array and add them to the HashMap.
		
		HashMap<String, Integer> resultMap = map.entrySet().parallelStream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		//A long line of getting the hashmap to stream to compare and sort by value, then put it on a new hashmap with the order by value.
		
		String outputString = "";
		
		for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
		    String word = entry.getKey();
			int number = entry.getValue();
			String histogram = "";
			for(int i=0; i<number;i++) histogram = histogram + "=";
			String line = String.format("%15s|", word);//Make it align like a histogram
			outputString = outputString + line + histogram + " (" + number + ")\n";
		}//Add the string that will be written to the output text
		System.out.println(outputString);
		File output = new File("output.txt");
		
		if(output.exists()) {
			output.delete();
		}//If exists, delete to "overwrite" it
		try {
			output.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}//Create the output text file
		try {
			FileWriter fw = new FileWriter("output.txt");
			fw.write(outputString);
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}//Write on the output text file.

	}

}
