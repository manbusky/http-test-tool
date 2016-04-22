package com.manbu.tool.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class AuthorizationPanel extends JPanel {

	private JTextField usernameField;
	private JTextField passwordField;

	public AuthorizationPanel() {

		setLayout(null);
		setBounds(10, 575, 740, 400);
		// setBorder(BorderFactory.createTitledBorder("BasicAuthorization"));

		JLabel lable = new JLabel("BasicAuthorization:");
		lable.setBounds(10, 5, 150, 25);
		add(lable);

		lable = new JLabel("username:");
		lable.setBounds(150, 5, 80, 25);
		add(lable);

		usernameField = new JTextField("");
		usernameField.setBounds(220, 5, 100, 25);
		add(usernameField);

		lable = new JLabel("password:");
		lable.setBounds(360, 5, 80, 25);
		add(lable);

		passwordField = new JTextField("");
		passwordField.setBounds(430, 5, 100, 25);
		add(passwordField);
	}

	public String getBasicAuthorizationCode() {
		String username = usernameField.getText().trim();
		String password = passwordField.getText().trim();
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
			return "Basic " + new String(Base64.encodeBase64("admin:admin".getBytes()));
		}
		return null;
	}

}
