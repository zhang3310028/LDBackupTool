package com.celula;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.math.R.Rsession;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;

import com.celula.utils.RServeHelper;

public class TaskUi extends JFrame {

	private BackupToolGui parent;

	private JPanel contentPane;
	private JPanel table_panel;
	private JTable table;

	private JTextField dest_dir_txt;
	private JTextField src_dir_txt;

	Rsession rsessionInstance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskUi frame = new TaskUi();
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
	public TaskUi() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel src_dir_label = new JLabel("srt_dir:");
		panel.add(src_dir_label);

		src_dir_txt = new JTextField();
		src_dir_txt.setEditable(false);
		src_dir_txt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File file=new File(src_dir_txt.getText()); 
				try {
					java.awt.Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(src_dir_txt);
		src_dir_txt.setColumns(10);

		JLabel dest_dir_label = new JLabel("dest_dir:");
		panel.add(dest_dir_label);

		dest_dir_txt = new JTextField();
		dest_dir_txt.setEditable(false);
		dest_dir_txt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File file=new File(dest_dir_txt.getText()); 
				try {
					java.awt.Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(dest_dir_txt);
		dest_dir_txt.setColumns(10);

		table_panel = new JPanel();
		contentPane.add(table_panel, BorderLayout.CENTER);
		table_panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("copy");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double[] rows_array = new double[table.getSelectedRows().length];

				int i = 0;
				for (int rowindex : table.getSelectedRows()) {
					String index = (String) table.getValueAt(rowindex, 0);
					rows_array[i++] = Double.parseDouble(index);
				}

				rsessionInstance.set("dest_dir", TaskUi.this.dest_dir_txt.getText());
				rsessionInstance.set("list_index", rows_array);
				rsessionInstance.eval("cat(as.character(dl$absdir[list_index]))");
				rsessionInstance.eval("a<-copy_files(dest_dir,dl$absdir[list_index])");
				rsessionInstance.eval("cat(1:nrow(dl)%in%list_index)");
				rsessionInstance.eval("dl<-dl[!1:nrow(dl)%in%list_index,]");
				try {
					listData(table,rsessionInstance);
				} catch (REXPMismatchException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_2.add(btnNewButton);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int)(toolkit.getScreenSize().getWidth()-this.getWidth())/2;
		int y = (int)(toolkit.getScreenSize().getHeight()-this.getHeight())/2;
		this.setLocation(x, y);
	}

	public TaskUi(BackupToolGui parent) {
		this();
		this.parent = parent;

		this.src_dir_txt.setText(this.parent.src_path_txt.getText());
		this.dest_dir_txt.setText(this.parent.dest_path_txt.getText());

		try {
			rsessionInstance = RServeHelper.getRsessionInstance();
			rsessionInstance.source(new File("src\\main\\resources\\DataBackUtil.R"));
			rsessionInstance.set("src_path", this.src_dir_txt.getText());

			String max_size_str = this.parent.max_size_text.getText();
			rsessionInstance.eval("arg_list<-list('src_dir' = src_path)");
			if(!max_size_str.isEmpty()){
				double max_size = Double.parseDouble(max_size_str);
				rsessionInstance.set("max_size", max_size);
				rsessionInstance.eval("arg_list['max.size']=max_size");
			}
			String max_date_str = this.parent.max_date_text.getText();
			if(!max_date_str.isEmpty()){
				rsessionInstance.set("max_date", max_date_str);
				rsessionInstance.eval("arg_list['max.date']=max_date");
			}
			String filter_str = this.parent.filter_text.getText();
			if(!filter_str.isEmpty()){
				rsessionInstance.set("filter_str", filter_str);
				rsessionInstance.eval("arg_list['filt_str']=filter_str");
			}
			rsessionInstance.eval("dl<-data.frame()");
			REXP eval = rsessionInstance.eval("dl<-do.call('data_list',arg_list)");
			rsessionInstance.eval("cat(as.character(dl$basedir))");
			
			table = new JTable();
			listData(table,rsessionInstance);
			table_panel.setLayout(new BorderLayout());
			table_panel.add(new JScrollPane(table), BorderLayout.CENTER);
			table_panel.add(table.getTableHeader(), BorderLayout.NORTH);
			table_panel.setVisible(true);
			table_panel.setSize(200, 200);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		}

	}
	
	public void listData(JTable table,Rsession rsessionInstance) throws REXPMismatchException{
		String[] rownum_list = rsessionInstance.eval("1:length(dl$absdir)").asStrings();
		rsessionInstance.eval("cat(dim(dl))");
		String[] dir_list = rsessionInstance.eval("dl$absdir").asStrings();
		String[] basedir_list = rsessionInstance.eval("dl$basedir").asStrings();
		String[] mtime_list = rsessionInstance.eval("as.character(dl$mtime)").asStrings();
		String[] acc_size_list = rsessionInstance.eval("as.character(dl$acc_size)").asStrings();
		
		List<String> columns = new ArrayList<String>();
        List<String[]> values = new ArrayList<String[]>();
        columns.add("select");
        columns.add("dir");
        columns.add("basedir");
        columns.add("time");
        columns.add("acc_size");
        for (int i = 0; i < dir_list.length; i++) {
        	values.add(new String[] {rownum_list[i],dir_list[i],basedir_list[i],mtime_list[i],acc_size_list[i]});
        }
        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
			/*
			 * (non-Javadoc) 此方法用于向方法调用者返回某一单元格的渲染器（即显示数据的组建--或控件）
			 * 可以为JCheckBox JComboBox JTextArea 等
			 * 
			 * @see javax.swing.table.TableCellRenderer#
			 * getTableCellRendererComponent(javax.swing.JTable,
			 * java.lang.Object, boolean, boolean, int, int)
			 */
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
				// 创建用于返回的渲染组件
				JCheckBox ck = new JCheckBox();
				// 使具有焦点的行对应的复选框选中
				ck.setSelected(isSelected);
				// 设置单选box.setSelected(hasFocus);
				// 使复选框在单元格内居中显示
				ck.setHorizontalAlignment((int) 0.5f);
				return ck;
			}
		});
	}
	
}
