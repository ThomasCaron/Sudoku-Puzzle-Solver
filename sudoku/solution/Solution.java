package sudoku.solution;
import java.util.ArrayList;
/**
 * This class is called from the Sudoku_Driver class. This is the brain of the program.
 * First it will fill the Cells with potentialNums(1), then use a a bit of "simple" logic to try to eliminate some of them. T
 * Then using some "not so simple logic" it will try to get rid of more possibleNums before starting its fill method to fill the Cells.
 * 
 * (1)-possibleNums: a possibleNums is an Arraylist that every Cell object has. For non filled Cells, possibleNums is
 * the ArrayList of the numbers 1-9 that the non filled Cell could legaly hold.
 * 
 * 
 * @author Thomas Caron
 */
public class Solution {
    /**
     * boxes, rows, and cols are the 2d array objects from the Sudoku_Driver class.
     * They are now static variabls so that they can be called easier and increase the readibility of the prgram.
     **/
    static Box[] boxes;
    static Row[] rows;
    static Column[] cols;
    static Cell tempcell2;
    
    Solution(Box[] Boxes, Row[] Rows, Column[] Cols) {
        
        //important to note that whenever a Cell is changed, it is changing the object and will be shown in 
        //the rows, cols and boxes objects that point to that Cell.
       this.boxes = Boxes; 
        this.rows = Rows;
        this.cols = Cols;
//        static Cell tempcell2;
       

        fillPossibleNums();
        eliminatePossibleNums();
        simpleFill();
        
    }
    
    public ArrayList<Box> getBoxesInbnumsRowAndCol(int bnum){
        ArrayList<Box> adjBoxes = new ArrayList<Box>();
        
        //grab the row of boxes to the left and/or right of the current box, (bnum)
        if(bnum>=0 && bnum<=8){
            int div3 = bnum/3;
            if(div3==0){
                for(int i=0; i<3; i++){
    //                if(i!=bnum) adjBoxes.add(boxes[i]);
                    adjBoxes.add(boxes[i]);
                }
            }
            else if(div3==1){
                for(int i=3; i<6; i++){
    //                if(i!=bnum) adjBoxes.add(boxes[i]);
                    adjBoxes.add(boxes[i]);            } 
            }
            else if(div3==2){
                for(int i=6; i<9; i++){
    //                if(i!=bnum) adjBoxes.add(boxes[i]);
                    adjBoxes.add(boxes[i]);            }
            }

            //grab the boxes in bnum's column but does not exclude bnum
            int mod3 = bnum%3;
            if(mod3==0){
                for(int i=0; i<=6; i+=3){
    //                if(i!=bnum) adjBoxes.add(boxes[i]);
                    adjBoxes.add(boxes[i]);            }
            }
            else if(mod3==1){
                for(int i=1; i<=7; i+=3){
    //                if(i!=bnum) adjBoxes.add(boxes[i]);
                    adjBoxes.add(boxes[i]);            }
            }
            else if(mod3==2){
                for(int i=2; i<=8; i+=3){
    //                if(i!=bnum) adjBoxes.add(boxes[i]);
                    adjBoxes.add(boxes[i]);            }
            }
        }
        else{
            System.out.println("somehow getBoxesInbnumsRowAndCol was sent a box number"
                    + "that is out of the range of 0-8 inclusive");
        }
        return adjBoxes;
    }
    
