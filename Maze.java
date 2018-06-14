//Raymond Valdez

import java.util.*;

public class Maze {
	
	int depth;
	int width;
	int arrwidth;
	int arrdepth;
	boolean debug;
	boolean endingfound = false;
	boolean walled = false;
	Node[][] nodearr;
	
	
		public Maze(int width, int depth, boolean debug){
			
			this.width = width;
			this.depth = depth;
			this.arrwidth = (width*2) - 1;
			this.arrdepth = (depth*2) - 1;
			this.debug = debug;
			int i = 0;
			int j = 0;
			
			
			//creates a maze of width and depth 
			nodearr = new Node[arrwidth][arrdepth];
			
				for(i = 0; i< arrdepth; i++){
				
				for(j = 0; j< arrwidth; j++){
					
					Node element = new Node();
					nodearr[j][i] = element;
				}
			}
				
			for(i = 0; i<arrdepth; i = i+2){
				
				for( j = 0; j<arrwidth; j = j+2){
					
					nodearr[j][i].wall = ' ';
					
				}
			}
			
			//sets the very first element as start, and the very last element as finish for starters
			
			setvisited(0,0);
			nodearr[0][0].ends = 'S';
			nodearr[arrwidth - 1][arrdepth - 1].ends = 'E';
			
	///////////////////////////////////////////////////////////////////////////////////////////////////////
			//Here is where the maze generating happens
			//I use two different display methods, One display method is to display the visited nodes
			//the other display methods displays the final path solution.
			
			//testing traversal recursion
			//within traversal, it calls display() to show the steps 
			traverse(0,0);
			if(debug){ //debug is necessary 
			display(); //display is required to display the final element
			}
			
			////////////////////////////////////////////////////////////
			//testing finding the the path from the source to the destination
			//lots of troubleshooting methods
			//placepath(0,0); //for troubleshooting
			//placepath(0,1); //for troubleshooting
			//placev(0,0); //for troubleshooting
			
			
			//pathadjacentsFilled(0,0);
			//solve(1,0);
			//pathadjacentsFilled(0,1);
			//solve(0,0); 
			//pathadjacentsFilled(0,0);
			
			cleanmap(); //clears the 'V' but keeps all pluses
			//display(); //to display after cleaning the map
			
			placepath(0,0);
			solve(0,0);
			
			//display();
			placepath(0,0); //sets the starting point and ending point as a +
			placepath(arrwidth-1,arrdepth-1);
			
			//for displaying purposes
			//troubleshootdisplaypath(); //for troubleshooting purposes, shows if other places have been traversed before backtracking
			displaypath(); //displays only walls and pluses
	///////////////////////////////////////////////////////////////////////////////////////////////////

			
		}
		
		void traverse(int j, int i){
			
			//remember, j is width, i is length
			//first check if the location selected is valid just for safety 
			if(!isvalid (j,i)){
				System.out.println("not valid, array location not set \n");
				return;
			}
		
			else{

				setvisited(j,i);
				
				//random number generator
				
				Random rand = new Random();
					
				while(!adjacentsFilled(j,i)){
					//setvisited(j,i);
					
					int x = rand.nextInt(4);
					
					if(x == 0 && isvalid(j+2,i) && !isvisited(j+2,i)){
					//go right 2
						if(debug){
						display();
						}
						breakwall(j+1, i);
						traverse(j+2,i);
						//break;
					}
					
					else if(x == 1 && isvalid(j-2, i) && !isvisited(j-2,i)){
					//go left 2
						if(debug){
						display();
						}
						breakwall(j-1, i);
						traverse(j-2, i);
						//break;
					}
					
					
					else if(x == 2 && isvalid(j, i+2) && !isvisited(j,i+2)){
					//go up 2
						if(debug){
						display();
						}
						breakwall(j, i+1);
						traverse(j, i+2);
						//break;
					}
					
					else if(x == 3 && isvalid(j, i-2) && !isvisited(j, i-2)){
					//go down 2
						if(debug){
						display();
						}
						breakwall(j, i- 1);
						traverse(j, i-2);
						//break;
					}
					
					} //ends while statement
				
				return;
					
					
			} //end else valid location
			
	}
		
		boolean isvalid(int j, int i){
			
			if(j >= arrwidth || i >= arrdepth || j<0 || i<0){
				return false;
			}
			
			
			else return true;
		}
		
		boolean isvisited(int j, int i){
			
			if(nodearr[j][i].wall == 'V' ){
				return true;
			}
			
			
			else return false;
		}
		
