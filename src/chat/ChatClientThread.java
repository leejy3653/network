package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread {
	Socket socket = null;
	BufferedReader br;
	public ChatClientThread(Socket socket,BufferedReader br) {
		this.socket= socket;
		this.br= br;
	}
	@Override 
	public void run() {
		String message;
		try {
			while(true) {
				message = br.readLine();
				if(message==null) {
					break;
				}
				System.out.println(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
