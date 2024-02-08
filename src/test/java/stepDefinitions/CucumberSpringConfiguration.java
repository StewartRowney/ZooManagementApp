package stepDefinitions;

import com.example.zoo.ZooManagementAppApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@CucumberContextConfiguration
@ActiveProfiles("test")
//@Sql("../test-data.sql")
@SpringBootTest
@ContextConfiguration(classes = ZooManagementAppApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CucumberSpringConfiguration {
}
