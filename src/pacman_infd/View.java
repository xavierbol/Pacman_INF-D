/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Marinus
 */
public class View extends JFrame {

    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;

    private BufferedImage image;

    private GameController gameController;

    private GameWorld gameWorld;
    private Container contentPane;

    private JPanel gamePanel;
    private ScorePanel scorePanel;
    
    private JButton startButton;

    public View(){
        initComponents();
        
        gameController = new GameController(this, scorePanel);
        image = new BufferedImage(770,600,BufferedImage.TYPE_INT_ARGB);
    }

    private void initComponents() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Pacman v0.1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        scorePanel = new ScorePanel();
        scorePanel.setPreferredSize(new Dimension(130, 600));

        gamePanel = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtontActionPerformed(evt);
            }

        });
        
        contentPane.add(startButton, BorderLayout.SOUTH);
        contentPane.add(gamePanel, BorderLayout.CENTER);
        contentPane.add(scorePanel, BorderLayout.EAST);

        setFocusable(true);
    }

    public Graphics getGameWorldGraphics() {

        return image.getGraphics();

    }

    public void drawGameWorld() {

        gamePanel.getGraphics().drawImage(image, 0, 0, null);

    }
    
    private void startButtontActionPerformed(ActionEvent evt) {
        gameController.newGame();
    }

}
