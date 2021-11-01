package tests;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTests {
    String URL = "https://reqres.in";

    @Test(description = "List users, RESPONSE:200")
    public void ListUsers() {
        given().
                log().all().
        when().
                get(URL + "/api/users?page=2").
        then().
                statusCode(200).
                body("data[0].email", equalTo("michael.lawson@reqres.in"));
    }

    @Test(description = "Single user, RESPONSE:200")
    public void SingleUser() {
        given().
                log().all().
        when().
                get(URL + "/api/users/2").
        then().
                statusCode(200).
                body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test(description = "Single user not found, RESPONSE:404")
    public void SingleUserNotFound() {
        given().
                log().all().
        when().
                get(URL + "/api/users/23").
        then().
                statusCode(404);
    }

    @Test(description = "List <RESOURCE>, RESPONSE:200")
    public void ListResource() {
        given().
                log().all().
        when().
                get(URL + "/api/unknown").
        then().
                statusCode(200).
                body("data[0].color", equalTo("#98B2D1")).
                body("data[1].color", equalTo("#C74375"));
    }

    @Test(description = "Single <RESOURCE>, RESPONSE:200")
    public void SingleResource() {
        given().
                log().all().
        when().
                get(URL + "/api/unknown/2").
        then().
                statusCode(200).
                body("data.color", equalTo("#C74375")).
                body("support.url", equalTo("https://reqres.in/#support-heading"));
    }

    @Test(description = "Single <RESOURCE> not found, RESPONSE:404")
    public void SingleNotFound() {
        given().
                log().all().
        when().
                get(URL + "/api/unknown/23").
        then().
                statusCode(404);
    }

    @Test(description = "Post create, RESPONSE:201")
    public void PostCreate() {
        given().
                body("{\n" +
                        "\t\"name\":\"morpheus\",\n" +
                        "\t\"job\":\"leader\"\n" + "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post(URL + "/api/users").
        then().
                log().all().
                statusCode(201).
                body("name", equalTo("morpheus"),
                        "job", equalTo("leader"));
    }

    @Test(description = "Put update, RESPONSE:200")
    public void PutUpdate() {
        given().
                body("{\n" +
                        "\t\"name\":\"morpheus\",\n" +
                        "\t\"job\":\"zion resident\"\n" + "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                put(URL + "/api/users/2").
        then().
                log().all().
                statusCode(200).
                body("name", equalTo("morpheus"),
                        "job", equalTo("zion resident"));
    }

    @Test(description = "Delete, RESPONSE:204")
    public void Delete() {
        given().
                log().all().
        when().
                delete(URL + "/api/users/2").
        then().
                log().all().
                statusCode(204);
    }

    @Test(description = "Post, Register Successful, RESPONSE:200")
    public void PostRegister() {
        given().
                body("{\n" +
                        "\t\"email\":\"eve.holt@reqres.in\",\n" +
                        "\t\"password\":\"pistol\"\n" + "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post(URL + "/api/register").
        then().
                log().all().
                statusCode(200).
                body("id", equalTo(4),
                        "token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test(description = "Post, Register Unsuccessful, RESPONSE:400")
    public void PostRegisterUnsuccessful() {
        given().
                body("{\n" +
                        "\t\"email\":\"eve.holt@reqres.in\"\n" + "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post(URL + "/api/register").
        then().
                log().all().
                statusCode(400).
                body("error", equalTo("Missing password"));
    }

    @Test(description = "Post, Login Successful, RESPONSE:200")
    public void PostLoginSuccessful() {
        given().
                body("{\n" +
                        "\t\"email\":\"eve.holt@reqres.in\",\n" +
                        "\t\"password\":\"cityslicka\"\n" + "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post(URL + "/api/login").
        then().
                log().all().
                statusCode(200).
                body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test(description = "Post, Login Unsuccessful, RESPONSE:400")
    public void PostLoginUnsuccessful() {
        given().
                body("{\n" +
                        "\t\"email\":\"peter@klaven\"\n" + "}").
                header("Content-Type", "application/json").
                log().all().
        when().
                post(URL + "/api/login").
        then().
                log().all().
                statusCode(400).
                body("error", equalTo("Missing password"));
    }

    @Test(description = "Get, Delayed Response, RESPONSE:200")
    public void GetDelayedResponse() {
        given().
                log().all().
        when().
                get(URL + "/api/users?delay=3").
        then().
                log().all().
                statusCode(200).
                body("data[0].id", equalTo(1));
    }
}
