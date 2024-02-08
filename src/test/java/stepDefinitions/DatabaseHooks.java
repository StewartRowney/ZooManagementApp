package stepDefinitions;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

public class DatabaseHooks {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setupTestData() {
        try {
            ClassPathResource resource = new ClassPathResource("test-data.sql");
            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String sql = new String(bdata, StandardCharsets.UTF_8);

            // Execute SQL script
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            throw new RuntimeException("Failed to load test data", e);
        }
    }
}
