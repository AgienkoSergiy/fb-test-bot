package home.skyrvr.fbtestbot.sender;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.send.*;
import com.github.messenger4j.send.buttons.Button;
import com.github.messenger4j.send.templates.ButtonTemplate;
import com.github.messenger4j.send.templates.GenericTemplate;
import com.github.messenger4j.send.templates.ReceiptTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//TODO make abstract factory or interface
@Component
public class MessageSender {

    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
    private final MessengerSendClient sendClient;

    @Autowired
    public MessageSender(MessengerSendClient sendClient) {
        this.sendClient = sendClient;
    }


    public void sendTextMessage(String recipientId, String text) {
        try {
            final Recipient recipient = Recipient.newBuilder().recipientId(recipientId).build();
            final NotificationType notificationType = NotificationType.REGULAR;
            final String metadata = "SOME_DUMMY_METADATA";

            this.sendClient.sendTextMessage(recipient, notificationType, text, metadata);
        } catch (MessengerApiException | MessengerIOException e) {
            handleSendException(e);
        }
    }
/*
    private void sendImageMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendImageAttachment(recipientId, RESOURCE_URL + "/assets/rift.png");
    }

    private void sendGifMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendImageAttachment(recipientId, "https://media.giphy.com/media/11sBLVxNs7v6WA/giphy.gif");
    }

    private void sendAudioMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendAudioAttachment(recipientId, RESOURCE_URL + "/assets/sample.mp3");
    }

    private void sendVideoMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendVideoAttachment(recipientId, RESOURCE_URL + "/assets/allofus480.mov");
    }

    private void sendFileMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendFileAttachment(recipientId, RESOURCE_URL + "/assets/test.txt");
    }

    private void sendButtonMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        final List<Button> buttons = Button.newListBuilder()
                .addUrlButton("Open Web URL", "https://www.oculus.com/en-us/rift/").toList()
                .addPostbackButton("Trigger Postback", "DEVELOPER_DEFINED_PAYLOAD").toList()
                .addCallButton("Call Phone Number", "+16505551234").toList()
                .build();

        final ButtonTemplate buttonTemplate = ButtonTemplate.newBuilder("Tap a button", buttons).build();
        this.sendClient.sendTemplate(recipientId, buttonTemplate);
    }

    private void sendGenericMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        final List<Button> riftButtons = Button.newListBuilder()
                .addUrlButton("Open Web URL", "https://www.oculus.com/en-us/rift/").toList()
                .addPostbackButton("Call Postback", "Payload for first bubble").toList()
                .build();

        final List<Button> touchButtons = Button.newListBuilder()
                .addUrlButton("Open Web URL", "https://www.oculus.com/en-us/touch/").toList()
                .addPostbackButton("Call Postback", "Payload for second bubble").toList()
                .build();


        final GenericTemplate genericTemplate = GenericTemplate.newBuilder()
                .addElements()
                .addElement("rift")
                .subtitle("Next-generation virtual reality")
                .itemUrl("https://www.oculus.com/en-us/rift/")
                .imageUrl(RESOURCE_URL + "/assets/rift.png")
                .buttons(riftButtons)
                .toList()
                .addElement("touch")
                .subtitle("Your Hands, Now in VR")
                .itemUrl("https://www.oculus.com/en-us/touch/")
                .imageUrl(RESOURCE_URL + "/assets/touch.png")
                .buttons(touchButtons)
                .toList()
                .done()
                .build();

        this.sendClient.sendTemplate(recipientId, genericTemplate);
    }

    private void sendReceiptMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        final String uniqueReceiptId = "order-" + Math.floor(Math.random() * 1000);

        final ReceiptTemplate receiptTemplate = ReceiptTemplate.newBuilder("Peter Chang", uniqueReceiptId, "USD", "Visa 1234")
                .timestamp(1428444852L)
                .addElements()
                .addElement("Oculus Rift", 599.00f)
                .subtitle("Includes: headset, sensor, remote")
                .quantity(1)
                .currency("USD")
                .imageUrl(RESOURCE_URL + "/assets/riftsq.png")
                .toList()
                .addElement("Samsung Gear VR", 99.99f)
                .subtitle("Frost White")
                .quantity(1)
                .currency("USD")
                .imageUrl(RESOURCE_URL + "/assets/gearvrsq.png")
                .toList()
                .done()
                .addAddress("1 Hacker Way", "Menlo Park", "94025", "CA", "US").done()
                .addSummary(626.66f)
                .subtotal(698.99f)
                .shippingCost(20.00f)
                .totalTax(57.67f)
                .done()
                .addAdjustments()
                .addAdjustment().name("New Customer Discount").amount(-50f).toList()
                .addAdjustment().name("$100 Off Coupon").amount(-100f).toList()
                .done()
                .build();

        this.sendClient.sendTemplate(recipientId, receiptTemplate);
    }

    private void sendQuickReply(String recipientId) throws MessengerApiException, MessengerIOException {
        final List<QuickReply> quickReplies = QuickReply.newListBuilder()
                .addTextQuickReply("Action", "DEVELOPER_DEFINED_PAYLOAD_FOR_PICKING_ACTION").toList()
                .addTextQuickReply("Comedy", "DEVELOPER_DEFINED_PAYLOAD_FOR_PICKING_COMEDY").toList()
                .addTextQuickReply("Drama", "DEVELOPER_DEFINED_PAYLOAD_FOR_PICKING_DRAMA").toList()
                .addLocationQuickReply().toList()
                .build();

        this.sendClient.sendTextMessage(recipientId, "What's your favorite movie genre?", quickReplies);
    }

    private void sendReadReceipt(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendSenderAction(recipientId, SenderAction.MARK_SEEN);
    }

    private void sendTypingOn(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendSenderAction(recipientId, SenderAction.TYPING_ON);
    }

    private void sendTypingOff(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendSenderAction(recipientId, SenderAction.TYPING_OFF);
    }

    private void sendAccountLinking(String recipientId) {
        // supported by messenger4j since 0.7.0
        // sample implementation coming soon
    }

*/
    private void handleSendException(Exception e) {
        logger.error("Message could not be sent. An unexpected error occurred.", e);
    }

}
