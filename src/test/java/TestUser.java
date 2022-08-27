import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Users;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import utils.ApiData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestUser {

    @Test(description = "Users - Verify new user data can be created", priority = 1)
    public void testCreateUserWithValidToken() {
        String name = RandomStringUtils.random(6, true, false);
        String gender = "male";
        String email = name + "@gmail.com";
        String status = "active";

        Users users = new Users(name, gender, email, status);

        given()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(users.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.CREATE_USER)
                .then()
                .statusCode(201) //Check for POST success Status code
                .log().body() //Log the response

                // Verify the response body matches the variables passed in the request body
                .body("name", equalTo(name))
                .body("gender", equalTo(gender))
                .body("email", equalTo(email))
                .body("status", equalTo(status));
    }

    @Test(description = "Users - Verify new user data can be created without a token", priority = 2)
    public void testCreateUserWithoutToken() {
        String name = RandomStringUtils.random(6, true, false);
        String gender = "male";
        String email = name + "@gmail.com";
        String status = "active";

        Users users = new Users(name, gender, email, status);

        given()
                .header("Authorization", "Bearer " + "")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(users.toString())
                .log().body()
                .when()
                .post(ApiData.BASE_URL + ApiData.CREATE_USER)
                .then()
                .statusCode(401) //Check for POST Un-Authorized Status code
                .log().body() //Log the response
                .body("message", equalTo("Authentication failed"))
                .extract().response();
    }

    @Test(description = "Users - Verify new user data with invalid headers", priority = 3)
    public void testCreateUserWithInvalidHeaders() {
        String name = RandomStringUtils.random(6, true, false);
        String gender = "male";
        String email = name + "@gmail.com";
        String status = "active";

        Users users = new Users(name, gender, email, status);

        given()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "text") // Headers when missing Content-Type
                .body(users.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.CREATE_USER)
                .then()
                .statusCode(422) //Check for POST Un-Authorized Status code
                .log().body() //Log the response
                .extract().response();
    }

    @Test(description = "Users - Verify two user can be created with the same email address", priority = 4)
    public void testCreateNewUsersWithSameEmail() {
        //Creating random strings for usernames and email to avoid duplicates
        String name1 = RandomStringUtils.random(6, true, false);
        String name2 = RandomStringUtils.random(6, true, false);
        String gender = "male";
        String email = name1 + "@gmail.com";
        String status = "active";

        Users user1 = new Users(name1, gender, email, status);
        Users user2 = new Users(name2, gender, email, status);

        given()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(user1.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.CREATE_USER)
                .then()
                .statusCode(201) //Check for POST Success Status code
                .log().body(); //Log the response

        given()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(user2.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.CREATE_USER)
                .then()
                .statusCode(422) //Check for POST Un-processable Entity response  Status code
                .log().body() //Log the response
                //Verify the error response
                .body("[0].field", equalTo("email"))
                .body("[0].message", equalTo("has already been taken"));
    }

    @Test(description = "Users - Verify new user data can be created with invalid email", priority = 5)
    public void testCreateUserWithInvalidEmail() {
        String name = RandomStringUtils.random(6, true, false);
        String gender = "male";
        String InvalidEmail = name + "|@!@.com";
        String status = "active";

        Users users = new Users(name, gender, InvalidEmail, status);
        given()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(users.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.CREATE_USER)
                .then()
                .statusCode(422) //Check for POST success Status code
                .log().body() //Log the response
                //Verify the error response
                .body("[0].field", equalTo("email")) //Checking first Index's field
                .body("[0].message", equalTo("is invalid"));//Checking first Index's message
    }

    @Test(description = "Verify user is able to delete user", priority = 6)
    public void testDeleteUser() {
        //get the first id of the total user's response for the delete operation
        Response response =
                given().when()
                        .get(ApiData.BASE_URL + ApiData.GET_USER)
                        .then()
                        .statusCode(200)
                        .extract().response();
        JsonPath jsonPathEvaluator = response.jsonPath();
        String user_id = jsonPathEvaluator.getString("[0].id");

        //Perform the delete operation for the user_id extracted
        given().when()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .delete(ApiData.BASE_URL + ApiData.GET_USER + user_id)
                .then()
                .statusCode(204) //Successful delete operation status code 204
                .log().body();

        //verify if the user_id is exists once deleted
        given().when()
                .get(ApiData.BASE_URL + ApiData.GET_USER + user_id)
                .then()
                .statusCode(404)
                .log().body()
                //Verify the warning message
                .body("message", equalTo("Resource not found"));
    }
}
