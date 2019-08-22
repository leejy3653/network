package echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static String SERVER_IP = "192.168.1.4";// 서버 IP
	private static int SERVER_PORT = 8000; // 포트는 8000

	public static void main(String[] args) {

		Socket socket = null;
		try {
			socket = new Socket();

			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);

			socket.connect(inetSocketAddress);

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			while (true) { // 반복문
				// 쓰기
				System.out.print(">>");
				Scanner scanner = new Scanner(System.in);
				String s = scanner.nextLine();
				String data = s;
				os.write(s.getBytes("UTF-8"));
				if ("exit".equals(s)) { // exit 입력시 while문 탈출
					break;
				}

				// 읽기
				byte[] buffer = new byte[256];
				int readByteCount = is.read(buffer);
				if (readByteCount == -1) {
					System.out.println("[TCPClient] closed by client");
					return;
				}
				s = new String(buffer, 0, readByteCount, "UTF-8");

				System.out.println("<< " + s);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
