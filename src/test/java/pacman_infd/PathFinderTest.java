/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Color;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman_infd.Elements.Ghost;
import pacman_infd.Elements.Pacman;
import pacman_infd.Enums.Direction;
import pacman_infd.Games.Cell;
import pacman_infd.Games.GameController;
import pacman_infd.Games.GameWorld;
import pacman_infd.Games.View;
import pacman_infd.Strategies.PathFinder;

/**
 *
 * @author ivanweller
 */
public class PathFinderTest {
    View view;
    GameController gameController;
    GameWorld gameWorld;
    PathFinder pathFinder;

    public PathFinderTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @BeforeAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        view = new View();
        gameController = new GameController(view, null);
        pathFinder = new PathFinder();
    }

    @AfterEach
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
        
        //create a new GameWorld.
        gameWorld = new GameWorld(gameController, levelMap, 0);
        
        // Set up moving elements in the GameWorld.
        Ghost ghost1 = new Ghost(gameWorld.getCell(0,1), null, 100, null, Color.yellow);
        Ghost ghost2 = new Ghost(gameWorld.getCell(0,1), null, 100, null, Color.yellow);
        Ghost ghost3 = new Ghost(gameWorld.getCell(0,1), null, 100, null, Color.yellow);
        Ghost ghost4 = new Ghost(gameWorld.getCell(0,2), null, 100, null, Color.yellow);
        Pacman pacman = new Pacman(gameWorld.getCell(0,2), null, 100);
        
        //Find the path to Pacman.
        List<Cell> path = pathFinder.findPathToPacman(gameWorld.getCell(0,0));
        
        //assert that the list is not empty.
        assert(path != null);
        //assert that the size of the list equals 2.
        assert(path.size() == 2);
        //assert that the path is correct.
        assert(path.get(0).equals(gameWorld.getCell(0,0).getNeighbor(Direction.DOWN)));
        Cell pacCell = (Cell)path.get(1);
        //assert that the cell that holds Pacman, holds it as the second element in the list.
        assert(pacCell.getMovingElements().get(1).equals(pacman));
    }

    @Test
    public void pathFinderTestGeval2() {
        char[][] levelMap = {
            {'-', 'P'},
            {'A', '-'}
        };
        //create a new GameWorld.   
        gameWorld = new GameWorld(gameController, levelMap, 0);
        //find the next cell in the shortest path to the cell that holds Pacman from LevelMap[0][1].
        Cell cell = pathFinder.nextCellInPathToPacman(gameWorld.getCell(0,0));
        // check if the cell is not null
        assert (cell != null);
        //assert that cell equals the cell that holds Pacman.
        assert (cell.equals(gameWorld.getCell(1,0)));
        //assert that cell holds an instance of Pacman as the first element in the cell.
        assert (cell.getMovingElements().get(0) instanceof Pacman);
    }

    @Test
    public void pathFinderTestGeval3() {
        char[][] levelMap = {
            {'-'},
            {'A'}
        };
        
        //create a new GameWorld.        
        gameWorld = new GameWorld(gameController, levelMap, 0);
        //assert that there's no valid path to Pacman.
        assert (pathFinder.findPathToPacman(gameWorld.getCell(0,0)) == null);
    }

    @Test
    public void pathFinderTestGeval4() {
        char[][] levelMap = {
            {'-'},
            {'P'}
        };
        
        //create a new GameWorld.
        gameWorld = new GameWorld(gameController, levelMap, 0);
        //Get the next cell in the path to Pacman.
        Cell cell = pathFinder.nextCellInPathToPacman((gameWorld.getCell(0,0)));
        //assert that cell holds an instance of Pacman.
        assert (cell.getMovingElements().get(0) instanceof Pacman);
        //assert that cell is a valid neighbour of gameWorld.getCell(0, 0).
        assert (gameWorld.getCell(0,0).getNeighbors().containsValue(cell));
    }
}
