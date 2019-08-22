package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {

		try {
			while (true) {

				System.out.print(">");
				Scanner scanner = new Scanner(System.in);
				String s = scanner.nextLine();
				if ("exit".equals(s)) {
					break;
				}

				InetAddress[] inetAddresses = InetAddress.getAllByName(s);
				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(s + " : " + inetAddress.getHostAddress());

				}

			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
