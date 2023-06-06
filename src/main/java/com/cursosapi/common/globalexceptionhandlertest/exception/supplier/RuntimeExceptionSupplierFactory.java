package com.cursosapi.common.globalexceptionhandlertest.exception.supplier;

import com.cursosapi.common.globalexceptionhandlertest.exception.EntityNotFoundException;

import java.util.function.Supplier;

public class RuntimeExceptionSupplierFactory {
    public static <E> Supplier<? extends RuntimeException > createEntityNotFoundExceptionSupplier(Class<E> entityClass, String searchParameter) {
        String className = entityClass.getSimpleName();
        return () -> new EntityNotFoundException(String.format("%s not found [search parameter: %s]", className, searchParameter));
    }

}
