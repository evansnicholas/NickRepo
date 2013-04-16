package UnitTests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextArea;

import nl.ziggo.icc.tooling.codegenerator.tooling.codegenerator.Utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SearchAndReplaceTest {
	
	JTextArea log = new JTextArea();

	Utilities utilities = new Utilities(log);
	
	ArrayList<String[]> termsToBeReplaced = new ArrayList<String[]>();
	
	String[] pairOfTerms = {"**COMPONENT_NAME**", "adpCramer"};
	
	
	@Test
	public final void testSearchReplaceAndWriteToTempFolder() {
		
		termsToBeReplaced.add(pairOfTerms);
		
		utilities.searchReplaceAndWriteToTempFolder(termsToBeReplaced, new File("C:\\Users\\iggo\\Desktop\\Test"), new File("C:\\Users\\iggo\\Desktop\\Test\\Temp"));
		
		System.out.print("Is this working?");
		
		//fail("Not yet implemented"); // TODO
	}

}
