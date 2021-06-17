package com.example.videotest.presenter;

import android.media.MediaPlayer;
import android.os.Bundle;
import com.example.videotest.model.Video;
import com.example.videotest.model.VideoQueue;
import com.example.videotest.model.YoutubeVideo;
import com.example.videotest.view.MainView;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private VideoQueue mVideoQueue;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;

    private static final String CURRENT_VIDEO_POSITION_KEY = "CURRENT_VIDEO_POSITION_KEY";
    private static final String CURRENT_VIDEO_INDEX_KEY = "CURRENT_VIDEO_INDEX_KEY";

    private static final String YOUTUBE_API_KEY = "AIzaSyD33WmIaJ8aIyrqcdLeRJeHMcyNCqBXhKM";


    public MainPresenterImpl() {
        mVideoQueue = new VideoQueue(new ArrayList<>());
        mVideoQueue.addVideo(new Video("https://whatsaper.ru/wp-content/uploads/2019/05/whatsaper.ru-%D0%90%D0%BB%D1%8E%D0%BC%D0%B8%D0%BD%D0%B8%D0%B9-%D0%B2-%D0%BC%D1%83%D1%80%D0%B0%D0%B2%D0%B5%D0%B9%D0%BD%D0%B8%D0%BA..mp4?_=1"));
        mVideoQueue.addVideo(new YoutubeVideo("4VR9Iemy8SM"));
        mVideoQueue.addVideo(new Video("https://www.adidas-group.com/media/filer_public/3f/b6/3fb67c7f-b58d-4787-be9a-17f675e14047/adidas_innovation_master_fullhd.mp4"));
        mVideoQueue.addVideo(new Video("https://fiverr-res.cloudinary.com/video/upload/t_fiverr_hd/s5axe29abi4fcndxbdoh"));
        mVideoQueue.addVideo(new YoutubeVideo("a3SXHrMOFzA"));
        mVideoQueue.addVideo(new Video("https://whatsaper.ru/wp-content/uploads/2020/05/whatsaper.ru-%D0%A7%D1%82%D0%BE-%D1%82%D0%B0%D0%BA%D0%BE%D0%B5-%D0%B1%D0%BE%D0%BB%D1%8C%D1%88%D0%B0%D1%8F-%D0%BF%D1%80%D0%BE%D0%B1%D0%BB%D0%B5%D0%BC%D0%B0.mp4?_=1"));
        initVideo();
    }

    @Override
    public void onPause() {
        mVideoQueue.setCurrentVideoPosition(mMainView.getCurrentVideoPosition());
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
    }

    @Override
    public void playCurrentVideo() {
        playVideo(mVideoQueue.getCurrentVideo(), mVideoQueue.getCurrentVideoPosition());
    }

    @Override
    public void playNextVideo() {
        playVideo(mVideoQueue.nextVideo(), mVideoQueue.getCurrentVideoPosition());
    }

    private void playVideo(Video video, int currentVideoPosition) {
        if (video instanceof YoutubeVideo) {
            mMainView.showYoutubeVideo(video.getName(), currentVideoPosition);
        } else {
            mMainView.showVideo(video.getName(), currentVideoPosition);
        }
    }

    @Override
    public MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return mOnCompletionListener;
    }

    @Override
    public String getApiKey() {
        return YOUTUBE_API_KEY;
    }
}
