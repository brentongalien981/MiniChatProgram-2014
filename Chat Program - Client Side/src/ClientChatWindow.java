import java.net.*;

public class ClientChatWindow {
	public static DatagramSocket ds;
	public static int cliChatPort = 1000;
	public static int cliMainPort = 999; 

	public static int buffer_size = 1024;  
	public static byte buffer[] = new byte[buffer_size];
	
	public static void main(String args[]) throws Exception {		
		ds = new DatagramSocket(cliChatPort); 		
		receive();
	}
	
	public static void receive() throws Exception { 
		while(true) {
		  DatagramPacket p = new DatagramPacket(buffer, buffer.length); 
		  ds.receive(p);
		  
		  if(p.getPort() == cliMainPort) {
			  System.out.print("You: ");
		  } 
		  else {
			  System.out.print("Server: ");
		  }
	  	  System.out.println(new String(p.getData(), 0, p.getLength())); 
	  	 }
	} 
}
