package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import task.Message;

public class ClientListen implements Runnable{
	
	private ArrayList<Message> leftMessages;
	private Socket socket;
	private ObjectInputStream objectInputStream;
	private Thread threadListen;

	public ClientListen(Socket socket, ArrayList<Message> leftMessages) throws IOException {
		this.socket = socket;
		this.leftMessages = leftMessages;
		this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
		this.threadListen = new Thread(this);
	}

	public void receive(){
		System.out.println("client listen");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Message message = (Message) this.objectInputStream.readObject();
			this.leftMessages.add(message);
			System.out.println(message.toString());				
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true){
			this.receive();
		}
	}

	public ArrayList<Message> getLeftMessages() {
		return leftMessages;
	}

	public void setLeftMessages(ArrayList<Message> leftMessages) {
		this.leftMessages = leftMessages;
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

	public Thread getThreadListen() {
		return threadListen;
	}

	public void setThreadListen(Thread threadListen) {
		this.threadListen = threadListen;
	}

}