package com.example.videotest.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import com.example.videotest.R;
import com.example.videotest.presenter.MainPresenter;
import com.example.videotest.presenter.MainPresenterImpl;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity implements MainView {
    private VideoView mVideoView;
    private MainPresenter mMainPresenter;
    private YouTubePlayerView mYouTubePlayerView;

    // client id 121743756885-fa9srgdipmo9dl9vjd72pfvrv0hjbfoe.apps.googleusercontent.com
    // api key AIzaSyD33WmIaJ8aIyrqcdLeRJeHMcyNCqBXhKM
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showViews();
        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attachView(this);
        mVideoView.setOnCompletionListener(mMainPresenter.getOnCompletionListener());
        mVideoView.requestFocus(0);


        mYouTubePlayerView.initialize(mMainPresenter.getApiKey(), mMainPresenter.getOnInitializedListener());
        mMainPresenter.playCurrentVideo();
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
            mMainPresenter.playCurrentVideo();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mMainPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.clearData();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showViews() {
        mVideoView = findViewById(R.id.videoview);
        mYouTubePlayerView = findViewById(R.id.youtube_videoview);
    }

    @Override
    public void hideViews() {
        mVideoView.setVisibility(View.GONE);
    }

    @Override
    public void showVideo(String video, int videoPosition) {
        mYouTubePlayerView.setVisibility(View.GONE);
        mVideoView.setVisibility(View.VISIBLE);
        mVideoView.setVideoURI(Uri.parse(video));
        mVideoView.seekTo(videoPosition);
        mVideoView.start();
    }

    @Override
    public void showYoutubeVideo() {
        mVideoView.setVisibility(View.GONE);
        mYouTubePlayerView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getCurrentVideoPosition() {
        return mVideoView.getCurrentPosition();
    }

    @Override
    public boolean isYoutubePlayerViewVisible() {
        return mYouTubePlayerView.getVisibility() == View.VISIBLE;
    }
}