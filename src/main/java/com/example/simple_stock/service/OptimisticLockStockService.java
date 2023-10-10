package com.example.simple_stock.service;

import com.example.simple_stock.domain.Stock;
import com.example.simple_stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long id, Long quantity) {
        stockRepository.findByIdWithOptimisticLock(id).decrease(quantity);
    }
}
