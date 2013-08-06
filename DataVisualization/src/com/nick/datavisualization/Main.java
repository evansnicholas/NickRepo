package com.nick.datavisualization;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 //PieChart demo = new PieChart("Comparison", "Which operating system are you using?");
		Dial demo = new Dial("Test");
         demo.pack();
         demo.setVisible(true);
         
         for (int i = 0; i < 100; i++){
        	 
        	 demo.incrementData();
        	 
         }

	}

}
