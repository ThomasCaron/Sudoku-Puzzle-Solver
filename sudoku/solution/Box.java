package sudoku.solution;
/**
 * @author Thomas Caron
 * 
 */
public class Box {
    public int boxNum;
    public Cell[] numsInBox = new Cell [9];
    
//    isRowFilled[0] represents if the three number slots in the top row of the box are filled in. [0]=top, [1]=middle, [2]=bottom
    boolean [] isRowFilled = new boolean [3];
    
//    isColFilled[0] represents if the three number slots in the left column of the box are filled in. [0]=left, [1]=middle, [2]=right
    boolean [] isColFilled = new boolean [3];
    /*
    below is the grid of Boxs. in the Grid num==Box.boxNum
    the %3 and /3 is how the Box's locstion in the grid will be determined.
    ex: box with num=4; 4%3=1 and 4/3=1
    ============================
    |num=0   |num=1   |num=2   |
    | /3=0   | /3=0   | /3=0   |
    | %3=0   | %3=1   | %3=2   |
    ============================
    |num=3   |num=4   |num=5   |
    | /3=1   | /3=1   | /3=1   |
    | %3=0   | %3=1   | %3=2   |
    ============================
    |num=6   |num=7   |num=8   |
    | /3=2   | /3=2   | /3=2   |
    | %3=0   | %3=1   | %3=2   |
    ============================
    */
    
    
    
    public Box(int boxnum){
        this.boxNum=boxnum;
    }
    
    /**
     *Searchs the Box for the num, by checking it against the Cell.Numberint field of the Numbers in the Box.
     * @param num The number you are searching the Box for.
     * @return If the Box contains the num, it will return the Cell object. ***If it does NOT contain the num, it will return NULL***
     */
    public Cell contains(int num){
        for( int i=0; i<9; i++){
            if(this.numsInBox[i].Numberint==num){
                return this.numsInBox[i];
            }
        }
        return null;       
    }

    /**
     *Counts the number of possibleNums that contain the value num.
     * @param num The value in possibleNums being counted.
     * @param box The box of Numbers you are doing the counting in.
     * @return
     */
    public int countOccurancesBox(int num) {
        int occurances=0;
        Cell n;
        for(int i=0; i<9; i++){
            n = this.numsInBox[i];
            // n is a Cell object
            if(n.Numberint==0 && n.possibleNums.contains(num)){
                occurances++;
            }
        }
        return occurances;
    }
    
    /**
     *This method fills out the objects isRowFilled and isColFilled for the eliminatePossibleNums to use later on.
     */
    public void checkIfFilledRowsorCol() {
//        check if the three number slots in the boxes rows are filled then do the same thing for its columns
        if(this.numsInBox[0].Numberint!=0 && this.numsInBox[1].Numberint!=0 && this.numsInBox[2].Numberint!=0){
            this.isRowFilled[0]=true;
        }
        else if(this.numsInBox[3].Numberint!=0 && this.numsInBox[4].Numberint!=0 && this.numsInBox[5].Numberint!=0){
            this.isRowFilled[1]=true;
        }
        else if(this.numsInBox[6].Numberint!=0 && this.numsInBox[7].Numberint!=0 && this.numsInBox[8].Numberint!=0){
            this.isRowFilled[2]=true;
        }
        
//        check if the three number slots in the boxes columns are filled
        if(this.numsInBox[0].Numberint!=0 && this.numsInBox[3].Numberint!=0 && this.numsInBox[6].Numberint!=0){
            this.isColFilled[0]=true;
        }
        else if(this.numsInBox[1].Numberint!=0 && this.numsInBox[4].Numberint!=0 && this.numsInBox[7].Numberint!=0){
            this.isColFilled[1]=true;
        }
        else if(this.numsInBox[2].Numberint!=0 && this.numsInBox[5].Numberint!=0 && this.numsInBox[8].Numberint!=0){
            this.isColFilled[2]=true;
        }
    }

