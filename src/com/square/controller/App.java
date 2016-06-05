package com.square.controller;

import javax.swing.JOptionPane;

import com.square.view.MainFrame;

import groovy.lang.GroovyShell;

public class App {

	public static GroovyShell createMathShell() {
        GroovyShell shell = new GroovyShell();
        
        shell.evaluate("" +
	        "cos = {double x -> Math.cos(x)}\n" +   // predefine functions as lambda
	        "sin = {double x -> Math.sin(x)}\n" +   // expressions
	        "pi = Math.PI\n" +                      // define pi
	        "sqrt = {double x -> Math.sqrt(x)\n}" 
               
        );
        
        shell.evaluate("" +
	    	"tan = {double x -> Math.tan(x)}\n" +
	    	"atan = {double x -> Math.atan(x)}\n" +
	        "acos = {double x -> Math.acos(x)}\n" +
	        "asin = {double x -> Math.asin(x)}\n" 
	        
        );
        
        shell.evaluate("" +
			"cbrt = {double x -> Math.cbrt(x)}\n" +
			"exp = {double x -> Math.exp(x)}\n" +
			"log = {double x -> Math.log(x)}\n" +
			"log10 = {double x -> Math.log10(x)}\n" 
			
        );
        
        return shell;
        
    }
	
	public static int getCountOfDigits(long number) {
		return (number == 0) ? 1 : (int) Math.ceil(Math.log10(Math.abs(number) + 0.5));
		
	}
	
	public static int[] getDigits(int number) {
		
		int[] digits = new int[getCountOfDigits(number)];
		for(int i = 0; number > 0; ++i) {
			digits[i] = number % 10;
			number = number / 10;
		}
		
		return digits;
		
	}

	public static void main(String[] args) {
		
		try {
			MainFrame frame = new MainFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Программа не може запуститись", "Помилка", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
