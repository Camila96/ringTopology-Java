package frameConsole;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import connection.Connection;
import node.Node;

public class ConsoleNode implements Runnable{

	private Thread thread;

	private int serverPort;
	private boolean dataServer;

	private int clientPort;
	private String ip;

	private String command;

	private Scanner scanner;

	private Node node;

	public ConsoleNode() {

		this.thread = new Thread(this);

		this.node = new Node();

		this.scanner = new Scanner(System.in);

		System.out.println("--------------------------- Node settings ---------------------------");
		try {
			System.out.println("NODE IP: " + Connection.findIp());
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Server port: ");
		this.serverPort = this.scanner.nextInt();
		System.out.print("Is dataserver? (yes/no): ");
		switch (this.scanner.next()) {
		case "yes":
			this.dataServer = true;
			break;

		case "no":
			this.dataServer = false;
			break;
		}
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Client");
		System.out.print("ip: ");
		this.ip = this.scanner.next();
		System.out.print("Client port: ");
		this.clientPort = this.scanner.nextInt();
		System.out.println("----------------------------------------------------------------------");
		System.out.print("Turn on node? (yes/no): ");
		switch (this.scanner.next()) {
		case "yes":
			this.node.setDataServer(this.dataServer);
			try {
				this.node.turnOnServer(this.serverPort);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			try {
				this.node.turnOnClient(this.clientPort, this.ip);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			break;

		case "no":
			System.exit(0);
			break;
		}
		System.out.println("----------------------------------------------------------------------");

		this.thread.start();
	}

	@Override
	public void run() {

		while(true){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Server status:  " + this.node.getConnectionServer().getStatus());
			System.out.println("Client status:  " + this.node.getConnectionClient().getStatus());
			this.command = this.scanner.next();
		}

	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public boolean isDataServer() {
		return dataServer;
	}

	public void setDataServer(boolean dataServer) {
		this.dataServer = dataServer;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
}