		boolean isEnd(int j, int i){
			
			if(nodearr[j][i].ends == 'E'){
				return true;
			}
			
			else return false;
		}
		
		void breakwall(int j, int i){
			
			nodearr[j][i].wall = ' ';
			
		}
		
		void setvisited(int j, int i){
			
			nodearr[j][i].wall = 'V';
			
		}

		
		boolean adjacentsFilled(int j, int i){
			//check for all adjacent values
			boolean bool = false;
			int of = 0;
			int count = 0;
			
			if(isvalid(j+2,i)){
				of++;
				if(isvisited(j+2,i)){
					count++;
				}
			}
			
			if(isvalid(j-2,i)){
				of++;
				if(isvisited(j-2,i)){
					count++;
				}
			}
			
			if(isvalid(j,i+2)){
				of++;
				if(isvisited(j,i+2)){
					count++;
				}
			}
			
			if(isvalid(j,i-2)){
				of++;
				if(isvisited(j,i-2)){
					count++;
				}
			}
			//display count and of
			//System.out.println("at location " + j + "," + i);
			//System.out.println(count + " out of " + of + " locations that are possible to be visited has been visited");
			
			//check if all locations that can be visited has been visited
			
			if(count == of){
				bool = true; 
			}
			
			//System.out.println("has everything been filled? " + bool);
			return bool;
		}
		
		
		
		
		void display(){
			
			
			//initially prints out the top of the graph
			
			System.out.print("X   ");
			for(int i = 0; i < arrwidth; i++){
			System.out.print("X ");	
			}
			System.out.println();
			
			//then print out the interiors of the graph
			for(int i = 0; i< arrdepth; i++){
			System.out.print("X");
				
				for(int j = 0; j< arrwidth; j++){
					
					System.out.print(" " + nodearr[j][i].wall);
					
					//System.out.print(" X");
					
				}
				System.out.println(" X");	
				//System.out.println();
			}
			
			
			//finally display the bottom of the graph
			for(int i = 0; i<arrwidth; i++){
				System.out.print("X ");
			}
			System.out.println("  X");
			
			System.out.println(); //just to space out multiple graphs
			
			
		}
		
///////////////////////////////////////////////////////////////////////////////////////////////////
	//solving path methods
	//attempt to solve using a stack
		
		void solve(int j, int i){
			
			Random rand = new Random();
			
			Stack<String> nodes = new Stack<>();
			
			nodes.push("Hello");
			
			while(!nodes.isEmpty()){
				if(isvalid(j,i)){
					if(isEnd(j,i))
					break;
				}
				
				if(isvalid(j+1,i)){
					if(isEnd(j+1,i))
						break;
				}
				
				if(isvalid(j-1, i)){
					if(isEnd(j-1,i))
						break;
				}
				
				if(isvalid(j,i+1)){
					if(isEnd(j,i+1))
						break;
				}
				
				if(isvalid(j, i-1)){
					if(isEnd(j,i-1))
						break;
				}
				
				
				int x = rand.nextInt(4);
				
				if(x == 0 && isvalid(j+1,i) && !haswall(j+1,i) && !isvisited(j+1,i)){ //right
					//troubleshootdisplaypath();
					nodes.push("right");
					placepath(j+1,i);
					placev(j+1,i);
					j = j+1;
					
					
				}
				
				else if(x == 1 && isvalid(j-1,i) && !haswall(j-1,i) && !isvisited(j-1,i)){ //left
					//troubleshootdisplaypath();
					nodes.push("left");
					placepath(j-1,i);
					placev(j-1,i);
					j = j-1;
					
					
				}
				
				else if(x == 2 && isvalid(j,i+1) && !haswall(j,i+1) && !isvisited(j,i+1)){ //up
					//troubleshootdisplaypath();
					nodes.push("up");
					placepath(j,i+1);
					placev(j,i+1);
					i = i+1;
					
					
				}
				
				else if(x == 3 &&isvalid(j,i-1) && !haswall(j,i-1) && !isvisited(j,i-1)){ //down
					//troubleshootdisplaypath();
					nodes.push("down");
					placepath(j,i-1);
					placev(j,i-1);
					i = i-1;
					
				}
				
				
				if(pathadjacentsFilled(j,i)){
					
					String popped = nodes.pop();
					
					if(popped.equals("right")){
						
						removepath(j,i);
						//troubleshootdisplaypath();
						j = j-1; //move left
					}
					
					else if(popped.equals("left")){
						removepath(j,i);
						//displaypath();
						j = j+1; //move right
						
					}
					
					else if(popped.equals("up")){
						removepath(j,i);
						//troubleshootdisplaypath();
						i = i-1; //move down
						
					}
					
					else if(popped.contentEquals("down")){
						removepath(j,i);
						//troubleshootdisplaypath();
						i = i+1; //move up
					}
					
					else 
					{
						break;
					}
					
					
				} //if adjacents filled
			} //while statement
			
		}
		
		
		void setpath(int j, int i){
			
			nodearr[j][i].path = '+';
			
		}
		
