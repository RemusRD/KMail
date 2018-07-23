
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import entities.Body;
import entities.EmailAddress;
import entities.Message;
import entities.Recipient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import service.SMTPService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SMTPServiceIT {

    @Autowired
    SMTPService sut;
    GreenMail greenMail;

    @BeforeAll
    public void setUp() {
        greenMail = new GreenMail(new ServerSetup(2525, null, "smtp"));
        greenMail.start();
    }

    @Test
    public void sendSimpleMailShouldSendAMail() throws MessagingException, IOException {
        //arrange
        Message sentMessage = new Message("subject", new Body("text/html", "test"),
                new Recipient(new EmailAddress("test", "from@test.com")),
                new Recipient(new EmailAddress("test", "sender@test.com")));

        //act
        sut.sendSimpleMail(sentMessage);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage lastRecieved = receivedMessages[receivedMessages.length - 1];

        //asserts
        assertThat(lastRecieved.getSubject(), is(sentMessage.getSubject()));
        assertThat(lastRecieved.getContent(), is(sentMessage.getBody().getContent()));
        assertThat(lastRecieved.getFrom()[0], is(sentMessage.getFrom().getEmailAddress().getAddress()));
        assertThat(lastRecieved.getSender(), is(sentMessage.getSender().getEmailAddress().getAddress()));

    }

    @AfterAll
    public void tearDown() {
        greenMail.stop();
    }
}
