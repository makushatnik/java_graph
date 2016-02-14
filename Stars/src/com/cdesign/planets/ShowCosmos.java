package com.cdesign.planets;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShowCosmos {
    public static void main(String[] args) {
        JFrame frame = new PlanetsFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class PlanetsFrame extends JFrame {

    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    public PlanetsFrame() {
        setSize(width, height);
        setTitle("Planets");

        PlanetsCanvas canvas = new PlanetsCanvas(width, height); 
        setContentPane(canvas);
        Container contentPane = getContentPane();
        canvas.setPanel(contentPane);
        //contentPane.setBackground(Color.BLACK);
        //contentPane.setBackground(Color.BLACK);
    }
}

class PlanetsCanvas extends JPanel {
    private static final int FRAMES_PER_SECOND = 20;
    private static final int FRAME_DURATION = 1000 / FRAMES_PER_SECOND;

    private static final int STARS_MIN  =  50;
    private static final int STARS_MAX  = 300;
    private static final int STARS_SIZE =   1;
    
    private int width, height;
    private int centerX = 0;
    private int centerY = 0;
    private Thread runner;
    private boolean running = false;
    private Container panel;
    private Random rand = new Random();
    private Planet[] planets = {
    		new Planet("Venera" , 1,  5.0,  50.0,   3, 0, Color.GREEN),
            new Planet("Mercury", 2,  3.0,  75.0, 	4, 0, Color.ORANGE),
            new Planet("Earth"  , 3,  6.0, 100.0,   2, 1, Color.BLUE),
            new Planet("Jupiter", 4, 12.0, 150.0,   1, 0, Color.RED),
            new Planet("Saturn",  5,  4.0, 180.0, 2.5, 0, Color.MAGENTA),
            new Planet("Mars",    6,  3.5, 250.0, 3.5, 0, Color.CYAN)
    };
    private Color[] colors = {
    	Color.WHITE,
    	Color.LIGHT_GRAY,
    	Color.GRAY,
    	Color.DARK_GRAY,
    	Color.YELLOW,
    	Color.ORANGE,
    	Color.RED,
    	Color.PINK,
    	Color.MAGENTA,
    	Color.GREEN,
    	Color.BLUE,
    	Color.CYAN
    };
    private Star[] stars = new Star[STARS_MAX];
    private int nStar = 0;

    public PlanetsCanvas(int w, int h) {
        width = w;
        height = h;
        centerX = w / 2;
        centerY = h / 2;
        //Stars
    	int starsCnt = STARS_MIN + rand.nextInt(STARS_MAX - STARS_MIN);
        for(int i=0; i<starsCnt; i++) {
        	int rCol = rand.nextInt(colors.length-1);
        	int rS   = 3 + rand.nextInt(5);
        	int rX   = rand.nextInt(width);
        	int rY   = rand.nextInt(height);
        	Star star = new Star(rX, rY, rS, colors[rCol]);
        	stars[nStar] = star;
        	nStar++;
        	//g.fillRect(stars[i].getPoint().x, stars[i].getPoint().y, 1, 1);
        }
        
        startAnimation();
    }
    
    public void setPanel(Container panel) {
    	this.panel = panel;
    }

    protected void drawFrame(Graphics g) {
    	panel.setBackground(Color.BLACK);
    	//Stars
    	int nAlive = 0;
    	//alived
    	for(int i=0; i<nStar; i++) {
    		if(!stars[i].isAlive()) {
    			//передвигаем следующие к началу
    			for(int j=i; j<nStar-1; j++) {
    				stars[j] = stars[j+1];
    			}
    			nStar--;
    		}
    	}
    	nStar = nAlive;
    	//new
    	/*if(nStar > STARS_MAX) {
    		for(int i=STARS_MAX; i<nStar; i++) {
    			stars[i] = null;
    			stars[i].destroy();
    		}
    		nStar = STARS_MAX;
    	}
    	else*/
    	
    	if(nStar < STARS_MIN) {
    		int newCnt = STARS_MAX - STARS_MIN;
    		newCnt = (STARS_MIN - nStar) + rand.nextInt(newCnt);
    		for(int i=0; i<newCnt; i++) {
    			int rCol = 0;
    			rCol = rand.nextInt(colors.length-1);
    			int rS   = 3 + rand.nextInt(5);
            	int rX   = rand.nextInt(width);
            	int rY   = rand.nextInt(height);
            	Star star = new Star(rX, rY, rS, colors[rCol]);
            	//Star star = new Star(rX, rY, rS, getRandomColor());
            	stars[nStar] = star;
            	nStar++;
    		}
    		
    	}
    	//Paint stars
    	for(int i=0; i<nStar; i++) {
    		g.setColor(stars[i].getColor());
    		//int tekx = stars[i].getPoint().x;
    		//int teky = stars[i].getPoint().y;
    		g.fillRect(stars[i].getPoint().x, stars[i].getPoint().y, STARS_SIZE + 1, STARS_SIZE + 1);
    		//g.drawLine(tekx, teky, tekx, teky);
    	}
    	// Sun
        g.setColor(Color.YELLOW);
        g.fillOval(width / 2 - 25, height / 2 - 25, 50, 50);
        for (int i = 0; i < planets.length; i++) {
            Planet p = planets[i];
            g.setColor(p.getColor());
            int newX = (int) (centerX + Math.cos(p.getAngle()*Math.PI/180)
                    * p.getRadiusOrbit());
            int newY = (int) (centerY - Math.sin(p.getAngle()*Math.PI/180*(
            		planets[i].getDirection()==0?1:-1)) * p.getRadiusOrbit());
            g.fillOval((int) (newX - p.getRadius()),
                    (int) (newY - p.getRadius()), (int) p.getRadius() * 2,
                    (int) p.getRadius() * 2);
            int angle = (int)(p.getAngle() + p.getVelocity());
            if (angle >= 360) angle = 0;
            p.setAngle(angle);
        }
    }

    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFrame(g);
    }

    public void startAnimation() {
        runner = new Thread() {
            @Override
			public void run() {
                try {
                    while (!Thread.interrupted()) {
                        repaint();
                        
                        Thread.sleep(FRAME_DURATION);
                    }
                } catch (Exception e) {

                }
            }
        };
        runner.start();
        running = true;
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