package jframes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import connection.Connection;
import jlabels.JLabelStatus;
import node.Node;

public class JFrameNode extends JFrame implements ActionListener, Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanelServer;

	private JCheckBox jCheckBoxDataServer;

	private JTextField jTextFieldServerPort;

	private JTextArea jTextAreaServer;

	private JScrollPane jScrollPaneServer;

	private JLabelStatus jLabelStatusServer;

	private JLabel jLabelServerNews;

	private JPanel jPanelClient;

	private JTextField jTextFieldClientPort;
	private JTextField jTextFieldIp;

	private JLabelStatus jLabelStatusClient;

	private JButton jButtonTurnOnNode;
	private JButton jButtonTurnOffNode;

	private JLabel jLabelClientNews;

	private Thread threadJFrameNode;

	private Node node;

	public JFrameNode() {

		this.node = new Node();

		this.setTitle("Node");
		this.setSize(800, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		//		************************************************************
		this.jLabelServerNews = new JLabel(Connection.OFF, JLabel.CENTER);
		this.jLabelClientNews = new JLabel(Connection.OFF, JLabel.CENTER);
		//		************************************************************
		JPanel jPanelServerClient = new JPanel(new GridLayout(1, 2));
		//		************************************************************
		this.jPanelServer = new JPanel(new BorderLayout());
		this.jPanelServer.setBorder(BorderFactory.createTitledBorder("Server"));
		JPanel jPanelA = new JPanel(new GridLayout(4, 1));
		jPanelA.setBorder(BorderFactory.createEtchedBorder());
		jPanelA.add(this.jLabelStatusServer = new JLabelStatus());
		JPanel jPanelB = new JPanel(new BorderLayout());
		jPanelB.add(new JLabel("  Ip  "), BorderLayout.WEST);
		try {
			jPanelB.add(new JLabel(Connection.findIp(), JLabel.CENTER));
		} catch (UnknownHostException e) {
			this.jLabelServerNews.setText(e.getMessage());
		}
		JPanel jPanelC = new JPanel(new BorderLayout());
		jPanelC.add(new JLabel("  Port  "), BorderLayout.WEST);
		jPanelC.add(this.jTextFieldServerPort = new JTextField(), BorderLayout.CENTER);
		JPanel jPanelH = new JPanel(new BorderLayout());
		jPanelH.add(new JLabel("  Data server  ", JLabel.RIGHT), BorderLayout.CENTER);
		jPanelH.add(this.jCheckBoxDataServer = new JCheckBox(), BorderLayout.EAST);
		jPanelA.add(jPanelB);
		jPanelA.add(jPanelC);
		jPanelA.add(jPanelH);
		this.jPanelServer.add(jPanelA, BorderLayout.NORTH);
		//		*************************************************************
		this.jPanelClient = new JPanel(new BorderLayout());
		this.jPanelClient.setBorder(BorderFactory.createTitledBorder("Client"));
		JPanel jPanelD = new JPanel(new GridLayout(3, 1));
		jPanelD.setBorder(BorderFactory.createEtchedBorder());
		jPanelD.add(this.jLabelStatusClient = new JLabelStatus());
		JPanel jPanelE = new JPanel(new BorderLayout());
		jPanelE.add(new JLabel("  Ip  "), BorderLayout.WEST);
		jPanelE.add(this.jTextFieldIp = new JTextField(), BorderLayout.CENTER);
		JPanel jPanelF = new JPanel(new BorderLayout());
		jPanelF.add(new JLabel("  Port  "), BorderLayout.WEST);
		jPanelF.add(this.jTextFieldClientPort = new JTextField(), BorderLayout.CENTER);
		jPanelD.add(jPanelE);
		jPanelD.add(jPanelF);
		this.jPanelClient.add(jPanelD, BorderLayout.NORTH);
		//		*************************************************************
		JPanel jPanelG = new JPanel(new BorderLayout());
		this.jLabelServerNews.setBorder(BorderFactory.createEtchedBorder());
		jPanelG.add(this.jLabelServerNews, BorderLayout.SOUTH);
		this.jPanelServer.add(jPanelG, BorderLayout.CENTER);
		this.jScrollPaneServer = new JScrollPane(this.jTextAreaServer = new JTextArea());
		this.jTextAreaServer.setEnabled(false);
		jPanelG.add(this.jScrollPaneServer);
		//		*************************************************************
		JPanel jPanelI = new JPanel(new BorderLayout());
		this.jLabelClientNews.setBorder(BorderFactory.createEtchedBorder());
		jPanelI.add(this.jLabelClientNews, BorderLayout.SOUTH);
		this.jPanelClient.add(jPanelI, BorderLayout.CENTER);
		//		*************************************************************
		jPanelServerClient.add(this.jPanelServer);
		jPanelServerClient.add(this.jPanelClient);
		this.add(jPanelServerClient, BorderLayout.CENTER);
		this.jButtonTurnOnNode = new JButton("Turn on node");
		this.jButtonTurnOnNode.addActionListener(this);
		this.add(this.jButtonTurnOnNode, BorderLayout.SOUTH);
		//		*********************************************
	}

	private void turnOn(){

		this.remove(this.jButtonTurnOnNode);
		this.jButtonTurnOffNode = new JButton("Turn off node");
		this.jButtonTurnOffNode.addActionListener(this);
		this.add(this.jButtonTurnOffNode, BorderLayout.SOUTH);
	}

	private void turnOff(){

		this.remove(this.jButtonTurnOffNode);
		this.jButtonTurnOnNode = new JButton("Turn on node");
		this.jButtonTurnOnNode.addActionListener(this);
		this.add(this.jButtonTurnOnNode, BorderLayout.SOUTH);

	}

	private void turnOnServer() throws IOException{

		this.jCheckBoxDataServer.setEnabled(false);
		this.jTextFieldServerPort.setEnabled(false);
		this.jLabelServerNews.setText(Connection.WAITING);

		if (this.jCheckBoxDataServer.isSelected()) {			
			this.node.setDataServer(true);
		}else{
			this.node.setDataServer(false);
		}
		this.node.turnOnServer(Integer.valueOf(this.jTextFieldServerPort.getText()));

		this.threadJFrameNode = new Thread(this);
		this.threadJFrameNode.start();
	}

	@SuppressWarnings("deprecation")
	private void turnOffServer() throws IOException{

		this.jCheckBoxDataServer.setEnabled(true);
		this.jCheckBoxDataServer.setSelected(false);
		this.jTextFieldServerPort.setEnabled(true);
		this.jTextFieldServerPort.setText("");
		this.jCheckBoxDataServer.setSelected(false);
		this.jLabelServerNews.setText(Connection.OFF);

		this.node.turnOffServer();

		this.threadJFrameNode.stop();

		this.jLabelStatusServer.setColorJLabel(JLabelStatus.COLOR_RED);
		this.jLabelServerNews.setText(this.node.getConnectionServer().getStatus());
		this.repaint();
	}

	private void turnOnCLient() throws NumberFormatException, UnknownHostException, IOException{

		this.jTextFieldClientPort.setEnabled(false);
		this.jTextFieldIp.setEnabled(false);

		this.jLabelClientNews.setText(Connection.SEARCHING);

		this.node.turnOnClient(Integer.parseInt(this.jTextFieldClientPort.getText()), this.jTextFieldIp.getText());
		this.repaint();
	}

	private void turnOffClient(){

		this.jTextFieldClientPort.setEnabled(true);
		this.jTextFieldIp.setEnabled(true);

		this.jTextFieldClientPort.setText("");
		this.jTextFieldIp.setText("");

		this.jLabelStatusClient.setColorJLabel(JLabelStatus.COLOR_RED);
		this.jLabelClientNews.setText(Connection.OFF);
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource().equals(jButtonTurnOnNode)){
			this.turnOn();
			try {
				this.turnOnServer();
			} catch (IOException e1) {
				this.jLabelServerNews.setText(e1.getMessage());
			}
			try {
				this.turnOnCLient();
			} catch (Exception e2) {
				this.jLabelClientNews.setText(e2.getMessage());
			}
		}
		if(e.getSource().equals(jButtonTurnOffNode)){
			this.turnOff();
			try {
				this.turnOffServer();
			} catch (IOException e1) {
				this.jLabelServerNews.setText(e1.getMessage());
			}
			try {
				this.turnOffClient();
			} catch (Exception e2) {
				this.jLabelClientNews.setText(e2.getMessage());
			}
		}
	}

	@Override
	public void run() {

		while(true){

			if(this.node.getFileActions() != null){
				String string = "";
				ArrayList<String> arrayList = this.node.getFileActions().read("messages.txt");
				for (int i = 0; i < arrayList.size(); i++) {
					string += arrayList.get(i)+"\n";
				}
				this.jTextAreaServer.setText(string);
			}

			this.jLabelStatusServer.setColorJLabel(JLabelStatus.COLOR_BACKGROUND);
			this.jLabelStatusClient.setColorJLabel(JLabelStatus.COLOR_BACKGROUND);

			this.repaint();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				this.jLabelServerNews.setText(e.getMessage());
			}
			switch (this.node.getConnectionServer().getStatus()) {
			case Connection.WAITING:
				this.jLabelStatusServer.setColorJLabel(JLabelStatus.COLOR_ORANGE);
				break;
			case Connection.LISTENING:
				this.jLabelStatusServer.setColorJLabel(JLabelStatus.COLOR_GREEN);
				this.jLabelServerNews.setText(this.node.getConnectionServer().getStatus());
				break;
			case Connection.OFF:
				this.jLabelStatusServer.setColorJLabel(JLabelStatus.COLOR_RED);
				break;
			}

			switch (this.node.getConnectionClient().getStatus()) {
			case Connection.SEARCHING:
				this.jLabelStatusClient.setColorJLabel(JLabelStatus.COLOR_ORANGE);
				break;
			case Connection.CONNECTED:
				this.jLabelStatusClient.setColorJLabel(JLabelStatus.COLOR_GREEN);
				this.jLabelClientNews.setText(this.node.getConnectionClient().getStatus());
				break;
			case Connection.OFF:
				this.jLabelStatusClient.setColorJLabel(JLabelStatus.COLOR_RED);
				break;
			}
			this.repaint();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				this.jLabelServerNews.setText(e.getMessage());
			}
		}
	}

	public JPanel getjPanelServer() {
		return jPanelServer;
	}

	public void setjPanelServer(JPanel jPanelServer) {
		this.jPanelServer = jPanelServer;
	}

	public JCheckBox getjCheckBoxDataServer() {
		return jCheckBoxDataServer;
	}

	public void setjCheckBoxDataServer(JCheckBox jCheckBoxDataServer) {
		this.jCheckBoxDataServer = jCheckBoxDataServer;
	}

	public JTextField getjTextFieldServerPort() {
		return jTextFieldServerPort;
	}

	public void setjTextFieldServerPort(JTextField jTextFieldServerPort) {
		this.jTextFieldServerPort = jTextFieldServerPort;
	}

	public JLabelStatus getjLabelStatusServer() {
		return jLabelStatusServer;
	}

	public void setjLabelStatusServer(JLabelStatus jLabelStatusServer) {
		this.jLabelStatusServer = jLabelStatusServer;
	}

	public JLabel getjLabelServerNews() {
		return jLabelServerNews;
	}

	public void setjLabelServerNews(JLabel jLabelServerNews) {
		this.jLabelServerNews = jLabelServerNews;
	}

	public JPanel getjPanelClient() {
		return jPanelClient;
	}

	public void setjPanelClient(JPanel jPanelClient) {
		this.jPanelClient = jPanelClient;
	}

	public JTextField getjTextFieldClientPort() {
		return jTextFieldClientPort;
	}

	public void setjTextFieldClientPort(JTextField jTextFieldClientPort) {
		this.jTextFieldClientPort = jTextFieldClientPort;
	}

	public JTextField getjTextFieldIp() {
		return jTextFieldIp;
	}

	public void setjTextFieldIp(JTextField jTextFieldIp) {
		this.jTextFieldIp = jTextFieldIp;
	}

	public JLabelStatus getjLabelStatusClient() {
		return jLabelStatusClient;
	}

	public void setjLabelStatusClient(JLabelStatus jLabelStatusClient) {
		this.jLabelStatusClient = jLabelStatusClient;
	}

	public JButton getjButtonTurnOnNode() {
		return jButtonTurnOnNode;
	}

	public void setjButtonTurnOnNode(JButton jButtonTurnOnNode) {
		this.jButtonTurnOnNode = jButtonTurnOnNode;
	}

	public JButton getjButtonTurnOffNode() {
		return jButtonTurnOffNode;
	}

	public void setjButtonTurnOffNode(JButton jButtonTurnOffNode) {
		this.jButtonTurnOffNode = jButtonTurnOffNode;
	}

	public JLabel getjLabelClientNews() {
		return jLabelClientNews;
	}

	public void setjLabelClientNews(JLabel jLabelClientNews) {
		this.jLabelClientNews = jLabelClientNews;
	}

	public Thread getThreadJFrameNode() {
		return threadJFrameNode;
	}

	public void setThreadJFrameNode(Thread threadJFrameNode) {
		this.threadJFrameNode = threadJFrameNode;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

}
