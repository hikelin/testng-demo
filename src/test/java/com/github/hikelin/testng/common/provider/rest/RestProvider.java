package com.github.hikelin.testng.common.provider.rest;

import com.github.hikelin.testng.common.provider.jdbc.ModelArgument;

import java.util.ArrayList;
import java.util.List;

public class RestProvider {

    public static Object[][] prepareArguments(List<?> modelList){
        List<Object[]> objects = new ArrayList<>();
        modelList.forEach(m -> {
            objects.add(new Object[]{new ModelArgument<>(m)});
        });
        return objects.toArray(new Object[0][]);
    }
}
