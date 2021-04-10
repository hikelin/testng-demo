package com.github.hikelin.testng.cases;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.hikelin.testng.common.provider.rest.RestClient;
import com.github.hikelin.testng.common.provider.rest.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RestDemoTest {

    private Logger log = LoggerFactory.getLogger(RestDemoTest.class);

    private RestClient restClient;

    @BeforeClass
    public void beforeClass() {
        this.restClient = new RestClient();
    }

    @Test
    public void demoTest() throws RestException {
        String response = this.restClient.get("https://gitee.com/api/v5/repos/baidu/apache-doris/stargazers");
        log.info(response);
    }

    @Test
    public void requestObjectTest() throws RestException {
        JsonNode stargazer = this.restClient.get("https://gitee.com/api/v5/repos/baidu/apache-doris/stargazers", JsonNode.class);
        log.info(stargazer.toPrettyString());
    }

    @Test
    public void postTest() throws RestException {
       Map<String, String> header = new HashMap<>();
       header.put("Accept", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("access_token", "xxxxx");
        body.put("name", "xxxxx");
        body.put("org", "xxxxx");
        body.put("description", "xxxxx");

        String response = this.restClient.post("https://gitee.com/api/v5/users/organization", header, body, String.class);
        log.info(response);
    }
}
