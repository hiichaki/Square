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
	private double[] duration = new double[3];
	
	private UnivariateFunction uf;
	
	public Integrator(double[] vector) {
		PolynomialFunction f = new PolynomialFunction(vector);
		uf = (UnivariateFunction) new PolynomialFunction(vector);
		
		System.out.println("To String " + uf.toString());
		System.out.println("Degree: " + f.degree() + "\n");
		
		long startTime = System.nanoTime();
		simpsonIntegrate();
		long endTime = System.nanoTime();

		duration[0] = (endTime - startTime)/10000; 
	
		startTime = System.nanoTime();;
		trapezoidIntegrate();
		endTime = System.nanoTime();
		
		duration[1] = (endTime - startTime)/10000; 
		
		startTime = System.nanoTime();;
		rombergIntegrate();
		endTime = System.nanoTime();
		
		duration[2] = (endTime - startTime)/10000; 
			
	}
	
	private void simpsonIntegrate() {
		simpson = new SimpsonIntegrator();
		
		double integral = simpson.integrate(100000, uf, 2,3);
		
		results[0] = integral;

	}
	
	private void trapezoidIntegrate() {
		trapeze = new TrapezoidIntegrator();
		
		double integral = trapeze.integrate(100000, uf, 2,3);
		
		results[1] = integral;

	}
	
	private void rombergIntegrate() {
		romberg = new RombergIntegrator();

		double integral = romberg.integrate(100000, uf, 2,3);
		
		results[2] = integral;

	}
	
	public double[] getResults() {
		return results;
		
	}
	
	public double[] getDuration() {
		return duration;
		
	}
	
}
