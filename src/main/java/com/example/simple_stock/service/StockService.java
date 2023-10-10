package com.example.simple_stock.service;

import com.example.simple_stock.domain.Stock;
import com.example.simple_stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    //@Transactional이 경우 decrease가 wrapping되기 때문에, 트랜잭션이 커밋되기 전에 decrease가 호출될 수 있다.
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
