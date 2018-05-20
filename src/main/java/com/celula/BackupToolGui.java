package com.celula;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class BackupToolGui {

	private JFrame frame;
	public JTextField src_path_txt;
	public JTextField dest_path_txt;
	public JTextField max_date_text;
	public JTextField max_size_text;
	public JTextField filter_text;

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
		frame.setBounds(100, 100, 417, 249);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int)(toolkit.getScreenSize().getWidth()-frame.getWidth())/2;
		int y = (int)(toolkit.getScreenSize().getHeight()-frame.getHeight())/2;
		frame.setLocation(x, y);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel path_panel = new JPanel();
		path_panel.setBounds(new Rectangle(20, 20, 8, 4));
		path_panel.setBorder(new TitledBorder(null, "\u8DEF\u5F84\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		path_panel.setSize(new Dimension(6, 10));
		frame.getContentPane().add(path_panel, BorderLayout.CENTER);
		path_panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("263px"),},
			new RowSpec[] {
				RowSpec.decode("22px"),
				RowSpec.decode("24px"),}));
		
		JLabel lblSrcPath = new JLabel("src path:");
		path_panel.add(lblSrcPath, "1, 1, left, center");
		
		src_path_txt = new JTextField();
		src_path_txt.setPreferredSize(new Dimension(30, 20));
		src_path_txt.setText("d:/tmp");
		path_panel.add(src_path_txt, "3, 1, fill, default");
		src_path_txt.setColumns(10);
		
		JLabel dest_path_label = new JLabel("dest path");
		path_panel.add(dest_path_label, "1, 2, left, center");
		
		dest_path_txt = new JTextField();
		dest_path_txt.setText("d:/test");
		path_panel.add(dest_path_txt, "3, 2, fill, default");
		dest_path_txt.setColumns(10);
		
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
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel search_panel = new JPanel();
		panel.add(search_panel);
		search_panel.setBorder(new TitledBorder(null, "\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		search_panel.setPreferredSize(new Dimension(17, 75));
		search_panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("66px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("84px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("65px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("84px"),},
			new RowSpec[] {
				RowSpec.decode("22px"),
				RowSpec.decode("21px"),}));
		
		JLabel max_date_label = new JLabel("maxDate:");
		search_panel.add(max_date_label, "2, 1, left, center");
		
		max_date_text = new JTextField();
		max_date_text.setColumns(10);
		search_panel.add(max_date_text, "4, 1, fill, top");
		
		JLabel lblNewLabel = new JLabel("maxSize:");
		search_panel.add(lblNewLabel, "6, 1, left, center");
		
		max_size_text = new JTextField();
		max_size_text.setColumns(10);
		search_panel.add(max_size_text, "8, 1, fill, top");
		
		JLabel label_1 = new JLabel("filter:");
		search_panel.add(label_1, "2, 2, left, center");
		
		filter_text = new JTextField();
		filter_text.setColumns(10);
		search_panel.add(filter_text, "4, 2, 5, 1, fill, top");
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(30, 10));
		panel.add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(30, 10));
		panel.add(panel_2, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(10, 20));
		panel.add(panel_5, BorderLayout.NORTH);
		
		JLabel label = new JLabel("数据备份");
		panel_5.add(label);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(30, 10));
		frame.getContentPane().add(panel_3, BorderLayout.WEST);
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(30, 10));
		panel_4.setMinimumSize(new Dimension(30, 10));
		frame.getContentPane().add(panel_4, BorderLayout.EAST);
	}

}
