package edu.poly.assjava5banhang.service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.poly.assjava5banhang.service.MailService;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void send(Mail mail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

          //  Ghi thông tin người gửi
            helper.setFrom(mail.getFrom());
            helper.setReplyTo(mail.getFrom());
            // Ghi thông tin người nhận
            helper.setTo(mail.getTo());

            if (!this.isNullOrEmpty(mail.getCc())) {
                helper.setCc(mail.getCc());
            }

            if (!this.isNullOrEmpty(mail.getBcc())) {
                helper.setBcc(mail.getBcc());
            }

            // Ghi tiêu đề và nội dung
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true);

            // 2.4. Đính kèm file
            String filenames = mail.getFilenames();
            if(!this.isNullOrEmpty(filenames)) {
            for(String filename: filenames.split("[,;]+")) {
            File file = new File(filename.trim());
            helper.addAttachment(file.getName(), file);
                 }
            }
            javaMailSender.send(message);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    private boolean isNullOrEmpty(String text){
        return (text == null || text.trim().length() == 0);
    }

    List<Mail> queue = new ArrayList<>();


    @Override
    public void push(Mail mail) {
        queue.add(mail);
    }

    @Scheduled(fixedDelay = 500)
    public void run(){
        while (!queue.isEmpty()) {
            try{
                this.send(queue.remove(0));
            }catch(Exception e){
                e.printStackTrace();

            }
        }
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom("WebShop <hieu@gmail>");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,true);
            javaMailSender.send(message);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendActivationEmail(String recipientEmail, String activationLink) {
      SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Kích hoạt tài khoản của bạn");
        message.setText("Nhấn vào link sau để kích hoạt tài khoản: " + activationLink);
        javaMailSender.send(message);
    }

}
