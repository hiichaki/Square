package com.square.controller;

import com.square.model.Integrator;

public class App {

	public static void main(String[] args) {
		
//		try {
//			MainFrame frame = new MainFrame();
//			frame.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		double[] vector = new double[3];
		vector[0] = 1;
		vector[1] = 2;
		vector[2] = 3;
//		vector[3] = 0.2;
//		vector[4] = 2;
		
		Integrator integ = new Integrator(vector);
				
		double[] v = integ.getResults();
	
		
		for(int i = 0; i < v.length; ++i) {
			for(int j = i; j < v.length; ++j) {
				if(i != j) {
					switch(i) {
						case 0: System.out.print("Simpson - "); break;
						case 1: System.out.print("Trapezoid - "); break;
						case 2: System.out.print("Romberg - "); break;
					}
					switch(j) {
						case 0: System.out.print("Simpson = "); break;
						case 1: System.out.print("Trapezoid = "); break;
						case 2: System.out.print("Romberg = "); break;
					}
					
					System.out.print(Math.abs(((v[i]-v[j])*100)/v[i]) + " %");
					System.out.println();
				}
				
			}
		}
		
	}

}
