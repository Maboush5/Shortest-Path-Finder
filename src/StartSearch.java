import java.io.FileNotFoundException;
import java.io.IOException;

public class StartSearch {

	private Map map;

	public StartSearch(String mapName) {
		
		try {
			map = new Map(mapName);		// new map object takes in file name as parameter
			
		} catch (InvalidMapException e) {		// catch exceptions
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	public MapCell bestCell(MapCell currentCell) {
				
		try {
			
//			 first check is neighboring cell is covid or is marked in stack
			for (int i = 0; i < 4; i++) {
				
				if (currentCell.getNeighbour(i).isCovid() ||  currentCell.getNeighbour(i).isMarked()) { // if neighboring cell is covid or marked return null
					
					return null;
					
					}
				
				else {
					
				
				}
		
			}
			
			// if currentCell is a vertical path, can only go north and south (index 0 and 2)
			if (currentCell.isVerticalPath()) {
			
				for (int i = 0; i < 3; i++) {	// loop at index 0 and 2
					MapCell neighbour = currentCell.getNeighbour(i);
					
					if (neighbour.isExit()) {		// First priority
						
						return neighbour;
				
					}	
			
					else if (neighbour.isDonut()) {	// Second priority
						return neighbour;
					}
					
					else if (neighbour.isCrossPath()) {    // Third priority
						
						return neighbour;
						
					}
					else if (neighbour.isVerticalPath()) {		// Fourth priority
						
						return neighbour;
						
						}
					
					i++;	// skip i = 1, check index 2
				}
				
			}	// end elseIf for vertical path	
			
			// if currentCell is a horizontal path, can only go east and west (index 1 and 3)
			else if (currentCell.isHorizontalPath()) {
				
				for (int i = 1; i < 4; i++) {	
					MapCell neighbour = currentCell.getNeighbour(i);
					
					if (neighbour.isExit()) {			// First priority						
						return neighbour;				
					}	
			
					else if (neighbour.isDonut()) {			// Second priority
						return neighbour;
					}
					
					else if (neighbour.isCrossPath()) {		// Third priority
						
						return neighbour;
						
					}
					else if (neighbour.isHorizontalPath()) {	// Fourth priority
						
						return neighbour;
						
						}
					
					i++;		// skip i = 2, check index 3
				}
				
			} // end elseIf for horizontal path	
			
			// check if neighbor is a destination cell
			else {
				
				
				// Check indices 0 - 3 in order of cell priority:				
				for (int i = 0; i < 4; i++) {
			
				MapCell neighbour = currentCell.getNeighbour(i);
				
				if (neighbour.isExit()) {
					
					return neighbour;
					
				}
			}
			
			// check if neighbor is donut cell
			for (int i = 0; i < 4; i++) {
				
				MapCell neighbour = currentCell.getNeighbour(i);
				
				if (neighbour.isDonut()) {
					
					return neighbour;
					
				}
			}
			// check if neighbor is a cross path
			for (int i = 0; i < 4; i++) {
				
				MapCell neighbour = currentCell.getNeighbour(i);
				
				if (neighbour.isCrossPath()) {
					
					return neighbour;
					
					}
				}
			
			
			// check if neighbor is horizontal or vertical path
			for (int i = 0; i < 4; i++) {
				
				MapCell neighbour = currentCell.getNeighbour(i);
				
				if (neighbour.isHorizontalPath() || neighbour.isVerticalPath()) {
					
					return neighbour;
					
					}
				}
			}
			
		} catch (InvalidNeighbourIndexException e) {	// catch invalid neighbour exception
						
			e.printStackTrace();
		}
				
		return null;
	}
			
	public String findPath() {
		
		ArrayStack path = new ArrayStack();		// Stack to hold steps
		String actionString = "";				// string to hold steps taken
		boolean statusFlag = false;			// return true if destination is reached
		int energyLevel = 10;				// track energy level
		
		MapCell startCell = this.map.getStart();	// get start cell, push it into stack, mark in stack
		
		path.push(startCell);
		
		startCell.markInStack();

		while (!path.isEmpty() && statusFlag == false) {		// while stack isnt empty and destination isnt reached, continue;
			
			MapCell currentCell = (MapCell) path.peek();	// current cell is the last item in the stack
			
			MapCell nextCell = bestCell(currentCell);		
			
			actionString +=  nextCell.toString() + "-";	// add cell ID to string
			
			if (nextCell != null && energyLevel > 0) {
				
				if (nextCell.isExit()) { // if destinations been reached, statusFlag is true
					
					statusFlag = true;
																
				}
				
				if (nextCell.isDonut()) {
					
					energyLevel = energyLevel + 3;	// increase energy level by 3
					
				}
						
				path.push(nextCell);
				nextCell.markInStack();
				energyLevel = energyLevel - 1;
				
			}
			
			else {    // Since there are no unmarked neighbouring cells, do the following:
				
			MapCell lastStep = (MapCell) path.pop();
			lastStep.markOutStack();
				
			if (lastStep.isDonut()) {	// since were going back, decremene energy level by 3
				
				energyLevel = energyLevel - 3;			
				
			}
			
			if (!lastStep.isStart()) {	// increase energy by 1 since we are taking back a movement
				
				energyLevel = energyLevel + 1;
							
			}
				
				
				
			} // end else
			
			MapCell popped = (MapCell) path.pop();
			popped.markOutStack();
			
		
		
		} // end method
		actionString = actionString + "E" + energyLevel;
		return	actionString  ;
		
	}

	
	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("You must provide the name of the input file");
			}
			String mapFile = args[0];
			StartSearch ss = new StartSearch(mapFile);
			
	}

}
