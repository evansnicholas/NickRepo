package nl.ziggo.icc.tooling.codegenerator;

import java.awt.BorderLayout;
import java.awt.Component;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import nl.ziggo.icc.tooling.codegenerator.entity.Service;
import nl.ziggo.icc.tooling.codegenerator.exceptions.CodeGeneratorException;
import nl.ziggo.icc.tooling.codegenerator.exceptions.ComponentNotFoundException;
import nl.ziggo.icc.tooling.codegenerator.exceptions.NoXsdsFoundException;


public class CodeGenerator extends JPanel implements ActionListener{

	protected static final String componentFieldString = "Component Name"; 
	protected static final String ftfString = "JFormattedTextField";
	private String newline = "\n";
	private JTextArea log;
	private JButton generateCodeButton;
	private JButton generateServiceInvocationButton;
	private JTextField componentTextField;
	private JComboBox componentsList;
	private String[] previousComponents;
	private CodeGeneratorConfiguration configFileLoader;
	private static HashMap<String, String> placeHolders;
	
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
      this.configFileLoader = new CodeGeneratorConfiguration(StandardCharsets.US_ASCII, log);  
        
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
        //actionLabel = new JLabel("Enter the name of the component you wish to create and press generate to start code generation.");
        //actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        
        //Create a generate button
        generateCodeButton = new JButton("Generate!");
        generateCodeButton.addActionListener(this);
        generateCodeButton.setPreferredSize(new Dimension(200, 40));
        generateCodeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Create a button for generating service invocations
        generateServiceInvocationButton = new JButton("Generate service invocations.");
        generateServiceInvocationButton.addActionListener(this);
        generateServiceInvocationButton.setPreferredSize(new Dimension(200, 40));
        generateServiceInvocationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Lay out the text controls and the labels for the input panel.
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints textConstraints = new GridBagConstraints();
 
        textControlsPane.setLayout(gridbag);
 
        //JLabel[] labels = {componentFieldLabel};
        //JTextField[] textFields = {componentTextField};
        //JComboBox[] jComboBoxes = {componentsList};
        //addLabelTextRows(labels, textFields, gridbag, textControlsPane);
        //addLabelJComboRows(labels, jComboBoxes, gridbag, textControlsPane);
        
 
        //textConstraints.gridwidth = GridBagConstraints.REMAINDER; //last
        //textConstraints.anchor = GridBagConstraints.WEST;
        //textConstraints.weightx = 1.0;
        textConstraints.fill = GridBagConstraints.BOTH;
        textConstraints.gridx = 0;
        textConstraints.gridy = 0;
        //textControlsPane.add(actionLabel, textConstraints);
        textControlsPane.add(componentsList, textConstraints);
        textControlsPane.add(componentFieldLabel, textConstraints);
        
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Create a new component"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
        
        //Lay out the text controls and the labels for the log panel.
        JPanel logDisplayPane = new JPanel();
        
        logDisplayPane.setLayout(gridbag);
        
        GridBagConstraints logConstraints = new GridBagConstraints();
        //logConstraints.weightx = 1.0;
        //logConstraints.weightx = 1.0;
        logConstraints.fill = GridBagConstraints.BOTH;
        logConstraints.gridx = 0;
        logConstraints.gridy = 1;
        
        logDisplayPane.add(logScrollPane, logConstraints);
        
        logDisplayPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Log Screen"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
        
        //Create a panel for the buttons
        JPanel buttonPane = new JPanel();
        GroupLayout groupLayout = new GroupLayout(buttonPane);
        buttonPane.setLayout(groupLayout);
        
        groupLayout.setAutoCreateGaps(true);
        
