package cn.henuer.sms.controller;

import cn.henuer.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
public class SmsController {
    @Autowired
    private SmsService smsService;
    @GetMapping("/sendsms")
    public String sendSms(@PathVariable("phonenum") String phoneNum){
        String templateParam = UUID.randomUUID().toString().substring(0,3);
        smsService.sendSms(phoneNum,templateParam);
        return templateParam;
    }
}
