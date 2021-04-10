package com.github.hikelin.testng.common.provider.rest;

public class EntityArgument<T> {

    private final T entity;

    public EntityArgument(T entity) {
        this.entity = entity;
    }

    public T getEntity(){
        return this.entity;
    }
}
