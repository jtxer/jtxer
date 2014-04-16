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

import org.jtxer.proxy.AnnotationTransactionManager;

/**
 * jtxer帮助类
 * 1.获取{@link TransactionManager}
 * 2.获取{@link AnnotationTransactionManager}
 * @author yuqs
 * @since 0.1
 */
public class JtxerHelper {
	private static TransactionManager transactionManager = new TransactionManager();
	private static AnnotationTransactionManager annotationTransactionManager;
	public static TransactionManager getTxManager() {
		return transactionManager;
	}
	
	public static AnnotationTransactionManager getAnnotationTxManager() {
		if(annotationTransactionManager == null) {
			annotationTransactionManager = new AnnotationTransactionManager(transactionManager);
		}
		return annotationTransactionManager;
	}
}
