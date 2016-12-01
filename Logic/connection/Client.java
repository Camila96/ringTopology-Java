package connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import node.Node;
import task.Message;

public class Client implements Runnable{

	private int port;
	private String ip;
	private Node node;
	private String status;

	private Socket socket;

	private ObjectOutputStream objectOutputStream;

	private Thread threadClient;

	private ArrayList<Message> leftMessages;
	private ArrayList<Message> rightMessages;

	private ClientListen clientListen;

	private boolean comeBack;

	public Client(int port, String ip, Node node) throws UnknownHostException, IOException{

		this.status = Constant.SEARCHING;

		this.port = port;
		this.ip = ip;
		this.node = node;
		this.threadClient = new Thread(this);
		this.rightMessages = node.getRightMessages();
		this.leftMessages = node.getLeftMessages();
		this.comeBack = false;
	}

	private void searching(){
		try {
			this.socket = new Socket(this.ip, this.port);
			this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
			this.status = Constant.CONNECTED;
			this.clientListen = new ClientListen(this);
		} catch (IOException e) {
			this.status = Constant.SEARCHING;
		}
	}

	private void connected(){
		Constant.waitThread();
		if((this.rightMessages.isEmpty() == false)){
			try {
				this.objectOutputStream.writeObject(this.rightMessages.get(0));
				this.rightMessages.remove(0);				
			} catch (IOException e) {
				this.status = Constant.BROKEN;
				System.err.println(e.getMessage());
			}
		}
	}

	private void broken(){
		Constant.waitThread();
		if (!this.comeBack) {
			System.out.println("Cliente no puede enviar");
			ArrayList<Message> aux = this.leftMessages;
			this.leftMessages = new ArrayList<Message>();
			this.leftMessages.add(new Message(Constant.COME_BACK));
			this.leftMessages.addAll(aux);
		}
	}

	private void client(){
		switch (this.status) {
		case Constant.SEARCHING:
			this.searching();
			break;
		case Constant.CONNECTED:
			this.connected();
			break;
		case Constant.BROKEN:
			this.broken();
			break;
		}
	}

	@Override
	public void run() {
		while(true){
			this.client();
		}
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ArrayList<Message> getLeftMessages() {
		return leftMessages;
	}

	public void setLeftMessages(ArrayList<Message> leftMessages) {
		this.leftMessages = leftMessages;
	}

	public ArrayList<Message> getRightMessages() {
		return rightMessages;
	}

	public void setRightMessages(ArrayList<Message> rightMessages) {
		this.rightMessages = rightMessages;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Thread getThreadClient() {
		return threadClient;
	}

	public void setThreadClient(Thread threadClient) {
		this.threadClient = threadClient;
	}

	public ClientListen getClientListen() {
		return clientListen;
	}

	public void setClientListen(ClientListen clientListen) {
		this.clientListen = clientListen;
	}

	public boolean isComeBack() {
		return comeBack;
	}

	public void setComeBack(boolean comeBack) {
		this.comeBack = comeBack;
	}

	
}
