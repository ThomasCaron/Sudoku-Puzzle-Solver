package sudoku.solution;
import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Thomas Caron
 */
public class Sudoku_Driver/*afterJunitTest*/ {

    /**
     * This is the skeleton of the program. 
     * This class contains the bethods needed for reading in the Sudoku file,
     * initalizing the data structures, and creating the output file.
     */
    public static void main(String[] args) {
        
        //Prompts the user to enter the name of a Sudoku file. The main method will then call readInFile(String filename)
        //that reads it in, and turn it into one string.
        //That string will be used to create
        System.out.println("please insert the full file path of the Sudoku text file, including the \".txt\" extension to begin. \n");
        System.out.println("for example: C:\\Users\\my pc\\Documents\\a JOBS\\Summer Jobs 2018\\GAVANT code challenge\\puzzle5.txt");
        
        Scanner filein = new Scanner(System.in);
        String fileIn=filein.nextLine();
        String stringReadIn = readInFile(fileIn);
        
        
        //now create the data structures that will be needed for the solution.
        Row [] GridRows;
        Column [] GridCol;
        Box [] GridBox;
               
        
        //    now that all numbers are in the stringReadIn variable, we will now turn that string into the game board
        //    by using an array of the Cell objects to fill the Row, Column, and Box objects.
        GridRows=fillGridRows(stringReadIn);
        GridCol=fillGridCol(GridRows);
        GridBox=fillGridBoxs(GridRows);
        //now that structures are filled, it is time to begin the logic.
        Solution sudokuSolution = new Solution(GridBox, GridRows, GridCol);
        readOutFile(GridRows, fileIn);
        
    }

