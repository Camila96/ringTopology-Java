package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import task.Message;

public class Connection implements Runnable{

	public static final String OFF = "OFF";
	public static final String ON = "ON";

	public static final String WAITING = "WAITING";
	public static final String SEARCHING = "SEARCHING";
	public static final String CONNECTED = "CONNECTED";
	public static final String LISTENING = "LISTENING";

	public static final String SERVER = "SERVER";
	public static final String CLIENT = "CLIENT";

	public static final String OPEN = "OPEN";
	public static final String CLOSE = "CLOSE";

	private int port;

	private String ip;
	private String status;
	private String connectionType;

	private ServerSocket serverSocket;
	private Socket socket;

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	private Thread threadConnection;

	private ArrayList<Message> messages;

	private Message message;

	public Connection(int port) throws IOException {

		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		this.threadConnection = new Thread(this);
		this.status = Connection.WAITING;
		this.connectionType = Connection.SERVER;
		this.messages = new ArrayList<Message>();
	}

	public Connection(int port, String ip) throws UnknownHostException, IOException{

		this.port = port;
		this.ip = ip;
		this.threadConnection = new Thread(this);
		this.status = Connection.SEARCHING;
		this.connectionType = Connection.CLIENT;
		this.message = null;
	}

	public static String findIp() throws UnknownHostException{
		return InetAddress.getLocalHost().getHostAddress();
	}

	@SuppressWarnings("deprecation")
	private void server() {

		switch (this.status) {
		case Connection.WAITING:
			try {
				this.socket = this.serverSocket.accept();
				this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
				this.status = Connection.LISTENING;
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case Connection.LISTENING:
			try {
				this.messages.add((Message) this.objectInputStream.readObject());
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			break;
		case Connection.OFF:
			try {
				//Arreglar no reinicia el server
				this.objectInputStream.close();
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.threadConnection.stop();
			break;
		}
	}

	private void client(){

		switch (this.status) {
		case Connection.SEARCHING:
			try {
				this.socket = new Socket(this.ip, this.port);
				this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
				this.status = Connection.CONNECTED;
			} catch (IOException e) {
				//				TO-DO
				this.status = Connection.SEARCHING;
				//*************************
			}
			break;

		case Connection.CONNECTED:
			if(this.message != null){
				try {
					this.objectOutputStream.writeObject(this.message);
					this.message = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
	}

	@Override
	public void run() {

		while(true){
			switch (this.connectionType) {
			case Connection.SERVER:
				this.server();
				break;
			case Connection.CLIENT:
				this.client();
				break;
			}
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public Thread getThreadConnection() {
		return threadConnection;
	}

	public void setThreadConnection(Thread threadConnection) {
		this.threadConnection = threadConnection;
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
