
package cobranza.v2.pgt.com.models.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import cobranza.v2.pgt.com.utils.otros.MailRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {
  
  private Logger logger = LoggerFactory.getLogger(EmailService.class);
  
  @Autowired
  private JavaMailSender emailSender;
  
  @Autowired
  private Configuration config;
  
  public void sendSimpleMessage(MailRequest mail) {
    SimpleMailMessage message = new SimpleMailMessage( );
    message.setSubject(mail.getSubject( ));
    message.setText("This is the test email template for your email:\n%s\n");
    message.setTo(mail.getTo( ));
    message.setFrom(mail.getFrom( ));
    message.setBcc("elson.vicente@pagatodo360.net");
    message.setCc("elson619v@gmail.com");
    logger.info("enviando correo.. " + mail.getTo( ));
    emailSender.send(message);
    logger.info("Correo enviado....");
  }
  
  public boolean sendEmail(String[ ] correos,
                           MailRequest request,
                           Map<String, Object> model,
                           String template,
                           String NamePDF) {
    MimeMessage message = emailSender.createMimeMessage( );
    try {
      MimeMessageHelper helper = new MimeMessageHelper(
        message,
        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name( ));
      Template t = config.getTemplate(template);
      String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
      helper.setTo(correos);
      helper.setText(html, true);
      helper.setSubject(request.getSubject( ));
      helper.setFrom(request.getFrom( ));
      
      if (!NamePDF.equals("") && NamePDF != null) {
        ClassPathResource pdf = new ClassPathResource("file/" + NamePDF);
        helper.addAttachment(NamePDF, pdf);
      }
      logger.info("enviando correo.. " + String.join(",", correos));
      emailSender.send(message);
      logger.info("Correo enviado....");
      return true;
    }catch(MessagingException | IOException | TemplateException e) {
      logger.error("Error al enviar correo a: " + e.getMessage( ));
      return false;
    }
  }
  
  public boolean sendEmailComplet(String[ ] to,
                                  String[ ] cc,
                                  MailRequest request,
                                  Map<String, Object> model,
                                  String template,
                                  String NamePDF) {
    MimeMessage message = emailSender.createMimeMessage( );
    try {
      // Set From: header field
      message.setFrom(new InternetAddress(request.getFrom( )));
      InternetAddress[ ] toAddress = new InternetAddress[to.length];
      
      // To get the array of toaddresses
      for(int i = 0;i < to.length;i++) {
        toAddress[i] = new InternetAddress(to[i]);
        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
      }
      InternetAddress[ ] ccAddress = new InternetAddress[cc.length];
      for(int i = 0;i < cc.length;i++) {
        ccAddress[i] = new InternetAddress(cc[i]);
        message.addRecipient(Message.RecipientType.CC, ccAddress[i]);
      }
      // Set Subject: header field
      message.setSubject(request.getSubject( ));
      
      // Put the content of your message
      message.setText("Hi there, this is my first message sent with JavaMail");
      logger.info("enviando correo To.. " + String.join(",", to));
      logger.info("enviando correo CC.. " + String.join(",", cc));
      // Send message
      Transport.send(message);
      logger.info("Correo enviado....");
      return true;
    }catch(MessagingException e) {
      logger.error("Error al enviar correo a: " + e.getMessage( ));
      return false;
    }
  }
}