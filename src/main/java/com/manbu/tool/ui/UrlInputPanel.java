package com.manbu.tool.ui;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class UrlInputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox<String> urlComboBox;
	private JComboBox<String> methodComboBox;
	private JButton btnStart;
	public UrlInputPanel(ActionListener listener) {
		
		urlComboBox = new JComboBox<String>();
		urlComboBox.setEditable(true);
		urlComboBox.setBounds(0, 0, 470, 30);
		
		methodComboBox = new JComboBox<String>();
		methodComboBox.setEditable(false);
		methodComboBox.setBounds(480, 0, 80, 30);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(570, 0, 120, 30);
		
		setBounds(10, 10, 740, 30);
		setLayout(null);
		add(urlComboBox);
		add(methodComboBox);
		add(btnStart);
		
		methodComboBox.addItem("GET");
		methodComboBox.addItem("HEAD");
		methodComboBox.addItem("POST");
		methodComboBox.addItem("PUT");
		methodComboBox.addItem("DELETE");
		methodComboBox.addItem("TRACE");
		
		btnStart.addActionListener(listener);
	}
	
	public void appendUrl(List<String> urls) {
		for(String url: urls) {
			urlComboBox.addItem(url);
		}
	}
	
	public String getUrl () {
		return (String)urlComboBox.getSelectedItem();
	}
	
	public String getMethod() {
		return (String)methodComboBox.getSelectedItem();
	}


}
