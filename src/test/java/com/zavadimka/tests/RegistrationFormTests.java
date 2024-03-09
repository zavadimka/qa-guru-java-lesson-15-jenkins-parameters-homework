package com.zavadimka.tests;

import com.zavadimka.pages.LambdaSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("demoqa")
public class RegistrationFormTests extends TestBase {

    @Test
    @DisplayName("Successful registration test")
    void successfulRegistrationFormFillingTest() {

        LambdaSteps steps = new LambdaSteps(createProjectDataConfig());

        steps.fillTheForm();
        steps.verifyRegistrationFormSummaryTable();
        steps.checkBackToTheEmptyRegistrationFormPage();
    }
}
