package com.celula;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class BackupToolGui {

	private JFrame frame;
	public JTextField src_path_txt;
	public JTextField dest_path_txt;
	public JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BackupToolGui window = new BackupToolGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BackupToolGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 558, 431);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel path_panel = new JPanel();
		path_panel.setSize(new Dimension(6, 10));
		path_panel.setPreferredSize(new Dimension(10, 30));
		frame.getContentPane().add(path_panel, BorderLayout.CENTER);
		GridBagLayout gbl_path_panel = new GridBagLayout();
		gbl_path_panel.columnWidths = new int[]{143, 54, 66, 54, 66, 0};
		gbl_path_panel.rowHeights = new int[]{0, 0, 21, 0};
		gbl_path_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_path_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		path_panel.setLayout(gbl_path_panel);
		
		JLabel lblSrcPath = new JLabel("src path:");
		GridBagConstraints gbc_lblSrcPath = new GridBagConstraints();
		gbc_lblSrcPath.anchor = GridBagConstraints.WEST;
		gbc_lblSrcPath.insets = new Insets(0, 0, 5, 5);
		gbc_lblSrcPath.gridx = 1;
		gbc_lblSrcPath.gridy = 1;
		path_panel.add(lblSrcPath, gbc_lblSrcPath);
		
		src_path_txt = new JTextField();
		src_path_txt.setText("d:/tmp");
		GridBagConstraints gbc_src_path_txt = new GridBagConstraints();
		gbc_src_path_txt.fill = GridBagConstraints.BOTH;
		gbc_src_path_txt.gridwidth = 3;
		gbc_src_path_txt.insets = new Insets(0, 0, 5, 5);
		gbc_src_path_txt.gridx = 2;
		gbc_src_path_txt.gridy = 1;
		path_panel.add(src_path_txt, gbc_src_path_txt);
		src_path_txt.setColumns(10);
		
		JLabel dest_path_label = new JLabel("dest path");
		GridBagConstraints gbc_dest_path_label = new GridBagConstraints();
		gbc_dest_path_label.anchor = GridBagConstraints.WEST;
		gbc_dest_path_label.insets = new Insets(0, 0, 0, 5);
		gbc_dest_path_label.gridx = 1;
		gbc_dest_path_label.gridy = 2;
		path_panel.add(dest_path_label, gbc_dest_path_label);
		
		dest_path_txt = new JTextField();
		dest_path_txt.setText("d:/test");
		GridBagConstraints gbc_dest_path_txt = new GridBagConstraints();
		gbc_dest_path_txt.fill = GridBagConstraints.HORIZONTAL;
		gbc_dest_path_txt.gridwidth = 3;
		gbc_dest_path_txt.insets = new Insets(0, 0, 0, 5);
		gbc_dest_path_txt.anchor = GridBagConstraints.NORTH;
		gbc_dest_path_txt.gridx = 2;
		gbc_dest_path_txt.gridy = 2;
		path_panel.add(dest_path_txt, gbc_dest_path_txt);
		dest_path_txt.setColumns(10);
		
		JPanel search_panel = new JPanel();
		search_panel.setPreferredSize(new Dimension(10, 70));
		frame.getContentPane().add(search_panel, BorderLayout.NORTH);
		
		JLabel max_date_label = new JLabel("maxDate:");
		search_panel.add(max_date_label);
		
		textField = new JTextField();
		textField.setColumns(10);
		search_panel.add(textField);
		
		JLabel label_1 = new JLabel("filter:");
		search_panel.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		search_panel.add(textField_1);
		
		JLabel lblNewLabel = new JLabel("maxSize:");
		search_panel.add(lblNewLabel);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		search_panel.add(textField_2);
		
		JPanel button_panel = new JPanel();
		frame.getContentPane().add(button_panel, BorderLayout.SOUTH);
		
		JButton search_button = new JButton("search");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaskUi taskUi = new TaskUi(BackupToolGui.this);
				taskUi.setVisible(true);
			}
		});
		button_panel.add(search_button);
	}

}
