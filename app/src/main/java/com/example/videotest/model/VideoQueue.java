package com.example.videotest.model;

import java.util.ArrayList;
import java.util.Queue;

public class VideoQueue {
    private ArrayList<Video> mQueue;
    private int mCurrentVideoIndex;
    private int mCurrentVideoPosition;

    public VideoQueue(ArrayList<Video> queue) {
        mQueue = queue;
        mCurrentVideoIndex = 0;
        mCurrentVideoPosition = 0;
    }

    public Video getCurrentVideo() {
        return mQueue.get(mCurrentVideoIndex);
    }

    public Video nextVideo() {
        mCurrentVideoIndex = ++mCurrentVideoIndex % mQueue.size();
        mCurrentVideoPosition = 0;
        return mQueue.get(mCurrentVideoIndex);
    }

    public void addVideo(Video video) {
        mQueue.add(video);
    }

    public void removeVideo(int index) {
        mQueue.remove(index);
        if (mCurrentVideoIndex == mQueue.size() && mCurrentVideoIndex == index || mCurrentVideoIndex > index) {
            mCurrentVideoIndex--;
        }
    }

    public void clearQueue() {
        mQueue.clear();
    }

    public void setQueue(ArrayList<Video> queue) {
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
