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
import java.util.Random;
import pacman_infd.Elements.*;
import pacman_infd.Strategies.ChasePacmanStrategy;
import pacman_infd.Strategies.MoveRandomStrategy;

/**
 *
 * @author Marinus
 */
public class GameWorld {

    private static final int CELL_SIZE = 26; //pixels
    private int width;
    private int height;
    private GameController gameController;
    private ArrayList<Cell> cells;
    private Cell[][] cellMap;
    private char[][] elementMap;
    private ArrayList<Ghost> ghosts;
    private int gameSpeed = 150;
    private int numberOfPelletsAtStart;

    public GameWorld(GameController gameController, String mapPath) {

        this.gameController = gameController;

        if (mapPath != null) {
            elementMap = loadMap(mapPath);
        }

        ghosts = new ArrayList<>();

        if (elementMap != null) {

            width = elementMap[0].length;
            height = elementMap.length;
            createCells();
            findNeighbors();

            placeElements(elementMap, cellMap);

            numberOfPelletsAtStart = countPellets();

            Pacman pacman = new Pacman(cellMap[1][1], gameController, gameSpeed);
            gameController.getView().addKeyListener(pacman);
//
//            Ghost ghost = new Ghost(cellMap[01][26], gameController, 100, new ChasePacmanStrategy());
//            ghosts.add(ghost);
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
        cellMap[14][0].setNeighbor(Direction.LEFT, cellMap[14][27]);
        cellMap[14][27].setNeighbor(Direction.RIGHT, cellMap[14][0]);
    }

    /**
     * Places walls on the cellMap according to wallMap
     *
     * @param wMap array of integers representing the walls (1=wall, 0=no wall)
     * @param cMap cell array of level.
     */
    private void placeElements(char[][] elementMap, Cell[][] cellMap) {

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (elementMap[x][y] == '0') {
                    Pellet p = new Pellet(cellMap[x][y]);
                } else if (elementMap[x][y] == '2') {
                    SuperPellet s = new SuperPellet(cellMap[x][y]);
                } else if (elementMap[x][y] == 'w') {
                    OneWayWall ow = new OneWayWall(cellMap[x][y], Direction.UP);
                } else if (elementMap[x][y] == 'a') {
                    Ghost blinky = new Ghost(cellMap[x][y], gameController, gameSpeed, new ChasePacmanStrategy(), Color.RED);
                    ghosts.add(blinky);
                } else if (elementMap[x][y] == 'b') {
                    Ghost pinky = new Ghost(cellMap[x][y], gameController, gameSpeed, new ChasePacmanStrategy(), Color.PINK);
                    ghosts.add(pinky);
                } else if (elementMap[x][y] == 'c') {
                    Ghost inky = new Ghost(cellMap[x][y], gameController, gameSpeed, new MoveRandomStrategy(), Color.CYAN);
                    ghosts.add(inky);
                } else if (elementMap[x][y] == 'd') {
                    Ghost clyde = new Ghost(cellMap[x][y], gameController, gameSpeed, new MoveRandomStrategy(), Color.ORANGE);
                    ghosts.add(clyde);
                } else if (elementMap[x][y] == '-') {
                        // niks
                } else {
                    Wall w = new Wall(cellMap[x][y], elementMap[x][y]);
                }
            }
        }
    }

    /**
     * Loads an elementMap from a file on the given path location.
     *
     * @param path file path
     * @return elementMap
     */
    private char[][] loadMap(String path) {

        try {
            FileLoader file = new FileLoader(path);

            return file.openMap();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

    }

    /**
     * Draw each cell in the game world.
     *
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
    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    /**
     * Draw the game world.
     *
     * @param g
     */
    public void draw(Graphics g) {
        g.clearRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
        drawCells(g);
    }

    private void neighborTest() {
        for (Cell cell : cells) {
            System.out.println(cell);
        }
    }

    /**
     *
     * @return number of Pellets at the start of the game.
     */
    public int getNumberOfPelletsAtStart() {
        return numberOfPelletsAtStart;
    }

    /**
     * Counts the number of pellets currently in the GameWorld.
     *
     * @return number of pellets
     */
    public int countPellets() {
        int number = 0;
        for (Cell cell : cells) {
            if (cell.getStaticElement() instanceof Pellet) {
                number++;
            }
        }
        return number;
    }

    /**
     * Places a cherry on a random cell that has no static element.
     */
    public void placeCherryOnRandomEmptyCell() {
        ArrayList<Cell> emptyCells = getEmptyCells();

        Random r = new Random();
        if (!emptyCells.isEmpty()) {
            Cherry c = new Cherry(emptyCells.get(r.nextInt(emptyCells.size() - 1)));
        }

    }

    /**
     * Returns a list of all cells that have no static element placed on them.
     *
     * @return list of cells
     */
    private ArrayList<Cell> getEmptyCells() {
        ArrayList<Cell> emptyCells = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.getStaticElement() == null) {
                emptyCells.add(cell);
            }
        }
        return emptyCells;
    }

}
