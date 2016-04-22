package com.manbu.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class ConfigLoader {

	public static final String CONFIG_FILE = "httptesttool.config";
	
	
	private static Properties load() {
		
		Properties messageContent = null; 
		try {
			
			File configFile = getConfigFile();
			InputStream in = FileUtils.openInputStream(configFile);
			messageContent = new Properties();
			messageContent.load(in);
			IOUtils.closeQuietly(in);
		} catch (Exception e) {
			
			e.printStackTrace();
			
			messageContent = new Properties();
			saveConfig(messageContent);
			
		}
		return messageContent;
	}
	
	private static void saveConfig(Properties messageContent) {
		
		String comments = (new Date(System.currentTimeMillis())).toString();
		
		File configFile = getConfigFile();

		try {
			OutputStream out = FileUtils.openOutputStream(configFile);
			messageContent.store(out, comments);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveDescription(Descriptions des) {
		Properties p = load();
		
		List<String> headers = des.getHeaders();
		List<String> urls = des.getUrls();
		
		int length = headers.size();
		for(int i=0; i<length; i++) {
			p.put("header|" + i, headers.get(i));
		}
		
		length = urls.size();
		for(int i=0; i<length; i++) {
			p.put("url|" + i, urls.get(i));
		}
		saveConfig(p);
		
	}
	
	public static Descriptions loadDescription() {
		
		Descriptions des = new Descriptions();
		
		Properties p = load();
		Iterator<Object> iterator = p.keySet().iterator();
		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			String value = p.getProperty(key);
			
			if(key.contains("url|")) {
				des.appendUrl(value);
			}
			if(key.contains("header|")) {
				des.appendHeader(value);
			}
		}
		return des;
	}
	
	private static File getConfigFile() {
		String tmpdir = System.getProperty("user.home");
		File tempDirectory = new File(tmpdir + File.separatorChar + ".temp");
		File configFile = new File(tempDirectory, CONFIG_FILE);
		return configFile;
	}

}
