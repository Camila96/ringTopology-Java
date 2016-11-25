package connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import task.Message;

public class ServerWrite implements Runnable{

	private ArrayList<Message> leftMessages;
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private Thread threadWrite;

	public ServerWrite(Socket socket, ArrayList<Message> leftMessages) throws IOException {
		this.socket = socket;
		this.leftMessages = leftMessages;
		this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
		this.threadWrite = new Thread(this);
	}

	public void send(){
		System.out.println("serverWrite");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if((this.leftMessages.isEmpty() == false)){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				this.objectOutputStream.writeObject(this.leftMessages.get(0));
				this.leftMessages.remove(0);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while(true){
			this.send();
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

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public Thread getThreadWrite() {
		return threadWrite;
	}

	public void setThreadWrite(Thread threadWrite) {
		this.threadWrite = threadWrite;
	}
}
