package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientThread extends Thread {
	Socket socket = null;
	BufferedReader br;

	public ChatClientThread(Socket socket, BufferedReader br) {
		this.socket = socket;
		this.br = br;
	}

	@Override
	public void run() {
		String message;
		try {
			while (true) {
				System.out.print(">>");
				message = br.readLine();
				if (message == null) {
					break;
				}
				System.out.println(message);
			}
		} 
//		catch(SocketException e) {
//			System.out.println("어쩌라고");
//		}
		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			if (socket.isClosed() == false && socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
