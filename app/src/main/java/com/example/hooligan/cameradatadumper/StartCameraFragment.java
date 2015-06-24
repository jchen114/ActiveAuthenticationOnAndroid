package com.example.hooligan.cameradatadumper;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hooligan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartCameraFragment extends Fragment implements CameraFragmentInterface {

    private boolean isDumping = false;
    private Button mCamButton;
    private static final String KEY_IS_DUMPING = "KEY_IS_DUMPING";
    private static final String mLogTag = "StartCameraFragment";
    Intent mIntent;

    public StartCameraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isDumping = savedInstanceState.getBoolean(KEY_IS_DUMPING);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_camera, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mCamButton = (Button) getView().findViewById(R.id.cam_button);
        setButtonText();
    }

    @Override
    public void didPressCameraButton(View v) {
        Intent mIntent = new Intent(getActivity().getApplicationContext(), FrontBackCameraService.class);
        if (isDumping) {
            Log.i(mLogTag, "Stop Camera Service");
            getActivity().stopService(mIntent);
        } else {
            Log.i(mLogTag, "Start Camera Service");
            getActivity().startService(mIntent);
        }
        isDumping = !isDumping;
        setButtonText();
    }

    private void setButtonText() {
        if (isDumping) {
            mCamButton.setText("Stop Dumping Camera");
        } else {
            mCamButton.setText("Start Dumping Camera");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_DUMPING, isDumping);
    }

    @Override
    public void turnOnService() {
        if (!isDumping) {
            mIntent = new Intent(getActivity().getApplicationContext(), FrontBackCameraService.class);
            getActivity().startService(mIntent);
            isDumping = true;
            setButtonText();
        }
    }

    @Override
    public void turnOffService() {
        if (isDumping) {
            mIntent = new Intent(getActivity().getApplicationContext(), FrontBackCameraService.class);
            getActivity().stopService(mIntent);
            isDumping = false;
            setButtonText();
        }
    }
}
