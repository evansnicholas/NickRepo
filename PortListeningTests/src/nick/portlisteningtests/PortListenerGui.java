package nick.portlisteningtests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PortListenerGui extends JPanel implements ActionListener{
	
	private JTextField portInputField;
	private JButton startMonitoringButton;

	public PortListenerGui(){
        
		this.setLayout(new BorderLayout());
		
        //Create a text field to input port number
        portInputField = new JTextField(20);
        
        //Create a button to start monitoring
        startMonitoringButton = new JButton("Monitor");
        startMonitoringButton.addActionListener(this);
        
        this.add(portInputField, BorderLayout.PAGE_START);
        this.add(startMonitoringButton, BorderLayout.LINE_START);
	
	}

	
	public void actionPerformed(ActionEvent e){
		
		if (e.getSource() == startMonitoringButton){
		
			(new Thread(new PortListener())).start();
			
		}
	}
	
	private static void createAndShowGUI() {
        
		//Create and set up the window.
        JFrame frame = new JFrame("Port Sniffer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        frame.add(new PortListenerGui());
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

	}

}
