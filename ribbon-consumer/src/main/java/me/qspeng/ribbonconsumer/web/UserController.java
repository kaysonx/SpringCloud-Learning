package me.qspeng.ribbonconsumer.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import me.qspeng.ribbonconsumer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getUserFallback")
    @CacheResult(cacheKeyMethod = "getUserByIdCacheKey")
    public User getUserById(@CacheKey("id") Long id) {
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
    }

    private User getUserFallback(String id, Throwable e) {
        return new User();
    }

    private Long getUserByIdCacheKey(Long id) {
        return id;
    }

    @HystrixCommand
    @CacheRemove(commandKey = "getUserById")
    public User update(@CacheKey("id") User user) {
        return restTemplate.postForObject("http://USER-SERVICE/users", user, User.class);
    }
}
