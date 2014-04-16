package org.jtxer.test.service;

import org.jtxer.proxy.TransactionInterceptor;

/**
 * @author yuqs
 */
public class TestService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TransactionInterceptor interceptor = new TransactionInterceptor();
		Service1 service1 = interceptor.getProxy(Service1.class);
		Service2 service2 = interceptor.getProxy(Service2.class);
		service1.execute(service2);
	}

}
