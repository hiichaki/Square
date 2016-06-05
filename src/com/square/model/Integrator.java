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

	public Integrator(double[] vector, int eval, int from, int to) {
		PolynomialFunction f = new PolynomialFunction(vector);
		
		uf = (UnivariateFunction) new PolynomialFunction(vector);
		
		System.out.println(f.toString());
		
		long startTime = System.nanoTime();
		simpsonIntegrate(eval, from, to);
		long endTime = System.nanoTime();

		duration[0] = (endTime - startTime) / 10000;

		startTime = System.nanoTime();
		
		trapezoidIntegrate(eval, from, to);
		endTime = System.nanoTime();

		duration[1] = (endTime - startTime) / 10000;

		startTime = System.nanoTime();

		rombergIntegrate(eval, from, to);
		endTime = System.nanoTime();

		duration[2] = (endTime - startTime) / 10000;

	}

	private void simpsonIntegrate(int eval, int from, int to) {
		simpson = new SimpsonIntegrator();

		double integral = simpson.integrate(eval, uf, from, to);

		results[0] = integral;

	}

	private void trapezoidIntegrate(int eval, int from, int to) {
		trapeze = new TrapezoidIntegrator();

		double integral = trapeze.integrate(eval, uf, from, to);

		results[1] = integral;

	}

	private void rombergIntegrate(int eval, int from, int to) {
		romberg = new RombergIntegrator();

		double integral = romberg.integrate(eval, uf, from, to);

		results[2] = integral;

	}

	public double[] getResults() {
		return results;

	}

	public double[] getDuration() {
		return duration;

	}

}
