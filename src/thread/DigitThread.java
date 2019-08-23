package thread;

public class DigitThread extends Thread { // Thread클래스 상속

	@Override // run을 Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.print(i);
			try {
				Thread.sleep(1000);//동기 맞추기
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}