    /**
     * The method will return an ArrayList of Box objects. These Box objects will be the Boxes that are
     * in the same Row or Column that the main box, bnum, is.
     * @param bnum the main box. 
     * @param boxes An array of Box objects of size 8
     * @param number The number that you are searching for in the same Row/Column as the main box, bnum.  
     * @return An ArrayList of Box objects that both contain the filled in number, num, and are in the same Row/Column as boxes[bnum] 
     */
    public ArrayList<Box> getReleventBoxes(int bnum, int number){
        ArrayList<Box> relBoxes = new ArrayList<Box>();
        
        //grab the row of boxes to the left and/or right of the current box, (bnum)
        int div3 = bnum/3;
        
        //grabs the other boxes from the row that bnum is in as long as they have contain the number.
        //bnumInrow stands for the BoxNum in the row. EX: 0,1,2 or 3,4,5
        for(int bnumInrow=div3*3; bnumInrow<bnumInrow+3; bnumInrow++){
            if(bnumInrow!=bnum && (boxes[bnumInrow].contains(number)!=null)){
                relBoxes.add(boxes[bnumInrow]);
            }
        }
        
        //grab the boxes in bnum's column, excluding bnum, that contain the number in question (number)
        int mod3 = bnum%3;
        
        for(int bnumIncol=mod3; bnumIncol<bnumIncol+7; bnumIncol+=3){
            if(bnumIncol!=bnum && (boxes[bnumIncol].contains(number)!=null)){
                relBoxes.add(boxes[bnumIncol]);
            }
        }
        
        
    return relBoxes;
    }
    