		void removepath(int j, int i){
			
			nodearr[j][i].path = ' ';
			
		}
		 
		void displaypath(){
			//initially prints out the top of the graph
			
			System.out.print("X   ");
			for(int i = 0; i < arrwidth; i++){
			System.out.print("X ");	
			}
			System.out.println();
			
			//then print out the interiors of the graph
			for(int i = 0; i< arrdepth; i++){
			System.out.print("X");
				
				for(int j = 0; j< arrwidth; j++){
					
					if(nodearr[j][i].path == '+'){
					System.out.print(" " + nodearr[j][i].path);
					}
					
					//Node element = new Node();
					else if(nodearr[j][i].wall == 'V'){ //if there is a 'V', don't display it
					System.out.print("  "); 
					}
					
					else
					System.out.print(" " + nodearr[j][i].wall);
					
					//System.out.print(" X");
					
				}
				System.out.println(" X");	
				//System.out.println();
			}
			
			
			//finally display the bottom of the graph
			for(int i = 0; i<arrwidth; i++){
				System.out.print("X ");
			}
			System.out.println("  X");
			
			System.out.println(); //just to space out multiple graphs

		}
		
		
		
		void cleanmap(){
				for(int i = 0; i<arrdepth; i = i+2){
				
				for(int j = 0; j<arrwidth; j = j+2){
					
					if(nodearr[j][i].wall == 'V')
					nodearr[j][i].wall = ' ';
					
				}
			}
			
		}
		
		boolean pathadjacentsFilled(int j, int i){
			//check for all adjacent values
			boolean bool = false;
			int of = 0;
			int count = 0;
			
			if(isvalid(j+1,i)){
				of++;
				if(haswall(j+1,i) || isvisited(j+1,i))
					count++;
			}
			
			if(isvalid(j-1,i)){
				of++;
				if(haswall(j-1,i) || isvisited(j-1,i))
					count++;
			}
			
			if(isvalid(j,i+1)){
				of++;
				if(haswall(j,i+1)|| isvisited(j,i+1))
					count++;
				
			}
			
			if(isvalid(j,i-1)){
				of++;
				if(haswall(j,i-1) || isvisited(j,i-1))
					count++;
			}
			//display count and of
			//System.out.println("at location " + j + "," + i);
			//System.out.println(count + " out of " + of + " locations that are possible to be visited has been visited");
			
			//check if all locations that can be visited has been visited
			
			if(count == of){
				bool = true; 
			}
			
			//System.out.println("has everything been filled? " + bool);
			return bool;
		}
		
	
		
		void placev(int j, int i){
			
			nodearr[j][i].wall = 'V';
			
			
		}
		
		void placepath(int j, int i){
			
			nodearr[j][i].path = '+';
			
			
		}
		
		boolean haswall(int j, int i){
			
			if(nodearr[j][i].wall == 'X'){
			return true;
			}
						
		return false;
		}
			
	
		void troubleshootdisplaypath(){ //displays the maze for troubleshooting
			//initially prints out the top of the graph
			
			System.out.print("X   ");
			for(int i = 0; i < arrwidth; i++){
			System.out.print("X ");	
			}
			System.out.println();
			
			//then print out the interiors of the graph
			for(int i = 0; i< arrdepth; i++){
			System.out.print("X");
				
				for(int j = 0; j< arrwidth; j++){
					
					if(nodearr[j][i].path == '+'){
					System.out.print(" " + nodearr[j][i].path);
					}
					
					
					else
					System.out.print(" " + nodearr[j][i].wall);
					
					//System.out.print(" X");
					
				}
				System.out.println(" X");	
				//System.out.println();
			}
			
			
			//finally display the bottom of the graph
			for(int i = 0; i<arrwidth; i++){
				System.out.print("X ");
			}
			System.out.println("  X");
			
			System.out.println(); //just to space out multiple graphs

		}
		
}
