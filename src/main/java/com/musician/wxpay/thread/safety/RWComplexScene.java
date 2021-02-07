package com.musician.wxpay.thread.safety;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: LXR
 * @since: 2021/2/7 10:43
 */
public class RWComplexScene {

    private final Map<Object, Object> map = new HashMap<>();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public Object readWrite(String id) {
        Object value = null;
        reentrantReadWriteLock.readLock().lock();
        try{
            value = map.get(id);
            if (value == null) {
                reentrantReadWriteLock.readLock().unlock();

                reentrantReadWriteLock.writeLock().lock();
                try{
                    if (value == null) {
                        value = "aaa";
                    }
                }finally {
                    reentrantReadWriteLock.writeLock().unlock();
                }
                reentrantReadWriteLock.readLock().lock();
            }
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
        return value;
    }

}
