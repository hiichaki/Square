package com.square.view;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.square.controller.App;
import com.square.model.Integrator;

import groovy.lang.GroovyShell;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4040271638401617542L;
	private JPanel contentPane;
	private String sup = "⁰¹²³⁴⁵⁶⁷⁸⁹";
	private JPanel resultPanel;
	private double[] vector;
	private JTextField tmpTextField;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}

		JScrollPane polynomScrollPanel = new JScrollPane();
		polynomScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		polynomScrollPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		polynomScrollPanel.setBounds(24, 102, 289, 260);
		contentPane.add(polynomScrollPanel);
		JViewport polynomView = polynomScrollPanel.getViewport();
		JPanel polynomPanel = new JPanel();
		polynomPanel.setPreferredSize(new Dimension(polynomView.getWidth(), polynomScrollPanel.getHeight() - 10));
		polynomView.add(polynomPanel);
		polynomPanel.add(new JTextField(5));

		resultPanel = new JPanel();
		resultPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		resultPanel.setBounds(348, 14, 433, 348);
		contentPane.add(resultPanel);
		resultPanel.setLayout(new GridLayout(12, 1, 25, 0));

		JLabel degreeLabel = new JLabel("Степінь:");
		degreeLabel.setBounds(24, 14, 101, 14);
		contentPane.add(degreeLabel);

		JSpinner degreeSpinnerField = new JSpinner();
		degreeSpinnerField.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
		degreeSpinnerField.setBounds(117, 11, 58, 20);
		contentPane.add(degreeSpinnerField);

		createPolynomFields(2, polynomPanel, polynomView);

		JSpinner fromSpinner = new JSpinner();
		fromSpinner.setModel(new SpinnerNumberModel(new Integer(-1), null, 0, new Integer(1)));
		fromSpinner.setBounds(117, 40, 58, 20);
		contentPane.add(fromSpinner);

		JSpinner toSpinner = new JSpinner();
		toSpinner.setModel(new SpinnerNumberModel(new Integer(1), 0, null, new Integer(1)));
		toSpinner.setBounds(185, 40, 58, 20);
		contentPane.add(toSpinner);

		JSpinner evalSpinner = new JSpinner();
		evalSpinner.setModel(new SpinnerNumberModel(new Integer(100000), new Integer(2100), null, new Integer(1)));
		evalSpinner.setBounds(117, 71, 126, 20);
		contentPane.add(evalSpinner);

		Button integrateButton = new Button("Порахувати");

		integrateButton.addActionListener(e -> {
			Component[] components = polynomPanel.getComponents();
			JTextField[] masTextField = new JTextField[(int) degreeSpinnerField.getValue() + 1];
			int i = 0;
			for (Component tmp : components) {
				if (tmp.getClass().equals(JTextField.class)) {
					masTextField[i] = (JTextField) tmp;
					++i;
				}
			}

			vector = new double[masTextField.length];

			GroovyShell shell = App.createMathShell();

			for (int j = 0; j < masTextField.length; ++j) {

				try {
					Object tmpObject = new Object();
					tmpObject = shell.evaluate(masTextField[j].getText());

					vector[j] = new Double(tmpObject.toString());

					// Double.parseDouble(masTextField[j].getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Невірно задані значення", "Помилка",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			int from = (int) fromSpinner.getValue();
			int to = (int) toSpinner.getValue();
			int eval = (int) evalSpinner.getValue();

			doAllIntegrations(vector, eval, from, to);

		});

		integrateButton.setBounds(71, 382, 152, 22);
		contentPane.add(integrateButton);

		fromSpinner.addChangeListener(e -> {

			toSpinner.setModel(new SpinnerNumberModel(new Integer((Integer) toSpinner.getValue()),
					(Integer) fromSpinner.getValue() + 1, null, 1.0));

		});

		toSpinner.addChangeListener(e -> {

			fromSpinner.setModel(new SpinnerNumberModel(new Integer((Integer) fromSpinner.getValue()), null,
					(Integer) toSpinner.getValue() - 1, 1.0));

		});

		JLabel lineSegmentLabel = new JLabel("Відрізок від до:");
		lineSegmentLabel.setBounds(24, 43, 85, 14);
		contentPane.add(lineSegmentLabel);

		JLabel evalLabel = new JLabel("Точність:");
		evalLabel.setBounds(24, 74, 95, 17);
		contentPane.add(evalLabel);

		Button drawButton = new Button("Намалювати");

		drawButton.addActionListener(e -> {
			// new DrawFrame(vector);
			new Chart(vector, (int) fromSpinner.getValue(), (int) toSpinner.getValue());
		});

		drawButton.setBounds(487, 382, 152, 22);
		contentPane.add(drawButton);
		degreeSpinnerField.addChangeListener(e -> {
			createPolynomFields((Integer) degreeSpinnerField.getValue(), polynomPanel, polynomView);

		});

	}

	private void doAllIntegrations(double[] vector, int eval, int from, int to) {

		Integrator integ = new Integrator(vector, eval, from, to);
		resultPanel.removeAll();
		double[] v = integ.getResults();
		double[] d = integ.getDuration();

		for (int i = 0; i < v.length; ++i) {
			JLabel tmpLabel = null;
			switch (i) {
			case 0:
				tmpLabel = new JLabel("  Cімпсон: ");
				tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				break;
			case 1:
				tmpLabel = new JLabel("  Трапеція: ");
				tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				break;
			case 2:
				tmpLabel = new JLabel("  Ромберг: ");
				tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				break;
			}

			tmpLabel.setText(tmpLabel.getText() + v[i]);
			resultPanel.add(tmpLabel);
			tmpLabel = new JLabel("  Час виконання: " + d[i] + "ms");
			tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
			resultPanel.add(tmpLabel);
			JSeparator separator = new JSeparator();
			separator.setSize(new Dimension(resultPanel.getWidth(), 3));
			resultPanel.add(separator);

		}

		JLabel tmpLabel;

		try {
			tmpLabel = new JLabel("  Сімпсон - Трапеція = "
					+ new BigDecimal(Math.abs(((v[0] - v[1]) * 100) / v[0])).setScale(35, RoundingMode.HALF_UP) + " %");
			tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
			resultPanel.add(tmpLabel);
			tmpLabel = new JLabel("  Сімпсон - Ромберг = "
					+ new BigDecimal(Math.abs(((v[0] - v[2]) * 100) / v[0])).setScale(35, RoundingMode.HALF_UP) + " %");
			tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
			resultPanel.add(tmpLabel);
			tmpLabel = new JLabel("  Трапеція - Ромберг = "
					+ new BigDecimal(Math.abs(((v[1] - v[2]) * 100) / v[1])).setScale(35, RoundingMode.HALF_UP) + " %");
			tmpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
			resultPanel.add(tmpLabel);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Невірно задані значення", "Помилка", JOptionPane.ERROR_MESSAGE);
		}
		resultPanel.repaint();
		resultPanel.revalidate();
	}

	private void createPolynomFields(Integer degree, JPanel polynomPanel, JViewport polynomView) {

		try {
			
			JLabel tmpPolynomLabel = new JLabel();
			tmpPolynomLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

			polynomPanel.removeAll();
			
			tmpTextField = new JTextField(5);
			tmpTextField.setText("0");
			
			polynomPanel.add(tmpTextField);

			for (int i = 0; i < degree; ++i) {
				tmpPolynomLabel = new JLabel("+");
				tmpPolynomLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				polynomPanel.add(tmpPolynomLabel);
				tmpTextField = new JTextField(5);
				tmpTextField.setText("0");
				polynomPanel.add(tmpTextField);
				if (i + 1 > 99) {
					tmpPolynomLabel = new JLabel("x " + sup.charAt((i + 1) / 100) + sup.charAt((i + 1) % 100));
				} else if (i + 1 > 9) {
					tmpPolynomLabel = new JLabel("x " + sup.charAt((i + 1) / 10) + sup.charAt((i + 1) % 10));
				} else if (i == 0) {
					tmpPolynomLabel = new JLabel("x ");
				} else {
					tmpPolynomLabel = new JLabel("x " + sup.charAt(i + 1));
				}
				tmpPolynomLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
				polynomPanel.add(tmpPolynomLabel);

				polynomPanel.setPreferredSize(new Dimension(polynomView.getWidth(), ((i / 3) + 2) * 25));

			}

			for (Component tmp : polynomPanel.getComponents()) {
				if (tmp.getClass().equals(JTextField.class)) {
					((JTextField) tmp).addFocusListener(new FocusListener() {
						
						@Override
						public void focusLost(FocusEvent e) {
						}
						
						@Override
						public void focusGained(FocusEvent e) {
							((JTextField) tmp).selectAll();
							
						}
					});;
					((JTextField) tmp).addKeyListener(new KeyListener() {
						
						@Override
						public void keyTyped(KeyEvent e) {
						}
						
						@Override
						public void keyReleased(KeyEvent e) {
							if(e.getKeyChar()=='('){
								((JTextField) tmp).setText(((JTextField) tmp).getText()+')');
								((JTextField) tmp).setCaretPosition(((JTextField) tmp).getText().length()-1);
							}
							
						}
						
						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
				}
			}
			
			repaint();
			revalidate();

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}

}
