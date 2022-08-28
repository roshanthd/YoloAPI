## YoloAPI

### Test scenarios Covered
- See the sample REST API at: https://gorest.co.in/ and create a personal access token
- Create a new user, post, comment and todo. A test for success scenario and also for when some mandatory fields are missing.
- Test if you can create two users with the same email address.
- Test if the endpoints allow you to create new entries with invalid email address formats.
- Fetch the entries that you created, test that the returned data matches your input.

### Tools and Frameworks used
This automation is developed based on "BDD Test (Behavior Driven Development". This helps in readability, Clarity in requirements and acceptance critera and so on.

- IntelliJ community edition - IDE used.
- Java - Programming language used (18.0.2.1).
- TestNG for @Test Annotations.
- Maven Build tool for dependency management.
- Rest assured -  supports a Given/When/Then test notation, which makes tests human readable.
- Lombok plugin to generate @Getters and @Setters

### Steps to execute the test case (Manual)

#### Users Test

- Verify new user data can be created
- Verify new user data can be created without a token
- Verify new user data with invalid headers
- Verify two user can be created with the same email address.
- Verify new user data can be created with invalid email
- Verify user is able to delete user

#### Posts Test

- Verify user is able to create new posts
- Verify user is able to create new posts invalid user_id

#### Comments Test
- Verify user is able to add new Comments
- Verify user is not able to send add comments without a valid token
- Verify fetching comment successfully

#### Todo Test
- Verify user is able to create ToDo
- Verify user is able to create ToDo without access token
- Verify user is able to update Todo
- Verify user is able to delete Todo

### Steps to execute (Automation run)
- Open project file in IDE IntelliJ
- resolve the dependencies in pom.xml file to download and resolve the dependencies for the versions specified eg- Example.
- Run the test by executing each test method in classes.
- Verify the Animated GIF below for the results

![Automation API](https://user-images.githubusercontent.com/53547532/187061412-06709692-c909-4cbc-a226-299372e24946.gif)

