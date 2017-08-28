package club.eval.jhipster.cucumber.stepdefs;

import club.eval.jhipster.JhipsterSampleApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = JhipsterSampleApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
