package com.github.hikelin.testng.common.provider.jdbc;

import com.google.common.base.CaseFormat;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDao {

    private static Logger log = LoggerFactory.getLogger(BaseDao.class);

    protected JdbcTemplate jdbcTemplate;

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BaseDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public <T> T mapResultSetToModel(ResultSet rs, Class<T> clazz) {
        T model = null;
        try {
            model = clazz.newInstance();
            for(Field field: clazz.getDeclaredFields()) {
                String fieldName = field.getName();
                String columnLabel = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
                try {
                    PropertyUtils.setSimpleProperty(model, fieldName, rs.getObject(columnLabel));
                } catch (NoSuchMethodException | InvocationTargetException | SQLException e) {
                    log.error("Can not map {} to {}, Error: {}", columnLabel, fieldName, e);
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            log.error("Can not create instance for class: {}, Error: {}", clazz.getName(), e);
        }
        return model;
    }
}
