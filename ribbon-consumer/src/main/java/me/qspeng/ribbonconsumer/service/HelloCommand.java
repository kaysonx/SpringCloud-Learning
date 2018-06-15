package me.qspeng.ribbonconsumer.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.springframework.web.client.RestTemplate;

public class HelloCommand extends HystrixCommand<String> {
    private RestTemplate restTemplate;

    protected HelloCommand(RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("Hello"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("T1"))
        );
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() {
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody();
    }

    @Override
    protected String getFallback() {
        return "Error invoke: " + getExecutionException().getMessage();
    }
}
