package cn.henuer.sms.service;

public interface SmsService {
    public boolean sendSms(String phoneNum,String templateParam);
}
