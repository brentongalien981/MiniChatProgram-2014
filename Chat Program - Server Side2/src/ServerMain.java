import java.net.*;
import java.io.*;

public class ServerMain {	
	private static ServerSocket ss;
	private static DatagramSocket ds; 
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
		serIAdrs = InetAddress.getLocalHost();
		
		// create connection
		ss = new ServerSocket(serMainPort);
		System.out.println("waiting for client to connect...");
		Socket s = ss.accept();
		System.out.println("connection made!");
		
		ds = new DatagramSocket(serMainPort); 
		
		// receive complete InetAddress sent by client
		iStream = s.getInputStream();
		datInStrm = new DataInputStream(iStream);
		String cliCompleteIAdrs = datInStrm.readUTF();
		
		// breakdown the InetAddress sent by client to IP and
		// assign it to Client InetAddress
		for (int i=0, len=cliCompleteIAdrs.length();		i<len;		i++) 
		{
			if(cliCompleteIAdrs.charAt(i) == '/') 
			{
				cliIAdrs = InetAddress.getByName(cliCompleteIAdrs.substring(++i));
			}
		}		
		
		try {
			Runtime.getRuntime().exec(new String[] { "cmd.exe",		"/C",		"\"start; java ServerChatWindow\"" });			
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
