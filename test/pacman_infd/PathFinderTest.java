/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pacman_infd.Elements.Ghost;
import pacman_infd.Elements.Pacman;
import pacman_infd.Enums.Direction;

/**
 *
 * @author ivanweller
 */
public class PathFinderTest {

    GameController gameController;
    GameWorld gameWorld;
    PathFinder pathFinder;

    public PathFinderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        gameController = new GameController(null, null);
        pathFinder = new PathFinder();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void pathFinderTestGeval1() {
        char[][] levelMap = {
            {'-', 'A'},
            {'-', '-'},
            {'-', 'A'},
            {'A', 'A'}
        };
        
        gameWorld = new GameWorld(gameController, levelMap, gameController.getSoundManager(), new View());
        
        Ghost ghost1 = new Ghost(gameWorld.getCellMap()[1][0], null, 100, null, Color.yellow, null);
        Ghost ghost2 = new Ghost(gameWorld.getCellMap()[1][0], null, 100, null, Color.yellow, null);
        Ghost ghost3 = new Ghost(gameWorld.getCellMap()[1][0], null, 100, null, Color.yellow, null);
        Ghost ghost4 = new Ghost(gameWorld.getCellMap()[2][0], null, 100, null, Color.yellow, null);
        Pacman pacman = new Pacman(gameWorld.getCellMap()[2][0], null, 100, null);
        
        List path = pathFinder.findPathToPacman(gameWorld.getCellMap()[0][0]);
        
        assert(path != null);
        assert(path.size() == 2);
        assert(path.get(0) == gameWorld.getCellMap()[0][0].getNeighbor(Direction.DOWN));
        Cell pacCell = (Cell)path.get(1);
        assert(pacCell.getMovingElements().get(1) == pacman);
        
    }

    @Test
    public void pathFinderTestGeval2() {
        char[][] levelMap = {
            {'-', 'P'},
            {'A', '-'}
        };

        gameWorld = new GameWorld(gameController, levelMap, gameController.getSoundManager(), new View());

        Cell cell = pathFinder.nextCellInPathToPacman(gameWorld.getCellMap()[0][1]);

        assert (cell == gameWorld.getCellMap()[0][1]);
        assert (cell.getMovingElements().get(0) instanceof Pacman);
    }

    @Test
    public void pathFinderTestGeval3() {

        char[][] levelMap = {
            {'-'},
            {'A'}
        };

        gameWorld = new GameWorld(gameController, levelMap, gameController.getSoundManager(), new View());

        assert (pathFinder.findPathToPacman(gameWorld.getCellMap()[0][0]) == null);

        for (Cell cell : (Collection<Cell>) gameWorld.getCellMap()[0][0].getNeighbors().values()) {
            assert (cell.hasWall());
        }

    }

    @Test
    public void pathFinderTestGeval4() {

        char[][] levelMap = {
            {'-'},
            {'P'}
        };

        gameWorld = new GameWorld(gameController, levelMap, gameController.getSoundManager(), new View());

        Cell cell = pathFinder.nextCellInPathToPacman((gameWorld.getCellMap()[0][0]));

        assert (cell.getMovingElements().get(0) instanceof Pacman);

        assert (gameWorld.getCellMap()[0][0].getNeighbors().values().contains(cell));

    }

}
