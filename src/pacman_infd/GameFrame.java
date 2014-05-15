/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Marinus
 */
public class GameFrame extends JFrame {
    
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    
    private GameWorld gameWorld;
    private Container contentPane;
    
    public GameFrame()
    {
        initComponents();      
    }
    
    private void initComponents()
    {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Pacman v0.1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        gameWorld = new GameWorld();
        gameWorld.setPreferredSize(new Dimension(500,500));
        
        contentPane.add(gameWorld, BorderLayout.CENTER);     
       
    }
    
            
}
