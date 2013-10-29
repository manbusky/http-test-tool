package com.manbu.tool.ui;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ResponseOutputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3742568506735003304L;
	
	private JTable headerTable;
	private DefaultTableModel headersTableModel; 
	
	private JLabel jlbBodyContentLength;
	private JLabel jlbStatusCode;
	private JTextArea bodyTextArea;
	
	public ResponseOutputPanel() {
		setLayout(null);
		setBounds(10, 335, 740, 230);
		
		
		headersTableModel = new DefaultTableModel(new String[]{"key", "value"}, 0);
		headerTable = new JTable(headersTableModel);
		headerTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		headerTable.setSelectionBackground(Color.LIGHT_GRAY);
		headerTable.setRowHeight(24);
		headerTable.setSelectionForeground(Color.BLUE);
		headerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(headerTable);
		jScrollPane.setBounds(10, 20, 310, 180);
		
		JPanel headPanel = new JPanel(null);
		headPanel.setBorder(BorderFactory.createTitledBorder("heads"));
		headPanel.add(jScrollPane);
		headPanel.setBounds(10, 20, 330, 205);
		
		
		JLabel jlbLength = new JLabel("Length:");
		jlbLength.setBounds(10, 20, 60, 20);
//		jlbLength.setBorder(BorderFactory.createLineBorder(Color.RED));
		jlbBodyContentLength = new JLabel("0");
		jlbBodyContentLength.setBounds(90, 20, 60, 20);
//		jlbBodyContentLength.setBorder(BorderFactory.createLineBorder(Color.RED));
		jlbStatusCode = new JLabel();
		jlbStatusCode.setHorizontalAlignment(JLabel.CENTER);
		jlbStatusCode.setForeground(Color.RED);
		jlbStatusCode.setBounds(275, 20, 50, 20);
		jlbStatusCode.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		bodyTextArea = new JTextArea(100, 0);
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(bodyTextArea);
		jScrollPane2.setBounds(10, 50, 315, 150);
		
		JPanel bodyPanel = new JPanel(null);
		bodyPanel.setBorder(BorderFactory.createTitledBorder("body"));
		bodyPanel.add(jScrollPane2);
		bodyPanel.add(jlbLength);
		bodyPanel.add(jlbBodyContentLength);
		bodyPanel.add(jlbStatusCode);
		bodyPanel.setBounds(340, 20, 340, 205);
		
		
		
		JPanel responsePanel = new JPanel(null);
		responsePanel.setBorder(BorderFactory.createTitledBorder("response"));
		responsePanel.setBounds(0, 0, 690, 230);
		responsePanel.add(headPanel);
		responsePanel.add(bodyPanel);
		
		add(responsePanel);
		
	}
	
	public void setHeaders(Map<String, String> headers) {
		headersTableModel.getDataVector().removeAllElements();
		Iterator<String> iterator = headers.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			String value = headers.get(key);
			headersTableModel.addRow(new String[]{key, value});
			headerTable.repaint();
		}
	}
	
	public void clear() {
		headersTableModel.getDataVector().removeAllElements();
		jlbStatusCode.setText("");
		bodyTextArea.setText("");
		jlbBodyContentLength.setText("");
		jlbStatusCode.setText("");
		
	}
	
	public void setStatus(int code) {
		jlbStatusCode.setText(String.valueOf(code));
	}
	
	public void setData(String string) {
		int length = string.getBytes().length;
		
		jlbBodyContentLength.setText(String.valueOf(length));
		bodyTextArea.setText(string);
	}

}
