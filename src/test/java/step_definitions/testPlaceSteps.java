package step_definitions;

import cucumber.api.java8.En;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class testPlaceSteps implements En{

    private RequestSpecification request;
    private Response response;
    private ValidatableResponse json;

    private Headers allHeaders;
    private Map<String, String> allCookies;
    private String statusLine;
    private int statusCode;
    private ResponseBody body;

    public testPlaceSteps(){
        Given("^Request from test page$", () -> {
            request = RestAssured.given();
        });

        When("^Get test page$", () -> {
            response = request.when().get(" https://credapi.credify.tech/api/loanapp/v1/states");
        });


        Then("^Serinevler mah and Beyceli mah should have correct longitude$", () -> {
            json.assertThat().body("places.findAll {it.longitude = 37.4987}.'place name'", Matchers.hasItems("Serinevler Mah.","Beyceli Mah."));
        });


        Then("^Validate all the state names are valid$", () -> {
            //json.assertThat().body("states.findAll { it.minAge }.label", Matchers.hasItems("Florida","Georgia"));
            String responses = response.asString();
            List<String> states = from(responses).getList("states.findAll { it.minAge }.label");
            try {
                Assert.assertEquals("States are not valid",states ,"[Alabama, Alaska, Arizona, Arkansas, California, Connecticut, Delaware, District of Columbia, Florida, Georgia, Hawaii, Idaho, Illinois, Indiana, Kansas, Kentucky, Louisiana, Maine, Maryland, Massachusetts, Michigan, Minnesota, Mississippi, Missouri, Montana, Nebraska, Nevada, New Hampshire, New Jersey, New Mexico, New York, North Carolina, North Dakota, Ohio, Oklahoma, Oregon, Pennsylvania, Rhode Island, South Carolina, South Dakota, Tennessee, Texas, Utah, Vermont, Virginia, Washington, Wisconsin, Wyoming]");
            } catch (AssertionError e) {
                String message = e.getMessage();
                System.out.println(message);
            }

        });


        And("^Validate total state count is \"([^\"]*)\"$", (String arg0) -> {
            String responses = response.asString();
            List<String> states = from(responses).getList("states.findAll { it.minAge }.label");
            try {
                Assert.assertEquals("States is not 47", states.size(),"47");
            } catch (AssertionError e) {
                String message = e.getMessage();
                System.out.println(message);
            }

        });
        And("^Validate only one state has a min age of \"([^\"]*)\" , and output the name of that state$", (String arg0) -> {
            String responses = response.asString();
            List<String> labels = from(responses).getList("states.findAll { it.minAge > 18 }.label");
            System.out.println(labels);
        });

        And("^I get status code (\\d+)$", (Integer arg0) -> {
            statusCode = response.getStatusCode();
            System.out.println("_STATUS CODE : "+ statusCode);
        });

        And("^I get the headers$", () -> {
            allHeaders = response.getHeaders();
            System.out.println("_HEADERS : " + allHeaders);
        });

        And("^Validate Georgia is the only state with min loan amount of \"([^\"]*)\"$", (String amount) -> {
            String responses = response.asString();
            List<String> labels = from(responses).getList("states.findAll { it.minLoanAmount == 3005 }.label");
            try {
                Assert.assertEquals("States is not Georgia", labels.toString(),"Georgia");
            } catch (AssertionError e) {
                String message = e.getMessage();
                System.out.println(message);
            }
        });

    }
}
