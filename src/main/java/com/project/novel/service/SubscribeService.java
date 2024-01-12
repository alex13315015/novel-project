package com.project.novel.service;

import com.project.novel.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    @Transactional
    public void subscribe(Long customerDetailsId, Long book_id) {
        subscribeRepository.subscribe(customerDetailsId,book_id);
    }

    @Transactional
    public void unSubscribe(Long customerDetailsId, Long urlId) {
        subscribeRepository.unsubscribe(customerDetailsId,urlId);
    }
}
