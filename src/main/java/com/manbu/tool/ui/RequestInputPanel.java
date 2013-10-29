package com.manbu.tool.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class RequestInputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4075932645235972927L;

	private JComboBox<String> headKey;
	private JTextField headValue;
	private JButton btnAppend;
	private JButton btnRemove;
	private JTable headerTable;
	private DefaultTableModel headersTableModel;
	private JTextArea bodyTextArea;
	private JLabel jlbBodyContentLength;
	
	public RequestInputPanel() {
		
		setLayout(null);
		setBounds(10, 45, 740, 285);
		
		headKey = new JComboBox<String>();
		headKey.setEditable(true);
		headKey.setBounds(10, 25, 200, 25);
		headValue = new JTextField();
		headValue.setBounds(10, 55, 200, 25);
		btnAppend = new JButton("Append");
		btnAppend.setBounds(220, 25, 100, 25);
		btnRemove = new JButton("Remove");
		btnRemove.setBounds(220, 55, 100, 25);
		
		headersTableModel = new DefaultTableModel(new String[]{"key", "value"}, 0);
		headerTable = new JTable(headersTableModel);
		headerTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		headerTable.setSelectionBackground(Color.LIGHT_GRAY);
		headerTable.setRowHeight(24);
		headerTable.setSelectionForeground(Color.BLUE);
		headerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jScrollPane1 = new JScrollPane();
		jScrollPane1.setViewportView(headerTable);
		jScrollPane1.setBounds(10, 85, 310, 160);

		JPanel headPanel = new JPanel(null);
		headPanel.setBorder(BorderFactory.createTitledBorder("heads"));
		
		headPanel.add(headKey);
		headPanel.add(headValue);
		headPanel.add(btnAppend);
		headPanel.add(btnRemove);
		headPanel.add(jScrollPane1);
		headPanel.setBounds(10, 20, 330, 255);
		
		JLabel jlbLength = new JLabel("Length:");
		jlbLength.setBounds(10, 20, 100, 20);
		jlbBodyContentLength = new JLabel("0");
		jlbBodyContentLength.setBounds(130, 20, 100, 20);
		
		bodyTextArea = new JTextArea(100, 20);
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(bodyTextArea);
		jScrollPane2.setBounds(10, 50, 315, 195);
		
		JPanel bodyPanel = new JPanel(null);
		bodyPanel.setBorder(BorderFactory.createTitledBorder("body"));
		bodyPanel.add(jScrollPane2);
		bodyPanel.add(jlbLength);
		bodyPanel.add(jlbBodyContentLength);
		bodyPanel.setBounds(340, 20, 340, 255);
		
		
		JPanel requestPanel = new JPanel(null);;
		requestPanel.setBorder(BorderFactory.createTitledBorder("request"));
		requestPanel.add(headPanel);
		requestPanel.add(bodyPanel);
		requestPanel.setBounds(0, 0, 690, 285);
		
		add(requestPanel);
		
		init();
		addListener();
		
	}
	
	private void init() {
		
		headersTableModel.addRow(new String[]{"Content-Type", "application/json;charset=UTF-8"});
		
	}
	
	private void addListener() {
		btnAppend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = String.valueOf(headKey.getSelectedItem());
				String value = headValue.getText();
				
				int rowCount = headersTableModel.getRowCount();
				int index = -1;
				for(int i=0; i<rowCount; i++) {
					String tempKey = (String)headersTableModel.getValueAt(i, 0);
					if(tempKey.equals(key)) {
						index = i;
						break;
					}
				}
				if(index == -1) {
					headersTableModel.addRow(new String[]{key, value});
				} else {
					headersTableModel.setValueAt(key, index, 0);
					headersTableModel.setValueAt(value, index, 1);
				}
				headerTable.repaint();
				
			}
		});
		
		btnRemove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				int row = headerTable.getSelectedRow();
				if(row != -1) {
					headersTableModel.removeRow(row);
					headerTable.repaint();
				}
			}
		});
		
	}
	
	public Map<String, String> getHeaders() {
		
		int rowCount = headersTableModel.getRowCount();
		Map<String, String> headers = new HashMap<String, String>();
		for(int i=0; i<rowCount; i++) {
			String key = (String)headersTableModel.getValueAt(i, 0);
			String value = (String)headersTableModel.getValueAt(i, 1);
			headers.put(key, value);
		}
		
		return headers;
	}
	
	public String getData() {
		return bodyTextArea.getText();
	}

	public void appendHeader(List<String> headers) {
		for(String header: headers) {
			headKey.addItem(header);
		}
	}
	
}