    /**
     * This method uses the java.io inport and reads in the file by turning it into one
     * 80 character long string.
     * 
     * @param filePath What the user typed in as the file name.
     * 
     * @return The 80 character long string.
     */
    public static String readInFile(String filePath) {
        String fileName="";
        String str="";
        try {
            File file = new File(filePath);
            //if the file path works, then pull the file name out of the path excluding the extension.
            fileName = getFileName(filePath);
            
            Scanner scan = new Scanner(file);
            
            //make a while loop so that you can fill stringReadIn with the numbers from every line in the file
            while(scan.hasNext()){
                str += scan.next();
            }
            
        } catch (FileNotFoundException ex) {
            if(fileName.contains(".")){
                System.out.println("There was an error reading in the file. \n"
                + "In the filepath, the file name contains a \'.\' . So the name of the file looks something like filename.something.txt \n"
                        + "Please rename the file so that it follows standard file naming conventions.");
            }
            else{
                System.out.println("There was an error reading in the file from: "+filePath+". \n"
                + "Try making sure that you are entering the correct file path.");
            }
            
            Logger.getLogger(Sudoku_Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
    /**
     * This program takes in a String of numbers that is 81 characters long, that can ONLY consist of numbers between 1-9 inclusive,
     * and the uppercase character 'X'
     * @param str The string that was read in from the file. It will be used to fill the GridRows with its initial numbers.
     * @return It returns a 2d array of Cell objects used to fill the critical GridRows Variable
     */
    public static Row[] fillGridRows(String str) {
        
        
        //uses a nested 'for' loop to fill the grid. 
        //The grid is made up of 9 'Row' objects. Every Row object has an array of Cell objects that is 9 elements long.
        
        //make a temporary grid to fill the main method's Grid
        Row [] g = new Row [9];
        
        //strIndex is the index for parsing through the string.
        int strIndex=0;
        
        //the r index stands for row
        //Need a for loop to step through the list of rows
        for(int r=0; r<9; r++){
            
            //when creating numbers, if there is an 'X' (an empty cell) it will be represented in the program by a '0'
            // the c index stands for column
            //need a nested for loop to step through each cell in every Row.
            Row row = new Row(r);
            for(int c=0; c<9; c++){
                String ch = str.substring(strIndex, strIndex+1);
                
                int Cellsnumber=-1;
                int rowNumber=r;
                int colNumber=c;
                
                /* 
                if ch is a 'X' then convert it into a '0'.
                if the char is not (an int or the uppercase character 'X'),  or (it is a number outside the range of 1-9 inclusive)
                an error will be thrown
                */
                if( ch.equals("X") ) Cellsnumber=0;
                else {
                    try{
                        Cellsnumber=Integer.parseInt(ch);
                    }
                    catch(NumberFormatException ex){
                        System.out.println("The "+strIndex+" character in the string, \'"+ch+"\' is not a number or the uppercase \'X\' character."
                                +"Please fix error in the input file, as the game Sudoku is only played with the numbers 1-9 inclusive "); 
                    }
                
                }
                /*if ch was a number and able to be converted into an int, AND it was outside the range of 1-9 inclusive, even if it was 0
                an error will be thrown for it being a number out of the range of those the game uses.*/
                if( (Cellsnumber>9) || (Cellsnumber<1 && ch.equals("X")==false)){
                    System.out.println("the character \'"+ch+"\' is a number that is outside of the range of those used in Sudoku. Please fix the error in the input file.");
                }
                
                Cell cell = new Cell(Cellsnumber, rowNumber, colNumber);
                row.numberList[c]=cell;
                
                strIndex++;
                //if the current row (r) is filled, (c==8), then put that row into the Grid object
                if(c==8){
                    g[r]=row;
                }
            }
        }
            return g;
    }

    
    /**
     * This program makes an array of Column objects, similar to what fillGridRows does, except that
     * all 81 Cell objects are already created.
     * 
     * 
     * @param Row[] rows. The array of Row objects just created. It will be used to fill the GridCols with its initial numbers.
     * @return It returns a 2d array of Cell objects. 
     */
    public static Column[] fillGridCol(Row[] rows) {
        /*
        need to make a grid of Columns to be able to meet the constraint of no repeating numbers in a column
        as well as completing tasks that could be done with just the GridRows, but would be easier to follow with the use of this 2d array.
        */
        
        //this method will fill itself with the cell objects in the GridRows  
        Column [] g = new Column [9];
        /*c is the index of the Column [],  g (g stands for grid), as well as the index of row.numberList where the Cell object for the column list 
        is located*/
        for(int c=0; c<9; c++){
            Column col = new Column(c);
            for(int r=0; r<9; r++){
                Cell n = rows[r].numberList[c];
                col.numberList[r]=n;
                if(r==8){
                    g[c]=col;
                }
            }
        }
        return g;
    }

    /**
     * There is also a 2d array of Box objects. The box grid will be frequently used due
     * to its critical function in eliminating possible number locations.
     * @param rows The 2d array GridRows
     * @return A 2d array of Box objects.
     */
    public static Box[] fillGridBoxs(Row[] rows) {
        //the index 'bnum' stands for and corresponds to box number.  
        
        /*
        * It would be very useful to view the Box class before reading this method for the first time,
        * as it provides a superior explination on how the boxNumber works with the /3 and %3
        */
        
        Box [] boxs = new Box [9];
        for(int bnum=0; bnum<9; bnum++){
            Box b = new Box(bnum);
            int divThree = bnum/3;
            int modThree = bnum%3;
            int boxIndex=0;
            /*dont want to fill the box with every num in the row, just the corresponding three numbers ->
            need an inner loop and inner nested loop to fill box*/
            
            /*need to do divThree*3 because otherwise for boxnum=4 the rows would start at row 1. by doing divThree*3
            instead divThree will make rows 0-2 start at row 0, rows 3-5 start at row 3, and rows 6-8 start at row 6*/
            for(int r=divThree*3; r<(divThree*3)+3; r++){
                
                /*need to do modThree*3 because otherwise for boxnum=4 the rows would start at rownum[1]. by doing modThree*3
                instead, it will make index of the rows (for buxnum=4) start at row[3]. very similar to needing to do divThree*3*/
                for(int c = modThree*3; c<(modThree*3)+3; c++){
                    /*because all numbers in the b.numsInBox are Cell objects, and they all have int numbersRow and int numbersColumnl, 
                    it will help in the issue later on of trying to find out where the number is located*/
                  b.numsInBox[boxIndex] = rows[r]. numberList[c];
                  //increment boxIndex. it will reset to 0 when a new box is created
                  boxIndex++;
                }// end of the modThree loop                
            }//end of the divthree loop.    box is now full of the correct Cell objects
            //time to insert the box object into the Box [] box.
            boxs[bnum] = b;            
        }//end of creating boxs loop. Box array is now filled with boxes.
        return boxs;
    }

    /**
     * Takes the solution returned by the solution class and turns it into a String that gets written to a file.
     * the name of the new file will be (Input_File's_Name).sln.txt
     * @param GridRows The GridRows, but now filled in and complete.
     * @param filename The name of the String of the file's name that the user typed in to start the program.
     */
    private static void readOutFile(Row[] GridRows, String filename) {
        //go through each Cell, grabbing and printing its Numberint.
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+".sln.txt"), "utf-8"));
            for(int r=0; r<9; r++){
                String intToWrite="";
                for(int c=0; c<9; c++){
                    intToWrite+= ""+GridRows[r].numberList[c].Numberint;
                }
                writer.write(intToWrite);
                writer.write(System.getProperty( "line.separator" ));
            }
        } catch (IOException ex) {
            // catch the potential exception.
            System.out.println("There was an error in writing the file. The error occured inside"
                    + " of the readOutFile method in the Sudoke_Driver class");
        } finally {
           try {writer.close();} catch (Exception ex) {
           //if there is an exception here, cath it.
           System.out.println("There was an error trying to close the file after wrting it."
                   + " Error occured at the end of the readOutFile method in the Sudoku_Driver");
           }
        }
    }

    /**
     * Retrieves the name of the file from the filePath String.
     * Runs if and only if the file path, filePath, exists. 
     * @param filePath the file path the user entered into the program.
     * @return The name of the file that is being read in to the Sudoku solver. If the file contains a '.' in it, then this 
     * method returns "".
     */
    public static String getFileName(String filePath) {
        String fileName="";
        //if the file path works, then pull the file name out of the path excluding the extension.
        int lastSlashCharLoc=-1;
        int extensionBeginLoc=-1;
        //first find the location of the last '\' as that will indicate the file name will start on the next char
        for(int i=filePath.length()-1; i>0; i--){
            if(filePath.charAt(i)=='\\'){
                lastSlashCharLoc=i+1;
                //make i=0 so that it breaks out of the loop
                i=0;
            }
        }
        //now find where the extension begins
        for(int i=filePath.length()-1; i>0; i--){
            //do not need to check for the case of "fileName.extension".txt because it is in the catch statement
            if(filePath.charAt(i)=='.' && i!=filePath.length()-1){
                extensionBeginLoc=i;
                //make i=0 so that it breaks out of the loop
                i=0;
            }
        }
        fileName = filePath.substring(lastSlashCharLoc, extensionBeginLoc);


        if(fileName.contains(".")){
            System.out.println("There was an error reading in the file. \n"
            + "In the filepath, the file name contains a \'.\' , So please rename the file so that it follows standard file naming conventions.");
            //set fileName back to "".
            fileName = "";
        }
        return fileName;
    }
    
    
}