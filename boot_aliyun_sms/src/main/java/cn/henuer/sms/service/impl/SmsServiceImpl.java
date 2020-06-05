package cn.henuer.sms.service.impl;

import cn.henuer.sms.service.SmsService;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
//在线调试api接口https://api.aliyun.com/?spm=a2c4g.11186623.2.15.474860e2cTeRrj#/?product=Dysmsapi&version=2017-05-25&api=SendSms&params={%22RegionId%22:%22cn-hangzhou%22,%22PhoneNumbers%22:%2217839227703%22,%22SignName%22:%22123456789%22,%22TemplateCode%22:%22456789%22,%22TemplateParam%22:%22123456%22}&tab=DEMO&lang=JAVA
public class SmsServiceImpl implements SmsService {
    @Value("${aliyun.sms.regionid}")
    private String regionId;
    @Value("${aliyun.sms.accessKeyid}")
    private String accessKeyId;
    @Value("${aliyun.sms.secret}")
    private String secret;
    @Value("${aliyun.sms.sysDomain}")
    private String sysDomain;
    @Value("${aliyun.sms.templateCode}")
    private  String templateCode;


    @Override
    public boolean sendSms(String phoneNum,String templateParam) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(sysDomain);
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //签名
        request.putQueryParameter("SignName", "123456789");
        //模板
        request.putQueryParameter("TemplateCode", templateCode);
        //待发送的手机号
        request.putQueryParameter("PhoneNumbers", phoneNum);
        //待发送的手机验证码
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
