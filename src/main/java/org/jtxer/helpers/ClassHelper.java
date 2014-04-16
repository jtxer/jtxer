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
package org.jtxer.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Class帮助类
 * @author yuqs
 * @since 0.1
 */
public class ClassHelper {
	/**
	 * 读取指定注解的name对应的值
	 */
	public static Object readAnnotationValue(Annotation annotation, String name) {
		try {
			Method method  = annotation.annotationType().getDeclaredMethod(name);
			return method.invoke(annotation);
		} catch (Exception ignore) {
			return null;
		}
	}
	/**
	 * 判断给定的类型是否与目标类型一致，或者未目标类型的子类
	 */
	public static boolean isSubclass(Class<?> thisClass, Class<?> target) {
		if (target.isInterface()) {
			return isInterfaceImpl(thisClass, target);
		}
		for (Class<?> x = thisClass; x != null; x = x.getSuperclass()) {
			if (x == target) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断给定的类thisClass是否为targetInterface接口的实现类
	 */
	public static boolean isInterfaceImpl(Class<?> thisClass, Class<?> targetInterface) {
		for (Class<?> x = thisClass; x != null; x = x.getSuperclass()) {
			Class<?>[] interfaces = x.getInterfaces();
			for (Class<?> i : interfaces) {
				if (i == targetInterface) {
					return true;
				}
				if (isInterfaceImpl(i, targetInterface)) {
					return true;
				}
			}
		}
		return false;
	}
}
