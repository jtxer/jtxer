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
package org.jtxer.support.jdbc;

import javax.sql.DataSource;

import org.jtxer.core.ResourceManager;
import org.jtxer.core.JtxerException;
import org.jtxer.core.TransactionDefinition;

/**
 * jdbc资源管理器
 * @author yuqs
 * @since 0.1
 */
public class JdbcResourceManager implements ResourceManager<JdbcSession> {
	protected final DataSource dataSource;

	/**
	 * 根据DataSourceFactory创建对象
	 */
	public JdbcResourceManager(DataSourceFactory factory) {
		this.dataSource = factory.getDataSource();
	}
	
	/**
	 * 根据DataSource创建对象
	 * @param dataSource
	 */
	public JdbcResourceManager(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Class<JdbcSession> getResourceType() {
		return JdbcSession.class;
	}

	public JdbcSession beginTransaction(TransactionDefinition definition, boolean active) {
		JdbcSession session = new JdbcSession(dataSource);
		if (active) {
			session.beginTransaction(definition);
		}
		return session;
	}

	public void commitTransaction(JdbcSession resource) {
		if (resource.isTransactionActive()) {
			resource.commitTransaction();
		}
		resource.closeSession();
	}

	public void rollbackTransaction(JdbcSession resource) {
		try {
			if (resource.isTransactionActive()) {
				resource.rollbackTransaction();
			}
		} catch (Exception ex) {
			throw new JtxerException(ex);
		} finally {
			resource.closeSession();
		}
	}

	public void close() {
		//ignore
	}
}
