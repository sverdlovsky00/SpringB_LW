package ru.arhipov.url_db_rest_max.health;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseHealthIndicator implements HealthIndicator{

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource){
        this.dataSource=dataSource;}

    @Override
    public Health health(){
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {return Health.up()
                        .withDetail("database", "H2 Database")
                        .withDetail("message", "База данных доступна").build();}
            else {return Health.down().withDetail("database", "H2 Database")
                        .withDetail("message", "База данных недоступна").build();}}
        catch (SQLException e) {return Health.down()
                    .withDetail("database", "H2 Database")
                    .withDetail("error", e.getMessage())
                    .build();}
    }
}