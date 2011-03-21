package de.md.glassfish.properties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.appserv.server.LifecycleEvent;
import com.sun.appserv.server.LifecycleListener;
import com.sun.appserv.server.ServerLifecycleException;

public class PropertyLifeCycleListener implements LifecycleListener{

	@Override
	public void handleEvent(LifecycleEvent event)
			throws ServerLifecycleException {
		System.out.println("Event ist " + event.getEventType());
		if(LifecycleEvent.STARTUP_EVENT == event.getEventType()){
			File file = new File("properties");
			try{
			InitialContext context = new InitialContext();
			
			for(File property : file.listFiles()){
				System.out.println("!!!! PATH !!! " + property.getAbsolutePath());
				if(property.isFile() && property.getName().endsWith("properties")){
					System.out.println("!!!!! FOUND PROPERTY FILE !!!! " + property.getAbsolutePath());
					Properties props = new Properties();
					props.load(new FileInputStream(property));
					context.rebind("properties/" + property.getName(), props);
					System.out.println("BINDING to properties/" + property.getName());
				}
			}
			}catch(Exception ex){
				System.out.println("Exception!!");
				ex.printStackTrace();
			}
		}
	}

}
