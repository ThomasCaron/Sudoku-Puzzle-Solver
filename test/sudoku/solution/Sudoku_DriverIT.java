/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku.solution;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author my pc
 */
public class Sudoku_DriverIT {
    
    public Sudoku_DriverIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Sudoku_DriverafterJunitTest.
     */
//    @Test
//    public void testMain() {
//        System.out.println("main");
//        String[] args = null;
//        Sudoku_DriverafterJunitTest.main(args);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    
    /**
     * tests if it can pull out a file name from a file path
     */
    @Test
    public void testgetFileName(){
//        System.out.println("\n in Junit test of readInFile method \n");
        String filePath="C:\\Users\\my pc\\Documents\\a JOBS\\Summer Jobs 2018\\GAVANT code challenge\\puzzle5.txt";
        String expected = "puzzle5";
        String result = Sudoku_Driver.getFileName(filePath);
        assertEquals(expected, result);
    }
    
    /**
     * Junit test for if there is a '.' in the middle
     * This will never occur because the try statement in the readInFile method of the Sudoku_Driver class would fail before it is ever called
     */
    @Test
    public void testgetFileName2(){
//        System.out.println("\n in Junit test of readInFile method \n");
        String filePath="C:\\Users\\my pc\\Documents\\a JOBS\\Summer Jobs 2018\\GAVANT code challenge\\puz.zle5.txt";
        String expected = "";
        String result = Sudoku_Driver.getFileName(filePath);
        assertEquals(expected, result);
    }
    
    /**
     * Junit test for if there was no file extension.
     * 
     * This will never occur because the try statement in the readInFile method of the Sudoku_Driver class would fail before it is ever called
     */
    @Test(expected=StringIndexOutOfBoundsException.class)
    public void testgetFileName3(){
//        System.out.println("\n in Junit test of readInFile method \n");
        String filePath="C:\\Users\\my pc\\Documents\\a JOBS\\Summer Jobs 2018\\GAVANT code challenge\\puzzle5";
        String expected = "";
        String result = Sudoku_Driver.getFileName(filePath);
        assertEquals(expected, result);
    }
    
}
