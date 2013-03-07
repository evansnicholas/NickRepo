import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Nick's Sudoku Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(500,500);
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Sudoku");
	
		panel.add(label);
		frame.add(panel);
		
	

	}

}
