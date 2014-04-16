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

/**
 * 事务隔离级别枚举
 * @author yuqs
 * @sice 0.1
 */
public enum Isolation {
	ISOLATION_DEFAULT(TransactionDefinition.ISOLATION_DEFAULT),
	ISOLATION_NONE(TransactionDefinition.ISOLATION_NONE),
	ISOLATION_READ_UNCOMMITTED(TransactionDefinition.ISOLATION_READ_UNCOMMITTED),
	ISOLATION_READ_COMMITTED(TransactionDefinition.ISOLATION_READ_COMMITTED),
	ISOLATION_REPEATABLE_READ(TransactionDefinition.ISOLATION_REPEATABLE_READ),
	ISOLATION_SERIALIZABLE(TransactionDefinition.ISOLATION_SERIALIZABLE);

	private int value;

	Isolation(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
