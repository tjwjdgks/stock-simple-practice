package com.example.simple_stock.facade;

import com.example.simple_stock.repository.RedisLockRepository;
import com.example.simple_stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while(!redisLockRepository.lock(id)){
            Thread.sleep(100);
        }

        try{
            stockService.decrease(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
