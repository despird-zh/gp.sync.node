package gp.sync.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import org.apache.commons.lang.StringUtils;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Component;
import javax.swing.JSeparator;

public class SyncTestMainGui {

	private JFrame frmGroupressSyncTest;

	SyncTests syncTests = new SyncTests();
	
	private String version = "1.0";
	private JTextField endpointText;
	private JTextField loginText;
	private JTextField passText;
	private JTextField apiPrefix;
	private JTextField tokenText;
	SyncTestSupport support;
	private JTextField txtCenterServer;
	private JTextField txtCenterPort;
	private JTextArea logText;
	JTextArea sendArea;
	JTextArea receiveArea;
	private JComboBox<String> comboAPIs;
	private JTextField txtgpapi;
	private JLabel stateMessageLbl;
	private JComboBox<String> comboHttpApis;
	private JTextField txtNodePort;
	private JTextField txtNodeServer;
	private JTextField txtNodeToken;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SyncTestMainGui window = new SyncTestMainGui();
					window.frmGroupressSyncTest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SyncTestMainGui() {
		initialize();
		
		this.support = new SyncTestSupport(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGroupressSyncTest = new JFrame();
		frmGroupressSyncTest.setTitle("Groupress Sync Test Tool (" + version + ")");
		frmGroupressSyncTest.setBounds(100, 100, 1047, 720);
		frmGroupressSyncTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmGroupressSyncTest.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Client API Testing", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
	
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(null);
		panel_2.setPreferredSize(new Dimension(10, 185));
		panel_2.setSize(new Dimension(0, 100));
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Endpoint   ws://");
		lblNewLabel.setBounds(6, 6, 105, 16);
		panel_2.add(lblNewLabel);
		
		endpointText = new JTextField();
		endpointText.setText("/gpcenter");
		endpointText.setBounds(385, 1, 271, 26);
		panel_2.add(endpointText);
		endpointText.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Authen");
		lblNewLabel_1.setBounds(6, 34, 61, 16);
		panel_2.add(lblNewLabel_1);
		
		loginText = new JTextField();
		loginText.setText("dev1");
		loginText.setBounds(108, 29, 225, 26);
		panel_2.add(loginText);
		loginText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Passcode");
		lblNewLabel_2.setBounds(358, 34, 61, 16);
		panel_2.add(lblNewLabel_2);
		
		passText = new JTextField();
		passText.setText("1");
		passText.setBounds(431, 29, 225, 26);
		panel_2.add(passText);
		passText.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("WSocket Msgs");
		lblNewLabel_3.setBounds(6, 62, 105, 16);
		panel_2.add(lblNewLabel_3);
		
		comboAPIs = new JComboBox<String>();
		comboAPIs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selItem = (String)comboAPIs.getSelectedItem();
				String data = syncTests.getTestData(selItem);
				sendArea.setText(data);
			}
		});
		comboAPIs.setModel(new DefaultComboBoxModel(new String[] {"", "/sync.push", "/hello", "/test"}));
		comboAPIs.setBounds(358, 58, 298, 27);
		panel_2.add(comboAPIs);
		
		apiPrefix = new JTextField();
		apiPrefix.setText("/gpapp");
		apiPrefix.setBounds(108, 57, 225, 26);
		panel_2.add(apiPrefix);
		apiPrefix.setColumns(10);
		
		JButton btnNewButton = new JButton("login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = "http://" + txtCenterServer.getText() + ":" + txtCenterPort.getText() + "/authenticate";
				support.loginCenter(loginText.getText(), passText.getText(), url);
			}
		});
		btnNewButton.setBounds(668, 29, 96, 29);
		panel_2.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("send");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!support.isReady()) {
					setMessage("not ready please connect first");
					return;
				}
				String url = apiPrefix.getText() + comboAPIs.getModel().getElementAt(comboAPIs.getSelectedIndex());
				String msg = sendArea.getText();
				String token = tokenText.getText();
				support.send(url, msg, token);
			}
		});
		btnNewButton_1.setBounds(668, 57, 96, 29);
		panel_2.add(btnNewButton_1);
		
		tokenText = new JTextField();
		tokenText.setBounds(108, 84, 768, 26);
		panel_2.add(tokenText);
		tokenText.setColumns(10);
		
		JLabel lblToken = new JLabel("Token");
		lblToken.setBounds(6, 90, 61, 16);
		panel_2.add(lblToken);
		
		JLabel lblNewLabel_6 = new JLabel("+");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(333, 62, 24, 16);
		panel_2.add(lblNewLabel_6);
		
		txtCenterServer = new JTextField();
		txtCenterServer.setText("localhost");
		txtCenterServer.setBounds(109, 1, 149, 26);
		panel_2.add(txtCenterServer);
		txtCenterServer.setColumns(10);
		
		JLabel label = new JLabel(":");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(259, 6, 15, 16);
		panel_2.add(label);
		
		txtCenterPort = new JTextField();
		txtCenterPort.setText("8080");
		txtCenterPort.setBounds(281, 1, 79, 26);
		panel_2.add(txtCenterPort);
		txtCenterPort.setColumns(10);
		
		JButton btnConnect = new JButton("connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = "ws://" + txtCenterServer.getText() + ":" + txtCenterPort.getText() + endpointText.getText();
				String token = tokenText.getText();
				if(StringUtils.isNotBlank(token)) {
					support.connect(token, url);
					return;
				}
				String login = loginText.getText();
				String passcode = passText.getText();
				
				support.connect(login, passcode, url);
			}
		});
		btnConnect.setBounds(668, 1, 96, 29);
		panel_2.add(btnConnect);
		
		JButton btnDisconn = new JButton("disconn");
		btnDisconn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				support.disconnect();
			}
		});
		btnDisconn.setBounds(776, 1, 100, 29);
		panel_2.add(btnDisconn);
		
		JLabel lblHttpApis = new JLabel("Apis       http://");
		lblHttpApis.setBounds(6, 130, 105, 16);
		panel_2.add(lblHttpApis);
		
		txtgpapi = new JTextField();
		txtgpapi.setText("/gpapi");
		txtgpapi.setColumns(10);
		txtgpapi.setBounds(385, 125, 74, 26);
		panel_2.add(txtgpapi);
		
		JLabel label_1 = new JLabel("+");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(471, 130, 24, 16);
		panel_2.add(label_1);
		
		comboHttpApis = new JComboBox<String>();
		comboHttpApis.setModel(new DefaultComboBoxModel(new String[] {"", "/sync-push"}));
		comboHttpApis.setBounds(494, 126, 162, 27);
		panel_2.add(comboHttpApis);
		
		JButton button = new JButton("send");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if("".equals(tokenText.getText())) {
					setMessage("Please login the server firstly!");
					return;
				}
				if("".equals((String)comboHttpApis.getSelectedItem())) {
					setMessage("Please select a http api!");
					return;
				}else {
					String token = tokenText.getText();
					String pushData = syncTests.getTestData("/sync.push");
					String url = "http://" + txtNodeServer.getText() + ":" + txtNodePort.getText() + txtgpapi.getText() + (String)comboHttpApis.getSelectedItem();
					support.sendNodeData(url, token, pushData);
				}
			}
		});
		button.setBounds(780, 125, 96, 29);
		panel_2.add(button);
		
		txtNodePort = new JTextField();
		txtNodePort.setText("8081");
		txtNodePort.setColumns(10);
		txtNodePort.setBounds(281, 125, 79, 26);
		panel_2.add(txtNodePort);
		
		JLabel label_2 = new JLabel(":");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(259, 130, 15, 16);
		panel_2.add(label_2);
		
		txtNodeServer = new JTextField();
		txtNodeServer.setText("localhost");
		txtNodeServer.setColumns(10);
		txtNodeServer.setBounds(108, 125, 149, 26);
		panel_2.add(txtNodeServer);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(16, 111, 991, 12);
		panel_2.add(separator);
		
		JButton nodeLogin = new JButton("login");
		nodeLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = "http://" + txtNodeServer.getText() + ":" + txtNodePort.getText() + "/authenticate";
				support.loginNode(loginText.getText(), passText.getText(), url);
			}
		});
		nodeLogin.setBounds(668, 125, 96, 29);
		panel_2.add(nodeLogin);
		
		JLabel label_3 = new JLabel("Token");
		label_3.setBounds(6, 158, 61, 16);
		panel_2.add(label_3);
		
		txtNodeToken = new JTextField();
		txtNodeToken.setColumns(10);
		txtNodeToken.setBounds(108, 153, 768, 26);
		panel_2.add(txtNodeToken);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(null);
		splitPane.setDividerSize(6);
		panel.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 10, 10, 5));
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("Operation Log");
		lblNewLabel_4.setBorder(new EmptyBorder(5, 10, 5, 5));
		panel_3.add(lblNewLabel_4, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		panel_3.add(scrollPane, BorderLayout.CENTER);
		
		logText = new JTextArea();
		logText.setLineWrap(true);
		scrollPane.setViewportView(logText);
		
		JButton btnNewButton_2 = new JButton("Clear");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearLog();
			}
		});
		panel_3.add(btnNewButton_2, BorderLayout.SOUTH);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBorder(null);
		splitPane_1.setResizeWeight(0.5);
		splitPane.setRightComponent(splitPane_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		splitPane_1.setLeftComponent(scrollPane_1);
		
		sendArea = new JTextArea();
		sendArea.setLineWrap(true);
		scrollPane_1.setViewportView(sendArea);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(null);
		splitPane_1.setRightComponent(scrollPane_2);
		
		receiveArea = new JTextArea();
		receiveArea.setLineWrap(true);
		scrollPane_2.setViewportView(receiveArea);
		splitPane_1.setDividerLocation(350);
		splitPane.setDividerLocation(250);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(1, 0, 0, 0));
		panel_4.setPreferredSize(new Dimension(10, 30));
		panel.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		stateMessageLbl = new JLabel("");
		panel_4.add(stateMessageLbl);
		
		JLabel lblNewLabel_8 = new JLabel("  Message: ");
		panel_4.add(lblNewLabel_8, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Tool Info", null, panel_1, null);
		
		JLabel lblNewLabel_5 = new JLabel("not implemented yet");
		panel_1.add(lblNewLabel_5);
	}
	
	public void clearLog() {
		this.logText.setText("");
	}
	
	public void setCenterToken(String token) {
		this.tokenText.setText(token);
	}
	
	public void setNodeToken(String token) {
		this.txtNodeToken.setText(token);
	}
	
	public void appendLog(String msg) {
		int offset = this.logText.getDocument().getLength();
		try {
			this.logText.getDocument().insertString(offset, offset == 0? msg : "\n" + msg, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void appendReceived(String msg) {
		int offset = this.receiveArea.getDocument().getLength();
		try {
			this.receiveArea.getDocument().insertString(offset, offset == 0 ? msg : "\n" + msg, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setResult(String result) {
		this.receiveArea.setText(result);
	}
	
	public void setMessage(String message) {
		this.stateMessageLbl.setText(message);
	}
}
