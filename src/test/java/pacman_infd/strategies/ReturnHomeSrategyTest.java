package pacman_infd.strategies;

import pacman_infd.games.Cell;

class ReturnHomeSrategyTest extends StrategyTest {
    Cell homeCell;

    @Override
    public void setUp() {
        super.setUp();

        gameWorld.getCell(0, 0);
        strategy = new ReturnHomeSrategy(homeCell);
    }

    @Override
    public void testGiveNextCell() {
        Cell cell = strategy.giveNextCell(gameWorld.getCell(1, 0));

        assert(cell.equals(homeCell));
    }

}