package ru.mail.park.mechanics.handler;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.exeption.HandleException;
import ru.mail.park.mechanics.internal.GameProgressService;
import ru.mail.park.mechanics.requests.fromUsers.CoinActionRequest;
import ru.mail.park.websocket.MessageHandler;
import ru.mail.park.websocket.MessageHandlerContainer;

import javax.annotation.PostConstruct;

/**
 * Created by victor on 19.12.16.
 */
public class CoinActionHandler extends MessageHandler<CoinActionRequest> {

    @NotNull
    private GameProgressService gameProgressService;

    @NotNull
    private MessageHandlerContainer messageHandlerContainer;


    public CoinActionHandler(@NotNull GameProgressService gameProgressService,
                            @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(CoinActionRequest.class);
        this.gameProgressService = gameProgressService;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(CoinActionRequest.class, this);
    }

    @Override
    public void handle(@NotNull CoinActionRequest message, @NotNull Long forUser) throws HandleException {
        //пришел запрос на передвижение пирата
        System.out.println("К нам пришло сообщение о том, что пират хочет что-то сделать с монетой " + message.getPiratId());
        gameProgressService.coinAction(message.getPiratId(), message.getPickCoin(), message.getDropCoin(), forUser);
    }

}
