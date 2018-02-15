package home.skyrvr.fbtestbot.handler.event;

import com.github.messenger4j.receive.handlers.*;

public abstract class EventHandlerFactory {
    public abstract TextMessageEventHandler createTextMessageEventHandler();

    public abstract AttachmentMessageEventHandler createAttachmentMessageEventHandler();

    public abstract QuickReplyMessageEventHandler createQuickReplyMessageEventHandler();

    public abstract PostbackEventHandler createPostbackEventHandler();

    public abstract AccountLinkingEventHandler createAccountLinkingEventHandler();

    public abstract OptInEventHandler createOptInEventHandler();

    public abstract EchoMessageEventHandler createEchoMessageEventHandler();

    public abstract MessageDeliveredEventHandler createMessageDeliveredEventHandler();

    public abstract MessageReadEventHandler createMessageReadEventHandler();

    public abstract FallbackEventHandler createFallbackEventHandler();

}
