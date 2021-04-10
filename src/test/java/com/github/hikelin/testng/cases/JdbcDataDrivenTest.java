package com.github.hikelin.testng.cases;

import com.github.hikelin.testng.common.provider.jdbc.JdbcProvider;
import com.github.hikelin.testng.common.provider.jdbc.ModelArgument;
import com.github.hikelin.testng.common.provider.jdbc.SqlExecutorDao;
import com.github.hikelin.testng.common.provider.jdbc.impl.SqlExecutorDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.util.List;


public class JdbcDataDrivenTest {

    private static Logger log = LoggerFactory.getLogger(JdbcDataDrivenTest.class);

    public final static String JDBC_DATA_DEMO = "JDBC_DATA_DEMO";

    private transient SqlExecutorDao sqlExecutorDao;

    @BeforeClass
    public void beforeClass() {
        DataSource dataSource = new SimpleDriverDataSource();
        this.sqlExecutorDao = new SqlExecutorDaoImpl(dataSource);
    }

    @DataProvider(name = JDBC_DATA_DEMO)
    public Object[][] jdbcDataDemoProvider() {
        String sql = "select * from demo where age = :age";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("age", 18);
        List<Demo> demoList = this.sqlExecutorDao.executeSql(sql, sqlParameterSource, Demo.class);
        return JdbcProvider.prepareArguments(demoList);
    }

    @Test(dataProvider = JDBC_DATA_DEMO)
    public void demoTest(ModelArgument<Demo> modelArgument) {
        Demo demo = modelArgument.getModel();
        Assert.assertEquals(demo.getAge(), "18");
    }

}