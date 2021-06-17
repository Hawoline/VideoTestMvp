package com.example.videotest.presenter;

import android.media.MediaPlayer;
import android.os.Bundle;
import com.example.videotest.model.VideoQueue;
import com.example.videotest.view.MainView;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private VideoQueue mVideoQueue;

    private static final String CURRENT_VIDEO_POSITION_KEY = "CURRENT_VIDEO_POSITION_KEY";
    private static final String CURRENT_VIDEO_INDEX_KEY = "CURRENT_VIDEO_INDEX_KEY";

    private MediaPlayer.OnCompletionListener mOnCompletionListener;

    public MainPresenterImpl() {
        mVideoQueue = new VideoQueue(new ArrayList<>());
        mVideoQueue.addVideo("https://whatsaper.ru/wp-content/uploads/2019/05/whatsaper.ru-%D0%90%D0%BB%D1%8E%D0%BC%D0%B8%D0%BD%D0%B8%D0%B9-%D0%B2-%D0%BC%D1%83%D1%80%D0%B0%D0%B2%D0%B5%D0%B9%D0%BD%D0%B8%D0%BA..mp4?_=1");
        mVideoQueue.addVideo("https://whatsaper.ru/wp-content/uploads/2019/06/whatsaper.ru-%D0%9F%D0%A0%D0%9E%D0%91%D0%9B%D0%95%D0%9C%D0%90.-%D0%9C%D1%83%D0%BB%D1%8C%D1%82%D0%90%D0%BD%D0%B5%D0%BA%D0%B4%D0%BE%D1%82..mp4?_=1");
        mVideoQueue.addVideo("https://fiverr-res.cloudinary.com/video/upload/t_fiverr_hd/s5axe29abi4fcndxbdoh");
        mVideoQueue.addVideo("https://whatsaper.ru/wp-content/uploads/2020/05/whatsaper.ru-%D0%A7%D1%82%D0%BE-%D1%82%D0%B0%D0%BA%D0%BE%D0%B5-%D0%B1%D0%BE%D0%BB%D1%8C%D1%88%D0%B0%D1%8F-%D0%BF%D1%80%D0%BE%D0%B1%D0%BB%D0%B5%D0%BC%D0%B0.mp4?_=1");
        initVideo();
    }

    @Override
    public void onPause() {
        mVideoQueue.setCurrentVideoPosition(mMainView.getCurrentVideoPosition());
    }

    @Override
    public void onResume() {
        mMainView.showVideo(mVideoQueue.getCurrentVideo(), mVideoQueue.getCurrentVideoPosition());
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
                mMainView.showVideo(mVideoQueue.nextVideo(), mVideoQueue.getCurrentVideoPosition());
            }
        };
    }

    @Override
    public void startVideo() {
        mMainView.showVideo(mVideoQueue.getCurrentVideo(), 0);
    }

    @Override
    public MediaPlayer.OnCompletionListener getOnCompletionListener() {
        return mOnCompletionListener;
    }
}
