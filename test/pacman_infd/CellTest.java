/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.Graphics;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ivanweller
 */
public class CellTest {
    
    public CellTest() {
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
     * Test of draw method, of class Cell.
     */
    @Test
    public void testDraw() {
        System.out.println("draw");
        Graphics g = null;
        Cell instance = null;
        instance.draw(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawElements method, of class Cell.
     */
    @Test
    public void testDrawElements() {
        System.out.println("drawElements");
        Graphics g = null;
        Cell instance = null;
        instance.drawElements(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasWall method, of class Cell.
     */
    @Test
    public void testHasWall() {
        System.out.println("hasWall");
        Cell instance = null;
        boolean expResult = false;
        boolean result = instance.hasWall();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addElement method, of class Cell.
     */
    @Test
    public void testAddElement() {
        System.out.println("addElement");
        GameElement e = null;
        Cell instance = null;
        instance.addElement(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeElement method, of class Cell.
     */
    @Test
    public void testRemoveElement() {
        System.out.println("removeElement");
        GameElement e = null;
        Cell instance = null;
        instance.removeElement(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNeighbor method, of class Cell.
     */
    @Test
    public void testSetNeighbor() {
        System.out.println("setNeighbor");
        Direction dir = null;
        Cell cell = null;
        Cell instance = null;
        instance.setNeighbor(dir, cell);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeighbor method, of class Cell.
     */
    @Test
    public void testGetNeighbor() {
        System.out.println("getNeighbor");
        Direction dir = null;
        Cell instance = null;
        Cell expResult = null;
        Cell result = instance.getNeighbor(dir);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNeighbors method, of class Cell.
     */
    @Test
    public void testGetNeighbors() {
        System.out.println("getNeighbors");
        Cell instance = null;
        Map expResult = null;
        Map result = instance.getNeighbors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSize method, of class Cell.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        Cell instance = null;
        int expResult = 0;
        int result = instance.getSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getXpos method, of class Cell.
     */
    @Test
    public void testGetXpos() {
        System.out.println("getXpos");
        Cell instance = null;
        int expResult = 0;
        int result = instance.getXpos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getYPos method, of class Cell.
     */
    @Test
    public void testGetYPos() {
        System.out.println("getYPos");
        Cell instance = null;
        int expResult = 0;
        int result = instance.getYPos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Cell.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Cell instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
