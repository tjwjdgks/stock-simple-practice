package com.example.simple_stock.facade;

import com.example.simple_stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final StockService stockService;

    public void decrease(Long id, Long quantity){
        RLock lock = redissonClient.getLock(id.toString());
        try{
            boolean available = lock.tryLock(15, 1, TimeUnit.SECONDS);
            if(!available){
                System.out.println("Lock is not available");
                return;
            }
            stockService.decrease(id, quantity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
