package me.ramazanenescik04.enginetest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.emirenesgames.engine.DikenEngine;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		DikenEngine engine = DikenEngine.initEngineNonFrame(320, 240, 2, "Hello!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 772, 591);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMode = new JMenu("Mode");
		menuBar.add(mnMode);
		
		JPanel panel = new JPanel();
		JDesktopPane desktopPane = new JDesktopPane();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JMenuItem mnıtmNormalTest = new JMenuItem("Normal Test");
		JMenuItem mnıtmJnternalframeTest = new JMenuItem("JInternalFrame Test");
		JInternalFrame internalFrame = new JInternalFrame("DikenEngine JInternalFrame Test");
		mnıtmJnternalframeTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				engine.stop();
				panel.removeAll();
				panel.add(desktopPane, BorderLayout.CENTER);
				internalFrame.dispose();
				internalFrame.remove(engine);
				internalFrame.add(engine);
				desktopPane.removeAll();
				desktopPane.add(internalFrame);
				internalFrame.setVisible(true);
				engine.startEngine();
				Main.this.validate();
				Main.this.repaint();
			}
		});
		mnMode.add(mnıtmNormalTest);
		mnıtmNormalTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				engine.stop();
				panel.removeAll();
				internalFrame.dispose();
				panel.add(engine);
				engine.startEngine();
				Main.this.validate();
				Main.this.repaint();
			}
		});
		mnMode.add(mnıtmJnternalframeTest);
		
		panel.add(desktopPane, BorderLayout.CENTER);
		
		internalFrame.setMaximizable(true);
		internalFrame.setIconifiable(true);
		internalFrame.setClosable(true);
		internalFrame.setResizable(true);
		internalFrame.add(engine);
		internalFrame.pack();
		desktopPane.add(internalFrame);
		internalFrame.setVisible(true);
		
		engine.startEngine();
	}

}
