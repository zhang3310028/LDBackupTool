package com.celula;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class BackupToolPreference implements TreeSelectionListener{

	public JFrame frame;
	
	private JPanel contentPane;
	private Preferences softRoot;
	private JSplitPane splitPane;
	private JTree tree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BackupToolPreference window = new BackupToolPreference();
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
	public BackupToolPreference() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		//设置
		Preferences userRoot = Preferences.userRoot();
		
		//创建根节点配置
		softRoot = userRoot.node("/"+BackupToolPreference.class.getName());
        String config_file = softRoot.get("config_file",null);
        if(config_file == null){
            softRoot.put("config_file", "prpoerties.props");
            //创建子节点配置
    		Preferences node = softRoot.node("General/Apperances");
    		node.put("color", "red");
    		node.put("font", "14");
    		Preferences node2 = softRoot.node("R");
    		node2.put("install_path", "d:/install/R");
        }else{
            
        }
		
		//显示左侧树
		
		try {
			DefaultMutableTreeNode treeNode = getTreeNode(softRoot);
			tree = new JTree(treeNode);
			tree.addTreeSelectionListener(this);
			splitPane.setLeftComponent(tree);
			splitPane.setRightComponent(new JPanel());
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	public static DefaultMutableTreeNode getTreeNode(Preferences prefroot) throws BackingStoreException{
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(prefroot.name());
		for(String childName: prefroot.childrenNames()){
			Preferences tmpnode = prefroot.node(childName);
			DefaultMutableTreeNode node = getTreeNode(tmpnode);
			rootNode.add(node);
		}
		return rootNode;
	}
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		String string = Arrays.toString(node.getPath());
	    
	    String pathName = "";
	    for(int i = 0 ; i< node.getPath().length ; i ++){
	    	if(i==0){
	    		pathName = node.getPath()[i].toString();
	    	}else{
	    		pathName = pathName+"/"+node.getPath()[i].toString();
	    	}
	    	
	    }
	    System.out.println(pathName);
	    
	    Preferences node2 = softRoot.node("/"+pathName);
	    try {
			System.out.println("keys:"+Arrays.toString(node2.keys()));
		} catch (BackingStoreException e1) {
			e1.printStackTrace();
		}
	    BasePreferencePanel jPanel = new BasePreferencePanel();
	    if(pathName.equals(BackupToolPreference.class.getName()+"/R")){
	    	jPanel = new RPathPreferencePanel();
	    }
	    jPanel.showPreference(node2);
	    splitPane.setRightComponent(jPanel);
	}
	
	class BasePreferencePanel extends JPanel{

		public void showPreference(Preferences pref){
			try {
				String[] keys = pref.keys();
				for(String key : keys){
					String value = pref.get(key," ");
					JLabel label = new JLabel(key);
					JTextField textField = new JTextField(value);
					textField.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							pref.put(key,textField.getText());
						}
					});
					JPanel new_group = new JPanel();
					
					new_group.add(label);
					new_group.add(textField);
					this.add(new_group);
				}
				
			} catch (BackingStoreException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	class RPathPreferencePanel extends BasePreferencePanel{
		public void showPreference(Preferences pref){
			String install_path = pref.get("install_path", "");
			JLabel label = new JLabel("install_path");
			JTextField textField = new JTextField(install_path);
			textField.setPreferredSize(new Dimension(80, 20));
			textField.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					String text = textField.getText();
					pref.put("install_path", text);
				}
			});
			JPanel new_group = new JPanel();
			new_group.add(label);
			new_group.add(textField);
			this.add(new_group);
		}
	}
	
}
