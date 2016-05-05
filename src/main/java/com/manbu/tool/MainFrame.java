package com.manbu.tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

import com.manbu.tool.ui.AuthorizationPanel;
import com.manbu.tool.ui.RequestInputPanel;
import com.manbu.tool.ui.ResponseOutputPanel;
import com.manbu.tool.ui.UrlInputPanel;

public class MainFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9157780038093079000L;

	private JPanel contentPanel;

	private UrlInputPanel urlInputPanel;
	private RequestInputPanel requestInputPanel;
	private ResponseOutputPanel responseOutputPanel;
	private AuthorizationPanel authorizationPanel;

	private Descriptions descriptions = ConfigLoader.loadDescription();

	public MainFrame() {

		urlInputPanel = new UrlInputPanel(this);
		requestInputPanel = new RequestInputPanel();
		responseOutputPanel = new ResponseOutputPanel();
		authorizationPanel = new AuthorizationPanel();

		contentPanel = (JPanel) this.getContentPane();
		contentPanel.setLayout(null);

		contentPanel.add(urlInputPanel);
		contentPanel.add(requestInputPanel);
		contentPanel.add(responseOutputPanel);
		contentPanel.add(authorizationPanel);

		List<String> urls = descriptions.getUrls();
		List<String> headers = descriptions.getHeaders();
		urlInputPanel.appendUrl(urls);
		requestInputPanel.appendHeader(headers);

		setTitle("Http Test Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(720, 650);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private static final int CONNECTION_TIME_OUT = 30000;

	public void actionPerformed(ActionEvent e) {

		// clear
		responseOutputPanel.clear();
		// clear

		descriptions.appendUrl(urlInputPanel.getUrl());
		Map<String, String> headersMap = requestInputPanel.getHeaders();
		Iterator<String> iteratorTemp = headersMap.keySet().iterator();
		while (iteratorTemp.hasNext()) {
			String key = iteratorTemp.next();
			descriptions.appendHeader(key);
		}
		ConfigLoader.saveDescription(descriptions);

		HttpConnectionManager connectionManager = new SimpleHttpConnectionManager(false);

		HttpConnectionManagerParams connectionParams = new HttpConnectionManagerParams();
		connectionParams.setConnectionTimeout(CONNECTION_TIME_OUT);
		connectionManager.setParams(connectionParams);
		HttpClient client = new HttpClient(connectionManager);

		try {
			InputStream inputStream = null;
			URI uri = new URI(urlInputPanel.getUrl(), true);
			HttpMethod method = getMethod();
			method.setRequestHeader("Connection", "Keep-Alive");

			if (method instanceof EntityEnclosingMethod) {
				inputStream = new ByteArrayInputStream(requestInputPanel.getData().getBytes());
				InputStreamRequestEntity inputStreamEntity = new InputStreamRequestEntity(inputStream);
				((EntityEnclosingMethod) method).setRequestEntity(inputStreamEntity);
			}
			method.setURI(uri);

			Map<String, String> headers = requestInputPanel.getHeaders();
			Iterator<String> iterator = headers.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = headers.get(key);
				method.setRequestHeader(key, value);
			}

			String bac = authorizationPanel.getBasicAuthorizationCode();
			if (bac != null) {
				method.setRequestHeader("Authorization", bac);
			}

			method.getParams().setIntParameter(HttpMethodParams.SO_TIMEOUT, CONNECTION_TIME_OUT);

			int status = client.executeMethod(method);
			String body = new String(method.getResponseBody(),"utf-8");
			Header[] responseHeaders = method.getResponseHeaders();
			headers = new HashMap<String, String>();
			for (Header h : responseHeaders) {
				headers.put(h.getName(), h.getValue());
			}
			responseOutputPanel.setHeaders(headers);

			responseOutputPanel.setStatus(status);
			responseOutputPanel.setData(body);

			IOUtils.closeQuietly(inputStream);

			connectionManager.closeIdleConnections(0);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.OK_OPTION);
			ex.printStackTrace();
		}

	}

	public HttpMethod getMethod() {
		HttpMethod httpMethod = null;
		String method = urlInputPanel.getMethod();
		if (method.equalsIgnoreCase("GET")) {
			httpMethod = new GetMethod();
		} else if (method.equalsIgnoreCase("HEAD")) {
			httpMethod = new HeadMethod();
		} else if (method.equalsIgnoreCase("POST")) {
			httpMethod = new PostMethod();
		} else if (method.equalsIgnoreCase("PUT")) {
			httpMethod = new PutMethod();
		} else if (method.equalsIgnoreCase("DELETE")) {
			httpMethod = new DeleteMethod();
		} else if (method.equalsIgnoreCase("TRACE")) {
			httpMethod = new GetMethod();
		}

		return httpMethod;
	}

}
