package com.square.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.square.model.Integrator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollBar;
import javax.swing.JList;
import java.awt.List;
import java.awt.TextField;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4040271638401617542L;
	private JPanel contentPane;
	private Integer degree;
	private String sub = "₀₁₂₃₄₅₆₇₈₉";
	private String sup = "⁰¹²³⁴⁵⁶⁷⁸⁹";
	private JPanel resultPanel;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane polynomScrollPanel = new JScrollPane();
		polynomScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		polynomScrollPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		polynomScrollPanel.setBounds(10, 47, 257, 260);
		contentPane.add(polynomScrollPanel);
		JViewport polynomView = polynomScrollPanel.getViewport();
		JPanel polynomPanel = new JPanel();
		polynomPanel.setPreferredSize(new Dimension(polynomView.getWidth(), polynomScrollPanel.getHeight() - 10));
		polynomView.add(polynomPanel);

		resultPanel = new JPanel();
		resultPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		resultPanel.setBounds(278, 11, 433, 296);
		contentPane.add(resultPanel);
		resultPanel.setLayout(new GridLayout(12, 1, 25, 0));

		JLabel degreeLabel = new JLabel("Степінь:");
		degreeLabel.setBounds(25, 11, 75, 14);
		contentPane.add(degreeLabel);

		JSpinner degreeField = new JSpinner();
		degreeField.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		degreeField.setBounds(110, 11, 58, 20);
		contentPane.add(degreeField);

		Button integrateButton = new Button("Порахувати");

		integrateButton.addActionListener(e -> {
			Component[] components = polynomPanel.getComponents();
			JTextField[] masTextField = new JTextField[(int) degreeField.getValue()];
			int i = 0;
			for (Component tmp : components) {
				if (tmp.getClass().equals(JTextField.class)) {
					masTextField[i] = (JTextField) tmp;
					++i;
				}
			}
			
			double[] vector = new double[masTextField.length];
			
			for(int j = 0; j<masTextField.length; ++j) {
				vector[j] = Double.parseDouble(masTextField[j].getText());
			}
			doAllIntegrations(vector);
		});

		integrateButton.setBounds(62, 313, 152, 22);
		contentPane.add(integrateButton);

		degreeField.addChangeListener(e -> {

			try {
				JLabel tmpPolynomLabel = new JLabel();
				tmpPolynomLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				polynomPanel.removeAll();
				degree = (Integer) (degreeField.getValue());
				for (int i = 0; i < degree; ++i) {
					polynomPanel.add(new JTextField(5));
					if (i > 1) {
						if (i > 9) {
							tmpPolynomLabel = new JLabel("x " + sup.charAt(i / 10) + sup.charAt(i % 10));
							tmpPolynomLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
							polynomPanel.add(tmpPolynomLabel);
						} else {
							tmpPolynomLabel = new JLabel("x " + sup.charAt(i));
							tmpPolynomLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
							polynomPanel.add(tmpPolynomLabel);
						}

					} else if (i > 0) {
						tmpPolynomLabel = new JLabel("x ");
						tmpPolynomLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
						polynomPanel.add(tmpPolynomLabel);
					}
					if (i != degree - 1) {
						tmpPolynomLabel = new JLabel("+");
						tmpPolynomLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
						polynomPanel.add(tmpPolynomLabel);
					}

					polynomPanel.setPreferredSize(new Dimension(polynomView.getWidth(), ((i / 2) + 1) * 25));

				}
				repaint();
				revalidate();
			} catch (Exception ex) {
				System.out.println("fail");
				ex.printStackTrace(System.out);
			}
		});

	}

	private void doAllIntegrations(double[] vector) {
		
		Integrator integ = new Integrator(vector);
		resultPanel.removeAll();
		double[] v = integ.getResults();
		double[] d = integ.getDuration();

		for (int i = 0; i < v.length; ++i) {
			JLabel tmpLabel = null;
			switch (i) {
			case 0:
				tmpLabel = new JLabel("Cімпсон: ");
				tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				break;
			case 1:
				tmpLabel = new JLabel("Трапеція: ");
				tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				break;
			case 2:
				tmpLabel = new JLabel("Ромберг: ");
				tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				break;
			}

			tmpLabel.setText(tmpLabel.getText() + v[i]);
			resultPanel.add(tmpLabel);
			tmpLabel = new JLabel("Час виконання: " + d[i] + "ms");
			tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
			resultPanel.add(tmpLabel);
			JSeparator separator = new JSeparator();
			separator.setSize(new Dimension(resultPanel.getWidth(), 3));
			resultPanel.add(separator);

		}

		JLabel tmpLabel;
		tmpLabel = new JLabel("Сімпсон - Трапеція = " + Math.abs(((v[0] - v[1]) * 100) / v[0]) + " %");
		tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		resultPanel.add(tmpLabel);
		tmpLabel = new JLabel("Сімпсон - Ромберг = " + Math.abs(((v[0] - v[2]) * 100) / v[0]) + " %");
		tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		resultPanel.add(tmpLabel);
		tmpLabel = new JLabel("Трапеція - Ромберг = " + Math.abs(((v[1] - v[2]) * 100) / v[1]) + " %");
		tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		resultPanel.add(tmpLabel);
		resultPanel.repaint();
		resultPanel.revalidate();
	}
}
