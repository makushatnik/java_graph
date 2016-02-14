package com.cdesign.planets;
import java.awt.Color;
import java.awt.Point;

public class Star {
	private Point point;
	private int secs;
	private Color color;
	private long startTime;
	private boolean alive;
	
	public Star(int x, int y, int secs, Color color) {
		this.point = new Point(x, y);
		this.secs = secs;
		this.color = color;
		this.alive = true;
		this.startTime = System.currentTimeMillis();
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getSecs() {
		return secs;
	}

	public void setSecs(int secs) {
		this.secs = secs;
	}
	
	public Color getColor() {
		return color;
	}

	public boolean isAlive() {
		long time = System.currentTimeMillis();
		//if(time > (startTime + secs * 1000)) {(time-startTime)/1000
		if(((time-startTime)/1000) > secs) {
			setAlive(false);
			return false;
		}
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void destroy() {
		point = null;
	}
}
