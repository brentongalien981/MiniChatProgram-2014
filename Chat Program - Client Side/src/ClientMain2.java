import java.net.*;
import java.io.*;

public class ClientMain2 {	
	private static Socket s;
	public static DatagramSocket ds; 
	private static InetAddress cliIAdrs;
	private static InetAddress serIAdrs;
	
	public static int serMainPort = 997; 
	public static int serChatPort = 998; 
	public static int cliMainPort = 999;
	public static int cliChatPort = 1000;
	
	public static int buffer_size = 1024; 
	public static byte buffer[] = new byte[buffer_size];
	
	private static InputStream iStream;
	private static OutputStream oStream;
	private static DataOutputStream datOutStrm;
	private static DataInputStream datInStrm;
	
	public static void main(String args[]) throws Exception {
		serIAdrs = InetAddress.getByName("192.168.0.106")/*InetAddress.getLocalHost()*/;
		cliIAdrs = InetAddress.getLocalHost();
		
		// connect to server
		s = new Socket(serIAdrs, serMainPort);
		ds = new DatagramSocket(cliMainPort); 
		if(s.isConnected())
		{
			System.out.println("Connection Successful!");
		}
		
		oStream = s.getOutputStream();
		datOutStrm = new DataOutputStream(oStream);
		datOutStrm.writeUTF("" + InetAddress.getLocalHost());
		
		try {
			Runtime.getRuntime().exec(new String[] { "cmd.exe",		"/C",		"\"start; java ClientChatWindow\"" });			
		} catch(Exception exc) {}
		
		send();			
		s.close();
	}
	
	public static void send() throws Exception {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
		} catch(Exception e) {}
		
		while(true) {
			System.out.print("hist::> ");
			String s = br.readLine();
			
			int sLen = s.length();			
			for(int i=0; i<sLen; i++) {
				buffer[i] = (byte) s.charAt(i);					
			}
			
			ds.send(new DatagramPacket(buffer, sLen, serIAdrs, serChatPort));
			ds.send(new DatagramPacket(buffer, sLen, cliIAdrs, cliChatPort));
			
		}		
	}
 
}
