import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Todos;
import org.testng.annotations.Test;
import utils.ApiData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestToDo {

    @Test(description = "Verify user is able to create ToDo", priority = 1)
    public void testCreateTodoForUser() {

        int user_id = 2000;
        String title = "To Do Title";
        String due_on = "2022-09-03T00:00:00.000+05:30";
        String status = "pending";

        Todos todos = new Todos(user_id, title, due_on, status);

        //Perform the delete operation for the user_id extracted
        given()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(todos.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.TODO_USER)
                .then()
                .statusCode(201) //Successful creation
                .log().body()
                // verify response body
                .body("user_id", equalTo(user_id))
                .body("title", equalTo(title))
                .body("due_on", equalTo(due_on))
                .body("status", equalTo(status));
    }

    @Test(description = "Verify user is able to create ToDo without access token", priority = 2)
    public void testCreateTodoForUserWithoutValidToken() {

        int user_id = 2000;
        String title = "To Do Title";
        String due_on = "2022-09-03T00:00:00.000+05:30";
        String status = "pending";

        Todos todos = new Todos(user_id, title, due_on, status);

        //Perform the delete operation for the user_id extracted
        given()
                .header("Authorization", "Bearer " + "")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(todos.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.TODO_USER)
                .then()
                .statusCode(401) //Unauthorized without the token
                .log().body()
                // verify response body
                .body("message", equalTo("Authentication failed"));
    }

    @Test(description = "Verify user is able to update Todo", priority = 3)
    public void testUpdateToDo() {

        //get the first id of the total user's response for the delete operation
        Response response =
                given().when()
                        .get(ApiData.BASE_URL + ApiData.TODO_USER)
                        .then()
                        .statusCode(200)
                        .extract().response();
        JsonPath jsonPathEvaluator = response.jsonPath();
        String user_id = jsonPathEvaluator.getString("[0].id");

        String title = "New Updated title";
        String due_on = "2021-09-03T00:00:00.000+05:30";
        String status = "completed";

        Todos todos = new Todos(Integer.parseInt(user_id), title, due_on, status);

        //Perform the delete operation for the To do operation for user_id extracted
        given()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(todos.toString())
                .when()
                .patch(ApiData.BASE_URL + ApiData.TODO_USER + user_id)
                .then()
                .statusCode(200) //Successful Update operation
                .log().body()
                .body("user_id", equalTo(Integer.parseInt(user_id)))
                .body("title", equalTo(title))
                .body("due_on", equalTo(due_on))
                .body("status", equalTo(status))
                .body("id", equalTo(Integer.parseInt(user_id)));
    }

    @Test(description = "Verify user is able to delete Todo", priority = 4)
    public void testDeleteToDo() {
        //get the first id of the total user's response for the delete operation
        Response response =
                given().when()
                        .get(ApiData.BASE_URL + ApiData.TODO_USER)
                        .then()
                        .statusCode(200)
                        .extract().response();
        JsonPath jsonPathEvaluator = response.jsonPath();
        String user_id = jsonPathEvaluator.getString("[0].id");

        //Perform the delete operation for the To do operation for user_id extracted
        given().when()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .delete(ApiData.BASE_URL + ApiData.TODO_USER + user_id)
                .then()
                .statusCode(204) //Successful delete operation
                .log().body();

        //verify if the to do info exists once deleted
        given().when()
                .get(ApiData.BASE_URL + ApiData.TODO_USER + user_id)
                .then()
                .statusCode(404)
                .log().body()
                .body("message", equalTo("Resource not found")); // Message
    }
}