        groupLayout.setVerticalGroup(
        		   groupLayout.createSequentialGroup()
        		      .addComponent(generateCodeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        		      .addComponent(generateServiceInvocationButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        		);
        
       groupLayout.setHorizontalGroup(
        		   groupLayout.createSequentialGroup()
        		   	.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		           .addComponent(generateCodeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        		           .addComponent(generateServiceInvocationButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        		));  
        		   
        //buttonPane.add(generateCodeButton);
        //buttonPane.add(Box.createRigidArea(new Dimension(0,10)));
        //buttonPane.add(generateServiceInvocationButton);
        
        buttonPane.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Actions"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
            
        
        //Put everything together.
        /*
        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(textControlsPane,
                     BorderLayout.PAGE_START);
        
        leftPane.add(logDisplayPane,
        			  BorderLayout.CENTER);
 
        add(leftPane, BorderLayout.LINE_START);
        add(buttonPane, BorderLayout.LINE_END);
        */
        
        add(textControlsPane, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
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
        	
        	log.append("Generating code for "+componentName+"..."+newline);
        	log.setCaretPosition(log.getDocument().getLength());
        	
        	configFileLoader.addComponentNameToPastComponentsList(componentName);
        	 	
        	CodeGenerationManager cgManager = new CodeGenerationManager(componentName, this.log, CodeGenerator.placeHolders);
        	
        	try{
        	
        		cgManager.generateBWCodeForComponent();
        		
        		log.append("Code generation terminated successfully."+ "\n" + "\n");
	        	log.setCaretPosition(log.getDocument().getLength());
        	
        	}catch(ComponentNotFoundException componentNotFoundException){
        		
        		String[] possibleChoices = {"Create SVN structure", "Create SVN structure and empty BW Template"}; 
        		
        		String userChoice = (String) JOptionPane.showInputDialog(null, "Component was not found in your local SVN directory.  What would you like to do?", "New Component",JOptionPane.QUESTION_MESSAGE,
 				       null, possibleChoices, possibleChoices[0]);
        		
        		if (userChoice == possibleChoices[0]){
        			
        			cgManager.generateInitialSvnStructure();
        			
        			log.append("An SVN structure was created for "+ componentName+ "."+ newline);
        			log.setCaretPosition(log.getDocument().getLength());
        			
        		}else if(userChoice == possibleChoices[1]){
        		
        			JTextField userInput = new JTextField(50);
        			JPanel userInputPanel = new JPanel();
        			userInputPanel.add(new JLabel("Service names and versions:"));
        			userInputPanel.add(userInput);
        			
        		    int userServiceInfoInput = JOptionPane.showConfirmDialog(null, userInputPanel, "Enter the names and versions of the services you want to create an empty BW project for.  Syntax is: ServiceName1 ServiceVersion1 ServiceName2 ServiceVersion2 etc...", JOptionPane.OK_CANCEL_OPTION);
        			
        		    if (userServiceInfoInput == JOptionPane.OK_OPTION){
        		    	
        		    	String userInputString = userInput.getText();
        		    	String[] serviceNamesAndVersions = userInputString.split(" ");
        		    	
        		    	if (serviceNamesAndVersions.length % 2 != 0){
        		    		
        		    		log.append("Wrong service information was entered");
        		    		log.setCaretPosition(log.getDocument().getLength());
        		    		return;
        		    		
        		    	}
        		    	
        		    	Service[] services = new Service[serviceNamesAndVersions.length/2]; 
        		    	
        		    	for (int i = 0; i < serviceNamesAndVersions.length; i += 2){
        		    		
        		    		Service service = new Service(componentName);
        		    		service.setOperationName(serviceNamesAndVersions[i]);
        		    		service.setOperationVersion(serviceNamesAndVersions[i+1]);
        		    		
        		    		services[i/2] = service;
        		    		
        		    	}
        		    	
        		    	cgManager.generateInitialSvnStructure();
            			
        		    	try{
        		    		
        		    		cgManager.createEmptyBWTemplate(services);
        		    		
        		    	}catch(CodeGeneratorException codeGeneratorException){
        		    		
        		    		log.append(codeGeneratorException.getMessage());
        		    		log.setCaretPosition(log.getDocument().getLength());
        		    		
        		    		return;
        		    		
        		    	}
        		    	
        		    	log.append("An SVN structure and empty BW template was created for "+ componentName+ "."+ newline);
            			log.setCaretPosition(log.getDocument().getLength());
        		    	
        		    }else{
        		    	
        		    	log.append("Neither an SVN structure nor a BW template was created for "+ componentName+ "."+ newline);
            			log.setCaretPosition(log.getDocument().getLength());
        		    	
        		    }
        		    
        			
        		
        		}else{
        			
        			log.append("An SVN structure was not created for "+ componentName+ "."+ newline);
        			log.setCaretPosition(log.getDocument().getLength());
        			     			
        		}
        	
        	}catch(NoXsdsFoundException noXsdsFoundException){
        	
        		String[] possibleChoices = {"Create empty BW Template"}; 
        		
        		String userChoice = (String) JOptionPane.showInputDialog(null, "No new xsds were found.  What would you like to do?", "New Component", JOptionPane.QUESTION_MESSAGE,
 				       null, possibleChoices, possibleChoices[0]);
        		
        		if (userChoice == possibleChoices[0]){
        			
        			JTextField userInput = new JTextField(50);
        			JPanel userInputPanel = new JPanel();
        			userInputPanel.add(new JLabel("Service names and versions:"));
        			userInputPanel.add(userInput);
        			
        		    int userServiceInfoInput = JOptionPane.showConfirmDialog(null, userInputPanel, "Enter the names and versions of the services you want to create an empty BW project for.  Syntax is: ServiceName1 ServiceVersion1 ServiceName2 ServiceVersion2 etc...", JOptionPane.OK_CANCEL_OPTION);
        			
        		    if (userServiceInfoInput == JOptionPane.OK_OPTION){
        		    	
        		    	String userInputString = userInput.getText();
        		    	String[] serviceNamesAndVersions = userInputString.split(" ");
        		    	
        		    	if (serviceNamesAndVersions.length % 2 != 0){
        		    		
        		    		log.append("Wrong service information was entered");
        		    		log.setCaretPosition(log.getDocument().getLength());
        		    		return;
        		    		
        		    	}
        		    	
        		    	Service[] services = new Service[serviceNamesAndVersions.length/2]; 
        		    	
        		    	for (int i = 0; i < serviceNamesAndVersions.length; i += 2){
        		    		
        		    		Service service = new Service(componentName);
        		    		service.setOperationName(serviceNamesAndVersions[i]);
        		    		service.setOperationVersion(serviceNamesAndVersions[i+1]);
        		    		
        		    		services[i/2] = service;
        		    		
        		    	}
        		    	
        		    	try{
        		    		
        		    		cgManager.createEmptyBWTemplate(services);
        		    		
        		    	}catch(CodeGeneratorException codeGeneratorException){
        		    		
        		    		log.append(codeGeneratorException.getMessage());
        		    		log.setCaretPosition(log.getDocument().getLength());
        		    		
        		    		return;
        		    		
        		    	}
        		    	
        		    	log.append("Empty BW templates were created for "+ componentName+ "."+ newline);
            			log.setCaretPosition(log.getDocument().getLength());
        		    	
        		    }else{
        		    	
        		    	log.append("Neither an SVN structure nor a BW template was created for "+ componentName+ "."+ newline);
            			log.setCaretPosition(log.getDocument().getLength());
        		    	
        		    }
        			
        		}
        	
        	
        	
        	}catch(CodeGeneratorException codeGeneratorException){
        				
        				log.append(codeGeneratorException.getMessage()+newline);
        	        	log.setCaretPosition(log.getDocument().getLength());
        				
        	}
        		
        }else if(e.getSource() == generateServiceInvocationButton){
        	
        	String componentName = componentsList.getSelectedItem().toString();
 	
        	log.append("Generating service invocations for "+componentName+"..."+newline);
        	log.setCaretPosition(log.getDocument().getLength());
        	
        	CodeGenerationManager cgManager = new CodeGenerationManager(componentName, this.log, CodeGenerator.placeHolders);
        	
        	try{
        		
        		cgManager.generateServiceInvocations();
        	
        	}catch(FileNotFoundException fileNotFoundException){
        		
        		log.append(fileNotFoundException.getMessage() + newline + "Service Configurations were not successfully generated" + newline);
            	log.setCaretPosition(log.getDocument().getLength());
        		
        	}catch(CodeGeneratorException codeGeneratorException){
        		
        		log.append(codeGeneratorException.getMessage() + newline);
            	log.setCaretPosition(log.getDocument().getLength());
        		
        	}
        	
        }
    	
    }
	
	
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Code Generation Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new CodeGenerator());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
    }
	
	public static void main(String[] args) {
		
		placeHolders = new HashMap<String, String>();
    	placeHolders.put("cdmEntity", "[~]CDM_ENTITY[~]");
    	placeHolders.put("operationName", "[~]OPERATION_NAME[~]");
    	placeHolders.put("operationVersion", "[~]OPERATION_VERSION[~]");
    	placeHolders.put("adapterName", "[~]ADAPTER_NAME[~]");
    	placeHolders.put("adapterNameLowerCase", "[~]ADAPTER_NAME_LOWERCASE[~]");
    	placeHolders.put("operationNameLowerCase", "[~]OPERATION_NAME_LOWERCASE[~]");
    	
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
