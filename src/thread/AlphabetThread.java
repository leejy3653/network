package thread;

public class AlphabetThread extends Thread {

	@Override
	public void run() {
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);// 동기 맞추기
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
