package com.example.videotest;

import com.example.videotest.model.VideoQueue;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private VideoQueue videoQueue;

    private void initVideoQueue() {
        videoQueue = new VideoQueue(new ArrayList<>());
        videoQueue.addVideo("https://whatsaper.ru/wp-content/uploads/2019/05/whatsaper.ru-%D0%90%D0%BB%D1%8E%D0%BC%D0%B8%D0%BD%D0%B8%D0%B9-%D0%B2-%D0%BC%D1%83%D1%80%D0%B0%D0%B2%D0%B5%D0%B9%D0%BD%D0%B8%D0%BA..mp4?_=1");
        videoQueue.addVideo("https://whatsaper.ru/wp-content/uploads/2019/06/whatsaper.ru-%D0%9F%D0%A0%D0%9E%D0%91%D0%9B%D0%95%D0%9C%D0%90.-%D0%9C%D1%83%D0%BB%D1%8C%D1%82%D0%90%D0%BD%D0%B5%D0%BA%D0%B4%D0%BE%D1%82..mp4?_=1");
        videoQueue.addVideo("https://fiverr-res.cloudinary.com/video/upload/t_fiverr_hd/s5axe29abi4fcndxbdoh");
        videoQueue.addVideo("https://whatsaper.ru/wp-content/uploads/2020/05/whatsaper.ru-%D0%A7%D1%82%D0%BE-%D1%82%D0%B0%D0%BA%D0%BE%D0%B5-%D0%B1%D0%BE%D0%BB%D1%8C%D1%88%D0%B0%D1%8F-%D0%BF%D1%80%D0%BE%D0%B1%D0%BB%D0%B5%D0%BC%D0%B0.mp4?_=1");
    }

    @Test
    public void testNextVideo() {
        initVideoQueue();
        videoQueue.nextVideo();
        assertEquals(1, videoQueue.getCurrentVideoIndex());
        videoQueue.nextVideo();
        assertEquals(2, videoQueue.getCurrentVideoIndex());
        videoQueue.nextVideo();
        assertEquals(3, videoQueue.getCurrentVideoIndex());
        videoQueue.nextVideo();
        assertEquals(0, videoQueue.getCurrentVideoIndex());
    }

    @Test
    public void testRemovingVideo() {
        initVideoQueue();
        videoQueue.nextVideo();
        videoQueue.nextVideo();
        videoQueue.nextVideo();
        videoQueue.removeVideo(2);
        assertEquals(2, videoQueue.getCurrentVideoIndex());
        videoQueue.removeVideo(2);
        assertEquals(1, videoQueue.getCurrentVideoIndex());
    }
}