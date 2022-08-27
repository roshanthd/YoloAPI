package model;
@Getter
@Setter
public class Posts {
    private int user_id;
    private String title;
    private String body;

    public Posts(int user_id, String title, String body) {
        this.user_id = user_id;
        this.title = title;
        this.body = body;
    }
    @Override
    public String toString()
    {
        return "{\n" +
                "    \"user_id\" : \""+user_id+"\",\n" +
                "    \"title\" : \""+title+"\",\n" +
                "    \"body\" : \""+body+"\"\n" +
                "}";
    }
}
