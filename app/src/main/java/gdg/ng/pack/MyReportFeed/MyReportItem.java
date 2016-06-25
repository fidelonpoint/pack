package gdg.ng.pack.MyReportFeed;

/**
 * Created by Hanson on 6/22/2016.
 */
public class MyReportItem {
    private String imageUrl;
    private String title;
    private String address;
    private String support_count;
    private String seen_count;


    private String time;
    private int placeHolder;



    public MyReportItem(){

    }
    // this constructor change be restructed to fit the backend requirement
    // the drawable argument is just for the dummy placeholder image
    public MyReportItem(String imageUrl,String title,String address ,String time, String support_count, String seen_count,  int placeHolder) {
        this.address = address;
        this.imageUrl = imageUrl;
        this.seen_count = seen_count;
        this.support_count = support_count;
        this.title = title;
        this.placeHolder = placeHolder;
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSeen_count() {
        return seen_count;
    }

    public void setSeen_count(String seen_count) {
        this.seen_count = seen_count;
    }

    public String getSupport_count() {
        return support_count;
    }

    public void setSupport_count(String support_count) {
        this.support_count = support_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
