package com.square.controller;

import com.square.view.MainFrame;

public class App {

	public static void main(String[] args) {
		
		try {
			MainFrame frame = new MainFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
