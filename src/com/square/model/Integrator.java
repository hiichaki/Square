package com.square.model;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

public class Integrator {

	private SimpsonIntegrator simpson;
	private RombergIntegrator romberg;
	private TrapezoidIntegrator trapeze;
	
	private double[] results = new double[3];
	
	private UnivariateFunction uf;
	
	
	public Integrator(double[] vector) {
		PolynomialFunction f = new PolynomialFunction(vector);
		uf = (UnivariateFunction) new PolynomialFunction(vector);
		

		System.out.println("To String " + uf.toString());
		System.out.println("Degree: " + f.degree() + "\n");
		
		long startTime = System.nanoTime();
		simpsonIntegrate();
		long endTime = System.nanoTime();

		long duration = (endTime - startTime); 
		System.out.println("Simpson: " + duration/1000 + "ms\n");
		
		startTime = System.nanoTime();;
		trapezoidIntegrate();
		endTime = System.nanoTime();
		
		duration = (endTime - startTime); 
		
		System.out.println("Trapezoid:" + duration/1000 + "ms\n");
		
		startTime = System.nanoTime();;
		rombergIntegrate();
		endTime = System.nanoTime();
		
		duration = (endTime - startTime); 
		
		System.out.println("Romberg:" + duration/1000 + "ms\n");
			
	}
	
	private void simpsonIntegrate() {
		simpson = new SimpsonIntegrator();
		
		double integral = simpson.integrate(100000, uf, 2,3);
		
		results[0] = integral;
		
		System.out.println("Simpson integral : " + integral);

	}
	
	private void trapezoidIntegrate() {
		trapeze = new TrapezoidIntegrator();
		
		double integral = trapeze.integrate(100000, uf, 2,3);
		
		results[1] = integral;
		
		System.out.println("Trapezoid integral : " + integral);
	}
	
	private void rombergIntegrate() {
		romberg = new RombergIntegrator();

		double integral = romberg.integrate(100000, uf, 2,3);
		
		results[2] = integral;
		
		System.out.println("Romberg integral : " + integral);
	}
	
	public double[] getResults(){
		return results;
	}
	
}
