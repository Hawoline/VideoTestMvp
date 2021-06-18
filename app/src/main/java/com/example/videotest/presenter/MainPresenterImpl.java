package com.example.videotest.presenter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.videotest.model.Video;
import com.example.videotest.model.VideoQueue;
import com.example.videotest.model.YoutubeVideo;
import com.example.videotest.view.MainView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private VideoQueue mVideoQueue;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    private YouTubePlayer mYouTubePlayer;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;

    private static final String CURRENT_VIDEO_POSITION_KEY = "CURRENT_VIDEO_POSITION_KEY";
    private static final String CURRENT_VIDEO_INDEX_KEY = "CURRENT_VIDEO_INDEX_KEY";

    private static final String YOUTUBE_API_KEY = "AIzaSyD33WmIaJ8aIyrqcdLeRJeHMcyNCqBXhKM";


    public MainPresenterImpl() {
        mVideoQueue = new VideoQueue(new ArrayList<>());
        mVideoQueue.addVideo(new Video("https://fiverr-res.cloudinary.com/video/upload/t_fiverr_hd/jomgicuwxoe8hqqedzmj"));
        mVideoQueue.addVideo(new YoutubeVideo("4VR9Iemy8SM"));
        mVideoQueue.addVideo(new Video("https://www.adidas-group.com/media/filer_public/3f/b6/3fb67c7f-b58d-4787-be9a-17f675e14047/adidas_innovation_master_fullhd.mp4"));
        mVideoQueue.addVideo(new Video("https://fiverr-res.cloudinary.com/video/upload/t_fiverr_hd/s5axe29abi4fcndxbdoh"));
        mVideoQueue.addVideo(new YoutubeVideo("a3SXHrMOFzA"));
        mVideoQueue.addVideo(new Video("https://fiverr-res.cloudinary.com/video/upload/t_fiverr_hd/eon5hzpvzhmamjdord7c"));
        mVideoQueue.addVideo(new Video("QBwLH-p4rZU"));
        initVideo();
    }

    @Override
    public void onPause() {
        if (mMainView.isYoutubePlayerViewVisible()) {
            mYouTubePlayer.pause();
            mVideoQueue.setCurrentVideoPosition(mYouTubePlayer.getCurrentTimeMillis());
        } else {
            mVideoQueue.setCurrentVideoPosition(mMainView.getCurrentVideoPosition());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(CURRENT_VIDEO_INDEX_KEY, mVideoQueue.getCurrentVideoIndex());
        savedInstanceState.putInt(CURRENT_VIDEO_POSITION_KEY, mVideoQueue.getCurrentVideoPosition());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mVideoQueue.setCurrentVideoIndex(savedInstanceState.getInt(CURRENT_VIDEO_INDEX_KEY));
        mVideoQueue.setCurrentVideoPosition(savedInstanceState.getInt(CURRENT_VIDEO_POSITION_KEY));
    }

    @Override
    public void attachView(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void detachView() {
        mMainView = null;
    }

    private void initVideo() {
        mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextVideo();
            }
        };
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mYouTubePlayer = youTubePlayer;
                mYouTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
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
                        if (mMainView.isYoutubePlayerViewVisible()) {
                            playNextVideo();
                        }
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });

                mYouTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                    @Override
                    public void onPlaying() {

                    }

                    @Override
                    public void onPaused() {
                        if (mMainView.isYoutubePlayerViewVisible()) {
                            onPause();
                            playCurrentVideo();
                            mYouTubePlayer.play();
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
                mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                mYouTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(mMainView.getContext(), "Video player Failed", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void playCurrentVideo() {
        playVideo(mVideoQueue.getCurrentVideo(), mVideoQueue.getCurrentVideoPosition());
    }

    @Override
    public void playNextVideo() {
        playVideo(mVideoQueue.nextVideo(), mVideoQueue.getCurrentVideoPosition());
    }

    private void playVideo(Video video, int videoPosition) {
        if (video instanceof YoutubeVideo) {
            mMainView.showYoutubeVideo();
            if (mYouTubePlayer != null) {
                mYouTubePlayer.loadVideo(video.getName());
                mYouTubePlayer.seekRelativeMillis(videoPosition);
                mYouTubePlayer.play();
            }
        } else {
            mMainView.showVideo(video.getName(), videoPosition);
        }
    }

    @Override
    public MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return mOnCompletionListener;
    }

    @Override
    public YouTubePlayer.OnInitializedListener getOnInitializedListener() {
        return mOnInitializedListener;
    }

    @Override
    public String getApiKey() {
        return YOUTUBE_API_KEY;
    }

    @Override
    public void clearData() {
        mVideoQueue.clearQueue();
    }
}
