package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import task.Message;

public class Client implements Runnable{

	private int port;

	private String ip;
	private String status;

	private Socket socket;

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	private Thread threadClient;

	private ArrayList<Message> leftMessages;
	private ArrayList<Message> rightMessages;
	
	private ClientListen clientListen;


	public Client(int port, String ip, ArrayList<Message> rightMessages, ArrayList<Message> leftMessages) throws UnknownHostException, IOException{

		this.port = port;
		this.ip = ip;
		this.threadClient = new Thread(this);
		this.status = Connection.SEARCHING;
		this.rightMessages = rightMessages;
		this.leftMessages = leftMessages;
	}

	private void client(){

		switch (this.status) {
		case Connection.SEARCHING:
			try {
				this.socket = new Socket(this.ip, this.port);
				this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
				this.status = Connection.CONNECTED;
				this.clientListen = new ClientListen(this.socket, this.leftMessages);
				this.clientListen.getThreadListen().start();
			} catch (IOException e) {
				//				TO-DO
				this.status = Connection.SEARCHING;
				//*************************
			}
			break;

		case Connection.CONNECTED:
			if((this.rightMessages.isEmpty() == false)){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					this.objectOutputStream.writeObject(this.rightMessages.get(0));
					this.rightMessages.remove(0);				
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
			this.client();
		}
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
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

	public Thread getThreadClient() {
		return threadClient;
	}

	public void setThreadClient(Thread threadClient) {
		this.threadClient = threadClient;
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
