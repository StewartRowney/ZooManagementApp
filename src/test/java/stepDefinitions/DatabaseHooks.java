package stepDefinitions;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.Objects;

public class DatabaseHooks {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setupTestData() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
                new ClassPathResource("test-data.sql"));
        populator.execute(Objects.requireNonNull(jdbcTemplate.getDataSource()));
    }
}
