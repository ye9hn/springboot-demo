package com.henu.mq.service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {
    /**
     * work工作模型
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receive1(String message) {
        log.info("message1 ={} ", message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receive2(String message) {
        log.info("message2 ={} ", message);
    }

    /**
     * fanout工作模型
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,//创建临时队列，这种模式的队列式临时队列
                    exchange = @Exchange(value = "logs", type = "fanout")//创建logs交换机，交换机类型为fanout
            )})
    public void receive3(String message) {
        log.info("message1 ={} ", message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,//创建临时队列，这种模式的队列式临时队列
                    exchange = @Exchange(value = "logs", type = "fanout")//创建logs交换机，交换机类型为fanout
            )})
    public void receive4(String message) {
        log.info("message2 ={} ", message);
    }

    /**
     * 基于route路由模型
     *
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "directs", type = "direct"),
                    key = {"info"}//路由key
            )})
    public void receiveInfo(String message) {
        log.info("message info={} ", message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "directs", type = "direct"),
                    key = {"error"}//路由key
            )})
    public void receiveError(String message) {
        log.info("message error={} ", message);
    }
    /**
     * 基于topic动态路由
     *
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "topics", type = "topic"),
                    key = {"user.save", "user.*"}
            )})
    public void receiveTopic1(String message) {
        log.info("message1 ={} ", message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "topics", type = "topic"),
                    key = {"order.#", "product.#","user.#"}
            )})
    public void receiveTopic2(String message) {
        log.info("message2 ={} ", message);
    }
}
