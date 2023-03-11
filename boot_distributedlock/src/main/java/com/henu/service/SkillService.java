package com.henu.service;

import com.henu.dao.ProductDao;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SkillService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ProductDao productDao;
    private String PRODUCT_LOCK = "PRO_L";

    public String skillProduct(String productId) {
        RLock lock = redissonClient.getLock(PRODUCT_LOCK);
        lock.lock();
        try {
           // lock.tryLock();
            int stock = productDao.selectPhoneStock(1);
            if (stock > 0) {
                System.out.println("当前库存" + (stock - productDao.seckillPhone(1)));
            }
            boolean locked=lock.tryLock();
            if (locked){
                lock.unlock();
            }

            return "ok";
        } finally {
            lock.unlock();
        }
    }
}
