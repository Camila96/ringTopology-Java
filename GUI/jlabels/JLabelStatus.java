package jlabels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class JLabelStatus extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Color COLOR_ORANGE = new Color(255, 132, 25);
	public static final Color COLOR_RED = new Color(214, 7, 7);
	public static final Color COLOR_GREEN = new Color(23, 241, 74);
	public static final Color COLOR_BACKGROUND = new Color(238, 238, 238);

	private Color colorJLabel;

	public JLabelStatus() {
		
		this.colorJLabel = JLabelStatus.COLOR_RED;
	}

	@Override
	public void paint(Graphics g) {

		g.setColor (this.getColorJLabel());
		g.fillOval(5, ((this.getHeight()/2) - 5), 10, 10);
		super.paint(g);
	}

	public Color getColorJLabel() {
		return colorJLabel;
	}

	public void setColorJLabel(Color color) {
		this.colorJLabel = color;
	}
}
