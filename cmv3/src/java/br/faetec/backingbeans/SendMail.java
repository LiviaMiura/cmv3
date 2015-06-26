package br.faetec.backingbeans;

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
 *Implementa os métodos autenticaco e envio de email pelo gmail
@author Antonio Cassiano
 **/
public class SendMail {

    private String mensagemEmail;

    public String getMensagemEmail() {
        return mensagemEmail;
    }

    public void setMensagemEmail(String mensagemEmail) {
        this.mensagemEmail = mensagemEmail;
    }

    /**
    Metodo para enviar email com anexo pelo gmail
    @author Antonio Cassiano
    @param  from
    @param  to
    @param  subject
    @param  message
     **/
    public void sendMail(String from, String to, String subject, String message) {

        Properties props = new Properties();

        // quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado

        props.setProperty("proxySet", "true");//para usar o proxy mudar para true
        props.setProperty("socksProxyHost", "192.168.1.10"); // IP do Servidor Proxy (verificar na faculdade o endere�o n�o me lembro)
        props.setProperty("socksProxyPort", "3128");  // Porta do servidor Proxy
        props.setProperty("socksProxyUsername", "contay");
        props.setProperty("socksProxyPassword", "qwerty");



        props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); //server SMTP do GMAIL
        props.put("mail.smtp.auth", "true"); //ativa autenticacao
        props.put("mail.smtp.user", from); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465"); //porta
        props.put("mail.smtp.socketFactory.port", "465"); //mesma porta para o socket
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        //Cria um autenticador que sera usado a seguir
        SimpleAuth auth = null;
        auth = new SimpleAuth("faetec.awr@gmail.com", "netbeans");

        //Session - objeto que ira realizar a conex�o com o servidor
        /*Como h� necessidade de autentica��o � criada uma autenticacao que
         * � responsavel por solicitar e retornar o usu�rio e senha para
         * autentica��o */
        Session session = Session.getDefaultInstance(props, auth);
        session.setDebug(true); //Habilita o LOG das a��es executadas durante o envio do email

        //Objeto que cont�m a mensagem
        Message msg = new MimeMessage(session);

        try {
            //Setando o destinat�rio
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //Setando a origem do email
            msg.setFrom(new InternetAddress(from));
            //Setando o assunto
            msg.setSubject(subject);
            //Setando o conte�do/corpo do email
            msg.setContent(message, "text/plain");

            System.out.println("\nEmail enviado com sucesso!");
            setMensagemEmail("Email enviado com sucesso!");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado com sucesso Gmail!", "Email enviado com sucesso GMail!"));


        } catch (Exception e) {
            System.out.println(">> Erro: Completar Mensagem");

            setMensagemEmail("Email não foi enviado!");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Email não foi enviado Gmail!", "Email não foi enviado Gmail!"));

            e.printStackTrace();
        }

        //Objeto encarregado de enviar os dados para o email
        Transport tr;
        try {
            tr = session.getTransport("smtp"); //define smtp para transporte
            /*
             *  1 - define o servidor smtp
             *  2 - seu nome de usuario do gmail
             *  3 - sua senha do gmail
             */
            tr.connect("smtp.gmail.com", "faetec.awr@gmail.com", "netbeans");
            msg.saveChanges(); // don't forget this
            //envio da mensagem
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(">> Erro: Envio Mensagem");
            e.printStackTrace();
        }

    }
}

/**
Metodo para autenticar o envio de  email pelo gmail
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

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
