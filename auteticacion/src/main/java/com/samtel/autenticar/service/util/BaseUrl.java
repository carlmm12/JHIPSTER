package com.samtel.autenticar.service.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BaseUrl {

	public String getIp() {
			try {
				System.out.println(InetAddress.getLocalHost().getHostAddress());
				return InetAddress.getLocalHost().getHostAddress();
			
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return null;
	}
}
