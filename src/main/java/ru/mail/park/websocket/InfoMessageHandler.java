package ru.mail.park.websocket;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.mail.park.exeption.HandleException;

import javax.annotation.PostConstruct;

@Component
public class InfoMessageHandler extends MessageHandler<InfoMessage.Request> {

    @NotNull
    private MessageHandlerContainer messageHandlerContainer;

    public InfoMessageHandler(@NotNull MessageHandlerContainer messageHandlerContainer) {
        super(InfoMessage.Request.class);
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(InfoMessage.Request.class, this);
    }

    @Override
    public void handle(@NotNull InfoMessage.Request message, @NotNull Long userId) throws HandleException {

    }
}
