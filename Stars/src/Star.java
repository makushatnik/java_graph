import java.awt.Point;

public class Star {
	private Point point;
	private int secs;
	private boolean alive;
	
	public Star(int x, int y, int secs) {
		this.point = new Point(x, y);
		this.secs = secs;
		this.alive = true;
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

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void destroy() {
		point = null;
	}
}
