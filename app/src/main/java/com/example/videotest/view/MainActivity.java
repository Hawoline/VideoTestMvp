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
    private YouTubePlayer mYouTubePlayer;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;

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
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {
                        if (mYouTubePlayerView.getVisibility() == View.VISIBLE) {
                            mMainPresenter.playNextVideo();
                        }
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });

                youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                    @Override
                    public void onPlaying() {

                    }

                    @Override
                    public void onPaused() {
                        if (mYouTubePlayerView.getVisibility() == View.VISIBLE) {
                            mMainPresenter.onPause();
                            mMainPresenter.playCurrentVideo();
                            youTubePlayer.play();
                        }
                    }

                    @Override
                    public void onStopped() {
                        int s = 0;
                    }

                    @Override
                    public void onBuffering(boolean b) {

                    }

                    @Override
                    public void onSeekTo(int i) {

                    }
                });
                youTubePlayer.setPlayerStyle(PlayerStyle.CHROMELESS);
                mYouTubePlayer = youTubePlayer;
                mYouTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getApplicationContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
            }
        };

        mYouTubePlayerView.initialize(mMainPresenter.getApiKey(), mOnInitializedListener);
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
            mYouTubePlayer.pause();
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
    public void showYoutubeVideo(String video, int videoPosition) {
        mVideoView.setVisibility(View.GONE);
        mYouTubePlayerView.setVisibility(View.VISIBLE);
        if (mYouTubePlayer != null) {
            mYouTubePlayer.loadVideo(video);

            mYouTubePlayer.seekRelativeMillis(videoPosition);
            mYouTubePlayer.play();
        }
    }

    @Override
    public int getCurrentVideoPosition() {
        if (mVideoView.getVisibility() == View.VISIBLE) {
            int currentTimeMillis = mVideoView.getCurrentPosition();
            return mVideoView.getCurrentPosition();
        } else {
            int currentTimeMillis = mYouTubePlayer.getCurrentTimeMillis();
            return mYouTubePlayer.getCurrentTimeMillis();
        }
    }
}