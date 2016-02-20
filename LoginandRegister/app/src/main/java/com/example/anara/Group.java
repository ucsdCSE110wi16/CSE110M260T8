ackage com.example.anara;

/**
 * Created by anara on 2/15/2016.
 */
public class Group {
    String name;
    String desc;
    String[] keywords;

    public String getGroupName() { return name; }

    public void setGroupName(String name) { this.name = name; }

    public String[] getKeywords() { return keywords; }

    public void setKeywords(String[] keywords) { this.keywords = keywords; }

    public String getDescription() { return desc; }

    public void setDescription( String desc){ this.desc = desc; }

    public Group(){

    }
}