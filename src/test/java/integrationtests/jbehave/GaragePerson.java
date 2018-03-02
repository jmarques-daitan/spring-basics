package integrationtests.jbehave;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import integrationtests.jbehave.steps.GaragePersonSteps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;

@RunWith(JUnitReportingRunner.class)
public class GaragePerson extends JUnitStory {

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(new StoryReporterBuilder().withFormats(Format.CONSOLE, Format.TXT, Format.HTML));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), "integrationtests.jbehave.step");
    }
}
