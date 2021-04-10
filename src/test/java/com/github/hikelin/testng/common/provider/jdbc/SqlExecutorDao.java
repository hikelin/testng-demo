package com.github.hikelin.testng.common.provider.jdbc;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public interface SqlExecutorDao {
    <T> List<T> executeSql(String sql, SqlParameterSource paramSource, Class<T> clazz);
}
