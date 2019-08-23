package thread;

public class MultithreadEx03 {

	public static void main(String[] args) {
		Thread thread1 = new DigitThread();
		Thread thread2 = new AlphabetThread();
		Thread thread3 = new Thread(new UppercaseAlphabetRunnableImpl());//Runnable 인터페이스를 구현한 class 객체를 넣어준다
		
		thread1.start();
		thread2.start();
		thread3.start();		
	}

}
