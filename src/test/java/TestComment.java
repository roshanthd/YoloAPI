import model.Comments;
import org.testng.annotations.Test;
import utils.ApiData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestComment {

    @Test(description = "Verify user is able to add new Comments", priority = 1)
    public void testCreateNewComment() {
        int post_id = 1400;
        String name = "New Post";
        String email = "Test@gmail.com";
        String body = "Body of the new Comment";

        Comments comments = new Comments(post_id, name, email, body);

        given().when()
                .header("Authorization", "Bearer " + ApiData.TOKEN)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(comments.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.COMMENT)
                .then()
                .statusCode(201)

                //verify the content of the response
                .body("post_id", equalTo(post_id))
                .body("name", equalTo(name))
                .body("email", equalTo(email))
                .body("body", equalTo(body))
                .log().body();
    }

    @Test(description = "Verify user is not able to send add comments without a valid token", priority = 2)
    public void testCreateCommentWithInvalidToken() {
        int post_id = 1400;
        String name = "New Post";
        String email = "Test@gmail.com";
        String body = "Body of the new Comment";

        Comments comments = new Comments(post_id, name, email, body);
        System.out.println(comments);

        given().when()
                .header("Authorization", "Bearer " + "")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(comments.toString())
                .when()
                .post(ApiData.BASE_URL + ApiData.COMMENT)
                .then()
                .statusCode(401)

                // verify the content of the response
                .body("message", equalTo("Authentication failed"))
                .log().body();
    }

    @Test(description = "Verify fetching comment successfully", priority = 3)
    public void testFetchComment(){
        given().when()
                .get(ApiData.BASE_URL + ApiData.COMMENT + 1142)
                .then()
                .statusCode(200)
                .log().body();
    }
}