package com.puente.puenteapp.controller;

import java.lang.reflect.Field;

import org.springframework.data.jpa.repository.JpaRepository;

import com.puente.puenteapp.controller.util.PuenteException;

public class BaseController {
	
	/**
	 * Use reflection to copy object properties
	 * @param from
	 * @param to
	 */
    protected static <T1, T2> void copy(T1 from, T2 to) {
        Class<?> fromClass = from.getClass();
        Class<?> toClass = to.getClass();
        Field[] fromFields = fromClass.getDeclaredFields();
        Object value;
        for (Field fromField : fromFields) {
            Field toField;
            try {
                toField = toClass.getDeclaredField(fromField.getName());
                toField.setAccessible(true);
                fromField.setAccessible(true);
                value = fromField.get(from);
                toField.set(to, value);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            }
        }
    }
    
    /**
     * common getById method to use on all entities
     * @param repository
     * @param id
     * @return
     * @throws PuenteException
     */
    protected <T> T getById(JpaRepository<T, Integer> repository, Integer id) throws PuenteException {
		return repository.findById(id).orElseThrow(() -> new PuenteException("Id not found: " + id));
	}

}
