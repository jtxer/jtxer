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
 * 事务状态枚举
 * @author yuqs
 * @since 0.1
 */
public enum Status {

	/**
	 * 活动状态
	 */
	STATUS_ACTIVE(0),

	/**
	 * 标记回滚状态
     */
    STATUS_MARKED_ROLLBACK(1),

	/**
	 * 不支持.用于两阶段提交
     */
	STATUS_PREPARED(2),

	/**
	 * 提交状态
	 */
	STATUS_COMMITTED(3),

	/**
	 * 回滚状态
	 */
	STATUS_ROLLEDBACK(4),

	/**
     * 不确定状态
     */
	STATUS_UNKNOWN(5),

	/**
	 * 无事务状态(自动提交模式)
	 */
	STATUS_NO_TRANSACTION(6),

	/**
	 * 不支持.用于两阶段提交
	 */
	STATUS_PREPARING(7),
	
	/**
	 * 提交中状态
	 */
	STATUS_COMMITTING(8),

	/**
	 * 回滚中状态
	 */
	STATUS_ROLLING_BACK(9);
	

	private int value;

	Status(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
