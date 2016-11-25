package task;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import connection.Connection;
import connection.Server;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private String hour;
	private String message;


	public Message() {

		try {
			this.ip = Server.findIp();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.hour = this.hour();
		this.message = this.randomNumber();
	}

	private String hour(){
		Calendar calendar = new GregorianCalendar();
		return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(calendar.get(Calendar.MINUTE)) + ":" + String.valueOf(calendar.get(Calendar.SECOND));
	}

	private String randomNumber(){

		return String.valueOf(10 + (int)(Math.random() * 20));
	}

	@Override
	public String toString() {
		return "ip=" + ip + ";hour=" + hour + ";message=" + message;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
