package Assignment_2;
import java.util.*;

public class main {
    public static void main(String[] args) {
        System.out.println("Starting Program");
        node[][] env = new node[15][15];
        Scanner sc = new Scanner(System.in);
        
        System.out.println("After you enter your start and goal nodes they will be marked on the grid with 2 and 3 respectively.");


        //Setting up env with random obstacles
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++){
                if(Math.random() < 0.1){
                    env[i][j] = new node(i, j, 1);
                }
                else {
                    env[i][j] = new node(i, j, 0);
                }
            }
        }

        printEnv(env);

        //setting start and goal nodes
        System.out.println("Enter the starting row: ");
        int startRow = sc.nextInt();
        System.out.println("Enter the starting column: ");
        int startCol = sc.nextInt();
        System.out.println("Enter the ending row: ");
        int goalRow = sc.nextInt();
        System.out.println("Enter the ending column: ");
        int goalCol = sc.nextInt();

        //2 = start, 3 = goal
        env[startRow][startCol].setType(2);
        env[goalRow][goalCol].setType(3);
        node start = env[startRow][startCol];
        node goal = env[goalRow][goalCol];
        node current = start;

        PriorityQueue<node> openList = new PriorityQueue<>(Comparator.comparingInt(node::getF));
        ArrayList<node> closedList = new ArrayList<node>();

        openList.add(start);
        
        //A* algorithm
        while(!openList.isEmpty()){
            current = openList.poll();
            if(current.equals(goal)){
                System.out.println("Path to goal found.");
                break;
            }

            closedList.add(current);

            //calculate h and g for all pathable nodes surrounding current node
            for (int i = current.getRow() - 1; i <= current.getRow() + 1; i++) {
                for (int j = current.getCol() - 1; j <= current.getCol() + 1; j++) {
                    // check if the node is in env
                    if (i >= 0 && i < env.length && j >= 0 && j < env[0].length) {
                        node neighbor = env[i][j];
                        // check if the node is not a wall or in the closed list
                        if (neighbor.getType() == 1 || closedList.contains(neighbor)) {
                            continue;
                        }

                        // Temp g score to check if the current path is better than the previous one
                        int tempG = current.getG() + calcG(current, neighbor);

                        if (tempG < neighbor.getG() || !openList.contains(neighbor)) {
                            neighbor.setG(tempG);
                            neighbor.setH(calcH(neighbor, goal));
                            neighbor.setF();
                            neighbor.setParent(current);

                            if (!openList.contains(neighbor)) {
                                openList.add(neighbor);
                            }
                        }
                    }
                }
            }
        }

        //Showing path from start to goal      
        if(!current.equals(goal)){
            System.out.println("no path could be found");
        }

        LinkedList<node> path = new LinkedList<>();
        node temp = current; 
        
        while (temp != null) {
            path.addFirst(temp); // Add the node to the beginning of the list
            temp = temp.getParent(); // Move to the parent node
        }
    
        System.out.println("Path:");
        for (node p : path) {
            //tracing path from start to goal 
            if (!p.equals(start) && !p.equals(goal)) {
                env[p.getRow()][p.getCol()].setType(7); 
                try {
                    Thread.sleep(500); // Delay for 1 second
                } catch (InterruptedException e) {
                    System.out.println("Oops!");
                }
                printEnv(env);
                System.out.println("\n");
            }
        }
        
        System.out.println("Final Path:");
        printEnv(env);
        
        

        sc.close();
    }




    //prints the environment to the terminal
    public static void printEnv(node[][] env){
        for(int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++){
                System.out.print(env[i][j].getType() + " ");
            }
            System.out.println();
        }
    }

    //calculating h from current node to goal node
    public static int calcH(node current, node goal){
        return Math.abs(goal.getRow() - current.getRow()) + Math.abs(goal.getCol() - current.getCol());
    }

    //calculating g from current node to next node assuming next is directly nexto current node
    public static int calcG(node current, node next){
        //if diagonal move, cost is 14, else 10
        int cost = current.getG(); // Get parent's g cost
        if(Math.abs(current.getCol() - next.getCol()) == 1 && Math.abs(current.getRow() - next.getRow()) == 1) {
            cost += 14; // Diagonal
        }
        else {
            cost += 10; // Straight
        }
        return cost;
    }

}
