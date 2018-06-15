package me.qspeng.streamService;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface SinkSender {
    String INPUT = "myInput";

    @Output(SinkSender.INPUT)
    SubscribableChannel input();
}
