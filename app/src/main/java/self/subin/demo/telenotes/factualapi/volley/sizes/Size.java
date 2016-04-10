package self.subin.demo.telenotes.factualapi.volley.sizes;

import java.util.List;

/**
 * Created by Subin on 3/10/2016.
 */
public class Size {
    private int canblog;
    private int canprint;
    private int candownload;
    private List<IndivSize> size;

    public int getCanblog() {
        return canblog;
    }

    public void setCanblog(int canblog) {
        this.canblog = canblog;
    }

    public int getCanprint() {
        return canprint;
    }

    public void setCanprint(int canprint) {
        this.canprint = canprint;
    }

    public int getCandownload() {
        return candownload;
    }

    public void setCandownload(int candownload) {
        this.candownload = candownload;
    }

    public List<IndivSize> getSize() {
        return size;
    }

    public void setSize(List<IndivSize> size) {
        this.size = size;
    }
}
