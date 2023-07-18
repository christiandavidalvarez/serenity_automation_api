package co.com.automatio.certification;



import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(SerenityJUnit5Extension.class)
public class TestApiEmployees {

    private EnvironmentVariables environmentVariables;
    private Actor sam;
    private String theRestApiBaseUrl;

    public TestApiEmployees() {
        this.environmentVariables = Injectors.getInjector().getInstance(EnvironmentVariables.class);
    }


    @BeforeEach
    public void prepareTest(){

        theRestApiBaseUrl = environmentVariables.optionalProperty("restapi.baseurl")
                .orElse("https://dummy.restapiexample.com/api/v1");
        sam=Actor.named("Sam employers boss").whoCan(CallAnApi.at(theRestApiBaseUrl));

    }

    @Test
    public void test(){
        sam.attemptsTo(
                Get.resource("/employees")
        );
        sam.should(
                seeThatResponse("Verify status",
                        response->response.statusCode(200)
                        )
        );

    }


}
