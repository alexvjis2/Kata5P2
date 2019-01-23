package view;

import java.util.List;

import model.Histogram;
import model.Mail;

public final class MailHistogramBuilder {

    private MailHistogramBuilder() {}

    public static Histogram<String> build(final List<Mail> mails) {
        final Histogram<String> histogram = new Histogram<>();
        
        mails.forEach(mail -> histogram.increment(mail.getDomain()));
        
        return histogram;
    }
}
