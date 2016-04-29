package br.org.roger.hamcrest.matcher.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * 
 * @author Roger
 *
 */
public class MagicMatcher <T> implements InvocationHandler {
	
	private ResultDescription resultDescription;
	private Map<String, Object> methodMap;
	
	public MagicMatcher() {
		super();
		resultDescription = new ResultDescription();
		methodMap = new HashMap<>();
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if ("matches".equals(method.getName())) {
			
			for (Entry<String, Object> entry : methodMap.entrySet()) {
				String field = entry.getKey().substring(entry.getKey().indexOf("with") + 4);
				Object objectValue = extractObjectValue((T) args[0], entry.getKey());
				if (!(entry.getValue() instanceof Matcher)) {
					resultDescription.addExpectedDescription(field, objectValue);
					if (!entry.getValue().equals(objectValue)) {
						resultDescription.addMismatchDescription(field, entry.getValue());
						return new Boolean(false);
					}
				} else {
					Matcher<?> matcher = (Matcher<?>) entry.getValue();
					resultDescription.addExpectedDescription(field, objectValue);
					if (!matcher.matches(objectValue)) {
						resultDescription.addMismatchDescription(field, objectValue);
						return new Boolean(false);
					}
				}
			}
			
			return new Boolean(true);
		} else if ("describeTo".equals(method.getName())) {
			Description description = (Description) args[0];
			description.appendText(resultDescription.getExpectedDescription());
			return null;
		} else if ("describeMismatch".equals(method.getName())) {
			Description description = (Description) args[1];
			description.appendText(resultDescription.getMismatchDescription());
			return null;
		} else {
			if (args.length > 1) {
				throw new IllegalArgumentException("Cannot assert more than one argument at once.");
			}
			methodMap.put(method.getName(), args[0]);
			
			return proxy;
		}
	}

	protected Object extractObjectValue(T matchEntity, String entryKey)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String methodName = entryKey.replace("with", "get");
		Method personMethod = matchEntity.getClass().getMethod(methodName, (Class<?>[]) null);
		return personMethod.invoke(matchEntity);
	}
	
	class ResultDescription {
		
		private String expectedDescription = "";
		private String mismatchDescription = "";
		
		/**
		 * @return the expectedDescription
		 */
		public String getExpectedDescription() {
			return expectedDescription;
		}
		/**
		 * @return the mismatchDescription
		 */
		public String getMismatchDescription() {
			return mismatchDescription;
		}
		
		public void addExpectedDescription(String field, Object value) {
			expectedDescription += "\n" + field + ": containing value " + value;
		}
		
		public void addMismatchDescription(String field, Object value) {
			mismatchDescription += "\n" + field + ": contains value " + value;
		}
		
	}

}
