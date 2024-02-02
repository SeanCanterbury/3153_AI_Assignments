package Assignment_1;

import java.util.*;

public class board {
    //Board 2D array
    public int[][] board = new int[8][8];
    
    /*
     * 
     * Constructor for the board class
     */
    public board(int[][] newBoard){
        this.board = newBoard;
    }

    /*
     * 
     * Default Constructor for Board class
     */
    public board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++){
                board[i][j] = 0;
            }
        }
    }

    /*
     * updating the board array with given @value at the given @x and @y
     */
    public void updateBoard(int x, int y, int value){
        board[x][y] = value;
    }

    /*
     * 
     * Randomly resets board with 8 queens
     */
    public void resetBoard(){
        for(int i = 0; i < 8; i++){
            Arrays.fill(board[i], 0); 
        }

        for (int i = 0; i < 8; i++) {
            int index = (int)(Math.random() * 8);
            board[index][i] = 1;
        }
        
    }

    /*
     * 
     * prints board to terminal
     */
    public void showBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /*
     * 
     * checks for conflicts in the horizontal direction
     */
    public int checkHorozontal(){
        int count = 0;
        for(int i = 0; i < 8; i++){
            int sum = 0;
            for(int j = 0; j < 8; j++){
                sum += board[i][j];
            }
            if(sum > 1){
                count+= (sum - 1);
            }
        }
        return count;
    }

    /*
     * 
     * checks for conflicts in diagonal directions
     */
    public int checkDiagonal(){
        int count = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] == 1){
                    int xCord;
                    int yCord;
                    int xOrigin = i;
                    int yOrigin = j;
                    //check left to right diagonal \
                    for(xCord = xOrigin +1, yCord = yOrigin +1; xCord < 8 && yCord < 8; xCord++, yCord++){
                        if(board[xCord][yCord] == 1){
                            count++;
                        }
                    }
                    //Check right to left diagonal /
                    for(xCord = xOrigin +1, yCord = yOrigin -1; xCord < 8 && yCord >= 0; xCord++, yCord--){
                        if(board[xCord][yCord] == 1){
                            count++;
                        }
                    }
                    //only need to check from origins to the right so that the conflicts are not double counted
                }

            }
        }
        return count;
    }

    /*
     * 
     * Counts total conflicts by calling checkHorozontal and checkDiagonal
     */
    public int checkConflicts(){
        int conflicts = 0;
        conflicts += checkHorozontal();
        conflicts += checkDiagonal();
        return conflicts;
    }


    /*
     * 
     * Hill climbing algorithm solves 8 queen problem by checking every possible move at a given state and either moving to the best possible state
     * or resetting the board if no better state is found
     */
    public void hillClimbingAlg(int resets, int states){
        
        ArrayList<board> neighbors = new ArrayList<board>();
        int startingConflicts = checkConflicts();
        int currentConflicts = startingConflicts;
        int numBetterStates = 0;
       


        for(int i = 0; i < 8; i++){
            for(int j = 0; j<8; j++){
                if(board[i][j] == 1){
                    for(int k = 0; k < 8; k++){
                        if(k != i){
                            board neighborBoard = new board();
                            
                            for (int x = 0; x < 8; x++) {
                                System.arraycopy(board[x], 0, neighborBoard.board[x], 0, 8);
                            }

                            if (k >= 0 && k < 8 && j >= 0 && j < 8) {
                            neighborBoard.updateBoard(i, j, 0);
                            neighborBoard.updateBoard(k, j, 1);
                            neighbors.add(neighborBoard);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Current h: " + currentConflicts);
        System.out.println("Current State: ");
        showBoard();

        for(int i = 0; i < neighbors.size(); i++){
            int neighborConflicts = neighbors.get(i).checkConflicts();
            if(neighborConflicts < currentConflicts){
                numBetterStates++;
                currentConflicts = neighborConflicts;
                board = neighbors.get(i).board;  
                states++;              
            }
        }

        System.out.println("Neighbors found with lower h: " + numBetterStates);
        System.out.println("Setting new Current State: " + "\n");

        if(currentConflicts == 0){
            showBoard();
            System.out.println("Solution Found!!!");
            System.out.println("State Changes: " + states);
            System.out.println("Resets: " + resets);
            
        } else if(currentConflicts == startingConflicts){
            System.out.println("No better state Resetting: ");
            resets++;
            resetBoard();
            hillClimbingAlg(resets, states);
        } else {
            

            hillClimbingAlg(resets, states);
        }

        

    }
    
}