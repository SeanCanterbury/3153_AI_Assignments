package Assignment_2;

import java.util.Comparator;

public class node implements Comparator<node>{
    private int row, col, f, g, h, type;
    private node parent;

    public node(int r, int c, int t){
        row = r;
        col = c;
        type = t;
        parent = null;
        //type 0 is traverseable, 1 is not
    }

    //mutator methods to set values
    public void setF(){
    f = g + h;
    }

    public void setG(int value){
    g = value;
    }

    public void setH(int value){
    h = value;
    }

    public void setParent(node n){
    parent = n;
    }

    public void setType(int t){
    type = t;
    }

    //accessor methods to get values
    public int getF(){
    return f;
    }

    public int getG(){
    return g;
    }

    public int getH(){
    return h;
    }

    public int getType(){
    return type;
    }

    public node getParent(){
    return parent;
    }

    public int getRow(){
    return row;
    }

    public int getCol(){
    return col;
    }

    public boolean equals(Object in){
    //typecast to node
    node n = (node) in;
    return row == n.getRow() && col == n.getCol();
    }

    public String toString(){
    return "node: " + row + "_" + col;
    }

    public int compare(node n1, node n2){
        return n1.getF() - n2.getF();
    }
}
