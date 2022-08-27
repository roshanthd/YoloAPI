package model;

@Getter
@Setter
public class Comments {

    private int post_id;
    private String name;
    private String email;
    private String body;

    public Comments(int post_id, String name, String email, String body) {
        this.post_id = post_id;
        this.name = name;
        this.email = email;
        this.body = body;
    }
    @Override
    public String toString()
    {
        return "{\n" +
                "    \"post_id\" : \""+post_id+"\",\n" +
                "    \"name\" : \""+name+"\",\n" +
                "    \"email\" : \""+email+"\",\n" +
                "    \"body\" : \""+body+"\"\n" +
                "}";
    }
}
