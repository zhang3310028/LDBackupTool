package com.celula.utils;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.math.R.RserverConf;
import org.math.R.Rsession;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;

public class RServeHelper {

    private static Rsession rsession=null;

    /**
     * ����Rsession��ʼ��RServe
     * @return
     * @throws IOException
     */
    public static Rsession initRserve() throws IOException {
        //�������ļ��ж�ȡRserve��Ϣ��IP.�û���.����
    	Properties props = new Properties();
    	InputStream in = RServeHelper.class.getResourceAsStream("/config.properties");
    	props.load(in);
    	in.close();
        String hostname = props.getProperty("host");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        RserverConf rconf=new RserverConf(hostname,6311,username,password,new Properties());
        rsession = Rsession.newInstanceTry(System.out, rconf);
        return rsession;
    }

    /**
     * ����Rsession����
     * @return
     * @throws IOException
     */
    public static Rsession getRsessionInstance() throws IOException {
        if(rsession==null){
            rsession=initRserve();
        }
        return rsession;
    }
    
    public static void main(String[] args) {
	}
}