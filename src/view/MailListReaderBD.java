package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import model.Mail;

public class MailListReaderBD {

    private MailListReaderBD() {
    }

    // Email address regular expression that 99.99% works 
    // https://emailregex.com/
    private static final Pattern emailPattern = Pattern.compile(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?"
            + ":\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:"
            + "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b"
            + "\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-"
            + "\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\."
            + ")+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2"
            + "[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4]"
            + "[0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*"
            + "[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a"
            + "\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    );

    private static boolean validateEmail(String line) {
        return emailPattern.matcher(line).matches();
    }

    public static List<Mail> read(String fileName) {
        String sql = "SELECT Mail FROM EMAIL";

        List<Mail> mailList = new ArrayList<Mail>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                mailList.add(new Mail(rs.getString("Mail")));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return mailList;
    }
    
    private static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:KATA5.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
        return conn;
    }
}
