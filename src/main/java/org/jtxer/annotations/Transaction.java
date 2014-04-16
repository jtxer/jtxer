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
package org.jtxer.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import org.jtxer.core.Isolation;
import org.jtxer.core.Propagation;
import org.jtxer.core.TransactionDefinition;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Transaction {

	/**
	 * 事务传播特性. 默认: <code>PROPAGATION_REQUIRED</code>.
	 */
	Propagation propagation() default Propagation.PROPAGATION_REQUIRED;

	/**
	 * 事务隔离级别. 默认: <code>ISOLATION_DEFAULT</code>.
	 */
	Isolation isolation() default Isolation.ISOLATION_DEFAULT;

	/**
	 * 只读模式. 默认: <code>false</code>
	 */
	boolean readOnly() default false;

	/**
	 * 超时设置. 默认: -1
	 */
	int timeout() default TransactionDefinition.DEFAULT_TIMEOUT;
	
}
