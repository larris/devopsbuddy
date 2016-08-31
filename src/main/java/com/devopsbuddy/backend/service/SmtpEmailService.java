package com.devopsbuddy.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by larris on 31/08/16.
 */
public class SmtpEmailService extends AbstractEmailService {

    //Logger
    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Autowired
    private MailSender mailSender;


    @Override
    public void sendGenericEmaiMessage(SimpleMailMessage message) {

        LOG.debug("Sending mail for {}",message);
        mailSender.send(message);
        LOG.info("Mail send");
    }
}
