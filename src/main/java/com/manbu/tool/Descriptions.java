package com.manbu.tool;

import java.util.ArrayList;
import java.util.List;

public class Descriptions {

	private List<String> headers = new ArrayList<String>();
	private List<String> urls = new ArrayList<String>();
	
	/**
	 * if the list change, then return true
	 * @param header
	 * @return
	 */
	public boolean appendHeader(String header) {
		if(!headers.contains(header)) {
			headers.add(header);
			return true;
		}
		return false;
	}
	
	/**
	 * if the list change, then return true
	 * @param url
	 * @return
	 */
	public boolean appendUrl(String url) {
		if(!urls.contains(url)) {
			urls.add(url);
			return true;
		}
		return false;
	}
	
	public List<String> getHeaders() {
		return headers;
	}
	
	public List<String> getUrls() {
		return urls;
	}
	
	
}
