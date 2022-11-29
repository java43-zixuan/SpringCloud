package com.example.userservice2.test13;

import cn.hutool.extra.mail.MailUtil;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Mail {


    public static void main(String[] args)  {
        hutoolSend();
    }
    public static void hutoolSend(){
        //最简单的邮件发送
        MailUtil.send("562818031@qq.com", "测试", "邮件来自Hutool测试", false);
    }

    public static void aTuo()throws MessagingException{
        //mail.jar
        //  跟真正的邮箱建立链接      提交很多信息map
        Properties props = new Properties();
        props.put("mail.transport.protocol","smtp");//协议   SMTP
        props.put("mail.smtp.host","smtp.qq.com");
        Session session = Session.getInstance(props);
        //  创建一个虚拟的邮件对象
        MimeMessage message = new MimeMessage(session);
        //设置    发件人，收件人，主题，时间，正文
        message.setFrom(new InternetAddress(""));
        message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(""));
        message.setSubject("XX邮件");
        message.setSentDate(new Date());
        message.setText("aaaaa");
        message.saveChanges();
        //  创建一个发送者
        Transport transport = session.getTransport();
        //得到一个邮箱的认证
        transport.connect("","");
        //发送啦
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

}
