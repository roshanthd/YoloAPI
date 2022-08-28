import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Posts;
import org.testng.annotations.Test;
import utils.ApiData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestPost {

    @Test(description = "Verify user is able to create new posts", priority = 1)
    public void testCreateNewPosts() {
        int user_id = 2000;
        String title = "New Post";
        String body = "Body of the new Post of user :" + user_id;

        Posts posts = new Posts(user_id, title, body);

        Response response = given().when()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(posts.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.POST)
                .then()
                .statusCode(201)
                //verify the content of the response
                .body("user_id", equalTo(user_id))
                .body("title", equalTo(title))
                .body("body", equalTo(body))
                .log().body()
                .extract().response();
        JsonPath jsonPathEvaluator = response.jsonPath();
        String post_id = jsonPathEvaluator.getString("id");

        // use the same ID of the POST created above to fetch the created response
        given().when()
                .get(ApiData.BASE_URL + ApiData.POST + post_id)
                .then()
                .statusCode(200)
                .log().body()
                .body("user_id", equalTo(user_id),"does not exist")
                .body("title", equalTo(title))
                .body("body", equalTo(body));
    }

    @Test(description = "Verify user is able to create new posts invalid user_id", priority = 2)
    public void testCreateNewPostsWithInvalidUserID() {
        int user_id = 0000;
        String title = "New Post";
        String body = "Body of the new Post of user :" + user_id;

        Posts posts = new Posts(user_id, title, body);

        Response response = given().when()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(posts.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.POST)
                .then()
                .statusCode(422)
                //verify the content of the response
                .body("[0].field", equalTo("user"))
                .body("[0].message", equalTo("must exist"))
                .log().body()
                .extract().response();
        JsonPath jsonPathEvaluator = response.jsonPath();
        String post_id = jsonPathEvaluator.getString("id");

        // use the same ID of the POST created above to fetch the created response
        given().when()
                .get(ApiData.BASE_URL + ApiData.POST + post_id)
                .then()
                .statusCode(404)
                .log().body();
    }
}
