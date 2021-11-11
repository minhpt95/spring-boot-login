package com.catdev.project.service.impl;

import com.catdev.project.service.MailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Service
public class MailServiceImpl implements MailService {
    protected final Logger logger = LogManager.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Environment env;

    private static final String RECIPIENT_EMPTY = "Recipient empty";

    private static final String ERROR_SEND_EMAIL = "error when send email : {}";

    @Override
    @Async
    public void sendEmailAttach(String to, String subject, String body, String fileName, File file){
        logger.info("start send email attach file; to: {}; subject: {}; fileName: {}", to, subject, fileName);
        if(to.isBlank()) {
            logger.error(RECIPIENT_EMPTY);
        }else {
            try {
                String userName = env.getProperty("spring.mail.username");
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(userName, userName);
                helper.setTo(to);
                helper.setText(body,true);
                helper.setSubject(subject);
                if (fileName != null && !fileName.trim().isEmpty() && null != file) {
                    helper.addAttachment(fileName, file);
                }
                sender.send(message);
            }catch (MessagingException | MailException | UnsupportedEncodingException e){
                logger.error(ERROR_SEND_EMAIL,() -> e);
            }
        }
        logger.info("end send email attach file; to: {}; subject: {}; fileName: {}",() -> to,() -> subject,() -> fileName);
    }

    @Override
    @Async
    public void sendEmailAttach(String[] to, String subject, String body, String fileName, File file){
        logger.info("start send email attach file to multiple people; to: {}; subject: {}; fileName: {}", () -> Arrays.toString(to),() -> subject,() -> fileName);
        if(to.length <= 0) {
            logger.error(RECIPIENT_EMPTY);
        }else {
            try {
                String userName = env.getProperty("spring.mail.username");
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setText(body, true);
                helper.setFrom(userName, userName);
                helper.setTo(to);
                helper.setSubject(subject);
                if (!fileName.isBlank() && file != null) {
                    helper.addAttachment(fileName, file);
                }
                sender.send(message);
            }catch (MessagingException | MailException | UnsupportedEncodingException e){
                logger.error(ERROR_SEND_EMAIL,() -> e);
            }
        }
        logger.info("end send email attach file to multiple people; to: {}; subject: {}; fileName: {}",() -> Arrays.toString(to),() -> subject,() -> fileName);
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String body){
        if(to.isBlank()) {
            logger.error(RECIPIENT_EMPTY);
        }else {
            try {
                logger.info("start send email not attach file to: {}; subject: {};", to, subject);
                String hostingName = env.getProperty("spring.mail.username");
                String hostingEmail = env.getProperty("system.name.mail");
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(to);
                helper.setFrom(hostingName, hostingEmail);
                message.setText(body, "UTF-8", "html");
                helper.setSubject(subject);
                sender.send(message);
            logger.info("end send email not attach file to: {}; subject: {};", to, subject);
            }catch (MessagingException | MailException | UnsupportedEncodingException e){
                logger.error(ERROR_SEND_EMAIL,() -> e);
            }
        }
    }

    @Override
    @Async
    public void sendEmail(String[] to, String subject, String body){
        if(to.length <= 0) {
            logger.error(RECIPIENT_EMPTY);
        }else{
            try {
                logger.info("start send email not attach multiple people file to: {}; subject: {};",() -> Arrays.toString(to),() -> subject);
                String userName = env.getProperty("spring.mail.username");
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                message.setText(body, "UTF-8", "html");
                helper.setFrom(userName, userName);
                helper.setTo(to);
                helper.setSubject(subject);
                sender.send(message);
                logger.info("end send email not attach multiple people file to: {}; subject: {};",() -> Arrays.toString(to),() -> subject);
            }catch (MessagingException | MailException | UnsupportedEncodingException e){
                logger.error(ERROR_SEND_EMAIL,() -> e);
            }
        }
    }

    @Override
    @Async
    public void sendEmail(String[] to, String from, String personal, String subject, String body) {
        if(to.length <= 0) {
            logger.error(RECIPIENT_EMPTY);
        }else{
            try {
                logger.info("start send email not attach multiple people file to: {}; subject: {};",() -> Arrays.toString(to),() -> subject);
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                message.setText(body, "UTF-8", "html");
                helper.setFrom(from, personal);
                helper.setTo(to);
                helper.setSubject(subject);
                sender.send(message);
                logger.info("end send email not attach multiple people file to: {}; subject: {};",() -> Arrays.toString(to),() -> subject);
            }catch (MessagingException | MailException | UnsupportedEncodingException e){
                logger.error(ERROR_SEND_EMAIL,() -> e);
            }
        }
    }
}
