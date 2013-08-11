package com.nick.datavisualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;

public class Dial extends JFrame implements IDataDisplayer, ActionListener{

	private Integer data = 20;
	private DefaultValueDataset defaultData = new DefaultValueDataset(data);
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem exit;
	
	public Dial (String applicationTitle){
		
		super(applicationTitle);
		setContentPane(createDial());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create File JMenu
		fileMenu = new JMenu("File");
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		fileMenu.add(exit);
		
		//Make menu bar
		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		
		setJMenuBar(menuBar);
		
		
		
		
	}
	
	public JPanel createDial(){
		
		JPanel jPanel = new JPanel(new BorderLayout());
		
		//defaultData.setValue(30);
		
		DialPlot dialPlot = new DialPlot();
		dialPlot.setDataset(0, defaultData);
		dialPlot.setView(0.0D,0.0D,1.0D,1.0D);
		
		DialBackground dialBackground = new DialBackground();
		dialBackground.setPaint(Color.white);
		
		dialPlot.setBackground(dialBackground);
		
		StandardDialFrame dialFrame = new StandardDialFrame();
		//System.out.println("Radius" + dialFrame.getRadius());
		//dialFrame.setBackgroundPaint(Color.black);
		//dialFrame.setForegroundPaint(Color.white);
		dialPlot.setDialFrame(dialFrame);
		
		//DialValueIndicator indicator = new DialValueIndicator(0);
		//dialPlot.addLayer(indicator);
		
		StandardDialScale standardDialScale = new StandardDialScale(0.0D, 100.0D, -120.0D, -300.0D, 10.0D, 4);
	    standardDialScale.setTickRadius(0.9D);
	    standardDialScale.setTickLabelOffset(0.15D);
	    //standardDialScale2.setTickLabelFont(new Font("Dialog", 0, 10));
	    standardDialScale.setTickLabelPaint(Color.black);
	    //standardDialScale2.setMajorTickPaint(Color.red);
	    //standardDialScale2.setMinorTickPaint(Color.red);
	    dialPlot.addScale(0, standardDialScale);
	      
		DialPointer.Pin pin = new DialPointer.Pin(0);
		pin.setPaint(Color.black);
		//pin.setRadius(5.0D);
		
		dialPlot.mapDatasetToScale(1, 1);
		
		dialPlot.addPointer(pin);
		JFreeChart dialChart = new JFreeChart(dialPlot);
		dialChart.setTitle("Velocity");
		ChartPanel chartPanel = new ChartPanel(dialChart);
		chartPanel.setPreferredSize(new Dimension(400, 400));
		
		jPanel.add(chartPanel);
		
		return jPanel;
		
		
	}

	public void incrementData(){
		
		data = data+1;
		defaultData.setValue(data);
		try{
			Thread.sleep(500);
		}catch(Exception e){
			
		}
	}
	
	public void setDataValue(int data){
		
		this.data = data;
		defaultData.setValue(data);
		
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		
			if (event.getSource() == exit){
						
						System.exit(0);
						
			}
		
	}
}
