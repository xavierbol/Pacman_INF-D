/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import pacman_infd.Elements.*;
import pacman_infd.Strategies.ChasePacmanStrategy;

/**
 *
 * @author Marinus
 */
public class GameWorld {

    private static final int CELL_SIZE = 25; //pixels

    private int width;
    private int height;

    private GameController gameController;

    private ArrayList<Cell> cells;
    private Cell[][] cellMap;

    private int[][] elementMap;
    
    private ArrayList<Ghost> ghosts;

    public GameWorld(GameController gameController, String mapPath) {
        
        this.gameController  = gameController;
        elementMap = loadMap(mapPath);
        
        ghosts = new ArrayList<>();

        if (elementMap != null) {

            width = elementMap[0].length;
            height = elementMap.length;
            createCells();
            findNeighbors();

            placeElements(elementMap, cellMap);

            Pacman pacman = new Pacman(cellMap[1][1], gameController, 100);
            gameController.getView().addKeyListener(pacman);

            Ghost ghost = new Ghost(cellMap[01][26], gameController, 100, new ChasePacmanStrategy());
            ghosts.add(ghost);
        }
        //neighborTest();

    }

    /**
     * Create a grid of cells.
     *
     * @param width
     * @param height
     */
    private void createCells() {

        cells = new ArrayList<>();
        cellMap = new Cell[height][width];

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                Cell cell = new Cell(y, x, CELL_SIZE);
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

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (x - 1 >= 0) {
                    cellMap[x][y].setNeighbor(Direction.UP, cellMap[x - 1][y]);
                }
                if (x + 1 < height) {
                    cellMap[x][y].setNeighbor(Direction.DOWN, cellMap[x + 1][y]);
                }
                if (y - 1 >= 0) {
                    cellMap[x][y].setNeighbor(Direction.LEFT, cellMap[x][y - 1]);
                }
                if (y + 1 < width) {
                    cellMap[x][y].setNeighbor(Direction.RIGHT, cellMap[x][y + 1]);
                }
            }
        }
        
        //Special case voor portal
        //cellMap[14][0].setNeighbor(Direction.LEFT, cellMap[14][27]);
        //cellMap[14][27].setNeighbor(Direction.RIGHT, cellMap[14][0]);
    }

    /**
     * Places walls on the cellMap according to wallMap
     *
     * @param wMap array of integers representing the walls (1=wall, 0=no wall)
     * @param cMap cell array of level.
     */
    private void placeElements(int[][] elementMap, Cell[][] cellMap) {

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (elementMap[x][y] == 0) {
                    Pellet p = new Pellet(cellMap[x][y]);
                }
                if (elementMap[x][y] == 1) {
                    Wall w = new Wall(cellMap[x][y]);
                }
                if (elementMap[x][y] == 2) {
                    SuperPellet s = new SuperPellet(cellMap[x][y]);
                }
            }
        }
    }

    /**
     * Loads an elementMap from a file on the given path location.
     * @param path file path
     * @return elementMap
     */
    private int[][] loadMap(String path) {

        try {
            FileLoader file = new FileLoader(path);

            return file.openMap();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

    }

//    private void placePellets() {
//        for (Cell cell : cells) {
//            if (!cell.hasWall()) {
//                Pellet p = new Pellet(cell);
//            }
//        }
//    }
    
    /**
     * Draw each cell in the game world.
     * @param g Graphics object
     */
    private void drawCells(Graphics g) {
        if (cells != null) {
            for (Cell cell : cells) {
                cell.draw(g);
            }
        }
    }
    
    /**
     * 
     * @return a list of all Ghost Objects in the game world.
     */
    public ArrayList<Ghost> getGhosts()
    {
        return ghosts;
    }

    /**
     * Draw the game world.
     * @param g 
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
        drawCells(g);
    }

    private void neighborTest() {
        for (Cell cell : cells) {
            System.out.println(cell);
        }
    }

}