    /**
     *This method inserts a number into the Cells Numbersint, and removes that same number from the Cells that share its Row, Column, and Box with them.
     * @param num The number that is being inserted into the Cell
     * @param numRow The Row the cell is located in. It is called this way so that there is no chance of assigning  num to a wrong Cell
     * @param numCol The number of the Column in cols that the Cell is located in.
     */
    public void FillinNumber(int num, int numRow, int numCol){
        // asign num to the correct Cell object
        
        
        //when used with a breakpoint, the line below allows the programer tho see both the information comming in (by having access to the methods parameters)
        //as well as being able to easily find the cell that was previously filled in. Additionall, by it being static it always stays right up top when debuging across methods.
//        previousFilledIn =rows[numRow].numberList[numCol];
//                tempcell2 =rows[numRow].numberList[numCol];
        
        //if the Cell at [numRow] [numCol] has the num in its possibleNums Arraylist and it isn't already filled in, then fill it in.
        if(rows[numRow].numberList[numCol].possibleNums.contains(num) && rows[numRow].numberList[numCol].Numberint==0){
            rows[numRow].numberList[numCol].Numberint = num;
        }    
        
        
        
        
//        clear the newly filled in Cell's possibleNums
        rows[numRow].numberList[numCol].possibleNums.clear();
        
//        eliminate num from the possibleNums ArrayList of every Cell object in its box
        int bnum = (numRow/3)*3 + (numCol/3);
        Box box = boxes[bnum];
        for(int b=0; b<9; b++){
            if(box.numsInBox[b].possibleNums.size()==1 && box.numsInBox[b].possibleNums.contains(num)){ 
                System.out.println("Warning on line 148. Trying to remove the last value in a possibleNums ArrayList");}
            else box.numsInBox[b].possibleNums.remove(Integer.valueOf(num));
        }
        
        
        //        eliminate num from the possibleNums ArrayList of every Cell object in its row
        Row r = rows[numRow];
        for(int i=0; i<9; i++){
            if(r.numberList[i].possibleNums.size()==1 && r.numberList[i].possibleNums.contains(num)){ 
                System.out.println("Warning on line 157. Trying to remove the last value in a possibleNums ArrayList");}
            else r.numberList[i].possibleNums.remove(Integer.valueOf(num));
        }
        
        //        eliminate num from the possibleNums ArrayList of every Cell object in its column
        Column c = cols[numCol];
        for(int i=0; i<9; i++){
            if(c.numberList[i].possibleNums.size()==1 && c.numberList[i].possibleNums.contains(num)) {
                System.out.println("Warning on line 165. Trying to remove the last value in a possibleNums ArrayList");}
            else c.numberList[i].possibleNums.remove(Integer.valueOf(num));
        }
        
        //now that a new number is filled in, check if the box now has a full row or column
        
        boxes[bnum].checkIfFilledRowsorCol();
//        advancedFill();
        eliminatePossibleNums();
        removePurePairs();
    }
    
    
//    fills in the potentialNums ArrayList of all of the cells
    public void fillPossibleNums(){
        //for every Cell object, it fills the possibleNums ArrayList to asssist in the filling of numbers.
        
//        for each number, 1-9, fill each possibleNums in each box
        for(int num=1; num<10; num++){  

//            for every number 1-9, num, put num into every Cell objects, (that doesnt already have a number plugged into it) possibleNums arraylist
            for(int bnum=0; bnum<9; bnum++){
                
/*if the box already contains the number, then the possibleNums of the other Cell objects in the box shouldnt have the num val in their araylist 
if the current box, bnum, does not contain the number, then insert num into the possibleNums araylist of every Cell in the box*/
                if(boxes[bnum].contains(num)==null){ 
                    Box b = boxes[bnum];
                    for(int i=0; i<9; i++){
                        
/*this if statement check to see if the Cell, in Box b that i is pointing at, is already filled. if it is not, EX: it was read in with the value
 of 'X', then the Cell.numberint will == 0*/
                        if(b.numsInBox[i].Numberint==0) {
                            b.numsInBox[i].possibleNums.add(num);
                        }
                    }// end of the int i=0 for loop. === end of the current Box
                }//end of the .contains(num) statement. the possibleNums of every Cell in the Box is now has a num in it, if it went through the if statement 
            }//end of the bnum for loop
        }//end of the loop that fills every Cell's possibleNums ArrayList 
        

/*now that all Cell's possibleNums is filled,     
if a value in a Cell object's possibleNums ArrayList, has the same vaule as a filled in number {Cell.Numberint !=0) then it needs to be from that 
Cell object's possibleNums ArrayList.       */
        for(int num=1; num<10; num++){
            
//for each num, in every Row/Col, remove values from possibleNums that are the same value as a filled in Cell located in the same Row/Col
//the method will be use the index: "index" to step through both the Row's elimination process as well as Column's elimination process
            for(int index=0; index<9; index++){
                
//if the row contains a filled in Cell with the same Numberint value as num, then remove the num value from all possibleNums ArrayLists in that row
                if(rows[index].contains(num) != null){

//step throw the Row, and at each Cell/index remove the value num from the possibleNums ArrayList.                     
                    for(int i=0; i<9; i++){
                        if(rows[index].numberList[i].possibleNums.size()==1) {
//                            System.out.println("Warning on line 220. Trying to remove the last value in a possibleNums ArrayList");
                        }
                        rows[index].numberList[i].possibleNums.remove(Integer.valueOf(num));
                    }
                }//end of the Row elimination of the value num. now using "index" to be the index for the Column part
                
                if(cols[index].contains(num) != null){

//step throw the Col, and at each Cell/index remove the value num from the possibleNums ArrayList.                     
                    for(int i=0; i<9; i++){
                        if(cols[index].numberList[i].possibleNums.size()==1) {
//                            System.out.println("Warning on line 236. Trying to remove the last value in a possibleNums ArrayList");
                        }
                        cols[index].numberList[i].possibleNums.remove(Integer.valueOf(num));
                    }
                }
            }// end of the loop that steps through and removes the vaule num from the rows and cols arraylists
        }//end the row/col possibleNums elimination processes
        
//a more advanced elimination process
        
        for(int i=0;i<9; i++) boxes[i].checkIfFilledRowsorCol();
    }

