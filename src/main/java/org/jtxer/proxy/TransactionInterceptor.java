/* Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jtxer.proxy;

import java.lang.reflect.Method;

import org.apache.commons.dbcp.BasicDataSource;
import org.jtxer.annotations.Transaction;
import org.jtxer.core.JtxerHelper;
import org.jtxer.core.TransactionStatus;
import org.jtxer.core.TransactionDefinition;
import org.jtxer.support.jdbc.JdbcResourceManager;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 事务拦截器，用于产生业务逻辑类的代理类
 * 测试使用
 * @author yuqs
 * @since 0.1
 */
public class TransactionInterceptor implements MethodInterceptor {
	static {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3406/jtxer");
		ds.setUsername("root");
		ds.setPassword("root");
		JtxerHelper.getTxManager().registerResourceManager(new JdbcResourceManager(ds));
	}
	/**
	 * 使用Cglib产生业务类的代理
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> clazz) {
		return (T)Enhancer.create(clazz, this);
	}
	
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object result = null;
		if(method.isAnnotationPresent(Transaction.class)) {
			TransactionStatus tx = null;
			AnnotationTransactionManager manager = JtxerHelper.getAnnotationTxManager();
			try {
				TransactionDefinition definition = manager.getTransactionDefinition(obj.getClass(), method);
				System.out.println("TransactionMode="  +definition);
				String scope = manager.getScope(obj.getClass(), method);
				tx = manager.beginTransaction(definition, scope);
				result = proxy.invokeSuper(obj, args);
				manager.commitTransaction(tx);
			} catch (Exception ex) {
				ex.printStackTrace();
				manager.rollbackTransaction(tx, ex);
			}
		} else {
			result = proxy.invokeSuper(obj, args);
		}
		return result;
	}
}
