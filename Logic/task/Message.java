package task;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import connection.Constant;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;
	private String hour;
	private String message;
	private String type;
	private String lastIp;
	private String side;

	public Message() {
		this.ip = Constant.findIp();
		this.hour = this.hour();
		this.message = this.randomNumber();
		this.type = Constant.REAL;
	}

	public Message(char side){
		this.ip = Constant.findIp();
		this.hour = this.hour();
		this.message = this.randomNumber();
		this.type = Constant.REAL;
		if (side == 'l') {
			this.side = Constant.LEFT;
		}else if (side == 'r') {
			this.side =  Constant.RIGHT;
		}
	}

	public Message(String type) {
		this.ip = Constant.findIp();
		this.hour = this.hour();
		this.message = this.randomNumber();
		this.type = type;
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
		return "Ip=" + ip + "; Hour=" + hour + "; Message=" + message + "; Type=" + type + "; Side=" + side;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

}
