package ca.mcgill.ecse223.kingdomino.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = "pretty", 
		features = "src/test/resources/ca/mcgill/ecse223/kingdomino/features",
//		features = "src/test/resources/ca/mcgill/ecse223/kingdomino/features/SetGameOptions.feature",
		glue = "ca.mcgill.ecse223.kingdomino.features")
public class CucumberTestsRunner {
}
