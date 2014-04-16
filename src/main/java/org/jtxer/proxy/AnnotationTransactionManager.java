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

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Method;

import org.jtxer.annotations.Transaction;
import org.jtxer.core.Isolation;
import org.jtxer.core.Propagation;
import org.jtxer.core.TransactionDefinition;
import org.jtxer.core.TransactionManager;
import org.jtxer.core.TransactionStatus;
import org.jtxer.helpers.ClassHelper;

/**
 * 注解事务管理器
 * @author yuqs
 * @since 0.1
 */
public class AnnotationTransactionManager {
	private static final String SEPARATOR = "#";
	/**
	 * 拦截产生的事务定义集合
	 */
	protected final Map<String, TransactionDefinition> txmap = new HashMap<String, TransactionDefinition>();
	/**
	 * 事务管理器
	 */
	protected final TransactionManager transactionManager;
	/**
	 * 事务定义
	 */
	protected final TransactionDefinition transactionDefinition;

	public AnnotationTransactionManager(TransactionManager transactionManager) {
		this(transactionManager, null);
	}

	public AnnotationTransactionManager(TransactionManager transactionManager, TransactionDefinition definition) {
		this.transactionManager = transactionManager;
		this.transactionDefinition = definition == null ? new TransactionDefinition() : definition;
	}

	/**
	 * 获取事务定义
	 */
	public TransactionDefinition getTransactionDefinition() {
		return transactionDefinition;
	}
	
	/**
	 * 获取当前事务状态
	 */
	public TransactionStatus getCurrentTransaction() {
		return transactionManager.getTransaction();
	}

	/**
	 * 请求事务状态
	 * @see TransactionManager#requestTransaction(TransactionDefinition)
	 */
	public TransactionStatus beginTransaction(TransactionDefinition definition, Object scope) {
		if (definition == null) {
			return null;
		}
		TransactionStatus currentTx = transactionManager.getTransaction();
		TransactionStatus requestedTx = transactionManager.requestTransaction(definition, scope);
		if (currentTx == requestedTx) {
			return null;
		}
		return requestedTx;
	}


	/**
	 * 根据事务状态对象判断是否提交事务
	 */
	public boolean commitTransaction(TransactionStatus tx) {
		if (tx == null) {
			return false;
		}

		tx.commit();
		return true;
	}

	/**
	 * 根据事务状态对象与异常栈判断是否回滚事务
	 */
	public boolean rollbackTransaction(TransactionStatus tx, Throwable cause) {
		if (tx == null) {
			tx = getCurrentTransaction();
			if (tx == null) {
				return false;
			}

			tx.setRollbackOnly(cause);
			return false;
		}

		tx.rollback();
		return true;
	}
	
	/**
	 * 获取请求标识
	 */
	public String getScope(Class<?> type, Method method) {
		return type.getName() + SEPARATOR + method.getName();
	}

	/**
	 * 从方法注解中构造事务定义对象并由txmap进行缓存
	 * @param type 一般为代理的目标类型
	 * @param method 存在事务注解的方法
	 * @param unique 唯一字符标识
	 */
	public synchronized TransactionDefinition getTransactionDefinition(Class<?> type, Method method) {
		String signature = type.getName() + SEPARATOR + method.getName();
		TransactionDefinition definition = txmap.get(signature);
		if (definition == null) {
			if (!txmap.containsKey(signature)) {
				Annotation annotation = method.getAnnotation(Transaction.class);
				definition = buildTransactionDefinition(annotation);
				if(definition == null) {
					definition = transactionDefinition;
				}
				txmap.put(signature, definition);
			}
		}
		return definition;
	}
	
	/**
	 * 读取注解构造成事务定义对象
	 */
	public TransactionDefinition buildTransactionDefinition(Annotation annotation) {
		TransactionDefinition definition = new TransactionDefinition();
		try {
			definition.setPropagationBehaviour((Propagation)ClassHelper.readAnnotationValue(annotation, "propagation"));
			definition.setIsolationLevel((Isolation)ClassHelper.readAnnotationValue(annotation, "isolation"));
			definition.setReadOnly((Boolean)ClassHelper.readAnnotationValue(annotation, "readOnly"));
			definition.setTransactionTimeout((Integer)ClassHelper.readAnnotationValue(annotation, "timeout"));
		} catch(Exception e) {
			return null;
		}
		return definition;
	}
}
