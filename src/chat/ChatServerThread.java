package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ChatServerThread extends Thread {
	private String nickname = null;
	private Socket socket = null;
	List<PrintWriter> listWriters = null;

	public ChatServerThread(Socket socket, List<PrintWriter> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while (true) {
				String request = br.readLine();

				if (request == null) {
					doQuit(pw);
					log("클라이언트로부터 연결 끊김");
					break;
				}

				String[] tokens = request.split(":");
				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				} else if ("quit".equals(tokens[0])) {
					doQuit(pw);
					break;
				} else {
					ChatServer.log("error : 알수없는 요청(" + tokens[0] + ")");
				}
			}
		} catch (SocketException e) {
			log("클라이언트로 부터 비정상적인 종료");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket.isClosed() == false && socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	private void doQuit(PrintWriter writer) {
		removeWriter(writer);

		String data = nickname + "님이 퇴장했습니다.";
		broadcast(data);
	}

	private void removeWriter(PrintWriter pw) {
		synchronized (listWriters) {
			listWriters.remove(pw);
		}
	}

	private void doMessage(String message) {
		broadcast(nickname + ":" + message);
	}

	private void doJoin(String nickname, PrintWriter pw) {
		this.nickname = nickname;
		String data = nickname + "님이 입장하였습니다.";
		broadcast(data);
		addWriter(pw);
		pw.println("join:ok");
		pw.flush();
	}

	private void addWriter(PrintWriter pw) {
		synchronized (listWriters) {
			listWriters.add(pw);
		}
	}

	private void broadcast(String data) {
		synchronized (listWriters) {
			for (PrintWriter pw : listWriters) {
				pw.println(data);
				pw.flush();
			}
		}
	}

	private static void log(String log) {
		System.out.println("[ChatServer] : " + log);

	}
}
