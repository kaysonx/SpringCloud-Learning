package me.qspeng.ribbonconsumer.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import me.qspeng.ribbonconsumer.model.User;

import java.util.List;

public class UserBatchCommand extends HystrixCommand<List<User>> {


    private final BatchedUserService userService;
    private final List<Long> uIds;

    UserBatchCommand(BatchedUserService userService, List<Long> uIds) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GKey")));
        this.userService = userService;
        this.uIds = uIds;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.findAll(uIds);
    }
}
