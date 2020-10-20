package com.srijith.redditclone.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.srijith.redditclone.exceptions.SpringRedditException;
import com.srijith.redditclone.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	
	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;

	void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> { 
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			
			messageHelper.setFrom("springreddit@email.com");
			
			messageHelper.setTo(notificationEmail.getRecipient());
			
			messageHelper.setSubject(notificationEmail.getSubject());
			
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
		};
		
		try {
			mailSender.send(messagePreparator);
			
			log.info("Activation Email Sent");
		}
		catch(MailException e) {
			throw new SpringRedditException("Exeception occurred while sending email!!");
		}
	}
}
