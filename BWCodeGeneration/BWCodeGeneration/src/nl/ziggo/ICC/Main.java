package nl.ziggo.ICC;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



public class Main {

	/**
	 * @param args
	 */
	
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Code Generation Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new UserInterface());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
    }
	
	public static void main(String[] args) {
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        createAndShowGUI();
        
        
            }
        });
    }

}
