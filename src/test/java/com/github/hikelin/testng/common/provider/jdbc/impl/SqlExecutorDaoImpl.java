package com.github.hikelin.testng.common.provider.jdbc.impl;

import com.github.hikelin.testng.common.provider.jdbc.BaseDao;
import com.github.hikelin.testng.common.provider.jdbc.SqlExecutorDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.List;

public class SqlExecutorDaoImpl extends BaseDao implements SqlExecutorDao {

    private static Logger log = LoggerFactory.getLogger(SqlExecutorDaoImpl.class);

    public SqlExecutorDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <T> List<T> executeSql(String sql, SqlParameterSource paramSource, Class<T> clazz) {
       log.debug("Execute sql: {} with {}", sql, paramSource);
       return this.namedParameterJdbcTemplate.query(sql, paramSource, (rs, rowNum) -> mapResultSetToModel(rs, clazz));
    }

}
