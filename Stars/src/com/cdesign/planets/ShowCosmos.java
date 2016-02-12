package com.cdesign.planets;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ShowCosmos {
	public static void main(String[] args)
	{
		JFrame frame = new PlanetsFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class PlanetsFrame extends JFrame
{
	
	private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	/*Planet[] planets = {
			new Planet("Venera", 1, 5.0, 50.0, 2.0, Color.GREEN),
			new Planet("Mercury", 1, 3.0, 75.0, 1.0, Color.ORANGE),
			new Planet("Earth", 1, 6.0, 100.0, 2.0, Color.BLUE),
			new Planet("Jupiter", 1, 12.0, 150.0, 1.0, Color.RED)
	};*/
	//private boolean running = false;
	//private JPanel frm;
	//private Timer timer;
	//private int msecPFr = 40;
	//private Thread runner;
	
	//private int centerX = 0;
	//private int centerY = 0;
	
	//private PlanetsCanvas canvas;
	
	public PlanetsFrame()
	{
		setSize(width, height);
		setTitle("Planets");
		
		setContentPane(new PlanetsCanvas(width, height));
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.BLACK);
		//canvas = new PlanetsCanvas(width, height);
		//canvas.setBackground(Color.BLACK);
		//contentPane.add(canvas, BorderLayout.CENTER);
		
		/*timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
			}
		});*/
	}
}

class PlanetsCanvas extends JPanel
{
	//private ArrayList<Planet> planets = new ArrayList<Planet>();
	private int width, height;
	private int centerX = 0;
	private int centerY = 0;
	private Thread runner;
	private boolean running = false;
	Planet[] planets = {
			new Planet("Venera", 1, 5.0, 50.0, 1, Color.GREEN),
			new Planet("Mercury", 1, 3.0, 75.0, 1.5, Color.ORANGE),
			new Planet("Earth", 1, 6.0, 100.0, 2, Color.BLUE),
			new Planet("Jupiter", 1, 12.0, 150.0, 1, Color.RED)
	};
	public PlanetsCanvas(int w, int h)
	{
		width = w;
		height = h;
		centerX = (int)(w/2);
		centerY = (int)(h/2);
		Timer timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				repaint();
			}
		});
		//startAnimation();
	}
	
	/*public void add(Planet b)
	{
		planets.add(b);
	}*/
	protected void drawFrame(Graphics g)
	{
		//Sun
				g.setColor(Color.YELLOW);
				g.fillOval(width/2 - 25, height/2 - 25, 50, 50);
				for (int i = 0; i < planets.length; i++)
				{
					Planet p = planets[i];
					//b.draw(g2);
					g.setColor(p.getColor());
					int newX = (int)(centerX + Math.cos(p.getAngle())*p.getRadiusOrbit());
					int newY = (int)(centerY - Math.sin(p.getAngle())*p.getRadiusOrbit());
					g.fillOval((int)(newX-p.getRadius()),
							(int)(newY-p.getRadius()), 
							(int)p.getRadius()*2, (int)p.getRadius()*2);
					//int angle = (int)(p.getAngle() + p.getVelocity());
					//if (angle >= 360) angle = 0;
					//p.setAngle(angle);
				}
	}
	
	public void paintComponent(Graphics g)
	{
		//super.paintComponent(g);
		//Graphics2D g2 = (Graphics2D)g;
		/*Timer timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				drawFrame(getGraphics());
			}
		});*/
		drawFrame(g);
		//startAnimation();
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
				public void run() {
					try {
						while (!Thread.interrupted()) {
							//repaint();
							drawFrame(getGraphics());
							for(int i=0; i<planets.length; i++)
							{
								Planet p = planets[i];
								int angle = (int)(p.getAngle() + p.getVelocity());
								if (angle >= 360) angle = 0;
								p.setAngle(angle);
							}
							Thread.sleep(1000);
						}
					} catch (Exception e) {
						
					}
				}
			};
			runner.start();
			running = true;
		//}
	}
}