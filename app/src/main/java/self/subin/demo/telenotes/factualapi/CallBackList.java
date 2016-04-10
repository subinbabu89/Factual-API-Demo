package self.subin.demo.telenotes.factualapi;

/**
 * interface for communication between the adapter and calling activity
 * <p/>
 * Created by Subin on 3/12/2016.
 */
public interface CallBackList {

    /**
     * method to pass message from calling class to the implementation class
     *
     * @param message
     */
    void receivecallback(String message);
}
