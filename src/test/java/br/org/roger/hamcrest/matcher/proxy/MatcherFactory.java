package br.org.roger.hamcrest.matcher.proxy;

import java.lang.reflect.Proxy;

public class MatcherFactory <T> {
	
	public static <T> T proxying(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new MagicMatcher());
	}
	
	public static <T> T aProxy(Class<T> clazz) {
	    return proxying(clazz);
	}

}
