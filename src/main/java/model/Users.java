package model;

//getters and setters generators through Lombok plugin to reduce code redundancy
@Getter
@Setter
public class Users {

    private String name;
    private String gender;
    private String email;
    private String status;

    public Users(String name, String gender, String email, String status) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.status = status;
    }
    @Override
    public String toString()
    {
        return "{\n" +
                    "    \"name\" : \""+name+"\",\n" +
                    "    \"gender\" : \""+gender+"\",\n" +
                    "    \"email\" : \""+email+"\",\n" +
                    "    \"status\" : \""+status+"\"\n" +
                    "}";
    }
}