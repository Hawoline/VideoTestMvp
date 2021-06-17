package com.example.videotest.view;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import com.example.videotest.R;
import com.example.videotest.presenter.MainPresenter;
import com.example.videotest.presenter.MainPresenterImpl;

public class MainActivity extends Activity implements MainView {
    private VideoView mVideoView;
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showViews();
        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attachView(this);
        mVideoView.setOnCompletionListener(mMainPresenter.getOnCompletionListener());
        mVideoView.requestFocus(0);
        mMainPresenter.startVideo();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMainPresenter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        if (mVideoView != null) {
            mMainPresenter.onPause();
            mVideoView.pause();
            super.onPause();
        }
    }

    @Override
    protected void onResume() {
        if (mVideoView != null) {
            super.onResume();
            mMainPresenter.onResume();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mMainPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showViews() {
        mVideoView = findViewById(R.id.videoview);
    }

    @Override
    public void hideViews() {
        mVideoView.setVisibility(View.GONE);
    }

    @Override
    public void showVideo(String video, int videoPosition) {
        mVideoView.setVideoURI(Uri.parse(video));
        mVideoView.seekTo(videoPosition);
        mVideoView.start();
    }

    @Override
    public int getCurrentVideoPosition() {
        return mVideoView.getCurrentPosition();
    }
}