package com.celula.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.math.R.RserverConf;
import org.math.R.Rsession;
import org.math.R.StartRserve;
import org.rosuda.REngine.Rserve.RConnection;

import com.celula.BackupToolPreference;

public class RServeHelper {

	private static Logger logger = LogManager.getLogger(RServeHelper.class.getName());
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
        String port_str = props.getProperty("port");
        int port = Integer.parseInt(port_str);
        
        Preferences userRoot = Preferences.userRoot();
		Preferences softRoot = userRoot.node("/"+BackupToolPreference.class.getName());
		Preferences node2 = softRoot.node("R");
		String r_install = node2.get("install_path", "");
	    boolean is_running = isRserveRunning(hostname,port);
	    if( ! is_running ){
	    	StartRserve.launchRserve(r_install+"/bin/R.exe", null, "--RS-port " + port, true);
	    }
	    RserverConf rconf=new RserverConf(hostname,port,null,null,new Properties());
        
        //rsession = Rsession.newLocalInstance(System.out,null);
        rsession = Rsession.newRemoteInstance(System.out, rconf);
        return rsession;
    }
    
    public static boolean isRserveRunning(String host,int port) {
	    try {
	        RConnection c = new RConnection(host,port);
	        System.err.println("Rserve is running.");
	        c.close();
	        return true;
	    } catch (Exception e) {
	        System.err.println("First connect try failed with: " + e.getMessage());
	    }
	    return false;
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
    	try {
			Rsession rsessionInstance = RServeHelper.getRsessionInstance();
		} catch (IOException e) {
			logger.debug("log");
			e.printStackTrace();
		}
    	
	}
}