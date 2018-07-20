package de.kreth.clubinvoice.ui.presentation;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public class DataPresentators {

	private static final Map<Class<?>, DataPresentator<?>> cache = new HashMap<>();

	private DataPresentators() {
	}

	/**
	 * 
	 * @param obj
	 * @return never null, null object returns String "null".
	 */
	public static <T> String toPresentation(T obj) {
		if (obj == null) {
			return "null";
		}
		try {
			@SuppressWarnings("unchecked")
			DataPresentator<T> presenter = (DataPresentator<T>) getPresentor(
					obj.getClass());
			if (presenter != null) {
				return presenter.presentationString(obj);
			} else {
				return obj.toString();
			}
		} catch (ReflectiveOperationException e) {
			return obj.toString();
		}
	}

	static <T> DataPresentator<T> getPresentor(Class<T> forClass)
			throws ReflectiveOperationException {
		if (cache.containsKey(forClass)) {
			return getCached(forClass);
		} else {
			return addToCache(forClass);
		}
	}

	@SuppressWarnings("rawtypes")
	private static <T> DataPresentator<T> addToCache(Class<T> forClass)
			throws ReflectiveOperationException {
		Reflections reflections = new Reflections(
				"de.kreth.clubinvoice.ui.presentation");
		Set<Class<? extends DataPresentator>> implementations = reflections
				.getSubTypesOf(DataPresentator.class);

		for (Class<? extends DataPresentator> presentator : implementations) {
			if (Modifier.isAbstract(presentator.getModifiers()) == false) {
				@SuppressWarnings("unchecked")
				DataPresentator<T> instance = instanciate(
						(Class<? extends DataPresentator<T>>) presentator);
				cache.put(instance.forClass(), instance);
				if (forClass.equals(instance.forClass())) {
					return instance;
				}
			}
		}
		return null;
	}

	private static <T> DataPresentator<T> instanciate(
			Class<? extends DataPresentator<T>> presentator)
			throws ReflectiveOperationException {
		return presentator.newInstance();
	}

	@SuppressWarnings("unchecked")
	private static <T> DataPresentator<T> getCached(Class<T> forClass) {
		return (DataPresentator<T>) cache.get(forClass);
	}
}
