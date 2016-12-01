package connection;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerWrite implements Runnable{

	private Thread threadWrite;
	private Server server;
	private String status;
	private ObjectOutputStream objectOutputStream;

	public ServerWrite(Server server) throws IOException {
		this.server = server;
		this.objectOutputStream = new ObjectOutputStream(this.server.getSocket().getOutputStream());
		this.status = Constant.CONNECTED;
		this.threadWrite = new Thread(this);

		this.threadWrite.start();
	}

	@SuppressWarnings("deprecation")
	public boolean pause(){
		if (!this.threadWrite.isInterrupted()) {
			this.threadWrite.suspend();
			return true;
		}else{
			return false;
		}
	}

	public void send(){
		if(!this.server.getLeftMessages().isEmpty()){
			Constant.waitThread();
			try {
				this.objectOutputStream.writeObject(this.server.getLeftMessages().get(0));
				this.server.getLeftMessages().remove(0);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void serverWrite(){
		switch (this.status) {
		case Constant.CONNECTED:
			this.send();
			break;
		}
	}

	@Override
	public void run() {
		while(true){
			this.serverWrite();
		}
	}

	public Thread getThreadWrite() {
		return threadWrite;
	}

	public void setThreadWrite(Thread threadWrite) {
		this.threadWrite = threadWrite;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

}