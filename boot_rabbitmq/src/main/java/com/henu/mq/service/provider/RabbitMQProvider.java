package com.henu.mq.service.provider;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class RabbitMQProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * work工作模型 Provider
     */
    public void work(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work模型"+i);
        }
    }

    /**
     * fanout工作模型  广播模式
     *
     * 真正的交换机的创建取决消费者，不取决与生产者
     */
    public void fanout(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("logs","", "fanout模型");
        }
    }

    /**
     * 路由模式将消息指定路由发送到消息队列供消费者消费
     * 这种模式比fanout更有选择性
     */
    public void route() {
        rabbitTemplate.convertAndSend("directs","info","发送info路由消息");
        rabbitTemplate.convertAndSend("directs","error","发送error路由消息");
    }

    /**
     * topic工作模式，也叫做动态路由模式，发布订阅模式
     */
    public void topic() {
        rabbitTemplate.convertAndSend("topics","user.save","user.save消息");
        rabbitTemplate.convertAndSend("topics","order.save","order.save消息");
    }

    /**
     * 消费者手动确认ack机制
     * @return
     */
    public void manualAck() {
        CorrelationData messageId=new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("boot-topic-exchange","slow.red.ne","这是一个消息",messageId);
    }

    /**
     * 死信队列 fanout类型
     */
    public void deadMsg(String msg){
        rabbitTemplate.convertSendAndReceive("dead.letter.demo.simple.business.exchange", "", msg);
    }
}