    /**
     *This method uses the most rudimentary logic checks to fill in as many Numbers as possible. When it is able to determine what and where to assign a number,
     * it will call the FillinNumber method which will assign the value, and remove the value from the possibleNums in the same box, row, and column as it.
     * @param boxes An array, of size 8, that contains Box objects
     * @param rows An array, of size 8, that contains Row objects
     * @param cols An array, of size 8, that contains Column objects
     */
    public void simpleFill(){
/*
This method will run check1 on all Numbers, for the value of 1. Once it finishes with the last Cell object, if it was able to fill in a number, it will start check1
on the first box for the value of 1. It will continue to loop back until it is not able to fill the number 1 in anywhere. 
At that point, it will move on to check2 for value 1. The checks will continue to loop as long as they are able to place a number each time. 
Once the last check for value 1 is compleated, check1 will be run for the value 2. */

/*
There is a variable, "isDone". this will be inatialized as true. when a number is found and filled in, it will be set to fasle. 
After the last check is run on the last Cell object for the last value, 9, if "isDone" =false then the first check will run again on the first Cell for the value 1.
*/
        /* there are three logic checks, and the second check has three parts:
        check 1) If the value num is the only element inside of a possibleNums ArrayList, then it will call FillinNumber.
        check 2) If there is only one occurance of the number:
                2a) if there is only one occurance of the value in all of the, "possibleNums ArrayLists" of the Cell objects inside of there box, then the Cell objects who has the 
possibleNums field with the only occurance of the value has the only location where it could be correctly filled in.
                2b) Similar to 2a but with Rows instead. If a Cell object has the only occurance of the value in its Row, then that must be wherre the value needs to be placed.
                2c) The same thing as 2b but with Columns instead.
        check 3) if there is a number in a box and in its adjacent boxs there is a full row/column then the number must be located in that row/column of the thrird box.
EX: there is a 1 in the top left corner of box 0. The box to its right, box 1, has the numbers 2,3,4 filling its middle row. Box 1 needs to have a 1 on its bottom row
because of the 1 in box 0 and its middle row is filled. Therefore in box 2, 1 must be located in the middle row
        */
        removePurePairs();
        boolean isDone;
        //Start the while loop for the checks
        do{
            isDone=true;
            
            for(int num=1; num<10; num++){  

                boolean runCheck1Again=false;
                //Check 1) find the Cell objects whose possibleNums is the size 1 and the value of their the only element is num
                for(int bnum=0; bnum<9; bnum++){
                    if(bnum==0) runCheck1Again=false;
//                    if the box already contains the number, then just skip over it 
                    if(boxes[bnum].contains(num)==null){
                        //if boxes[bnum] does NOT contain the value:
                        Box b = boxes[bnum];
                        //iterate over the Cell objects in the Box.numsInBox while checking their possibleNums field.
                        for(int i=0; i<9; i++){
                            if(  (b.numsInBox[i].possibleNums.size()==1)  &&  (b.numsInBox[i].possibleNums.get(0) == num)  ){
                                FillinNumber(num, b.numsInBox[i].numbersRow, b.numsInBox[i].numbersColumn);
                                runCheck1Again=true;
                                isDone=false;
                                removePurePairs();
                            }
                        }
                    }//ends the box.contains(num)==null
                    if(bnum==8 && runCheck1Again==true) {
                        bnum=-1; //index reset for if the check filled in a number
                    }
                }// end of check 1!!!
                
                
                boolean runCheck2aAgain=false;
                //check 2a) counts the number of times the value num is found in the box. If at the end of the loop, occurancesInBox == 1 then FillinNumber will be called!!
                for(int bnum=0; bnum<9; bnum++){
                    if(bnum==0) runCheck2aAgain=false;
                    if(boxes[bnum].contains(num) == null){
                        int occurancesInBox = boxes[bnum].countOccurancesBox(num);
                        //check to see if there is only one occurance of the value in the possibleNums inside the box.                        
                        
                        if(occurancesInBox==1){
                            //there is only one occurance. now find the Cell Object with the only occurance of the value in its possibleNums 
                            Cell n;
                            for(int i=0; i<9; i++){
                                if(boxes[bnum].numsInBox[i].possibleNums.contains(num)==true){
                                    n = boxes[bnum].numsInBox[i];
//                                    safe to the below two lines here because it will only run once
                                    FillinNumber(num, n.numbersRow , n.numbersColumn);
                                    runCheck2aAgain=true;
                                    isDone=false;
                                    removePurePairs();
                                }
                            }
                            
                        }
                    }
                    if(bnum==8 && runCheck2aAgain==true)  bnum=(-1);
                } // check2a Done!!!!
                
                
                boolean runCheck2bAgain=false;
                //check 2b) counts the number of times the value num is found in the row. If at the end of the loop, occurancesInBox == 1 then FillinNumber will be called!!
                for(int rnum=0; rnum<9; rnum++){
                    if(rnum==0) runCheck2bAgain=false;
                    if(rows[rnum].contains(num) == null){
                        int occurancesInRow = rows[rnum].countOccurancesRow(num);
                        //check to see if there is only one occurance of the value in the possibleNums inside the box.                        
                        
                        if(occurancesInRow==1){
                            //there is only one occurance. now find the Cell Object with the only occurance of the value in its possibleNums 
                            Cell n;
                            for(int i=0; i<9; i++){
                                
                                if(rows[rnum].numberList[i].possibleNums.contains(num)==true){
                                    n = rows[rnum].numberList[i];
//                                    safe to the below two lines here because it will only run once
                                    FillinNumber(num, n.numbersRow , n.numbersColumn);
                                    runCheck2bAgain=true;
                                    isDone=false;
                                    removePurePairs();
                                }
                            }
                            
                        }
                    }
                    if(rnum==8 && runCheck2bAgain==true)  rnum=-1;
                } // check2b Done
                
                
                
                boolean runCheck2cAgain=false;
                //check 2c) does check 2b but with columns instead
                for(int cnum=0; cnum<9; cnum++){
                    if(cnum==0) runCheck2cAgain=false;
                    if(cols[cnum].contains(num) == null){
                        int occurancesInCol = cols[cnum].countOccurancesCol(num);
                        //check to see if there is only one occurance of the value in the possibleNums inside the box.                        
                        
                        if(occurancesInCol==1){
                        //there is only one occurance. now find the Cell Object with the only occurance of the value in its possibleNums 
                            Cell n;
                            for(int i=0; i<9; i++){
                                
                                if(cols[cnum].numberList[i].possibleNums.contains(num)==true){
                                    n = cols[cnum].numberList[i];
                                    //safe to the below two lines here because it will only run once
                                    FillinNumber(num, n.numbersRow , n.numbersColumn);
                                    runCheck2cAgain=true;
                                    isDone=false;
                                    removePurePairs();
                                }
                            }
                        }
                    }
                    if(cnum==8 && runCheck2cAgain==true)  cnum=-1;
                } // check2b Done                
                 
                
//check 3)   is now its own method called eliminatePossibleNums
            
                
            }
            //test to see if the solution is full yet
            boolean testisFull = isFull();
//            System.out.println("on line 395 of solution,  isFull=="+testisFull);
        }while(isDone==false);
        // if the check is done, isDone==true, check to see if the grid is full
        boolean full=isFull();
        if(full){
        }
        
       
        
        
        
        
        
    }
    
    
    /**
        a diagram of how the logic in the method below goes.
        n - is the number currently being searched for by this method that is located in b0,b1,b2,b3
        X - represents an already filled in Cell
        i - represents the central Box the the method is looking at.
        b0 and b1 - are the two Box objects in the same box row as boxes[i]
        b2 and b3 - are the two Box objects in the same box Col as boxes[i]
        box numbers go like this: 
        0   1   2
        3   4   5
        6   7   8
    ============================
    |num=0   |__|_|___|num=2   |
    | /3=0   |__|n|___| /3=0   |
    | %3=0   |__|_|___| %3=2   |
    ============================
    |num=3   |__|_|___|num=5   |
    | /3=1   |__|_|___| /3=1   |
    | %3=0   |__|_|___| %3=2   |
    ============================
    |num=6   |X_|_|___|num=8   |
    | /3=2   |X_|_|___| /3=2   |
    | %3=0   |X_|_|___| %3=2   |
    ============================
        if one of adjacent boxes has the number n in them, ( box 1 )
            and a box that is in the same row / column as the one with that number has a filled row or column, accordingly,
                then you can force n to be in the same row/col as the filled row. and eliminate n from all cells in the other unused 
                that arent in that row/ col.
        in the above example n has to be in the left column of the middle box because, 
        for the bottom box, n cannot be on the left becaused its filled in, and not in the middle because that is where the n from the top box is,
        therefore it needs to be on the right, making the n in the middle on the left
     */
    
