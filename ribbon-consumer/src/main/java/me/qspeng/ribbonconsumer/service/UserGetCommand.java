package me.qspeng.ribbonconsumer.service;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import me.qspeng.ribbonconsumer.model.User;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

public class UserGetCommand extends HystrixObservableCommand<User> {
    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");
    private final RestTemplate restTemplate;
    private final Long id;

    //同一command key会放到同一缓存对象中(默认)， 同一group key会放在同一线程池中(默认), 也可设置thread key.
    protected UserGetCommand(RestTemplate restTemplate, Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GName"))
                .andCommandKey(GETTER_KEY)
        );
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected Observable<User> resumeWithFallback() {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                User user = new User();
                subscriber.onNext(user);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    protected Observable<User> construct() {
        return Observable.create(subscriber -> {
            try {
                if (!subscriber.isUnsubscribed()) {
                    User user = restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
                    subscriber.onNext(user);
                    subscriber.onCompleted();
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    @Override
    protected String getCacheKey() {
        // 根据id 置入缓存
        return String.valueOf(id);
    }

    public static void flushCache(Long id) {
        //刷新缓存 根据id清理
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance())
                .clear(String.valueOf(id));
    }
}
