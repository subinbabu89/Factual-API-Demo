package self.subin.demo.telenotes.factualapi.custom;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelperBase;

/**
 * Custom implementation of the FadingActionBarHelperBase to change for AppCompatActivity instead of the regular Activity supported by the library
 * <p/>
 * Created by Subin on 3/11/2016.
 */
public class CustomFadingBar extends FadingActionBarHelperBase {

    private ActionBar mActionBar;

    /**
     * Implementation in order to accept AppCompatActivity instead of Activity
     *
     * @param activity instance of the AppCompatActivity to implement the feature on
     */
    public void initActionBar(AppCompatActivity activity) {
        mActionBar = activity.getSupportActionBar();
        super.initActionBar(activity);
    }

    @Override
    protected int getActionBarHeight() {
        return mActionBar.getHeight();
    }

    @Override
    protected boolean isActionBarNull() {
        return mActionBar == null;
    }

    @Override
    protected void setActionBarBackgroundDrawable(Drawable drawable) {
        mActionBar.setBackgroundDrawable(drawable);
    }
}
