package frameConsole;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import connection.Server;
import node.Node;
import task.Message;

public class ConsoleNode implements Runnable{

	private Thread threadConsoleNode;
	private int serverPort;
	private int clientPort;
	private String ip;
	private Scanner scanner;

	private Node node;

	public ConsoleNode() throws IOException {

		this.threadConsoleNode = new Thread(this);	
		this.node = new Node();
		this.scanner = new Scanner(System.in);

		System.out.println("--------------------------- Informacion local ---------------------------");
		try {
			System.out.println("NODE IP: " + Server.findIp());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		System.out.println("--------------------------Conectarse a otro host------------------------------");
		System.out.print("Client ip: ");
		this.ip = this.scanner.next();
		System.out.println("----------------------------------------------------------------------");
		System.out.print("Server port: ");
		this.serverPort = this.scanner.nextInt();
		System.out.print("Client port: ");
		this.clientPort = this.scanner.nextInt();

		this.node.turnOnServer(this.serverPort);
		this.node.turnOnClient(this.clientPort, this.ip);
		System.out.println("Conectado :) . . .");

		System.out.println("----------------------------------------------------------------------");

		this.threadConsoleNode.start();


	}

	@Override
	public void run() {

		while(true){
			System.out.println("Digite direccion del mensaje (izq/der) ");
			@SuppressWarnings("resource")
			Scanner scanner1 = new Scanner(System.in);
			switch (scanner1.next()) {
			case "der":
				this.node.getRightMessages().add(new Message());
				break;
			case "izq":
				this.node.getLeftMessages().add(new Message());
				break;
			default: 
				System.out.println("No valido");
				break;
			}

		}

	}

}