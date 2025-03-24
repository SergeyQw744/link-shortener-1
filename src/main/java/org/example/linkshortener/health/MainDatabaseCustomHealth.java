package org.example.linkshortener.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Endpoint(id = "db-custom-health")
public class MainDatabaseCustomHealth {

    private final DataSource dataSource;

    @Autowired
    public MainDatabaseCustomHealth(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @ReadOperation
    public Health health() {
        try (Connection conn = dataSource.getConnection()){
            if (conn.isValid(2)){
                return Health.up()
                        .withDetail("database", "connection is valid")
                        .build();
            } else {
                return Health.down()
                        .withDetail("database", "connection is invalid")
                        .build();
            }
        } catch (SQLException e) {
            return Health.down(e)
                    .withDetail("database", "connection failed")
                    .build();
        }
    }
}
