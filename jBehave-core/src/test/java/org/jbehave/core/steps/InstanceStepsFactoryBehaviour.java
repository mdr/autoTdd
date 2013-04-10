package org.jbehave.core.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.AnnotationProcessor;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.junit.Test;

public class InstanceStepsFactoryBehaviour {
    
    @Test
    public void shouldCreateCandidateSteps() {
        InjectableStepsFactory factory = new InstanceStepsFactory(new MostUsefulConfiguration(),AnnotationProcessor.defaultAnnotationProcessor(), new MySteps());
        List<CandidateSteps> candidateSteps = factory.createCandidateSteps();
        assertThat(candidateSteps.size(), equalTo(1));
        assertThat(candidateSteps.get(0), instanceOf(Steps.class));
        ParameterConverters converters = ((Steps)candidateSteps.get(0)).configuration().parameterConverters();
        assertThat((String)converters.convert("value", String.class), equalTo("valueConverted"));
    }    

    @Test
    public void shouldDetermineIfStepsInstanceHasAnnotatedMethods() {
        InstanceStepsFactory factory = new InstanceStepsFactory(new MostUsefulConfiguration(),AnnotationProcessor.defaultAnnotationProcessor());
        assertThat(factory.hasAnnotatedMethods(MySteps.class), is(true));
        assertThat(factory.hasAnnotatedMethods(NoAnnotatedMethods.class), is(false));
    }    

    static class MySteps  {

        @Given("foo named $name")
        public void givenFoo(String name) {
        }

        @When("foo named $name")
        public void whenFoo(String name) {
        }

        @Then("foo named $name")
        public void thenFoo(String name) {
        }

        @AsParameterConverter
        public String convert(String value){
            return value+"Converted";
        }
    }
    
    static class NoAnnotatedMethods {
        
    }
}
