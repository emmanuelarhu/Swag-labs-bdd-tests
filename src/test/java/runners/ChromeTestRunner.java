package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Chrome Test Runner - Forces Chrome browser execution
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "html:target/chrome-reports/html-report.tml",
                "json:target/chrome-reports/cucumber.json",
                "junit:target/chrome-reports/cucumber.xml"
        },
        monochrome = true,
        tags = "@swag_labs"
)
public class ChromeTestRunner {

    static {
        // Force Chrome browser for this runner
        System.setProperty("browser", "chrome");
    }
}