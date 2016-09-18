package node;

import java.io.IOException;
import java.net.UnknownHostException;

import connection.Connection;
import data.FileActions;
import task.Message;

public class Node implements Runnable{

	private Connection connectionServer;
	private Connection connectionClient;

	private FileActions fileActions;

	private boolean dataServer;

	private Thread threadNode;

	public Node() {

		this.connectionServer = null;
		this.connectionClient = null;
		this.fileActions = null;
	}

	public void turnOnServer(int port) throws IOException{

		this.connectionServer = new Connection(port);
		this.connectionServer.getThreadConnection().start();
		this.threadNode = new Thread(this);
		this.threadNode.start();
	}

	@SuppressWarnings("deprecation")
	public void turnOffServer() throws IOException{

		this.threadNode.stop();		
		this.connectionServer.setStatus(Connection.OFF);
	}

	public void turnOnClient(int port, String ip) throws UnknownHostException, IOException{

		this.connectionClient = new Connection(port, ip);
		this.connectionClient.getThreadConnection().start();
	}

	private void sendMessages(){

		this.connectionServer.getMessages().add(new Message());
		if((this.connectionServer.getMessages().isEmpty() == false) && (this.connectionClient != null) && (this.connectionClient.getStatus().equals(Connection.CONNECTED))){
			this.connectionClient.setMessage(this.connectionServer.getMessages().get(0));
			this.connectionServer.getMessages().remove(0);
		}
	}

	private void saveMessages(){

		if(this.fileActions == null){
			this.fileActions = new FileActions("messages.txt");
		}else{
			for (int i = 0; i < this.connectionServer.getMessages().size(); i++) {				
				this.fileActions.write(this.connectionServer.getMessages().get(i).toString());
			}
			this.connectionServer.getMessages().clear();
		}
	}

	@Override
	public void run() {

		while(true){

			if(this.dataServer == false){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.sendMessages();
			}else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.saveMessages();
			}
		}
	}

	public Connection getConnectionServer() {
		return connectionServer;
	}

	public void setConnectionServer(Connection connectionServer) {
		this.connectionServer = connectionServer;
	}

	public Connection getConnectionClient() {
		return connectionClient;
	}

	public void setConnectionClient(Connection connectionClient) {
		this.connectionClient = connectionClient;
	}

	public boolean isDataServer() {
		return dataServer;
	}

	public void setDataServer(boolean dataServer) {
		this.dataServer = dataServer;
	}

	public Thread getThreadNode() {
		return threadNode;
	}

	public void setThreadNode(Thread threadNode) {
		this.threadNode = threadNode;
	}

	public FileActions getFileActions() {
		return fileActions;
	}

	public void setFileActions(FileActions fileActions) {
		this.fileActions = fileActions;
	}

}