    /**
     * Searches The box object for a TriplePair. if it finds one it will remove the appropriate elements out of the speficied arrays.
     * @param c A Cell object. this will be the basis of the methods searching.
     */
    public void containsTripleBox(Cell c) {
        int val1=-1, val2=-1, val3=-1;
        Cell c1=null, c2=null;
        boolean tbr=false;
        
            //        CASES:
            //1) THE BASE CELL, c, HAS 3 ELEMENTS IN possibleNums   (easist)
        if(c.possibleNums.size()==3){
            val1=c.possibleNums.get(0).intValue();
            val2=c.possibleNums.get(1).intValue();
            val3=c.possibleNums.get(2).intValue();
            //go looking through the row for cells that have A:(the same three elements)  or  B: (two the the three elements)
            //iterate through the col looking for a triple
            for(int b=0; b<9; b++){
                if(this.numsInBox[b] != c){


                    //each cell has two options at this point if it is going to be part of the triple. 
                    // 1:need to have the same three possibleNums as Cell c
                    // 2:needs to have two elements in possibleNums, and those two elements must be two of the three elements that Cell c has.

                    if(this.numsInBox[b].possibleNums.size()==3){

                        if(  (c.possibleNums.contains(this.numsInBox[b].possibleNums.get(0))) &&
                                (c.possibleNums.contains(this.numsInBox[b].possibleNums.get(1))) &&
                                (c.possibleNums.contains(this.numsInBox[b].possibleNums.get(2))) ){

                            if(c1==null) {
                                c1=this.numsInBox[b];
                            }
                            else if(c1!=null){// this would mean that another value has already been that could be part of the triple.
                                c2=this.numsInBox[b];
                                tbr=true;
                            }
                        }
                    }
                    else if(this.numsInBox[b].possibleNums.size()==2){
                        if(  (c.possibleNums.contains(this.numsInBox[b].possibleNums.get(0))) &&
                                (c.possibleNums.contains(this.numsInBox[b].possibleNums.get(1))) ){

                            if(c1==null) {
                                c1=this.numsInBox[b];
                            }
                            else if(c1!=null && 
                            (c1.possibleNums.contains(this.numsInBox[b].possibleNums.get(0)) ^
                            c1.possibleNums.contains(this.numsInBox[b].possibleNums.get(1))==false) ){
                                c2=this.numsInBox[b];
                                tbr=true;
                            }
                        }
                    }
                }
            }
        }
        
            //2)  BASE CELL HAS 2 ELEMENTS IN possibleNums    (worst case)        
        
        else if(c.possibleNums.size()==2){
            val1=c.possibleNums.get(0);
            val2=c.possibleNums.get(1);
            for(int b=0; b<9; b++){
                //iterate through the Cell c' possibleNums
                if(this.numsInBox[b] != c){
                    
                    //option 1:
                    if(this.numsInBox[b].possibleNums.size()==3){
                        if(c1==null && (this.numsInBox[b].possibleNums.contains(c.possibleNums.get(0)) &&
                            this.numsInBox[b].possibleNums.contains(c.possibleNums.get(1)))){
                            c1=this.numsInBox[b];
                        }
                        else if(c1!=null){
                            if(c1.possibleNums.size()==3 && 
                                    (c1.possibleNums.contains(this.numsInBox[b].possibleNums.get(0)) &&
                                    c1.possibleNums.contains(this.numsInBox[b].possibleNums.get(1)) &&
                                    c1.possibleNums.contains(this.numsInBox[b].possibleNums.get(2))) ){
                                c2=this.numsInBox[b];
                                tbr=true;
                            }
                            else{//c1.possibleNums.size()==2
                                if(this.numsInBox[b].possibleNums.contains(c.possibleNums.get(0)) && this.numsInBox[b].possibleNums.contains(c.possibleNums.get(1)) &&
                                        this.numsInBox[b].possibleNums.contains(c1.possibleNums.get(0)) && this.numsInBox[b].possibleNums.contains(c1.possibleNums.get(1))){
                                    c2=this.numsInBox[b];
                                    tbr=true;
                                }
                            
                                
                            }
                        }
                    }
                    
                    //option 2:
                    else if(this.numsInBox[b].possibleNums.size()==2){
//                        int sharedNum=-1;
                        if(c1==null && 
                        (c.possibleNums.contains(this.numsInBox[b].possibleNums.get(0)) ^
                        c.possibleNums.contains(this.numsInBox[b].possibleNums.get(1))) ){
                            c1=this.numsInBox[b];
                            if(c.possibleNums.contains(c1.possibleNums.get(0))==true) val3=c1.possibleNums.get(0);//nums[0]=c.possibleNums.get(0).intValue();
                            else val3=c1.possibleNums.get(1);
                        }
                        
                        //final check to make sure we are grabing a triple pair and not a pair or four pair
                        else if(c1!=null && 
                        (c1.possibleNums.size()==2 && (this.numsInBox[b].possibleNums.contains(val3)==false)))
                            //makes sure c and this.numberList[r] are not pointingPairs
                                c2=this.numsInBox[b];
                                tbr=true;
                        }
                    }//in this case we know that both c and what would be c2 have a possibleNums of size 2
            }
        }
//        return false;
    }
    
