package com.henu.mq.service.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
public class RabbitMQConsumer {
    static ExecutorService service = new ThreadPoolExecutor(5,
            10,
            1L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * work使用线程池消费
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receivePool(String message) {
        service.execute(() -> {
            log.info("{}={} ", Thread.currentThread().getName(), message);
        });
    }

    /**
     * work工作模型
     *
     * @param message
     */
//    @RabbitListener(queuesToDeclare = @Queue("work"))
//    public void receive1(String message) {
//        log.info("message1 ={} ", message);
//    }
//
//    @RabbitListener(queuesToDeclare = @Queue("work"))
//    public void receive2(String message) {
//        log.info("message2 ={} ", message);
//    }

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
                    key = {"order.#", "product.#", "user.#"}
            )})
    public void receiveTopic2(String message) {
        log.info("message2 ={} ", message);
    }

    /**
     * 手动确认消息 ack
     *
     * @param meg
     * @param channel
     * @param message
     */
//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value = @Queue(value = "boot-queue"),
//                    exchange = @Exchange(value = "boot-topic-exchange", type = "topic"),
//                    key = {"*.red.*"}
//            )})
    public void getMsg(String meg, Channel channel, Message message) {
        log.info("boot-queue receive msg:{} ", message);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "boot-queue"),
                    exchange = @Exchange(value = "boot-topic-exchange", type = "topic"),
                    key = {"*.red.*"}
            )})
    public void getMsgRedis(String meg, Channel channel, Message message) {
        //0、获取MessageId
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String messageId = (String) headers.get("spring_returned_message_correlation");
        log.info(messageId);
        //1、设置key到redis
        if (redisTemplate.opsForValue().setIfAbsent(messageId, "0", 10L, TimeUnit.SECONDS)) {
            log.info(String.valueOf(redisTemplate.getExpire(messageId)));
            try {
                //2、消费消息
                log.info("boot-queue receive msg:{} ", message);
                //3、设置key的value为1
                //这里必须要设置上过期时间，因为不设置，这里相当于更新操作，会造成过期时间为-1，及其隐蔽的bug
                redisTemplate.opsForValue().set(messageId, "1", 10L, TimeUnit.SECONDS);
                //4、手动ack
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                log.info(String.valueOf(redisTemplate.getExpire(messageId)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //5、获取redis中的value即可，手动ack,1手动ack，0的话说明消费异常什么都不做
            if ("1".equalsIgnoreCase((String) redisTemplate.opsForValue().get(messageId))) {
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
