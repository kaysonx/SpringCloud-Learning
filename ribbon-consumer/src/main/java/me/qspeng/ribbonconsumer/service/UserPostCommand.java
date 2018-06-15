package me.qspeng.ribbonconsumer.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import me.qspeng.ribbonconsumer.model.User;
import org.springframework.web.client.RestTemplate;

public class UserPostCommand extends HystrixCommand<User> {

    private final RestTemplate restTemplate;
    private final User user;

    protected UserPostCommand(RestTemplate restTemplate, User user) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GKey")));
        this.restTemplate = restTemplate;
        this.user = user;
    }

    @Override
    protected User run() throws Exception {
        User u = restTemplate.postForObject("http://USER-SERVICE/{1}", user, User.class);
        UserGetCommand.flushCache(u.getId());
        return u;
    }
}
