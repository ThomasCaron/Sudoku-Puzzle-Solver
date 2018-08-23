/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku.solution;

import java.util.ArrayList;
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
public class SolutionIT {
    static Row [] rows;
    static Column [] cols;
    static Box [] boxes;
    static Solution solution;
    public SolutionIT() {
        Sudoku_Driver driver = new Sudoku_Driver();
        String readin = driver.readInFile("C:\\Users\\my pc\\Documents\\a JOBS\\Summer Jobs 2018\\GAVANT code challenge\\puzzle5.txt");
        rows = driver.fillGridRows(readin);
        cols = driver.fillGridCol(rows);
        boxes = driver.fillGridBoxs(rows);
        solution = new Solution(boxes, rows, cols);
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
     * Test of getBoxesInbnumsRowAndCol method, of class Solution when given the bnum of 0
     * Should result in an ArrayList of size 6 containg the boxes: [0, 1, 2, 0, 3, 6]
     */
    @Test
    public void testGetBoxesInbnumsRowAndCol() {
//        System.out.println("getBoxesInbnumsRowAndCol test case 0");
        int bnum = 0;
        
        ArrayList<Box> expResult = new ArrayList<Box>();
        expResult.add(boxes[0]);
        expResult.add(boxes[1]);
        expResult.add(boxes[2]);
        expResult.add(boxes[0]);
        expResult.add(boxes[3]);
        expResult.add(boxes[6]);
        ArrayList<Box> result = solution.getBoxesInbnumsRowAndCol(bnum);
        
        assertEquals(result, expResult);
    }
    
    /**
     * Test of getBoxesInbnumsRowAndCol method, of class Solution when given the bnum of 0
     * Should result in an ArrayList of size 6 containg the boxes: [0, 1, 2, 0, 3, 6]
     */
    @Test
    public void testGetBoxesInbnumsRowAndCol2() {
//        System.out.println("getBoxesInbnumsRowAndCol test case 2 - bnum=5");
        int bnum = 5;
        
        ArrayList<Box> expResult = new ArrayList<Box>();
        expResult.add(boxes[3]);
        expResult.add(boxes[4]);
        expResult.add(boxes[5]);
        expResult.add(boxes[2]);
        expResult.add(boxes[5]);
        expResult.add(boxes[8]);
        ArrayList<Box> result = solution.getBoxesInbnumsRowAndCol(bnum);
        
        assertEquals(result, expResult);
    }
    /**
     * Test of getBoxesInbnumsRowAndCol method, of class Solution when given the bnum of 9!
     * 9 should be out of the range of bnums resulting in an ArrayList of size 0
     */
    @Test
    public void testGetBoxesInbnumsRowAndCol3() {
//        System.out.println("getBoxesInbnumsRowAndCol test case 3 - bnum=9");
        int bnum = 9;
        
        ArrayList<Box> expResult = new ArrayList<Box>();

        ArrayList<Box> result = solution.getBoxesInbnumsRowAndCol(bnum);
        assertEquals(result, expResult);
    }
    /**
     * Test of getBoxesInbnumsRowAndCol method, of class Solution when given the bnum of -1!
     * -1 should be out of the range of bnums resulting in an ArrayList of size 0
     */
    @Test
    public void testGetBoxesInbnumsRowAndCol4() {
//        System.out.println("getBoxesInbnumsRowAndCol test case 3 - bnum=9");
        int bnum = -1;
        
        ArrayList<Box> expResult = new ArrayList<Box>();

        ArrayList<Box> result = solution.getBoxesInbnumsRowAndCol(bnum);
        assertEquals(result, expResult);
    }

    /**
     * Test of getReleventBoxes method, of class Solution 
     */
    @Test
    public void testGetReleventBoxes() {
        System.out.println("getReleventBoxes");
        int bnum = 0;
        int number = 0;
        Solution instance = null;
        ArrayList<Box> expResult = null;
        ArrayList<Box> result = instance.getReleventBoxes(bnum, number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of FillinNumber method, of class Solution.
     */
    @Test
    public void testFillinNumber() {
        System.out.println("FillinNumber");
        int num = 0;
        int numRow = 0;
        int numCol = 0;
        Solution instance = null;
        instance.FillinNumber(num, numRow, numCol);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillPossibleNums method, of class Solution.
     */
    @Test
    public void testFillPossibleNums() {
        System.out.println("fillPossibleNums");
        Solution instance = null;
        instance.fillPossibleNums();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of simpleFill method, of class Solution.
     */
    @Test
    public void testSimpleFill() {
        System.out.println("simpleFill");
        Solution instance = null;
        instance.simpleFill();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminatePossibleNums method, of class Solution.
     */
    @Test
    public void testEliminatePossibleNums() {
        System.out.println("eliminatePossibleNums");
        Solution instance = null;
        instance.eliminatePossibleNums();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of advancedFill method, of class Solution.
     */
    @Test
    public void testAdvancedFill() {
        System.out.println("advancedFill");
        Solution instance = null;
        instance.advancedFill();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFull method, of class Solution.
     */
    @Test
    public void testIsFull() {
        System.out.println("isFull");
        Solution instance = null;
        boolean expResult = false;
        boolean result = instance.isFull();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePurePairs method, of class Solution.
     */
    @Test
    public void testRemovePurePairs() {
        System.out.println("removePurePairs");
        Solution instance = null;
        instance.removePurePairs();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
