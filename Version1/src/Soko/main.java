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
		readingFile();
	}
	
	
	
	public static void readingFile() {
		ArrayList<String> extractFile = new ArrayList<String>();
		int numberColumn = 0,
				numberLine = 0;
		char[][] map = null;
		
		try{
		    File f = new File ("C:\\Users\\Asus\\Documents\\GitHub\\AIRobot\\Version1\\scheme1.txt");
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try	{
		        String line = br.readLine(); //faisable par char
		 
		        while (line != null) {
		           // System.out.println (line);
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
		
		//System.out.println("blabl");

		for(int i=0;i <extractFile.size(); i++) {
			//System.out.print(extractFile.get(i)+"\n");
			
			if(i == 0) {
				String[] parts = extractFile.get(i).split(Pattern.quote(" "));
				numberColumn = Integer.parseInt(parts[0]); 
				numberLine = Integer.parseInt(parts[1]); 
				map = new char[numberLine][numberColumn]; 
			
			}else {
				for(int j = 0;j < numberColumn; j++) {
					map[i-1][j] = extractFile.get(i).charAt(j);
				}
			}
		}
		
		for(int i = 0;i < 9;i++) {
			for(int j = 0;j<10;j++) {
				System.out.print(map[i][j]);
			}
			System.out.println("\n");
		}
	}

}
