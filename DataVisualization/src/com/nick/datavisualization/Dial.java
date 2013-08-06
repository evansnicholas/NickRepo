package com.nick.datavisualization;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;

public class Dial extends JFrame {

	Integer data = 20;
	DefaultValueDataset defaultData = new DefaultValueDataset(data);
	
	public Dial (String applicationTitle){
		
		super(applicationTitle);
		setContentPane(createDial());
		
	}
	
	public JPanel createDial(){
		
		JPanel jPanel = new JPanel(new BorderLayout());
		
		//defaultData.setValue(30);
		
		DialPlot dialPlot = new DialPlot();
		dialPlot.setDataset(0, defaultData);
		dialPlot.setView(0.0D,0.0D,1.0D,1.0D);
		
		StandardDialFrame dialFrame = new StandardDialFrame();
		dialFrame.setBackgroundPaint(Color.black);
		dialFrame.setForegroundPaint(Color.white);
		dialPlot.setDialFrame(dialFrame);
		
		//DialValueIndicator indicator = new DialValueIndicator(0);
		//dialPlot.addLayer(indicator);
		
		StandardDialScale standardDialScale = new StandardDialScale(0.0D, 100.0D, -120.0D, -300.0D, 10.0D, 4);
	    //standardDialScale.setTickRadius(0.5D);
	    //standardDialScale2.setTickLabelOffset(0.15D);
	    //standardDialScale2.setTickLabelFont(new Font("Dialog", 0, 10));
	    //standardDialScale2.setMajorTickPaint(Color.red);
	    //standardDialScale2.setMinorTickPaint(Color.red);
	    dialPlot.addScale(0, standardDialScale);
	      
		DialPointer.Pin pin = new DialPointer.Pin(0);
		pin.setRadius(10.0D);
		
		//dialPlot.mapDatasetToScale(1, 1);
		
		dialPlot.addPointer(pin);
		JFreeChart dialChart = new JFreeChart(dialPlot);
		dialChart.setTitle("My First Dial");
		ChartPanel chartPanel = new ChartPanel(dialChart);
		
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

}
