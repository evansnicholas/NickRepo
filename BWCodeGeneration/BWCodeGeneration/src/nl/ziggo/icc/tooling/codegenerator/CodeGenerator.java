package nl.ziggo.icc.tooling.codegenerator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class CodeGenerator extends JPanel implements ActionListener{

	protected static final String componentFieldString = "Component Name"; 
	protected static final String ftfString = "JFormattedTextField";
	private String newline = "\n";
	JTextArea log;
	JButton generateCodeButton;
	JTextField componentTextField;
	JComboBox componentsList;
	String[] previousComponents;
	CodeGeneratorConfiguration configFileLoader;
	
	 protected JLabel actionLabel;
	 protected JLabel logLabel;
	
	 public CodeGenerator(){
		 
		setLayout(new BorderLayout());
		
		//Create a screen to display log messages
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        logScrollPane.setPreferredSize(new Dimension(500, 500));
        logScrollPane.setMinimumSize(new Dimension(10, 10));
        logScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
      //Create a ConfigFileLoader object
      this.configFileLoader = new CodeGeneratorConfiguration("misc/memory.txt", StandardCharsets.US_ASCII, log);  
        
		//Create a regular text field.
	    componentTextField = new JTextField(20);
	    componentTextField.setActionCommand(componentFieldString);
	    componentTextField.addActionListener(this);
	    
	    //load the past components for the combo box
	    previousComponents = configFileLoader.getPastComponents();
	    
	    //Create an editable combo box
	    componentsList = new JComboBox(previousComponents);
		componentsList.setEditable(true);
		componentsList.addActionListener(this);
 
        //Create some labels for the fields.
        JLabel componentFieldLabel = new JLabel(componentFieldString + ": ");
        componentFieldLabel.setLabelFor(componentTextField);
 
        //Create a label to put messages during an action event.
        actionLabel = new JLabel("Enter the name of the component you wish to create and press generate to start code generation.");
        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        
        //Create a generate button
        generateCodeButton = new JButton("Generate!");
        generateCodeButton.addActionListener(this);
        
        //Lay out the text controls and the labels for the input panel.
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
 
        textControlsPane.setLayout(gridbag);
 
        JLabel[] labels = {componentFieldLabel};
        //JTextField[] textFields = {componentTextField};
        JComboBox[] jComboBoxes = {componentsList};
        //addLabelTextRows(labels, textFields, gridbag, textControlsPane);
        addLabelJComboRows(labels, jComboBoxes, gridbag, textControlsPane);
        
 
        c.gridwidth = GridBagConstraints.REMAINDER; //last
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
        textControlsPane.add(actionLabel, c);
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Create a new component"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
        
        //Lay out the text controls and the labels for the log panel.
        JPanel logDisplayPane = new JPanel();
        
        logDisplayPane.setLayout(gridbag);
        
        logDisplayPane.add(logScrollPane);
        
        logDisplayPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Log Screen"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
        
        //Create a panel for the buttons
        JPanel buttonPane = new JPanel();
        
        buttonPane.add(generateCodeButton);
        
        buttonPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Actions"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
            
        
        //Put everything together.
        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(textControlsPane,
                     BorderLayout.PAGE_START);
        
        leftPane.add(logDisplayPane,
        			  BorderLayout.CENTER);
 
        add(leftPane, BorderLayout.LINE_START);
        add(buttonPane, BorderLayout.LINE_END);
        
    }
	
	 private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, GridBagLayout gridbag, Container container) {
		 
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;
		
		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
			c.fill = GridBagConstraints.NONE;      //reset to default
			c.weightx = 0.0;                       //reset to default
			container.add(labels[i], c);
			
			c.gridwidth = GridBagConstraints.REMAINDER;     //end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}
	 
	 private void addLabelJComboRows(JLabel[] labels, JComboBox[] JComboBox, GridBagLayout gridbag, Container container) {
		 
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.EAST;
			int numLabels = labels.length;
			
			for (int i = 0; i < numLabels; i++) {
				c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
				c.fill = GridBagConstraints.NONE;      //reset to default
				c.weightx = 0.0;                       //reset to default
				container.add(labels[i], c);
				
				c.gridwidth = GridBagConstraints.REMAINDER;     //end row
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1.0;
				container.add(JComboBox[i], c);
			}
		}
	 
	 
	
	 
	 protected void addStylesToDocument(StyledDocument doc) {
	        //Initialize some styles.
	        Style def = StyleContext.getDefaultStyleContext().
	                        getStyle(StyleContext.DEFAULT_STYLE);
	 
	        Style regular = doc.addStyle("regular", def);
	        StyleConstants.setFontFamily(def, "SansSerif");
	 
	        Style s = doc.addStyle("italic", regular);
	        StyleConstants.setItalic(s, true);
	 
	        s = doc.addStyle("bold", regular);
	        StyleConstants.setBold(s, true);
	 
	        s = doc.addStyle("small", regular);
	        StyleConstants.setFontSize(s, 10);
	 
	        s = doc.addStyle("large", regular);
	        StyleConstants.setFontSize(s, 16);
	       
	    } 
	 
	public void actionPerformed(ActionEvent e) {

		String prefix = "You typed \"";
        
		/*if (componentFieldString.equals(e.getActionCommand())) {
            
        	//Update action label
        	JTextField source = (JTextField)e.getSource();
            actionLabel.setText(prefix + source.getText() + "\"");
            
            //Update log screen
            log.append(source.getText() + newline);
            
            log.setCaretPosition(log.getDocument().getLength());     
        }*/
		
        if (e.getSource() == generateCodeButton){
        	
        	String componentName = componentsList.getSelectedItem().toString();
        	
        	log.append("Code for "+componentName+" will be generated."+newline);
        	log.setCaretPosition(log.getDocument().getLength());
        	configFileLoader.addComponentNameToPastComponentsList(componentName);
        	
        	//Unit Tests
        	ArrayList<String[]> termsToBeReplaced = new ArrayList<String[]>();
        	String[] pair = {"[~]OPERATION_NAME[~]", "TcyManageBillingForTracy"};
        	String[] pair2 = {"[~]OPERATION_VERSION[~]", "2"};
        	String[] pair3 = {"[~]CDM_ENTITY[~]", "Order"};
        	String[] pair4 = {"[~]ADAPTER_NAME[~]", "adpTracy"};
        	String[] pair5 = {"[~]ADAPTER_NAME_LOWERCASE[~]", "adptracy"};
        	String[] pair6 = {"[~]OPERATION_NAME_LOWERCASE[~]", "tcymanagebillingfortracy"};
        	termsToBeReplaced.add(pair);
        	termsToBeReplaced.add(pair2);
        	termsToBeReplaced.add(pair3);
        	termsToBeReplaced.add(pair4);
        	termsToBeReplaced.add(pair5);
        	termsToBeReplaced.add(pair6);
        	Utilities u = new Utilities(log);
        	u.searchReplaceAndWriteToTempFolder(termsToBeReplaced, new File("C:\\Users\\iggo\\Desktop\\Test\\Templates"), new File("C:\\Users\\iggo\\Desktop\\Test\\Temp"));
        	u.copyFilesFromTempFolderBasedOnName(new File("C:\\Users\\iggo\\Desktop\\Test\\adpTracy"), new File("C:\\Users\\iggo\\Desktop\\Test\\Temp"), termsToBeReplaced);
        	
        	
        	
        }
	}

}
