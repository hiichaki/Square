package com.square.controller;

import javax.swing.JOptionPane;

import com.square.view.MainFrame;

public class App {

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
