package cucumber.runtime.java;

import cucumber.api.StepDefinitionReporter;
import io.cucumber.stepexpression.TypeRegistry;
import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;
import cucumber.runtime.Glue;
import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.PickleStepDefinitionMatch;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.java.stepdefs.Stepdefs;
import gherkin.pickles.PickleStep;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class JavaBackendTest {

    private ObjectFactory factory;
    private JavaBackend backend;

    @Before
    public void createBackend(){
        ClassLoader classLoader = currentThread().getContextClassLoader();
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        ResourceLoaderClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        this.factory = new DefaultJavaObjectFactory();
        TypeRegistry typeRegistry = new TypeRegistry(Locale.ENGLISH);
        this.backend = new JavaBackend(factory, classFinder, typeRegistry);
    }

    @Test
    public void finds_step_definitions_by_classpath_url() {
        GlueStub glue = new GlueStub();
        backend.loadGlue(glue, asList("classpath:cucumber/runtime/java/stepdefs"));
        backend.buildWorld();
        assertEquals(Stepdefs.class, factory.getInstance(Stepdefs.class).getClass());
    }

    @Test
    public void finds_step_definitions_by_package_name() {
        GlueStub glue = new GlueStub();
        backend.loadGlue(glue, asList("cucumber.runtime.java.stepdefs"));
        backend.buildWorld();
        assertEquals(Stepdefs.class, factory.getInstance(Stepdefs.class).getClass());
    }

    @Test(expected = CucumberException.class)
    public void detects_subclassed_glue_and_throws_exception() {
        GlueStub glue = new GlueStub();
        backend.loadGlue(glue, asList("cucumber.runtime.java.stepdefs", "cucumber.runtime.java.incorrectlysubclassedstepdefs"));
    }

    private class GlueStub implements Glue {
        public final List<StepDefinition> stepDefinitions = new ArrayList<StepDefinition>();

        @Override
        public void addStepDefinition(StepDefinition stepDefinition) {
            stepDefinitions.add(stepDefinition);
        }

        @Override
        public void addBeforeStepHook(HookDefinition beforeStepHook) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addBeforeHook(HookDefinition hookDefinition) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addAfterStepHook(HookDefinition hookDefinition) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addAfterHook(HookDefinition hookDefinition) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<HookDefinition> getBeforeHooks() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<HookDefinition> getAfterStepHooks() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<HookDefinition> getBeforeStepHooks() {
            return null;
        }

        @Override
        public List<HookDefinition> getAfterHooks() {
            throw new UnsupportedOperationException();
        }

        @Override
        public PickleStepDefinitionMatch stepDefinitionMatch(String featurePath, PickleStep step) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void reportStepDefinitions(StepDefinitionReporter stepDefinitionReporter) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeScenarioScopedGlue() {
        }
    }
}
