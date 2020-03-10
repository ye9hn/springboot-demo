package com.henu.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() {//测试数据库连接情况
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
