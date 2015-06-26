/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Cassiano
 */
//classe que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp
/**
 *envia de email
@param  from
@param  password
@param  to
@param  subject
@param  message
@author Cassiano
 * via hMailServer
 */
public class SendMailLocalhostSimples {

    private String mensagemEmail;

    public String getMensagemEmail() {
        return mensagemEmail;
    }

    public void setMensagemEmail(String mensagemEmail) {
        this.mensagemEmail = mensagemEmail;
    }

   

    public void sendMailLocalhostSimples(String from, String password, String to, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.host", "cmv3.com.br");//ip do servidor hmailserver
        props.put("mail.smtp.port", "25");  //era 266
        props.put("mail.smtp.auth", "true");
        Authenticator auth = new SimpleAuth(from, password);
        Session session = Session.getDefaultInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);

        try {
            //Setando a origem do email
            msg.setFrom(new InternetAddress(from));
            //Setando o destinatário
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSentDate(new Date());
            //Setando o assunto
            msg.setSubject(subject);
            //Setando o conteúdo/corpo do email
            msg.setContent(message, "text/plain");
            Transport.send(msg);

            System.out.println("\nEmail enviado com sucesso!");
            setMensagemEmail("Email enviado com sucesso!");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado com sucesso!", "Email enviado com sucesso!"));
        } catch (Exception e) {
            System.out.println("\n>> Erro: Completar Mensagem");
            
            setMensagemEmail("Email não foi enviado!");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Email não foi enviado!", "Email não foi enviado!"));
            e.printStackTrace();
        }
    }
}

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
