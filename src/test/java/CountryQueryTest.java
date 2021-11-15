import helpers.Constants;
import org.testng.annotations.Test;
import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryQueryTest {
    @Test
    public void shouldReturnStatus200AndAllRequiredFields(){
        given().
                header(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_APPLICATION).
                body("{\"query\":\"query {\\n  country(code: \\\"BR\\\") {\\n    name\\n    native\\n    capital\\n    emoji\\n    currency\\n    languages {\\n        code\\n        name\\n    }\\n  }\\n}\",\"variables\":{}}").
                when().
                post(Constants.BASE_URL).
                then().
                assertThat().
                statusCode(200).
                assertThat().body(containsString("{\"data\":{\"country\":{\"name\":\"Brazil\",\"native\":\"Brasil\",\"capital\":\"Brasília\",\"emoji\":\"\uD83C\uDDE7\uD83C\uDDF7\",\"currency\":\"BRL\",\"languages\":[{\"code\":\"pt\",\"name\":\"Portuguese\"}]}}}"));
    }

    @Test
    public void  shouldReturnDtatus400ErrorMessageForInvalidField() {
        given().
                header(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_APPLICATION).
                body("{\"query\":\"query {\\n  country(code: \\\"BR\\\") {\\n    nome\\n    native\\n    capital\\n    emoji\\n    currency\\n    languages {\\n        code\\n        name\\n    }\\n  }\\n}\",\"variables\":{}}").
                when().
                post(Constants.BASE_URL).
                then().
                assertThat().
                statusCode(400).
                assertThat().body(containsString("{\"errors\":[{\"message\":\"Cannot query field \\\"nome\\\" on type \\\"Country\\\". Did you mean \\\"name\\\" or \\\"code\\\"?\",\"locations\":[{\"line\":3,\"column\":5}],\"extensions\":{\"code\":\"GRAPHQL_VALIDATION_FAILED\"}}]}\n"));
    }
}