    public void eliminatePossibleNums (){
        for(int num=1; num<10; num++){
            for(int i=0; i<9; i++){
                //if the box does not contain the number.
                if(boxes[i].contains(num)==null){
                    ArrayList<Box> adjBoxes = getBoxesInbnumsRowAndCol(i);
                    //trim boxes[i] out of the ArrayList
                    adjBoxes.remove(boxes[i]);
                    adjBoxes.remove(boxes[i]); // boxes[i] was in the ArrayList twice
                    
                    //getBoxesInbnumsRowAndCol returns an ArrayList of the boxes. the first three elements are the boxes in boxes[i]'s row the next three are the boxes in its column

                        //if two of the three boxes in boxes[i]'s row already contain num, then there is no point in doing the check because it is already done.
                        //proceed if only one of the boxes has the num.
                    if(adjBoxes.get(0).contains(num)!=null ^ adjBoxes.get(1).contains(num)!=null){
                        //if adjBoxes[0] contains the num, get the rowNum of where the num is and check if the other box has a rowFilled where row number of num != row number of the filled in row
                        Box b0=adjBoxes.get(0), b1=adjBoxes.get(1); // grab b1 and b2 out of adjBoxes for easer calling and better readability
                        if(b0.contains(num)!=null){
                            int numsRow = b0.contains(num).numbersRow%3; // %3 the row number so it can be compared with the other box

                        //if the row in b2 is filled and not the row that num is in then that row is the only row in boxes[i] that num can be in
                                if(b1.isRowFilled[0] && 0!=numsRow){
                            //remove num for the possibleNums of every Cell in the box that is not in the top row                                    
                                    for(int index=3; index<9; index++){
                                        if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 471. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                            }
                                }
                                if(b1.isRowFilled[1] && 1!=numsRow){
                            //remove num for the possibleNums of every Cell in the box that is not in the middle row                                    
                                    for(int index=0; index<9; index++){
                                        if(index/3!=1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 482. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                        }
                                    }
                                }
                                if(b1.isRowFilled[2] && 2!=numsRow){
                            //remove num for the possibleNums of every Cell in the box that is not in the middle row                                    
                                    for(int index=0; index<6; index++){
                                        if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 493. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                    }
                                }
                            }
                        else{//else it is b1 that contains the num
                            int numsRow = b1.contains(num).numbersRow%3; // %3 the row number so it can be compared with the other box

                        //if the row in b2 is filled and not the row that num is in then that row is the only row in boxes[i] that num can be in
                            if(b0.isRowFilled[0] && 0!=numsRow){
                        //remove num for the possibleNums of every Cell in the box that is not in the top row                                    
                                for(int index=3; index<9; index++){
                                    if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 509. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                }
                            }
                            if(b0.isRowFilled[1] && 1!=numsRow){
                        //remove num for the possibleNums of every Cell in the box that is not in the middle row                                    
                                for(int index=0; index<9; index++){
                                    if(index/3!=1) {
                                        if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 519. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                    }
                                }
                            }
                            if(b0.isRowFilled[2] && 2!=numsRow){
                        //remove num for the possibleNums of every Cell in the box that is not in the middle row                                    
                                for(int index=0; index<6; index++){
                                    if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 530. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                }
                            }
                        }
                    }//end of the checking rows section
                    
                    
                //checking columns section
                    if(adjBoxes.get(2).contains(num)!=null ^ adjBoxes.get(3).contains(num)!=null){
                        Box b2=adjBoxes.get(2), b3=adjBoxes.get(3); // grab b1 and b2 out of adjBoxes for easer calling and better readability
                        if(b2.contains(num)!=null){
                            int numsCol = b2.contains(num).numbersColumn%3; // %3 the row number so it can be compared with the other box

                            //if the row in b2 is filled and not the row that num is in then that row is the only row in boxes[i] that num can be in
                                if(b3.isColFilled[0] && 0!=numsCol){
                            //remove num for the possibleNums of every Cell in the box that is not in the left col                                    
                                    for(int index=0; index<9; index++){
                                        if(index%3!=0) {
                                            if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 552. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                        }
                                    }
                                }
                                if(b3.isColFilled[1] && 1!=numsCol){
                            //remove num for the possibleNums of every Cell in the box that is not in the middle col                                    
                                    for(int index=0; index<9; index++){
                                        if(index%3!=1) {
                                            if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 564. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                        }
                                    }
                                }
                                if(b3.isColFilled[2] && 2!=numsCol){
                                //remove num for the possibleNums of every Cell in the box that is not in the right col                                    
                                    for(int index=0; index<9; index++){
                                        if(index%3!=2) {
                                            if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 576. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                        }
                                    }
                                }
                            }
                        else{//else it is b3 that contains the num
                            int numsCol = b3.contains(num).numbersColumn%3; // %3 the row number so it can be compared with the other box

                            //if the row in b2 is filled and not the row that num is in then that row is the only row in boxes[i] that num can be in
                            if(b2.isColFilled[0] && 0!=numsCol){
                            //remove num for the possibleNums of every Cell in the box that is not in the top row                                    
                                for(int index=0; index<9; index++){
                                    if(index%3!=0) {
                                        if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                            System.out.println("Warning on line 593. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                    }
                                }
                            }
                            if(b2.isColFilled[1] && 1!=numsCol){
                            //remove num for the possibleNums of every Cell in the box that is not in the middle row                                    
                                for(int index=0; index<9; index++){
                                    if(index%3!=1) {
                                        if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                                System.out.println("Warning on line 605. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                    }
                                }
                            }
                            if(b2.isColFilled[2] && 2!=numsCol){
                            //remove num for the possibleNums of every Cell in the box that is not in the middle row                                    
                                for(int index=0; index<6; index++){
                                    if(index%3!=2) {
                                        if(boxes[i].numsInBox[index].possibleNums.size()>1){
                                            if(boxes[i].numsInBox[index].possibleNums.size()==1) {
                                            System.out.println("Warning on line 617. Trying to remove the last value in a possibleNums ArrayList");}
                                            boxes[i].numsInBox[index].possibleNums.remove(Integer.valueOf(num));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
     public void advancedFill(){
         //look for pointing pairs.
         //if there are two possiblevalues located in the same: row, col, box then eliminate those vals for all other Numbers in there row, col, box
//        fillPossibleNums();
        eliminatePossibleNums();
        removePurePairs();
        eliminatePossibleNums();
        simpleFill();
    }
    
    /**
     * Checks to see if every Cell is filled in. 
     * @return if all Cells are filled in, return true. Else return false.
     */
    public boolean isFull() {
        boolean full=true;
        for(int i=0; i<9; i++){
            boolean rowFull=true;
            for(int j=0; j<9; j++){
                if(rows[i].numberList[j].Numberint==0) {
                    full=false;
                    rowFull=false;
                }
                
            }
            rows[i].isFilled=rowFull;
        }
        return full;
    }

    /**
     *This method look for pairs. first pointing pairs then triple pointing pairs. 
     */
    public void removePurePairs() {
    //           parse through every cell looking for pointing pairs
    for(int bnum=0; bnum<9; bnum++){
        for(int i=0; i<9; i++){
            if(boxes[bnum].numsInBox[i].possibleNums.size()==2){
                    boxes[bnum].hasPointingPairBox(boxes[bnum].numsInBox[i], rows, cols);
            }
        }
    }
    //below is a strong test case for the TriplePair, because you cannot solve "puzzle5.txt" without finding the triplePair located in row 4 and [4][3] is a member of that triplePair.
    //    rows[4].numberList[3].hasTriple(rows, cols, boxes);
    
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                if(rows[r].numberList[c].possibleNums.size()==2 || rows[r].numberList[c].possibleNums.size()==3){
                    rows[r].numberList[c].hasTriple(rows, cols, boxes);
            }
        }
    }

    
    }
}
