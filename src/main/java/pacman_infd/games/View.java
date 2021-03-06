/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.games;

import pacman_infd.enums.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * @author Marinus
 */
public class View extends JFrame implements MouseListener {
    private static final int FRAME_WIDTH = 740;
    private static final int FRAME_HEIGHT = 918;
    private static final String TITLE = "Pacman";

    private BufferedImage image;

    private GameController gameController;

    private JPanel gamePanel;
    private ScorePanel scorePanel;

    private JButton startButton;
    private JButton pauseButton;

    /**
     * Creates the view of the game.
     */
    public View() {
        initComponents();

        gameController = new GameController(this, scorePanel);
        image = new BufferedImage(FRAME_WIDTH, 850, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Initialize all components for this view.
     */
    private void initComponents() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        scorePanel = new ScorePanel();
        scorePanel.setPreferredSize(new Dimension(FRAME_WIDTH, 40));

        gamePanel = new JPanel();
        gamePanel.addMouseListener(this);
        startButton = new JButton("Start");
        startButton.addActionListener(this::startButtontActionPerformed);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this::pauseButtontActionPerformed);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setPreferredSize(new Dimension(FRAME_WIDTH, 35));
        controlPanel.setBackground(Color.BLACK);

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);

        contentPane.add(controlPanel, BorderLayout.SOUTH);
        contentPane.add(gamePanel, BorderLayout.CENTER);
        contentPane.add(scorePanel, BorderLayout.NORTH);

        setFocusable(true);
    }

    /**
     * Get the graphics object of this view.
     *
     * @return the graphics object of this view.
     */
    public Graphics getGameWorldGraphics() {
        return image.getGraphics();
    }

    /**
     * Draw the game world.
     */
    public void drawGameWorld() {
        gamePanel.getGraphics().drawImage(image, 0, 0, null);
    }

    /**
     * The behaviour of the start button when the user clicks on it.
     *
     * @param evt the action event.
     */
    private void startButtontActionPerformed(ActionEvent evt) {
        gameController.newGame();

        if (gameController.getGameState().equals(GameState.RUNNING)) {
            startButton.setText("Restart");
        } else {
            startButton.setText("Start");
        }
    }

    /**
     * The behaviour of the pause button when the user clicks on it.
     *
     * @param evt the action event.
     */
    private void pauseButtontActionPerformed(ActionEvent evt) {
        gameController.pauseGame();

        if (gameController.getGameState().equals(GameState.PAUSED)) {
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getY() > 40 && e.getY() < 890) {
            gameController.mouseClicked(e.getX(), e.getY(), e.getButton());
        }
        gamePanel.requestFocus();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
