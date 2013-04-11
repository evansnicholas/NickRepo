import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
 
/*
 * FileChooserDemo.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class BWProjectGenerator extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton, startButton;
    JTextArea log;
    JFileChooser fc;
    String adapterString = "Make an adapter";
    String processComponentString = "Make a process component";
    File selectedFileToBeEnriched;
 
    public BWProjectGenerator() {
        super(new BorderLayout());
 
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        logScrollPane.setPreferredSize(new Dimension(250, 155));
        logScrollPane.setMinimumSize(new Dimension(10, 10));
        
        //Create the radio buttons
        
        JRadioButton adapterButton = new JRadioButton(adapterString);
        adapterButton.setMnemonic(KeyEvent.VK_A);
        adapterButton.setActionCommand(adapterString);
        //adapterButton.setSelected(true);
        
        JRadioButton processComponentButton = new JRadioButton(processComponentString);
        processComponentButton.setMnemonic(KeyEvent.VK_P);
        processComponentButton.setActionCommand(processComponentString);
        
        //Group the buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(adapterButton);
        buttonGroup.add(processComponentButton);
        
        //Register a listener for the buttons
        adapterButton.addActionListener(this);
        processComponentButton.addActionListener(this);
        
        //Create a JPanel and add the buttons
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(adapterButton);
        radioPanel.add(processComponentButton);
        
        //Create a file chooser
        fc = new JFileChooser();
 
        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
 
        //Create the open button.
        openButton = new JButton("Select a project file.");
        openButton.addActionListener(this);
        
        //Create the Start button.
        startButton = new JButton("Create my project.");
        startButton.addActionListener(this);
 
        //Create the save button.
        //saveButton = new JButton("Save a File...");
        //saveButton.addActionListener(this);
 
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(startButton);
 
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
        add(radioPanel, BorderLayout.LINE_START);
    }
 
    public void actionPerformed(ActionEvent e) {
 
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(BWProjectGenerator.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                log.append("Selected: " + file.getAbsolutePath() + "." + newline);
                selectedFileToBeEnriched = file;
                
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
 
        //Handle save button action.
        } else if (e.getSource() == saveButton) {
            int returnVal = fc.showSaveDialog(BWProjectGenerator.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
                log.append("Saving: " + file.getName() + "." + newline);
            } else {
                log.append("Save command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
        //Handle the start button.
        else if (e.getSource() == startButton) {
        	
        	ProjectBuilder project = new ProjectBuilder();
            project.enrich(selectedFileToBeEnriched.getAbsolutePath());
        	
        	//BWProjectFolderManager bwProject = new BWProjectFolderManager("c:\\Users\\iggo\\Desktop\\BWTest");
            //bwProject.generateFolderStructureForOperation("BWTestOperation");
        }
        
        //Do something with the radio buttons
        if (e.getActionCommand().contentEquals(adapterString)){
        	log.append("You want to make an adapter." + newline);
        	log.setCaretPosition(log.getDocument().getLength());
        }
        
        if (e.getActionCommand().contentEquals(processComponentString)){
        	log.append("You want to make a process component." + newline);
        	log.setCaretPosition(log.getDocument().getLength());
        }
        
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = BWProjectGenerator.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new BWProjectGenerator());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
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