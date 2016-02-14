package com.cdesign.planets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.JApplet;
import javax.swing.JPanel;

public class ShowCosmos2 extends JApplet {
	//private Random rand;
	private int scrWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int scrHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	//Display drawing;
	Planet[] planets = {
			new Planet("Venera", 1, 5.0, 50.0, 2.0, 0, Color.GREEN),
			new Planet("Mercury", 1, 3.0, 75.0, 3.0, 0, Color.ORANGE),
			new Planet("Earth", 1, 6.0, 100.0, 1.5, 1, Color.BLUE),
			new Planet("Jupiter", 1, 12.0, 150.0, 1.0, 0, Color.RED)
	};
	private boolean running = false;
	private JPanel frm;
	//private Timer timer;
	//private int msecPFr = 40;
	private Thread runner;
	
	private int centerX = 0;
	private int centerY = 0;
	//private int angle = 0;
	
	protected void drawFrame(Graphics g) {
		//Sun
		g.setColor(Color.YELLOW);
		g.fillOval(scrWidth/2 - 25, scrHeight/2 - 25, 50, 50);
		
		for (int i = 0; i < planets.length; i++)
		{
			g.setColor(planets[i].getColor());
			int newX = (int)(centerX + Math.cos(planets[i].getAngle()*Math.PI/180)*planets[i].getRadiusOrbit());
			
			int newY = (int)(centerY - Math.sin(planets[i].getAngle()*Math.PI/180*(planets[i].getDirection()==0?1:-1))*planets[i].getRadiusOrbit());
			
			g.fillOval((int)(newX-planets[i].getRadius()),
					(int)(newY-planets[i].getRadius()), 
					(int)planets[i].getRadius()*2, (int)planets[i].getRadius()*2);
			int angle = (int)(planets[i].getAngle() + planets[i].getVelocity());
			if (angle >= 360) angle = 0;
			planets[i].setAngle(angle);
		}
		
	}
	
	@Override
	public void init() {
		setSize(scrWidth,scrHeight);
		setBackground(Color.BLACK);
		centerX = scrWidth / 2;
		centerY = scrHeight / 2;
		frm = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				scrWidth = getSize().width;
				scrHeight = getSize().height;
				centerX = scrWidth / 2;
				centerY = scrHeight / 2;
				drawFrame(g);
				
			}
		};
		setContentPane(frm);
		//drawing = new Display();
		//drawing.setBackground(Color.black);
		//Container container = getContentPane();
		//Graphics g = getGraphics();
		
		//g.setColor(Color.YELLOW);
		//g.fillOval(scrWidth/2, scrHeight/2, 50, 50);
		//setContentPane(drawing);
	}
	/*public void paintComponents(Graphics g)
	{
		super.paintComponents(g);
		g.setColor(Color.YELLOW);
		g.fillOval(scrWidth/2, scrHeight/2, 50, 50);
	}*/
	
	public void onResize()
	{
		scrWidth = getSize().width;
		scrHeight = getSize().height;
		centerX = scrWidth / 2;
		centerY = scrHeight / 2;
		//drawing.repaint();
	}
	/*class Display extends JPanel {
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			//Sun
			g.setColor(Color.YELLOW);
			g.fillOval(scrWidth/2-25, scrHeight/2-25, 50, 50);
			
			g.fillOval((int)(scrWidth/2),
					(int)(scrHeight/2), (int)planets[0].getRadius(), (int)planets[0].getRadius());
		}
	}*/
	
	public void actionPerformed(ActionEvent evt) {
		//frmNum++;
		//if (frmCount > 0 && (frmNum >= frmCount)) frmNum = 0;
		//elapTime = oldElapTime + (System.currentTimeMillis() - startTime);
		frm.repaint();
	}
	
	public void startAnimation() {
		/*if (timer == null) {
			timer = new Timer(msecPFr, this);
			timer.start();
		}
		else timer.restart();
		if (!running)
		{*/
			runner = new Thread() {
				@Override
				public void run() {
					try {
						while (!Thread.interrupted()) {
							repaint();
							//drawFrame(frm);
							Thread.sleep(50);
						}
					} catch (Exception e) {
						
					}
				}
			};
			runner.start();
			running = true;
		//}
	}
	
	public void stopAnimation() {
		
	}
	
	@Override
	public void start() {
		startAnimation();
	}
	
	@Override
	public void stop() {
		//stopAnimation();
		if (running)
		{
			runner.interrupt();
			runner = null;
		}
	}
	
	private Color getRandomColor()
	{
		Random generator = new Random();
		int r = generator.nextInt(256);
		int g = generator.nextInt(256);
		int b = generator.nextInt(256);
		return new Color(r, g, b);
	}
}
