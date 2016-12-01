package connection;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Constant{
	
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";

	public static final String OFF = "OFF";
	public static final String ON = "ON";

	public static final String WAITING = "WAITING";
	public static final String SEARCHING = "SEARCHING";
	public static final String CONNECTED = "CONNECTED";
	public static final String LISTENING = "LISTENING";

	public static final String SERVER = "SERVER";
	public static final String CLIENT = "CLIENT";

	public static final String OPEN = "OPEN";
	public static final String CLOSE = "CLOSE";

	public static final String BROKEN = "BROKEN";

	public static final String REAL = "REAL";
	public static final String CHECK = "CHECK";
	public static final String COME_BACK = "COME_BACK";

	public static final int TIME = 5000;

	public static void waitThread(){
		try {
			Thread.sleep(Constant.TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	wlp3s0 enp0s3	
	public static String findIp(){
		NetworkInterface networkInterface;
		InetAddress ia = null;
		try {
			networkInterface = NetworkInterface.getByName("wlp3s0");
			Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
			while (inetAddress.hasMoreElements()) {
				ia = inetAddress.nextElement();
				if (!ia.isLinkLocalAddress()) {
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ia.getHostAddress();
	}
}
