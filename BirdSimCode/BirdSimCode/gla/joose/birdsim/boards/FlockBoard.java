package gla.joose.birdsim.boards;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A BirdSim board with generic bird flying behaviour.
 */
public class FlockBoard extends Board implements BoardInterface{

    JPanel buttonPanel;
    JButton hatchEggButton;
    JButton scareBirdsButton;
    JLabel noOfBirdsLabel;
    
    Thread runningthread;
    
	public FlockBoard(int rows, int columns) {
		super(rows, columns);		
	}

	public void initBoard(final JFrame frame) {
        JPanel display = getJPanel();
        frame.getContentPane().add(display, BorderLayout.CENTER);
        
        // Install button panel
        buttonPanel = new JPanel();
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        hatchEggButton = new JButton("hatch egg");
        buttonPanel.add(hatchEggButton);
        hatchEggButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scareBirds = false;
            	runningthread = new Thread(new Runnable(){
					public void run() {
						fly();
					}            		
            	});
            	runningthread.start();
        }}); 

        scareBirdsButton = new JButton("scare birds");
        buttonPanel.add(scareBirdsButton);
        scareBirdsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scareBirds = true;
        }}); 
        
        noOfBirdsLabel = new JLabel();
        noOfBirdsLabel.setText("#birds: "+0);
        buttonPanel.add(noOfBirdsLabel);

        
        // Implement window close box
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	//used to invoke birds removal from the board
            	scareBirds = true;
            	if(runningthread !=null){
                    clear();
                    try {
						runningthread.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
            	}
            	frame.dispose();
                System.exit(0);
        }});
        
        frame.pack();
        frame.setSize(650, 650);
        frame.setVisible(true);
        		
	}

	public void updateStockDisplay(){
		updateStock();
		noOfBirdsLabel.setText("#birds: "+noofbirds);
	}
	
	public void fly(){
		
		Bird bird = new Bird();
		
		int randRow = rand.nextInt((getRows() - 3) + 1) + 0;
    	int randCol = rand.nextInt((getColumns() - 3) + 1) + 0;
    	
		place(bird,randRow, randCol);
		bird.setDraggable(false);
		bird.setSpeed(20);
		updateStockDisplay();
		
		while(!scareBirds){
			randRow = rand.nextInt((getRows() - 3) + 1) + 0;
        	randCol = rand.nextInt((getColumns() - 3) + 1) + 0; 
        	bird.moveTo(randRow, randCol);
    		bird.setSpeed(20);
			
		} 
		bird.remove();
		updateStockDisplay();
	}
	
	
	

}
