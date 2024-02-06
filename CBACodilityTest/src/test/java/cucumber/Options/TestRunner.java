package cucumber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/features",    
//    tags = "@test",
    glue = {"stepDefinations"},
    plugin = {
        "pretty",
        "json:target/jsonReports/cucumber-reports.json"
    }
)
public class TestRunner {
}