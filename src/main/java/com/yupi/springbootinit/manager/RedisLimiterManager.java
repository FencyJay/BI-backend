package com.yupi.springbootinit.manager;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.config.RedissonConfig;
import com.yupi.springbootinit.exception.BusinessException;
import kotlin.Unit;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流操作
     * @param key 区分不同的限流器，比如不同的用户 id 应该分比统计
     */
    public void doRateLimit(String key){
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, 5, 1, RateIntervalUnit.SECONDS);
        //每当来了一个操作来了后，请求一个令牌
        boolean canOp = rateLimiter.tryAcquire(2);
        rateLimiter.acquire(2);
        System.out.println("Can operate: " + canOp);
        if (!canOp){
            throw new BusinessException(ErrorCode.TOO_MANY_QUEST);
        }
    }

}
