package com.vadim.parser;


import java.util.Properties;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;
 
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NewsParserApplication.class)
@WebAppConfiguration
public class EmailTest {
 
	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
 
    private GreenMail testSmtp;
 
    @Before
    public void testSmtpInit(){
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
 
        
        Session mailSession = Session.getDefaultInstance(new Properties(), null);
        Properties props = mailSession.getProperties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "3025");
        props.put("mail.smtp.socketFactory.port", "3025");
        javaMailSender.setJavaMailProperties(props);
    }
 
    @Test
    public void testEmail() throws InterruptedException, MessagingException {
    	
		MimeMessage mail = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail);
        
    	helper.setFrom("RogerJavaTester@gmail.com");
    	helper.setTo("funnydaisyk@gmail.com");
    	helper.setSubject("test subject");
    	helper.setText("test message");
    	javaMailSender.send(mail);
         
        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("test subject", messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals("test message", body);
    }
 
    @After
    public void cleanup(){
        testSmtp.stop();
    }
}