import helpers.Constants;
import org.testng.annotations.Test;
import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class LanguageQueryTest {
    @Test
    public void shouldReturnStatus200AndBodyAttributes(){
        given().
            header(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_APPLICATION).
            body("{\"query\":\"query {\\nlanguage(code: \\\"en\\\"){\\n  name\\n  native\\n  rtl\\n  \\n  }\\n}\",\"variables\":{}}").
        when().
            post(Constants.BASE_URL).
        then().
            assertThat().
            statusCode(200).
            assertThat().body(containsString("{\"data\":{\"language\":{\"name\":\"English\",\"native\":\"English\",\"rtl\":false}}}"));
    }

    @Test
    public void  shouldReturnStatus200AndNullMessage() {
        given().
            header(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_APPLICATION).
            body("{\"query\":\"query {\\nlanguage(code: \\\"xx\\\"){\\n  name\\n  native\\n  rtl\\n  \\n  }\\n}\",\"variables\":{}}").
        when().
            post(Constants.BASE_URL).
        then().
            assertThat().
            statusCode(200).
            assertThat().body(containsString("{\"data\":{\"language\":null}}"));
    }

    @Test
    public void  shouldReturnStatus400AndErrorMessage() {
        given().
            header(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_APPLICATION).
            body("{\"query\":\"query {\\nlanguage(code: \\\"\\\"){\"variables\":{}}").
        when().
            post(Constants.BASE_URL).
            then().
        assertThat().
            statusCode(400).
            assertThat().body(containsString("Bad Request"));
    }
}
