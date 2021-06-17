package com.example.videotest.model;

import java.util.ArrayList;
import java.util.Queue;

public class VideoQueue {
    private ArrayList<String> mQueue;
    private int mCurrentVideoIndex;
    private int mCurrentVideoPosition;

    public VideoQueue(ArrayList<String> queue) {
        mQueue = queue;
        mCurrentVideoIndex = 0;
        mCurrentVideoPosition = 0;
    }

    public String getCurrentVideo() {
        return mQueue.get(mCurrentVideoIndex);
    }

    public String nextVideo() {
        mCurrentVideoIndex = ++mCurrentVideoIndex % mQueue.size();
        mCurrentVideoPosition = 0;
        return mQueue.get(mCurrentVideoIndex);
    }

    public void addVideo(String video) {
        mQueue.add(video);
    }

    public void removeVideo(int index) {
        mQueue.remove(index);
        if (mCurrentVideoIndex == mQueue.size() && mCurrentVideoIndex == index || mCurrentVideoIndex > index) {
            mCurrentVideoIndex--;
        }
    }

    public void setQueue(ArrayList<String> queue) {
        mQueue = queue;
    }

    public int getCurrentVideoIndex() {
        return mCurrentVideoIndex;
    }

    public void setCurrentVideoIndex(int currentVideoIndex) {
        mCurrentVideoIndex = currentVideoIndex % mQueue.size();
    }

    public int getCurrentVideoPosition() {
        return mCurrentVideoPosition;
    }

    public void setCurrentVideoPosition(int currentVideoPosition) {
        mCurrentVideoPosition = currentVideoPosition;
    }
}
