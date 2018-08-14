package sudoku.solution;
import java.util.ArrayList;
/**
 *
 * @author Thomas Caron
 */
public class Cell {
    public int Numberint;
    //public Row numbersRow;
    public int numbersRow;
    //public Column numbersColumn;
    public int numbersColumn;
    
    //the ArrayList possibleNums and assigned a value in the simpleFill method of the solution class. it is used to help eliminate some of the spots in it's box
    public ArrayList<Integer> possibleNums = new ArrayList<Integer>();
    
    public Cell(int num, int r, int c){
        this.Numberint = num;
        this.numbersRow = r;
        this.numbersColumn = c;

    }


    public void hasTriple(Row[] rows, Column[] cols, Box[] boxes){
//check if this cell has a pair in its row
        rows[this.numbersRow].containsTripleRow(this);
        
        //check if this cell has a pair in its row
        cols[this.numbersColumn].containsTripleCol(this);
        
//check if this cell has a pair in its box
        int boxnum=(this.numbersRow/3)*3 + this.numbersColumn%3;
        boxes[boxnum].containsTripleBox(this); 
    }
    
}

