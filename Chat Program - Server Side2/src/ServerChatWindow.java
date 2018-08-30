import java.net.*;

public class ServerChatWindow {
	public static DatagramSocket ds;
	public static int serMainPort = 997; 
	public static int serChatPort = 998; 

	public static int buffer_size = 1024;  
	public static byte buffer[] = new byte[buffer_size];
	
	public static void main(String args[]) throws Exception {		
		ds = new DatagramSocket(serChatPort); 		
		receive();
	}
	
	public static void receive() throws Exception { 
		while(true) {
		  DatagramPacket p = new DatagramPacket(buffer, buffer.length); 
		  ds.receive(p);
		  
		  if(p.getPort() == serMainPort) {
			  System.out.print("You: ");
		  } 
		  else {
			  System.out.print("Client: ");
		  }
	  	  System.out.println(new String(p.getData(), 0, p.getLength())); 
	  	 }
	} 
}
