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

public class Server implements Runnable{

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
	private Socket socket;
	private ServerSocket serverSocket;
	private Thread threadServer;
	private String status;
	private ArrayList<Message> leftMessages;
	private ArrayList<Message> rightMessages;
	private Message message;

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	private ServerWrite serverWrite;

	public Server(int port, ArrayList<Message> rightMessages, ArrayList<Message> leftMessages) throws IOException {

		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		this.threadServer = new Thread(this);
		this.status = Connection.WAITING;
		this.rightMessages = rightMessages;
		this.leftMessages = leftMessages;
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
				this.serverWrite = new ServerWrite(this.socket, this.leftMessages);
				this.serverWrite.getThreadWrite().start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case Connection.LISTENING:
			try {
				Message message = (Message) this.objectInputStream.readObject();
				this.rightMessages.add(message);
				System.out.println(message.toString());				
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
			this.threadServer.stop();
			break;
		}
	}

	@Override
	public void run() {
		while(true){
			this.server();
		}

	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
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

	public Thread getThreadServer() {
		return threadServer;
	}

	public void setThreadServer(Thread threadServer) {
		this.threadServer = threadServer;
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
}
