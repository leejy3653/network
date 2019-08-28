package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
//	private static String SERVER_IP = "192.168.1.4";
	private static String SERVER_IP = "127.0.0.1"; //강사님 IP
	private static int SERVER_PORT = 9000;

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;

		try {
			// Scanner scanner = new Scanner(System.in);
			scanner = new Scanner(System.in);
			// Socket socket = new Socket();
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			System.out.print("대화명:");
			String nickname = scanner.nextLine();
			pw.println("join:" + nickname);
			pw.flush();

			new ChatClientThread(socket, br).start();

//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			while (true) {
				System.out.print(">>");
				String input = scanner.nextLine();

				if ("quit".equals(input)) {
					pw.println("quit:");
					log("서버로부터 연결종료");
					break;
				}
				if ("".equals(input)) { //공백이 입력
					input = " "; //띄어쓰기로  처리
					pw.println(" :" + input);
				} else {
					pw.println("message:" + input);
					continue;
				}

			}
		} catch (IOException e) {
			log("error : " + e);
		} finally {
//			try {
//				if (socket != null && socket.isClosed() == false)
//					socket.close();
//			if (scanner != null)
			scanner.close();
//			} catch (IOException e) {

//				e.printStackTrace();
//			}
		}
	}

	private static void log(String log) {
		System.out.println("[Client]" + log);
	}

}