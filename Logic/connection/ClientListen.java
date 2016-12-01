package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import task.Message;

public class ClientListen implements Runnable{

	private Thread threadListen;
	private String status;
	private Client client;
	private ObjectInputStream objectInputStream;

	public ClientListen(Client client) throws IOException {
		this.client = client;
		this.objectInputStream = new ObjectInputStream(this.client.getSocket().getInputStream());
		this.status = Constant.LISTENING;
		this.threadListen = new Thread(this);

		this.threadListen.start();		
	}

	private void add(Message message){
		if(message.getType().equals(Constant.COME_BACK)){
			ArrayList<Message> aux = this.client.getLeftMessages();
			this.client.setLeftMessages(new ArrayList<Message>());
			this.client.getLeftMessages().add(message);
			this.client.getLeftMessages().addAll(aux);
		}else{
			this.client.getLeftMessages().add(message);
		}
	}

	private void listening(){
		Message message;
		try {
			message = (Message) this.objectInputStream.readObject();
			this.add(message);
			System.out.println(message.toString());
		} catch (ClassNotFoundException | IOException e) {
			System.err.println(e.getMessage());
			this.status = Constant.WAITING;
		}
	}

	public void waiting(){
//	try {
//			ServerSocket serverSocket = new ServerSocket(port);
//			this.socket = serverSocket.accept();
//			this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
//			this.status = Constant.LISTENING;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	private void clientListen(){
		switch (this.status) {
		case Constant.LISTENING:
			this.listening();
			break;
		case Constant.WAITING:
			this.waiting();
			break;
		}
	}

	@Override
	public void run() {
		while(true){
			this.clientListen();
		}
	}

	public Thread getThreadListen() {
		return threadListen;
	}

	public void setThreadListen(Thread threadListen) {
		this.threadListen = threadListen;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}



}