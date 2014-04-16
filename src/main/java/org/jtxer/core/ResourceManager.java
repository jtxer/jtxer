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
 * 资源管理器接口
 * @author yuqs
 * @since 0.1
 */
public interface ResourceManager<E> {
	/**
	 * 资源类型
	 * @return Class 类型
	 */
	Class<E> getResourceType();

	/**
	 * 根据活动状态active确定是否创建新的资源并且启动新的事务
	 * 通畅由事务的传播特性来决定
	 * @param definition 事务定义
	 * @param active 活动状态
	 * @return E 资源
	 */
	E beginTransaction(TransactionDefinition definition, boolean active);

	/**
	 * 提交并关闭资源
	 * @param resource 资源
	 */
	void commitTransaction(E resource);

	/**
	 * 回滚并关闭资源
	 * @param resource 资源
	 */
	void rollbackTransaction(E resource);

	/**
	 * 关闭管理器并释放所有资源
	 */
	void close();
}
