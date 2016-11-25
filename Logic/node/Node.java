package node;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import connection.Client;
import connection.Connection;
import connection.Server;
import task.Message;

public class Node {

	private ArrayList<Message> leftMessages;
	private ArrayList<Message> rightMessages;


	private Server server;
	private Client client;

	public Node(){
		this.leftMessages = new ArrayList<Message>();
		this.rightMessages = new ArrayList<Message>();
		this.server = null;
		this.client = null;
	}

	public void turnOnServer(int port) throws IOException{
		this.server = new Server(port, this.rightMessages, this.leftMessages);
		this.server.getThreadServer().start();
	}


	public void turnOffServer() throws IOException{
		this.server.setStatus(Connection.OFF);
	}


	public void turnOnClient(int port, String ip) throws UnknownHostException, IOException{

		this.client = new Client(port, ip, this.rightMessages, this.leftMessages);
		this.client.getThreadClient().start();
	}

	public ArrayList<Message> getLeftMessages() {
		return leftMessages;
	}

	public void setLeftMessages(ArrayList<Message> leftMessages) {
		this.leftMessages = leftMessages;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<Message> getRightMessages() {
		return rightMessages;
	}

	public void setRightMessages(ArrayList<Message> rightMessages) {
		this.rightMessages = rightMessages;
	}
}