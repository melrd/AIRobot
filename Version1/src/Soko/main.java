package Soko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		char[][] map = null;
		map = readingFile(map);
		for(int i = 0;i < 9;i++) {
			for(int j = 0;j<10;j++) {
				System.out.print(map[i][j]);
			}
			System.out.println("\t");
		}
	}
	
	
	
	public static char[][] readingFile(char[][] map) {
		ArrayList<String> extractFile = new ArrayList<String>();
		int numberColumn = 0,
				numberLine = 0,
				numberGoal = 0;
		
		try{
		    File f = new File ("C:\\Users\\Asus\\Documents\\GitHub\\AIRobot\\Version1\\scheme1.txt");
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try	{
		    	// open file and read line by line, memorise everything in the array
		        String line = br.readLine(); 
		 
		        while (line != null) {
		            extractFile.add(line);
		            line = br.readLine();
		        }
		 
		        br.close();
		        fr.close();
		    }
		    catch (IOException exception){
		        System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		    }
		}
		catch (FileNotFoundException exception){
		    System.out.println ("Le fichier n'a pas été trouvé");
		}
		

		for(int i=0;i <extractFile.size(); i++) {
			
			if(i == 0) { // for the first line we extract the parameters of the map and initialize the map table
				String[] parts = extractFile.get(i).split(Pattern.quote(" "));
				numberColumn = Integer.parseInt(parts[0]); 
				numberLine = Integer.parseInt(parts[1]); 
				numberGoal = Integer.parseInt(parts[2]); 
				map = new char[numberLine][numberColumn]; 
			
			}else {// we put all the data in the map
				for(int j = 0;j < numberColumn; j++) {
					map[i-1][j] = extractFile.get(i).charAt(j);
				}
			}
		}
		
		return map;
	}           

}
