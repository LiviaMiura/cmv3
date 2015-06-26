/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.faetec.backingbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *Implementa os métodos autenticaco e envio de email localhost.
@author Antonio Cassiano
 **/
public class SendMailLocalhost implements Serializable {

    private static final long serialVersionUID = 1L;
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private HttpSession session = request.getSession();
    String arquivo = session.getServletContext().getRealPath("/") + "arquivo/DadosCMV.xls";
    private String mensagemEmail;

    public String getMensagemEmail() {
        return mensagemEmail;
    }

    public void setMensagemEmail(String mensagemEmail) {
        this.mensagemEmail = mensagemEmail;
    }

    /**
    Metodo para enviar email com anexo (utilizando  hMailServer) localhost
    @author Antonio Cassiano
    @param  from
    @param  password
    @param  to
    @param  subject
    @param  message
     **/
    public void sendMailLocalhost(String from, String password, String to, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.host", "cmv3.com.br");//ip do servidor hmailserver
        props.put("mail.smtp.port", "25");  //era 266
        props.put("mail.smtp.auth", "true");
        Authenticator auth = new SimpleAuth(from, password);
        Session session = Session.getDefaultInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);
        System.out.println("\nCaminho arquivo Excel : " + arquivo);

        try {
            msg.setFrom(new InternetAddress(from));                                 //Setando a origem do email
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));   //Setando o destinatário
            msg.setSentDate(new Date());
            msg.setSubject(subject);                                                //Setando o assunto
            //msg.setContent(message, "text/plain");                              //Setando o conteúdo/corpo do email

            MimeBodyPart p1 = new MimeBodyPart();                        // Create and fill first part
            p1.setContent(message, "text/plain");

            MimeBodyPart p2 = new MimeBodyPart();                        // Create second part
            FileDataSource fds = new FileDataSource(arquivo);           // Put a file in the second part
            p2.setDataHandler(new DataHandler(fds));
            p2.setFileName(fds.getName());

            Multipart mp = new MimeMultipart();                         // Create the Multipart.  Add BodyParts to it.
            mp.addBodyPart(p1);
            mp.addBodyPart(p2);

            msg.setContent(mp);                                         // Set Multipart as the message's content
            Transport.send(msg);

            System.out.println("\nEmail enviado com sucesso!");
            setMensagemEmail("Email enviado com sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado com sucesso!", "Email enviado com sucesso!"));
        } catch (Exception e) {
            System.out.println("\n>> Erro: Completar Mensagem");
            setMensagemEmail("Email não foi enviado!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Email não foi enviado!", "Email não foi enviado!"));
        }
    }

    /**
    Metodo para autenticar o envio de  email (utilizando  hMailServer)
    @author Antonio Cassiano
    @return autenticacao do username e password
     **/
    class SimpleAuth extends Authenticator {

        public String username = null;
        public String password = null;

        public SimpleAuth(String user, String pwd) {
            username = user;
            password = pwd;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
}
