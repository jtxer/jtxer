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
package org.jtxer.core;

import java.io.Serializable;
import java.sql.Connection;

/**
 * 事务定义类
 * @author yuqs
 * @since 0.1
 */
public class TransactionDefinition implements Serializable {
	private static final long serialVersionUID = -5159457325218411750L;
	/**
	 * 只读模式
	 */
	public static final boolean READ_ONLY = true;
	/**
	 * 读写模式
	 */
	public static final boolean READ_WRITE = false;
	/**
	 * 默认超时设置为-1
	 */
	public static final int DEFAULT_TIMEOUT = -1;
	/**
	 * 只读模式
	 */
	private boolean readOnlyMode;
	/**
	 * 传播特性
	 */
	protected Propagation propagationBehavior;
	/**
	 * 隔离级别
	 */
	private Isolation isolationLevel;
	/**
	 * 超时设置-秒
	 */
	private int timeout;
	
	/**
	 * 默认隔离级别
	 */
	public static final int ISOLATION_DEFAULT 				= -1;
	/**
	 *  @see Connection#TRANSACTION_NONE
	 */
	public static final int ISOLATION_NONE 					= Connection.TRANSACTION_NONE;
	/**
	 *  @see Connection#TRANSACTION_READ_UNCOMMITTED
	 */
	public static final int ISOLATION_READ_UNCOMMITTED 		= Connection.TRANSACTION_READ_UNCOMMITTED;
	/**
	 *  @see Connection#TRANSACTION_READ_COMMITTED
	 */
	public static final int ISOLATION_READ_COMMITTED 		= Connection.TRANSACTION_READ_COMMITTED;
	/**
	 *  @see Connection#TRANSACTION_REPEATABLE_READ
	 */
	public static final int ISOLATION_REPEATABLE_READ 		= Connection.TRANSACTION_REPEATABLE_READ;
	/**
	 *  @see Connection#TRANSACTION_SERIALIZABLE
	 */
	public static final int ISOLATION_SERIALIZABLE 			= Connection.TRANSACTION_SERIALIZABLE;

	/**
	 * 创建默认的事务定义对象
	 * required传播特性、默认的隔离级别、读写模式、无超时设置
	 */
	public TransactionDefinition() {
		this.propagationBehavior = Propagation.PROPAGATION_REQUIRED;
		this.isolationLevel = Isolation.ISOLATION_DEFAULT;
		this.readOnlyMode = READ_WRITE;
		this.timeout = DEFAULT_TIMEOUT;
	}

	/**
	 * 传播特性: required.
	 * <pre>
	 * None -> T2
	 * T1 -> T1
	 * </pre>
	 */
	public TransactionDefinition propagationRequired() {
		this.propagationBehavior = Propagation.PROPAGATION_REQUIRED;
		return this;
	}

	/**
	 * 传播特性: supports.
	 * <pre>
	 * None -> None
	 * T1 -> T1
	 * </pre>
	 */
	public TransactionDefinition propagationSupports() {
		this.propagationBehavior = Propagation.PROPAGATION_SUPPORTS;
		return this;
	}

	/**
	 * 传播特性: mandatory.
	 * <pre>
	 * None -> Error
	 * T1 -> T1
	 * </pre>
	 */
	public TransactionDefinition propagationMandatory() {
		this.propagationBehavior = Propagation.PROPAGATION_MANDATORY;
		return this;
	}

	/**
	 * 传播特性: requires new.
	 * <pre>
	 * None -> T2
	 * T1 -> T2
	 * </pre>
	 */
	public TransactionDefinition propagationRequiresNew() {
		this.propagationBehavior = Propagation.PROPAGATION_REQUIRES_NEW;
		return this;
	}

	/**
	 * 传播特性: not supported.
	 * <pre>
	 * None -> None
	 * T1 -> None
	 * </pre>
	 */
	public TransactionDefinition propagationNotSupported() {
		this.propagationBehavior = Propagation.PROPAGATION_NOT_SUPPORTED;
		return this;
	}
	/**
	 * 传播特性: never.
	 * <pre>
	 * None -> None
	 * T1 -> Error
	 * </pre>
	 */
	public TransactionDefinition propagationNever() {
		this.propagationBehavior = Propagation.PROPAGATION_NEVER;
		return this;
	}

	public Isolation getIsolationLevel() {
		return isolationLevel;
	}

	public void setIsolationLevel(Isolation isolation) {
		this.isolationLevel = isolation;
	}

	public TransactionDefinition isolationNone() {
		this.isolationLevel = Isolation.ISOLATION_NONE;
		return this;
	}
	public TransactionDefinition isolationReadUncommitted() {
		this.isolationLevel = Isolation.ISOLATION_READ_UNCOMMITTED;
		return this;
	}
	public TransactionDefinition isolationReadCommitted() {
		this.isolationLevel = Isolation.ISOLATION_READ_COMMITTED;
		return this;
	}
	public TransactionDefinition isolationRepeatableRead() {
		this.isolationLevel = Isolation.ISOLATION_REPEATABLE_READ;
		return this;
	}
	public TransactionDefinition isolationSerializable() {
		this.isolationLevel = Isolation.ISOLATION_SERIALIZABLE;
		return this;
	}

	public int getTransactionTimeout() {
		return timeout;
	}

	public Propagation getPropagationBehavior() {
		return propagationBehavior;
	}

	public void setPropagationBehaviour(Propagation propagation) {
		this.propagationBehavior = propagation;
	}
	
	public void setTransactionTimeout(int timeout) {
		if (timeout < DEFAULT_TIMEOUT) {
			throw new RuntimeException("Invalid TX timeout: " + timeout);
		}
		this.timeout = timeout;
	}

	public TransactionDefinition transactionTimeout(int timeout) {
		setTransactionTimeout(timeout);
		return this;
	}
	
	public boolean isReadOnly() {
		return readOnlyMode;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnlyMode = readOnly;
	}

	public TransactionDefinition readOnly(boolean readOnly) {
		this.readOnlyMode = readOnly;
		return this;
	}

	/**
	 * Returns tx description for debugging purposes.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("jtx{");
		sb.append(propagationBehavior.toString());
		sb.append(',').append(readOnlyMode ? "readonly" : "readwrite");
		sb.append(',').append(isolationLevel.toString());
		sb.append(',').append(timeout).append('}');
		return sb.toString();
	}
}
