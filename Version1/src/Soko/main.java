package Soko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class main {
	enum DIRECTION{
		UP,
		DOWN,
		LEFT,
		RIGHT,
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		char[][] map = null;
		int startColumn = 0,
			startLine = 0,
			numberGoal = 0;
		Tree tree = null;
		
		
		map = readingFile();
		
		for(int i = 0;i < 9;i++) {
			for(int j = 0;j<10;j++) {
				System.out.print(map[i][j]);
				if(map[i][j] == 'M') {
					startColumn = j;
					startLine = i;
				}
			}
			System.out.println("\t");
		}
		
		tree = new Tree(startLine,startColumn,numberGoal); // start of the tree at M
		
		test(tree, map);

	}
	
		
	public static char[][] readingFile() {
		ArrayList<String> extractFile = new ArrayList<String>();
		char[][] map = null; 
		int numberColumn = 0, 
			numberLine = 0,
			numberGoal = 0;
		try{
		    File f = new File ("C:\\Users\\Asus\\Documents\\GitHub\\AIRobot\\Version1\\scheme1.txt");
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try	{// open file and read line by line, memorise everything in the array
		        String line = br.readLine(); 
		 
		        while (line != null) {
		            extractFile.add(line);
		            line = br.readLine();
		        }
		 
		        br.close();
		        fr.close();
		    }
		    catch (IOException exception){
		        System.out.println ("Error of reading : " + exception.getMessage());
		    }
		}
		catch (FileNotFoundException exception){
		    System.out.println ("File not found");
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

	private static void test(Tree tree, char[][] map) {
		if (tree.GameRules(map,false,DIRECTION.DOWN, tree.node) == true) {
			tree.addNode(tree.node, 0, +1,DIRECTION.DOWN);
			// ajouté dans la file
		}
		tree.node.printNode(tree.node.down);
		if (tree.GameRules(map,true,DIRECTION.LEFT, tree.node) == true) { 
			tree.addNode(tree.node, -1, 0,DIRECTION.LEFT);
			//ajouté dans la file
		}
		tree.node.printNode(tree.node.left);
		if (tree.GameRules(map,false,DIRECTION.UP, tree.node) == true) { 
			tree.addNode(tree.node, 0, -1,DIRECTION.UP);
			// ajouté dans la file
		}
		tree.node.printNode(tree.node.up);
		if (tree.GameRules(map,false,DIRECTION.RIGHT, tree.node) == true) { 
			tree.addNode(tree.node, +1, 0,DIRECTION.RIGHT);
			//ajouté dans la file
		}
		tree.node.printNode(tree.node.right);
	}
}
