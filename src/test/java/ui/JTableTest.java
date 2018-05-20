package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.border.TitledBorder;

public class JTableTest extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblNewJgoodiesTitle;
	private JPanel panel;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JTableTest frame = new JTableTest();
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
	public JTableTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("New JGoodies title");
		scrollPane.setColumnHeaderView(lblNewJgoodiesTitle);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.NORTH);
		
		btnNewButton = new JButton("New button");
		panel.add(btnNewButton);
	}

}
