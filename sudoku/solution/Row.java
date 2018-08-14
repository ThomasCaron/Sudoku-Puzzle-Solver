package sudoku.solution;



/**
 * @author Thomas Caron
 * 
 * A Row object is just an Array of length 9, that is stored in another array at its rowNumber;
 */
public class Row {
    public boolean isFilled=false;
    public Cell[] numberList = new Cell[9];
    public int rowNumber;
    
    /**
     * Initalizes the Row object
     * @param rowNum The parameter that will set the row's number for the rest of the program
     */
    public Row(int rowNum){
        this.rowNumber = rowNum;
    }
    
    /**
     * Checks if any of the Cells in this row are filled in with the number num. 
     * @param num The number whos presence in this row you are searching for.
     * @return If a Cell has been filled in with the number num, the method will return that Cell object.
     * * if there is no such object, than it will return null.
     */
    public Cell contains(int num){
        for( int i=0; i<9; i++){
            if(this.numberList[i].Numberint == num){
                return this.numberList[i];
            }
        }
        return null;       
    }
    
    /**
     *Counts the number of times that num occurs in the possibleNums arraylists of the Cells in this row
     * @param num The number whose occurances in this row are being counted.
     * @return The number of times that num occurs in the possibleNums Arraylist of the Cells in this row. 
     */
    public int countOccurancesRow(int num) {
        int occurances=0;
        Cell n;
        for(int i=0; i<9; i++){
            n = this.numberList[i];
            // n is a Cell object
            if(n.Numberint==0 && n.possibleNums.contains(num)){
                occurances++;
            }
        }
        return occurances;
    }
    
    /**
     *Given a number, num, this method removes num from the possibleNums of all Cells in the Row except from those that are being passed in.
     * @param num The number being removed from the possibleNums of the Cells in the row, excluding those Cells that are being passed in.
     * @param c1 A Cell that is being protected from this deletion method.
     * @param c2 A Cell that is being protected from this deletion method.
     * @param c3 A Cell that is being protected from this deletion method.
     */
    public void removePossibleNumRow(int num, Cell c1, Cell c2, Cell c3){
        for(int i=0; i<9; i++){
            if(this.numberList[i].possibleNums.size()==1 && this.numberList[i].possibleNums.contains(num)){
                    System.out.println("Warning in Row trying to remove "+num+"from a cell where that is its only element in possibleNums");
                }
            if(this.numberList[i]!= c1 && this.numberList[i]!=c2 &&this.numberList[i]!=c3) {
                

//                if(this.numberList[i].numbersRow==4 ){
                    //not a bad test case since row 4 has a triplePair
//                    System.out.println("test triple Pair. in Row class");
//                }
                if(this.numberList[i].possibleNums.size()>1) this.numberList[i].possibleNums.remove(Integer.valueOf(num));
            }
        }
    }
    
    
    /**
     *Looks through the Cells for a triple pointing pair.
     * If it finds one then it will remove the possibleNums involved in the triple pointing pair,
     * from the rest of the possibleNums ArrayLists of the other Cells
     * @param c The Cell that is the basis of the search. must have a possibleNums.size()==2 || ==3
     */
    public void containsTripleRow(Cell c) {
        int val1=-1, val2=-1, val3=-1;
        Cell c1=null, c2=null;     
        
             if(c.possibleNums.size()==2){
            
//            val1=c.possibleNums.get(0);
//            val2=c.possibleNums.get(1);
            for(int r=0; r<9; r++){
                //iterate through the Cell c' possibleNums
                if(this.numberList[r] != c){
                    
                    //option 1:             c1.size=3
                    if(this.numberList[r].possibleNums.size()==3){
                        if(c1==null && (this.numberList[r].possibleNums.contains(c.possibleNums.get(0)) &&
                                        this.numberList[r].possibleNums.contains(c.possibleNums.get(1)))){
                            //look for a cell  of size 2 that contains the possibleNum that this.numberList[r] and c does not have as well as the other num being one of the ones that c has

                            c1=this.numberList[r];
                            for(int index=0; index<9; index++){
                                c2=this.numberList[index];
                                if(c!=c2 && c1!=c2 && c2.possibleNums.size()==2 && (c1.possibleNums.contains(c2.possibleNums.get(0)) && c1.possibleNums.contains(c2.possibleNums.get(1))) && 
                                        c.possibleNums.contains(c2.possibleNums.get(0)) ^ c.possibleNums.contains(c2.possibleNums.get(1))){
                                            //found a triplePair
                                            int value1 =c1.possibleNums.get(0);
                                            int value2 =c1.possibleNums.get(1);
                                            int value3 =c1.possibleNums.get(2);
                                            this.removePossibleNumRow(value1, c, c1, c2);
                                            this.removePossibleNumRow(value2, c, c1, c2);
                                            this.removePossibleNumRow(value3, c, c1, c2);
                                }
                            }            
                        }
                    }
//if c.size==2 and hit a Cell with size 2 that shares one of c's possilbeNums, ... then look for the third pair that would have to have a possibleNums.size=2 and contain the two that c and c1 dont have in common
//or be size three and hold the value c and c1 have in common as well as the two they do not.
                        else if(c1==null && this.numberList[r].possibleNums.size()==2 && 
                               (this.numberList[r].possibleNums.contains(c.possibleNums.get(0)) ^ this.numberList[r].possibleNums.contains(c.possibleNums.get(1)))){
                                    //only want to count the same number once in the triplePair
                                    //either way make value1 the shared value for easier searching.
                                
                                c1=this.numberList[r];
                                int value1;
                                int value2;
                                int value3;
                                if(c.possibleNums.get(0) == this.numberList[r].possibleNums.get(0)){
                                    value1=c.possibleNums.get(0);
                                    value2=c.possibleNums.get(1);
                                    value3=this.numberList[r].possibleNums.get(1);  
                                }
                                else{
                                    value1=c.possibleNums.get(1);
                                    value2=c.possibleNums.get(0);
                                    value3=this.numberList[r].possibleNums.get(0);
                                }
                                for(int index=0;index<9; index++){
                                    if(c!=this.numberList[index]&& c1!=this.numberList[index] &&
                                        (this.numberList[index].possibleNums.size()==2 && this.numberList[index].possibleNums.contains(value2) && this.numberList[index].possibleNums.contains(value3)) ||
                                        (this.numberList[index].possibleNums.size()==3 && this.numberList[index].possibleNums.contains(value1) &&
                                         this.numberList[index].possibleNums.contains(value2)&& this.numberList[index].possibleNums.contains(value3))){
                                        //then found a triple pair!
                                        c1=this.numberList[r];
                                        c2=this.numberList[index];
                                        this.removePossibleNumRow(value1, c, c1, c2);
                                        this.removePossibleNumRow(value2, c, c1, c2);
                                        this.removePossibleNumRow(value3, c, c1, c2);
                                    }
                                }
                        }
                    }
                }
            }
        }
    
    


    

    
}
