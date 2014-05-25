/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import pacman_infd.Elements.*;
import pacman_infd.Strategies.MoveRandom;

/**
 *
 * @author Marinus
 */
public class GameWorld {

    private static final int CELL_SIZE = 25; //pixels
    private static final int WIDTH = 30; //cells
    private static final int HEIGHT = 22; //cells

    private GameController gameController;

    private ArrayList<Cell> cells;
    private Cell[][] cellMap;

    private int[][] wallMap
            = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1},
                {1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

            };

    public GameWorld(GameController gameController) {
        createCells(WIDTH, HEIGHT);
        findNeighbors();
        placeWalls(wallMap, cellMap);
        placePellets();

        this.gameController = gameController;

        Pacman pacman = new Pacman(cellMap[1][1], gameController);
        gameController.getView().addKeyListener(pacman);
        
        Ghost ghost = new Ghost(cellMap[1][1], gameController, new MoveRandom());

        //neighborTest();

    }

    /**
     * Create a grid of cells.
     * @param width
     * @param height 
     */
    private void createCells(int width, int height) {
        cells = new ArrayList<>();
        cellMap = new Cell[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = new Cell(x, y, CELL_SIZE);
                cellMap[x][y] = cell;
                cells.add(cell);
            }
        }
    }    

    /**
     * Finds all neighbors for each cell and adds them to the neighbors Map of
     * each cell.
     */
    private void findNeighbors() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x - 1 >= 0) {
                    cellMap[x][y].setNeighbor(Direction.LEFT, cellMap[x - 1][y]);
                }
                if (x + 1 < WIDTH) {
                    cellMap[x][y].setNeighbor(Direction.RIGHT, cellMap[x + 1][y]);
                }
                if (y - 1 >= 0) {
                    cellMap[x][y].setNeighbor(Direction.UP, cellMap[x][y - 1]);
                }
                if (y + 1 < HEIGHT) {
                    cellMap[x][y].setNeighbor(Direction.DOWN, cellMap[x][y + 1]);
                }
            }
        }
    }

    /**
     * Places walls on the cellMap according to wallMap
     * @param wMap array of integers representing the walls (1=wall, 0=no wall)
     * @param cMap cell array of level.
     */
    private void placeWalls(int[][] wallMap, Cell[][] cellMap) {

        for (int x = 0; x < 22; x++) {
            for (int y = 0; y < 30; y++) {
                if (wallMap[x][y] == 1) {
                    Wall wall = new Wall(cellMap[y][x]);
                    cellMap[y][x].addElement(wall);
                }
            }
        }
    }

    private void placePellets() {
        for (Cell cell : cells) {
            if (!cell.hasWall() && !cell.hasPellet()) {
                Pellet p = new Pellet(cell);
            }
        }
    }

    private void drawCells(Graphics g) {
        if (cells != null) {
            for (Cell cell : cells) {
                cell.draw(g);
            }
        }
    }

    public void draw(Graphics g) { 
        g.clearRect(0, 0, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        drawCells(g);
    }


    private void neighborTest() {
        for (Cell cell : cells) {
            System.out.println(cell);
        }
    }          

}
