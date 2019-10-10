package vineeth.test.com.testapp.retro_fit_ex;

public class PhotoModel {

    private long id;
    private String title,url,thumbNail;

    public PhotoModel(long id,String title,String url,String thumbNail)
    {
           this.id = id;
           this.title=title;
           this.url = url;
           this.thumbNail = thumbNail;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
}
