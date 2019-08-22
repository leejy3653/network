package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {


		try {
			Scanner scanner = new Scanner(System.in);
			String s = scanner.nextLine();
			InetAddress[] inetAddresses = InetAddress.getAllByName(s);
			for (InetAddress inetAddress : inetAddresses) {
				System.out.println(">>"+s+" : "+inetAddress.getHostAddress());
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
