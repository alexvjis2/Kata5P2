package model;

public class Mail {
    private final String mail;
    
    public Mail(String mail) {
        this.mail = mail;
    }
    
    public String getDomain(){
        return mail.substring(mail.indexOf('@')+1);
    }

    @Override
    public String toString() { return mail; }    
}