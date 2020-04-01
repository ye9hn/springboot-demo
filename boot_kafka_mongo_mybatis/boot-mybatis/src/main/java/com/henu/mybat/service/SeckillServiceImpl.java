package com.henu.mybat.service;

import com.henu.mybat.current.MyLock;
import com.henu.mybat.dao.PhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class SeckillServiceImpl implements SeckillService{
    @Autowired
    PhoneMapper phoneMapper;

    MyLock lock=new MyLock();
    private volatile Integer  stock=0;
    @Transactional
    @Override
    public void phoneSeckill(String pId) {
        stock=phoneMapper.selectPhoneStock(pId);
        if(stock<=0){
            System.out.println("商品已售完。。。。"+Thread.currentThread().getName());
            return ;
        }
        lock.lock();
        if(phoneMapper.selectPhoneStock(pId)>0){
            int row = phoneMapper.seckillPhone(pId);
            if(row==1){
                stock=phoneMapper.selectPhoneStock(pId);
                System.out.println("当前商品库存为："+Thread.currentThread().getName()+"     "+stock);
            }
        }
        lock.unlock();
    }

}
