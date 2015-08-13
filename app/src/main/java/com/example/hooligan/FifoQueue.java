package com.example.hooligan;

import java.util.LinkedList;

/**
 * Created by Hooligan on 8/12/2015.
 */
public class FifoQueue {

    private int mQueueSize;
    private LinkedList <Integer> mLinkedList;

    public FifoQueue(int size) {
        this.mQueueSize = size;
        mLinkedList = new LinkedList<Integer>();
    }

    public void put(int score) {
        mLinkedList.addFirst(score);
        if (mLinkedList.size() > mQueueSize) {
            mLinkedList.removeLast();
        }
    }

    public Integer[] getScores() {
        return (Integer[]) mLinkedList.toArray();
    }

    public Integer peekScore() {
        if (mLinkedList.peekFirst() != null) {
            return mLinkedList.peekFirst();
        } else {
            return -1;
        }
    }

}