    /**
     *The hasPointingPairs Method looks for pointing pairs(described within the method)
     * When it finds one, it uses the information gained to logicaly rule out and remove some possibleNums 
     * @param c c Is the Cell that the method is parsing through the box to find a pair for.
     * @param rows The static Rows Object. used for easier access to certain things.
     * @param cols The static Cols object. also used for easier access to things.
     */
    public void hasPointingPairBox(Cell c, Row[] rows, Column[] cols){
/*
If a pair of empty cells within a box in the same row or column share a given candidate then that candidate
can be removed from the candidate list of all other cells in the row or column if it is not a candidate of any of the other cells in the box. 
*/        

/*
there are two types of pointing pairs; 
1) in-box where the two pairs are in the same row/col of the box. in that case you would be deleting from the row/col  
2) in-row/col where the pairs are the only ones in the row and you would delete from the box 
*/

Cell cell0=null;
        Cell cell1=null;
        Cell cell2=null;
        Cell cell3=null;
        if(c.numbersColumn%3==0){
            cell0=rows[c.numbersRow].numberList[c.numbersColumn+1];
            cell1=rows[c.numbersRow].numberList[c.numbersColumn+2];
        }
        else if(c.numbersColumn%3==1){
            cell0=rows[c.numbersRow].numberList[c.numbersColumn-1];
            cell1=rows[c.numbersRow].numberList[c.numbersColumn+1];
        }
        else{
            cell0=rows[c.numbersRow].numberList[c.numbersColumn-2];
            cell1=rows[c.numbersRow].numberList[c.numbersColumn-1]; 
        }
        
        if(c.numbersRow%3==0){
            cell2=cols[c.numbersColumn].numberList[c.numbersRow+1];
            cell3=cols[c.numbersColumn].numberList[c.numbersRow+2];
        }
        else if(c.numbersRow%3==1){
            cell2=cols[c.numbersColumn].numberList[c.numbersRow-1];
            cell3=cols[c.numbersColumn].numberList[c.numbersRow+1];
        }
        else{
            cell2=cols[c.numbersColumn].numberList[c.numbersRow-2];
            cell3=cols[c.numbersColumn].numberList[c.numbersRow-1];
        }
        
//ORDER OF CHECKS:
/*
* TYPE 1 row c.possibleNums.get(0) the the ' ^ ' operator
* TYPE 1 row c.possibleNums.get(0) the the ' && ' operator

* TYPE 1 row c.possibleNums.get(1) the the ' ^ ' operator
* TYPE 1 row c.possibleNums.get(1) the the ' && ' operator

* TYPE 1 column c.possibleNums.get(0) the the ' ^ ' operator
* TYPE 1 column c.possibleNums.get(0) the the ' && ' operator

* TYPE 1 column c.possibleNums.get(1) the the ' ^ ' operator
* TYPE 1 column c.possibleNums.get(1) the the ' && ' operator


* TYPE 2 row c.possibleNums.get(0) the the ' ^ ' operator
* TYPE 2 row c.possibleNums.get(0) the the ' && ' operator

* TYPE 2 row c.possibleNums.get(1) the the ' ^ ' operator
* TYPE 2 row c.possibleNums.get(1) the the ' && ' operator

* TYPE 2 column c.possibleNums.get(0) the the ' ^ ' operator
* TYPE 2 column c.possibleNums.get(0) the the ' && ' operator

* TYPE 2 column c.possibleNums.get(1) the the ' ^ ' operator
* TYPE 2 column c.possibleNums.get(1) the the ' && ' operator
*/

//start  TYPE 1 row c.possibleNums.get(0) the the ' ^ ' operator
//        if only one of them contains it. need to make this distinction because it may affect the occurance count and potentially the result
        if(cell0.possibleNums.contains(c.possibleNums.get(0)) ^ cell1.possibleNums.contains(c.possibleNums.get(0))){
//if Cell c and either of these two cells are the only locations in the box that has the possibleNums c.possibleNums.get(0), and they are in the same row
//then it is legal to remove all c.possibleNums.get(0)
            if(this.countOccurancesBox(c.possibleNums.get(0)) == 2){
                //then remove c.possibleNums.get(0) from all cells in box except those in c.numbersRow
                int boxMinbound=(this.boxNum%3)*3;
                int boxMaxbound=boxMinbound+2;
                for(int i=0; i<9; i++){
                    if(rows[c.numbersRow].numberList[i].numbersColumn<boxMinbound || rows[c.numbersRow].numberList[i].numbersColumn>boxMaxbound){
                        rows[c.numbersRow].numberList[i].possibleNums.remove(c.possibleNums.get(0));
                    }
                }
            }
        }//end  TYPE 1 row c.possibleNums.get(0) the the ' ^ ' operator
        
//start  TYPE 1 row c.possibleNums.get(0) the the ' && ' operator
        else if(cell0.possibleNums.contains(c.possibleNums.get(0)) && cell1.possibleNums.contains(c.possibleNums.get(0))){
//if Cell c and these two cells are the only locations in the box that has the possibleNums c.possibleNums.get(0), and they are in the same row
//then it is legal to remove all c.possibleNums.get(0)
            if(this.countOccurancesBox(c.possibleNums.get(0)) == 3){
                //then remove c.possibleNums.get(0) from all cells in box except those in c.numbersRow
                int boxMinbound=(this.boxNum%3)*3;
                int boxMaxbound=boxMinbound+2;
                for(int i=0; i<9; i++){
                    if(rows[c.numbersRow].numberList[i].numbersColumn<boxMinbound || rows[c.numbersRow].numberList[i].numbersColumn>boxMaxbound){
                        rows[c.numbersRow].numberList[i].possibleNums.remove(c.possibleNums.get(0));
                    }
                }
            }
        } //end of  TYPE 1 row c.possibleNums.get(0) the the ' && ' operator
        
        
        
//start  TYPE 1 row c.possibleNums.get(1) the the ' ^ ' operator        
//        if only one of them contains it. need to make this distinction because it may affect the occurance count and potentially the result
        if(cell0.possibleNums.contains(c.possibleNums.get(1)) ^ cell1.possibleNums.contains(c.possibleNums.get(1))){
//if Cell c and either of these two cells are the only locations in the box that has the possibleNums c.possibleNums.get(0), and they are in the same row
//then it is legal to remove all c.possibleNums.get(0)
            if(this.countOccurancesBox(c.possibleNums.get(1)) == 2){
                //then remove c.possibleNums.get(0) from all cells in box except those in c.numbersRow
                int boxMinbound=(this.boxNum%3)*3;
                int boxMaxbound=boxMinbound+2;
                for(int i=0; i<9; i++){
                    if(rows[c.numbersRow].numberList[i].numbersColumn<boxMinbound || rows[c.numbersRow].numberList[i].numbersColumn>boxMaxbound){
                        rows[c.numbersRow].numberList[i].possibleNums.remove(c.possibleNums.get(1));
                    }
                }
            }
        }//end  TYPE 1 row c.possibleNums.get(1) the the ' ^ ' operator
        
//start  TYPE 1 row c.possibleNums.get(1) the the ' && ' operator
        else if(cell0.possibleNums.contains(c.possibleNums.get(1)) && cell1.possibleNums.contains(c.possibleNums.get(1))){
//if Cell c and these two cells are the only locations in the box that has the possibleNums c.possibleNums.get(0), and they are in the same row
//then it is legal to remove all c.possibleNums.get(0)
            if(this.countOccurancesBox(c.possibleNums.get(1)) == 3){
                //then remove c.possibleNums.get(0) from all cells in box except those in c.numbersRow
                int boxMinbound=(this.boxNum%3)*3;
                int boxMaxbound=boxMinbound+2;
                for(int i=0; i<9; i++){
                    if(rows[c.numbersRow].numberList[i].numbersColumn<boxMinbound || rows[c.numbersRow].numberList[i].numbersColumn>boxMaxbound){
                        rows[c.numbersRow].numberList[i].possibleNums.remove(c.possibleNums.get(1));
                    }
                }
            }
        }//end  TYPE 1 row c.possibleNums.get(1) the the ' && ' operator
        
//start  TYPE 1 column c.possibleNums.get(0) the the ' ^ ' operator
        if(cell2.possibleNums.contains(c.possibleNums.get(0)) ^ cell3.possibleNums.contains(c.possibleNums.get(0))){
            if(this.countOccurancesBox(c.possibleNums.get(0)) == 2){
                int boxMinbound=(this.boxNum/3)*3;
                int boxMaxbound=boxMinbound+2;
                for(int i=0;i<9; i++){
                    if(cols[c.numbersColumn].numberList[i].numbersRow <boxMinbound || cols[c.numbersColumn].numberList[i].numbersRow >boxMaxbound){
                        cols[c.numbersColumn].numberList[i].possibleNums.remove(c.possibleNums.get(0));
                    }
                }
            }
        }//end  TYPE 1 column c.possibleNums.get(0) the the ' ^ ' operator
        
//start  TYPE 1 column c.possibleNums.get(0) the the ' && ' operator
        else if(cell2.possibleNums.contains(c.possibleNums.get(0)) && cell3.possibleNums.contains(c.possibleNums.get(0))){
            if(this.countOccurancesBox(c.possibleNums.get(0)) == 3){
                int boxMinbound=(this.boxNum/3)*3;
                int boxMaxbound=boxMinbound+2;
                for(int i=0;i<9; i++){
                    if(cols[c.numbersColumn].numberList[i].numbersRow <boxMinbound || cols[c.numbersColumn].numberList[i].numbersRow >boxMaxbound){
                        cols[c.numbersColumn].numberList[i].possibleNums.remove(c.possibleNums.get(0));
                    }
                }
            }
        }//end  TYPE 1 column c.possibleNums.get(0) the the ' && ' operator
        
//start  TYPE 1 column c.possibleNums.get(1) the the ' ^ ' operator
        if(cell2.possibleNums.contains(c.possibleNums.get(1)) ^ cell3.possibleNums.contains(c.possibleNums.get(1))){
            if(this.countOccurancesBox(c.possibleNums.get(1)) == 2){
                int boxMinbound=(this.boxNum/3)*3;
                int boxMaxbound=boxMinbound+2;
                for(int i=0;i<9; i++){
                    if(cols[c.numbersColumn].numberList[i].numbersRow <boxMinbound || cols[c.numbersColumn].numberList[i].numbersRow >boxMaxbound){
                        cols[c.numbersColumn].numberList[i].possibleNums.remove(c.possibleNums.get(1));
                    }
                }
            }
        }//end  TYPE 1 column c.possibleNums.get(1) the the ' ^ ' operator
        
//start  TYPE 1 column c.possibleNums.get(1) the the ' && ' operator
        else if(cell2.possibleNums.contains(c.possibleNums.get(1)) && cell3.possibleNums.contains(c.possibleNums.get(1))){
            if(this.countOccurancesBox(c.possibleNums.get(1)) == 3){
                int boxMinbound=(this.boxNum/3)*3;
                int boxMaxbound=boxMinbound+2;
                for(int i=0;i<9; i++){
                    if(cols[c.numbersColumn].numberList[i].numbersRow <boxMinbound || cols[c.numbersColumn].numberList[i].numbersRow >boxMaxbound){
                        cols[c.numbersColumn].numberList[i].possibleNums.remove(c.possibleNums.get(1));
                    }
                }
            }
        }//end  TYPE 1 column c.possibleNums.get(1) the the ' && ' operator
        
      
//type 2:
/*
If a pair of empty cells within a box in the same row or column share a given candidate, then that candidate can be removed from
the candidate list of all other cells in the box if it is not a candidate of any other cells in the row or column.         
*/       
        

        
        
//TYPE 2 ROW
        
//start TYPE 2 row c.possibleNums.get(0) the the ' ^ ' operator
//if only one of them contains it. need to make this distinction because it may affect the occurance count and potentially the result
        if(cell0.possibleNums.contains(c.possibleNums.get(0)) ^ cell1.possibleNums.contains(c.possibleNums.get(0))){
            if(rows[c.numbersRow].countOccurancesRow(c.possibleNums.get(0)) == 2){
                //then remove c.possibleNums.get(0) from all cells in box except those in c.numbersRow
                for(int i=0; i<9; i++){
                    if(this.numsInBox[i].numbersRow!=c.numbersRow){
                        this.numsInBox[i].possibleNums.remove(c.possibleNums.get(0));
                    }
                }
            }
        }//end  TYPE 2 row c.possibleNums.get(0) the the ' ^ ' operator
        
//start  TYPE 2 row c.possibleNums.get(0) the the ' && ' operator
        else if(cell0.possibleNums.contains(c.possibleNums.get(0)) && cell1.possibleNums.contains(c.possibleNums.get(0))){
            if(rows[c.numbersRow].countOccurancesRow(c.possibleNums.get(0)) == 3){
                //then remove c.possibleNums.get(0) from all cells in box except those in c.numbersRow
                for(int i=0; i<9; i++){
                    if(this.numsInBox[i].numbersRow!=c.numbersRow){
                        this.numsInBox[i].possibleNums.remove(c.possibleNums.get(0));
                    }
                }
            }
        }//end  TYPE 2 row c.possibleNums.get(0) the the ' && ' operator
        
//start  TYPE 2 row c.possibleNums.get(1) the the ' ^ ' operator
        if(cell0.possibleNums.contains(c.possibleNums.get(1)) ^ cell1.possibleNums.contains(c.possibleNums.get(1))){
            if(rows[c.numbersRow].countOccurancesRow(c.possibleNums.get(1)) == 2){
                //then remove c.possibleNums.get(0) from all cells in box except those in c.numbersRow
                for(int i=0; i<9; i++){
                    if(this.numsInBox[i].numbersRow!=c.numbersRow){
                        this.numsInBox[i].possibleNums.remove(c.possibleNums.get(1));
                    }
                }
            }
        }//end  TYPE 2 row c.possibleNums.get(1) the the ' ^ ' operator
        
//start  TYPE 2 row c.possibleNums.get(1) the the ' && ' operator
        else if(cell0.possibleNums.contains(c.possibleNums.get(1)) && cell1.possibleNums.contains(c.possibleNums.get(1))){
            if(rows[c.numbersRow].countOccurancesRow(c.possibleNums.get(1)) == 3){
                //then remove c.possibleNums.get(0) from all cells in box except those in c.numbersRow
                for(int i=0; i<9; i++){
                    if(this.numsInBox[i].numbersRow!=c.numbersRow){
                        this.numsInBox[i].possibleNums.remove(c.possibleNums.get(1));
                    }
                }
            }
        }//end  TYPE 2 row c.possibleNums.get(1) the the ' && ' operator
        
        
//TYPE 2 COLUMN        
//start  TYPE 2 column c.possibleNums.get(0) the the ' ^ ' operator
        if(cell2.possibleNums.contains(c.possibleNums.get(0)) ^ cell3.possibleNums.contains(c.possibleNums.get(0))){
            if(cols[c.numbersColumn].countOccurancesCol(c.possibleNums.get(0)) == 2){
                for(int i=0;i<9; i++){
                    if(this.numsInBox[i].numbersColumn != c.numbersColumn){
                        this.numsInBox[i].possibleNums.remove(c.possibleNums.get(0));
                    }
                }
            }
        }//end  TYPE 2 column c.possibleNums.get(0) the the ' ^ ' operator
        
//start  TYPE 2 column c.possibleNums.get(0) the the ' && ' operator
        else if(cell2.possibleNums.contains(c.possibleNums.get(0)) && cell3.possibleNums.contains(c.possibleNums.get(0))){
            if(cols[c.numbersColumn].countOccurancesCol(c.possibleNums.get(0)) == 3){
                for(int i=0;i<9; i++){
                    if(this.numsInBox[i].numbersColumn != c.numbersColumn){
                        this.numsInBox[i].possibleNums.remove(c.possibleNums.get(0));
                    }
                }
            }
        }//end  TYPE 2 column c.possibleNums.get(1) the the ' && ' operator
        
//start  TYPE 2 column c.possibleNums.get(1) the the ' ^ ' operator
        if(cell2.possibleNums.contains(c.possibleNums.get(1)) ^ cell3.possibleNums.contains(c.possibleNums.get(1))){
            if(cols[c.numbersColumn].countOccurancesCol(c.possibleNums.get(1)) == 2){
                for(int i=0;i<9; i++){
                    if(this.numsInBox[i].numbersColumn != c.numbersColumn){
                        this.numsInBox[i].possibleNums.remove(c.possibleNums.get(1));
                    }
                }
            }
        }//end  TYPE 2 column c.possibleNums.get(1) the the ' ^ ' operator
        
//start  TYPE 2 column c.possibleNums.get(1) the the ' && ' operator
        else if(cell2.possibleNums.contains(c.possibleNums.get(1)) && cell3.possibleNums.contains(c.possibleNums.get(1))){
            if(cols[c.numbersColumn].countOccurancesCol(c.possibleNums.get(1)) == 3){
                for(int i=0;i<9; i++){
                    if(this.numsInBox[i].numbersColumn != c.numbersColumn){
                        this.numsInBox[i].possibleNums.remove(c.possibleNums.get(1));
                    }
                }
            }
        }// end  TYPE 2 column c.possibleNums.get(1) the the ' && ' operator

    }
}
