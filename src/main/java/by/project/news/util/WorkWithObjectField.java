package by.project.news.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkWithObjectField {

	private final static Logger log = LogManager.getLogger(WorkWithObjectField.class);

	private static final String START_SET = "set";
	private static final String START_GET = "get";
	private static final String EMPTY = "";
	private static final String REGEX_RETURN_TYPE = ".*\\.String$";

	public static void value(Object object) throws UtilException {

		Object value;

		Field[] fields = object.getClass().getDeclaredFields();

		for (Field field : fields) {

			field.setAccessible(true);

			try {

				value = field.get(object);
				System.out.println(value);

			} catch (IllegalArgumentException | IllegalAccessException e) {

				if (log.isDebugEnabled()) {

					log.debug("Debug WorkWithObjectField value: {}",
							"object: " + object.toString() + "; field:" + field + ". EOM. Exc: " + e);
				}

				throw new UtilException("Illegal exception in get field :: wwofv", e);
			}

		}
	}

	public static String methodGet(Object object, String nameMatch) throws UtilException {

		Method[] methods = object.getClass().getMethods();

		String methodName;
		String value = EMPTY;
		nameMatch = START_GET + nameMatch;

		for (Method method : methods) {

			methodName = method.getName();

			if (methodName.startsWith(START_GET) && methodName.equalsIgnoreCase(nameMatch)
					&& method.getReturnType().toString().matches(REGEX_RETURN_TYPE)) {

				try {

					value = (String) method.invoke(object);
					break;

				} catch (IllegalAccessException | InvocationTargetException e) {

					if (log.isDebugEnabled()) {

						log.debug("Debug WorkWithObjectField methodGet: {}",
								"object: " + object.toString() + "; nameMatch:" + nameMatch + "; method:" + method
										+ "; methodName:" + methodName + ". EOM. Exc: " + e);
					}

					throw new UtilException("Illegal exception in reflect get method :: wwofmg", e);
				}
			}
		}

		return value;

	}

	public static void methodSet(Object objectGetMethods, String nameMatch, String value) throws UtilException {

		Method[] methods = objectGetMethods.getClass().getMethods();

		String methodName;
		nameMatch = START_SET + nameMatch;

		for (Method method : methods) {

			methodName = method.getName();

			if (methodName.startsWith(START_SET) && methodName.equalsIgnoreCase(nameMatch)) {

				try {

					method.invoke(objectGetMethods, value);
					break;

				} catch (IllegalAccessException | InvocationTargetException e) {

					if (log.isDebugEnabled()) {

						log.debug("Debug WorkWithObjectField methodSet: {}",
								"object: " + objectGetMethods.toString() + "; nameMatch:" + nameMatch + "; method:"
										+ method + "; methodName:" + methodName + "; value:" + value + ". EOM. Exc: "
										+ e);
					}

					throw new UtilException("Illegal exception in reflect set method :: wwofms", e);
				}
			}
		}

	}

}
