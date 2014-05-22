/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.Graphics;
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
public class GameElementTest {
    
    public GameElementTest() {
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
     * Test of draw method, of class GameElement.
     */
    @Test
    public void testDraw() {
        System.out.println("draw");
        Graphics g = null;
        GameElement instance = null;
        instance.draw(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCell method, of class GameElement.
     */
    @Test
    public void testGetCell() {
        System.out.println("getCell");
        GameElement instance = null;
        Cell expResult = null;
        Cell result = instance.getCell();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCell method, of class GameElement.
     */
    @Test
    public void testSetCell() {
        System.out.println("setCell");
        Cell cell = null;
        GameElement instance = null;
        instance.setCell(cell);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class GameElementImpl extends GameElement {

        public GameElementImpl() {
            super(null);
        }

        public void draw(Graphics g) {
        }
    }
    
}
