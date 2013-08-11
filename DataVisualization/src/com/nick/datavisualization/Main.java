package com.nick.datavisualization;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 //PieChart demo = new PieChart("Comparison", "Which operating system are you using?");
		 Dial dial = new Dial("Data Visualizer");
         dial.pack();
         dial.setVisible(true);
         
         /*for (int i = 0; i < 100; i++){
        	 
        	 dial.incrementData();
        	 
         }*/
         
         while (true){
        	 
        	 int randomNumber = (int) Math.round(Math.random()*100);
        	 dial.setDataValue(randomNumber);
        	 //System.out.print(randomNumber);
         }

	}

}
