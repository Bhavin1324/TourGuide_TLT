package com.tlt.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/*
Freemarker: Freemarker is template engine in java maintained by the apache foundation, follow the goal of creating general purpose dynamic pages
Here we create template using free marker configuration and bind the datamodel with the template. This template in return would be sent as mail to the client.
 */
public class EmailUtil {

    private String templatePath;
    private String templateName;
    private String subject;
    private Map<String, Object> dataModel;

    private ResourceBundle bundle;
    private FacesContext context = FacesContext.getCurrentInstance();

    public EmailUtil() {
        dataModel = new HashMap<>();
    }

    public EmailUtil(String basename) {
        dataModel = new HashMap<>();
        this.bundle = ResourceBundle.getBundle(basename, context.getViewRoot().getLocale());
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getDataModel() {
        return dataModel;
    }

    public void setDataModel(Map<String, Object> dataModel) {
        this.dataModel = dataModel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean sendSingleMailSync(String recipient) {
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", bundle.getString("MAIL_HOST"));
        props.put("mail.smtp.port", bundle.getString("MAIL_PORT"));

        String senderMail = bundle.getString("OEMAIL");
        String senderPass = bundle.getString("OPASSWORD");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMail, senderPass);
            }
        });
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setRecipient(Message.RecipientType.TO, InternetAddress.parse(recipient)[0]);
            mimeMessage.setSubject(subject);
            // mimeMessage.setText(message);
            String emailContent = generateEmailContent();
            mimeMessage.setContent(emailContent, "text/html");
            if (!emailContent.equals("")) {
                Transport.send(mimeMessage);
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private String generateEmailContent() {
        try {
            // Create a free marker configuration
            Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_30);
            freeMarkerConfig.setDirectoryForTemplateLoading(new File(templatePath));

            //Loading template
            Template template = freeMarkerConfig.getTemplate(templateName);

            //Creating datamodel to merge with our template template
//            Map<String, Object> dataModel = new HashMap<>();
//            dataModel.put("OTP", "789548");
            //Merging template with free datamodel
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            return stringWriter.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
