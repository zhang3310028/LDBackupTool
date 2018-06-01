package com.celula.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.math.R.RserverConf;
import org.math.R.Rsession;

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
        RserverConf rconf=new RserverConf(hostname,6311,username,password,new Properties());
        
        //rsession = Rsession.newLocalInstance(System.out,null);
        rsession = Rsession.newRemoteInstance(System.out, rconf);
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
    	try {
			Rsession rsessionInstance = RServeHelper.getRsessionInstance();
		} catch (IOException e) {
			logger.debug("log");
			e.printStackTrace();
		}
    	
	}
}