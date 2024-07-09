package com.deathWingIN.edumanage.controller;

import com.deathWingIN.edumanage.util.tools.VerificationCodeGenerator;
import com.sun.org.apache.bcel.internal.classfile.Code;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class ForgotPasswordFormController {

    public AnchorPane context;
    public TextField txtEmail;
    public Button btn;


    public void backToLoginOnAction(ActionEvent actionEvent) throws IOException {

        setUI("LoginForm");
    }

    public void sendCodeOnAction(ActionEvent actionEvent) {

        try {
            int verificationCode = new VerificationCodeGenerator().getCode(5);


            String fromEmail = "imeshnirmal1u@gmail.com";
            String tomEmail = txtEmail.getText();
            String host = "localhost";

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
            //node => nodemailer, (sendgrid, twilio) for mobile

            Session session = Session.getDefaultInstance(properties);

            //---------------------
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(fromEmail));
            mimeMessage.setSubject("Verification Code");
            mimeMessage.setText("Your Verification Code is " + verificationCode);
            mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(tomEmail));

            //Transport.send(mimeMessage);
            System.out.println("Verification Code Sent");
            //==================

            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("../view/CodeVerificationForm.fxml"));
            Parent parent = fxmlLoader.load();
            CodeVerificationFormController controller = fxmlLoader.getController();
            controller.setUserData(verificationCode, tomEmail);
            Stage stage = (Stage) context.getScene().getWindow();
            stage.setScene(new Scene(parent));


        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    private void setUI(String location) throws IOException {

        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }
}
