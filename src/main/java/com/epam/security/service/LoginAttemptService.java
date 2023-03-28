package com.epam.security.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    public static final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;


    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(50, TimeUnit.SECONDS)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(final String key) {
                        return 0;
                    }
                });
    }

    public void loginFailed(final String key) {
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String username) {
        try {
            return attemptsCache.get(username) >= MAX_ATTEMPT;
        } catch (final ExecutionException e) {
            return false;
        }
    }

    public Set<String> getBlockedUsers() {
        return attemptsCache.asMap().keySet();
    }
}
