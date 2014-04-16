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
 * 资源访问类
 * 持有资源对象及所属的资源管理器
 * @author yuqs
 * @since 0.1
 */
public final class ResourceAccess<E> {
	final TransactionStatus transaction;
	final ResourceManager<E> resourceManager;
	private final E resource;

	/**
	 * 创建资源访问对象
	 * @param transaction 事务对象
	 * @param resourceManager 资源管理器
	 * @param resource 资源对象
	 */
	ResourceAccess(TransactionStatus transaction, ResourceManager<E> resourceManager, E resource) {
		this.transaction = transaction;
		this.resourceManager = resourceManager;
		this.resource = resource;
	}

	/**
	 * 提交事务
	 */
	void commitTransaction() {
		resourceManager.commitTransaction(resource);
	}

	/**
	 * 回滚事务
	 */
	void rollbackTransaction() {
		resourceManager.rollbackTransaction(resource);
	}

	/**
	 * 获取资源对象
	 * @return E 资源
	 */
	public E getResource() {
		return resource;
	}
}