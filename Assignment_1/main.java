package Assignment_1;


class Main{
    public static void main(String[] args) {
        System.out.println("Starting Program");
        board b = new board();
        b.resetBoard();
        b.hillClimbingAlg(0, 0);
    }
}