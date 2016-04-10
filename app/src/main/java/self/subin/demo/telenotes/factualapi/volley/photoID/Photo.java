package self.subin.demo.telenotes.factualapi.volley.photoID;

import java.util.List;

/**
 * Created by Subin on 3/10/2016.
 */
public class Photo {
    private int page;
    private int pages;
    private int perpage;
    private int total;
    private List<IndivPhoto> photo;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<IndivPhoto> getPhoto() {
        return photo;
    }

    public void setPhoto(List<IndivPhoto> photo) {
        this.photo = photo;
    }
}
