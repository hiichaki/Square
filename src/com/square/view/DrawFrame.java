package com.square.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

public class DrawFrame extends JFrame {

	private double[] vect;

	public DrawFrame(double[] vector) {
		vect = vector;
		setSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void paint(Graphics g) {

		g.setColor(Color.BLACK);

//		g.drawLine(0, 0, 500, 500);
		
		Polygon p = new Polygon();
		
		for(int x = -170; x< 170; ++x) {
			p.addPoint(x + 200,  (int) ((int) vect[0]+vect[1]*x+Math.pow(vect[2] * x, 2)) );
		}
		g.drawPolygon(p);
	}
}