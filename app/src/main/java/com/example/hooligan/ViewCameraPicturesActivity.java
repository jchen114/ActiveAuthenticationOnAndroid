package com.example.hooligan;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.hooligan.R;
import com.example.hooligan.cameradatadumper.ImageViewBoundingBox;
import com.example.hooligan.cameradatadumper.ImageViewer;

public class ViewCameraPicturesActivity extends AppCompatActivity {

    public static Handler mHandler;
    public static final int SHOW_IMAGE_WITH_BOX = 0;
    RelativeLayout mRelativeLayout;

    public static ViewCameraPicturesActivity mSharedViewCameraPicturesActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewCameraPicturesActivity = this;
        setContentView(R.layout.activity_view_camera_pictures);
        mHandler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SHOW_IMAGE_WITH_BOX:
                        ImageViewer iv = (ImageViewer) msg.obj;
                        ImageViewBoundingBox imageViewBoundingBox = new ImageViewBoundingBox(ViewCameraPicturesActivity.this, iv);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        mRelativeLayout.addView(imageViewBoundingBox, params);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_camera_pictures, menu);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.view_camera_relative_layout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void postImage(ImageViewer iv) {
        Message aMessage = mHandler.obtainMessage(SHOW_IMAGE_WITH_BOX, iv);
        aMessage.sendToTarget();
    }

}
