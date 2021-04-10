package com.github.hikelin.testng.common.provider.jdbc;

public class ModelArgument<T> {

    private final T model;

    public ModelArgument(T model) {
        this.model = model;
    }

    public T getModel(){
        return this.model;
    }
}
