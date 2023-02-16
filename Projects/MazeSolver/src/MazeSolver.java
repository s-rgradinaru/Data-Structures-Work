import java.util.ArrayList;

/**
 * Solves mazes. Please refer to the specification for instructions on how to solve mazes.
**/
public class MazeSolver
{
	    /**
	     * Provides a solution for a given maze, if possible. A solution is a path from the start cell
	     * to the finish cell (inclusive). If there is no solution to the maze then returns the static
	     * instance {@link Path#NO_PATH}. If the maze is perfect then there must be only one solution.
	     *
	     * @param ourMaze the maze to solve
	     * @return a solution for the maze or {@link Path#NO_PATH} if there is no solution
	     */
		public class Point{
			public int x;
			public int y;
			public int tag;
			
			Point(int x, int y, int tag){
				this.x = x;
				this.y = y;
				this.tag = tag;
			}
			Point(int x, int y){
				this.x = x;
				this.y = y;
			}
			
			public boolean equals(Point point2) {
				return (this.x == point2.x && this.y == point2.y);
			}
			public Direction findWay(Point point2) {
				if (this.x < point2.x) {
					return Direction.RIGHT;
				}
				if (this.x > point2.x) {
					return Direction.LEFT;
				}
				if (this.y < point2.y) {
					return Direction.UP;
				}
				if (this.y > point2.y) {
					return Direction.DOWN;
				}
				return Direction.LEFT;
			}
			public String toString() {
				return "(" + this.x + ", " + this.y + ")" + " tag: " + tag;
			}
		}
		
		
		public Point getNeighborTag(Point point, ArrayList<Point> cells, Maze ourMaze) {
			Point pnt = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
			int tag = point.tag;
			for(Point x : cells) {
				if(x.tag < tag) {
					if(x.equals(new Point(point.x + 1, point.y)) && ourMaze.isOpen(point.x, point.y, Direction.RIGHT)) {
						pnt = x;
					}
					else if(x.equals(new Point(point.x - 1, point.y)) && ourMaze.isOpen(point.x, point.y, Direction.LEFT)) {
						pnt = x;
					}
					else if(x.equals(new Point(point.x, point.y + 1)) && ourMaze.isOpen(point.x, point.y, Direction.UP)) {
						pnt = x;
					}
					else if(x.equals(new Point(point.x, point.y - 1)) && ourMaze.isOpen(point.x, point.y, Direction.DOWN)) {
						pnt = x;
					}
				}
			}
			return pnt;
		}
		
		public int indexOf(Point point1, ArrayList<Point> points) {
			int index = -1;
			for(int i = 0; i < points.size(); i++) {
				if(point1.equals(points.get(i))) {
					index = i;
					break;
				}
			} 
			return index;
		}
		
		
	    public Path solve(Maze ourMaze)
	    {
	        int size = ourMaze.size();
	        
	        Path finalPath = Path.NO_PATH;

			Queue<Cell> cellQue = new Queue<Cell>();
			// initial size n
	        ArrayList<Point> cellStack = new ArrayList<Point>();
			
			Cell first = ourMaze.getStart();
			//finalPath.addFirst(first);

			cellQue.enqueue(first);
	        cellStack.add(new Point(first.getX(), first.getY(), 0));
	        
	        Point finalCell = null;
	        
			
			boolean foundEnd = false;
	        while(!cellQue.isEmpty() && !foundEnd)
	        {
	            Cell currCell = cellQue.dequeue();
	            int x = currCell.getX();
	            int y = currCell.getY();
	            Point pl = new Point(x, y);
	            
	            int tag = cellStack.get(indexOf(pl, cellStack)).tag;
	            //System.out.println(currCell);
	            //cellStack.add(new Point(x, y, tag + 1));
	            //cellStack.add(new Point(x, y, removed));
	           
	            ourMaze.visit(x, y);

	            if (currCell.equals(ourMaze.getEnd()))
	            {
	            	finalCell = new Point(x, y, tag);
	                foundEnd = true;
	            }
	            //System.out.println(currCell);
	            
	            if (x > 0)
	            {
	                if (!ourMaze.isVisited(x - 1, y) && ourMaze.isOpen(x, y, Direction.LEFT)){
	                	cellQue.enqueue(new Cell(x - 1, y));
	                	cellStack.add(new Point(x - 1, y, tag + 1));
	                	
	                }
	            }
	            if (x < size - 1)
	            {
	                if (!ourMaze.isVisited(x + 1, y) && ourMaze.isOpen(x, y, Direction.RIGHT)){
	                	cellQue.enqueue(new Cell(x + 1, y));
	                	cellStack.add(new Point(x + 1, y, tag + 1));
	                }
	            }
	            if (y < size - 1)
	            {
	                if (!ourMaze.isVisited(x, y + 1) && ourMaze.isOpen(x, y, Direction.UP)){
	                	cellQue.enqueue(new Cell(x, y + 1));
	                	cellStack.add(new Point(x, y + 1, tag + 1));
	                }
	            }
	            if (y > 0)
	            {
	                if (!ourMaze.isVisited(x, y - 1) && ourMaze.isOpen(x, y, Direction.DOWN)){
	                	cellQue.enqueue(new Cell(x, y - 1));
	                	cellStack.add(new Point(x, y - 1, tag + 1));
	                }
	            }
	            
	        }
	        int tag = Integer.MAX_VALUE;
	        ArrayList<Cell> holdCell = new ArrayList<Cell>();
	        if(finalCell != null){
	        	//System.out.println(finalCell);
	        	finalPath.addLast(new Cell(finalCell.x, finalCell.y));
	        	holdCell.add(new Cell(finalCell.x, finalCell.y));
	        	
	        	
	        	Point g = finalCell;
		        while(tag > 0) {
		        	
		        	
		        	//System.out.println(tag);
		        	//System.out.println(cellStack);
		        	
		        	Point x = getNeighborTag(g, cellStack, ourMaze);
		        	tag = x.tag;
		        	g = x;
		        	
		        	//System.out.println(tag);
		        	//System.out.println(x);
		        	
		        	Cell p = new Cell(x.x, x.y);
		        	
		        	//System.out.println(p);
		        	
		        	finalPath.addFirst(p);
		        	holdCell.add(p);
		        	//break;
		        	
		        }
		        //finalPath.addFirst(first);
	        }
	        //System.out.println(cellStack);
	        //int i = 0;
	        /*
	        for(int j = 0; j < holdCell.size(); j++) {
	        	for(int i = 0; i < holdCell.size(); i++) {
		        	if(holdCell.get(j).equals(holdCell.get(i)) && j != i) {
		        		System.out.println("REPEAT");
		        	}
	        	}
	        }
	        */
	        //System.out.println(holdCell);
	        //ourMaze.draw();
	        	
	        System.out.println(finalPath.toString());
	        return finalPath;
	    }

    /**
     * Creates, solves, and draws a sample maze. Try solving mazes with different sizes!
     *
     * @param args unused
     */
    public static void main(String[] args)
    {
        // First, generate a new maze.
        int size = 3; // Setting above 200 is not recommended!
        MazeGenerator generator = new MazeGenerator();
        Maze maze = generator.generate(size);
        maze.freeze();

        // Next, solve it!
        MazeSolver solver = new MazeSolver();
        maze.resetVisited();
        Path solutionPath = solver.solve(maze);
        maze.setSolution(solutionPath);

        // This is so we can see which cells were explored and in what order.
        maze.setDrawVisited(true);

        maze.draw();
    }
}
