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
 * 事务传播特性枚举
 * @author yuqs
 * @since 0.1
 */
public enum Propagation {
	/**
	 * 支持当前事务，如果当前不存在则新建事务
	 */
	PROPAGATION_REQUIRED(1),

	/**
	 * 支持当前事务，如果当前不存在则以无事务方式执行
	 */
	PROPAGATION_SUPPORTS(2),

	/**
	 * 支持当前事务，如果当前不存在则抛出异常
	 */
	PROPAGATION_MANDATORY(3),

	/**
	 * 存在事务时暂停当前事务，创建新的事务
	 */
	PROPAGATION_REQUIRES_NEW(4),

	/**
	 * 无事务执行，如果当前存在事务则暂停
	 */
	PROPAGATION_NOT_SUPPORTED(5),

	/**
	 * 无事务执行，如果存在事务则抛出异常
	 */
	PROPAGATION_NEVER(6);

	private int value;

	Propagation(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
