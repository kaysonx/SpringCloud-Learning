package me.qspeng.ribbonconsumer.service;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import me.qspeng.ribbonconsumer.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Long> {

    private final BatchedUserService userService;
    private final Long uId;

    public UserCollapseCommand(BatchedUserService userService, Long uId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("CollapseKey"))
                .andCollapserPropertiesDefaults(
                        HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)
                ));
        this.userService = userService;
        this.uId = uId;
    }


    @Override
    public Long getRequestArgument() {
        return uId;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        List<Long> uIds = new ArrayList<>(collapsedRequests.size());
        uIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserBatchCommand(userService, uIds);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<User, Long> collapsedRequest : collapsedRequests) {
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }
}
