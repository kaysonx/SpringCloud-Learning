package me.qspeng.feignConsumer.web;

import me.qspeng.feignConsumer.model.User;
import me.qspeng.feignConsumer.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/feign-consumer", method = RequestMethod.GET)
    public String helloConsumer() {
        logger.info("=========call feign: consumer===========");
        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2", method = RequestMethod.GET)
    public String helloConsumer2() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(helloService.hello()).append("\n");
        stringBuilder.append(helloService.hello("haha")).append("\n");
        stringBuilder.append(helloService.hello("haha", 22)).append("\n");
        stringBuilder.append(helloService.hello(new User("h1juice", 23))).append("\n");
        logger.info("=========call feign: consumer2===========");
        return stringBuilder.toString();
    }
}
