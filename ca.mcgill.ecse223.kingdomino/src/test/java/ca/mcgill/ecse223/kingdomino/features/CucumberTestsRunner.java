package ca.mcgill.ecse223.kingdomino.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = "pretty", 
//		features = "src/test/resources/ca/mcgill/ecse223/kingdomino/features",
<<<<<<< HEAD
		features = "src/test/resources/to_do/SortingAndRevealingDraft.feature",
=======
		features = "src/test/resources/ca/mcgill/ecse223/kingdomino/features/SetGameOptions.feature",
>>>>>>> parent of 6c7061b... d2 refactoring almost complete
		glue = "ca.mcgill.ecse223.kingdomino.features")
public class CucumberTestsRunner {
}
