package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();

			System.out.println(hostname); // PC 이름
			System.out.println(hostAddress); // IP Address

			byte[] ipAddresses = inetAddress.getAddress();
			for (byte ipAddress : ipAddresses) {
				System.out.print(ipAddress & 0x000000ff);
				System.out.print(".");
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
