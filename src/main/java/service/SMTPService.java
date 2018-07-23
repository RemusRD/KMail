package service;

import entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SMTPService {


    private JavaMailSender mailSender;

    @Autowired
    public SMTPService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMail(Message messageToSend) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(messageToSend.getSubject());
        message.setFrom(messageToSend.getFrom().getEmailAddress().getAddress());
        String[] bccRecipients =  messageToSend.getBccRecipients().stream()
                            .map(e -> e.getEmailAddress().getAddress()).toArray(String[]::new);

        message.setBcc(bccRecipients);
        String[] ccRecipients =  messageToSend.getCcRecipients().stream()
                .map(e -> e.getEmailAddress().getAddress()).toArray(String[]::new);

        message.setCc(ccRecipients);
        message.setReplyTo(messageToSend.getReplyTo().getEmailAddress().getAddress());
        String[] toRecipients = messageToSend.getToRecipients().stream()
                        .map(e -> e.getEmailAddress().getAddress()).toArray(String[]::new);

        message.setTo(toRecipients);
        message.setSentDate(new Date());
        message.setText(messageToSend.getBody().getContent());
        mailSender.send(message);
    }
}
