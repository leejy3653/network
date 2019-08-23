package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

//workspace 이동후 (D:\itcen\eclipse-workspace\network\bin)
//java test.TCPServer명령어로 실행시키면서 client 작업할것
public class TCPServer {
	private static final int PORT = 5000;

	public static void main(String[] args) {
		// 1. 서버 소켓 생성
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();
			// 2. Binding : Socket에 SocketAddress(IPAddress + Port) 바인딩한다.
			InetAddress inetAddress = InetAddress.getLocalHost();
			// String localhostAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[TCPServer] binding " + inetAddress.getHostAddress() + ":" + PORT);

			// 3. accept : 클라이언트로부터 연결요청(Connect)을 기다린다.
			Socket socket = serverSocket.accept(); // Blocking이 된다. 더 밑으로 코드가 안내려간다.
			// System.out.println("Hello~");
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			// return하는 클래스가 SocketAddress라는 abstract class이다.
			// 따라서, 명시적 캐스팅을 해줘야 한다.
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetRemoteSocketAddress.getPort();// 클라이언트 socket의 Address와 Port
			System.out.println("[TCPServer] connected from client[" + remoteHostAddress + ":" + remoteHostPort + "]");

			try {
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// 무한루프
				while (true) {
					// 5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); // Blocking
					if (readByteCount == -1) {
						// 정상종료 : 클라이언트쪽에서 상대방이 close 호출
						// 즉, remote socket이 close() 메소드를 통해서 정상적으로 소켓을 닫았다.
						System.out.println("[TCPServer] closed by client");
						break;
					}
					String data = new String(buffer, 0, readByteCount, "UTF-8");// byte를 String으로 바꾼다(encoding).

					System.out.println("[TCPServer] reveived : " + data);// "[TCPServer] reveived : "이 한줄 더 출력되지만 신경쓰지
																			// 말것 (클라이언트 만들면 사라짐)

					// 6. 데이터 쓰기
					os.write(data.getBytes("UTF-8"));// UTF-8 굳이 안적어도 됨

				}
			} catch (SocketException e) {
				System.out.println("[TCPServer] abnormal closed by client"); // 비정상 종료
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 8. Socket 자원정리
				if (socket != null && socket.isClosed() == false)
					socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 8. ServerSocket 자원정리

			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
