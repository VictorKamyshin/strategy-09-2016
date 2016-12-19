package ru.mail.park.mechanics.handler;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.mail.park.exeption.HandleException;
import ru.mail.park.mechanics.game.CoordPair;
import ru.mail.park.mechanics.internal.GameProgressService;
import ru.mail.park.mechanics.requests.fromUsers.PiratMoveRequest;
import ru.mail.park.websocket.MessageHandler;
import ru.mail.park.websocket.MessageHandlerContainer;

import javax.annotation.PostConstruct;

@Component
public class PiratMoveHandler extends MessageHandler<PiratMoveRequest> {

    @NotNull
    private GameProgressService gameProgressService;

    @NotNull
    private  MessageHandlerContainer messageHandlerContainer;

    public PiratMoveHandler(@NotNull GameProgressService gameProgressService,
                            @NotNull MessageHandlerContainer messageHandlerContainer) {
        super(PiratMoveRequest.class);
        this.gameProgressService = gameProgressService;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @PostConstruct
    private void init() {
        messageHandlerContainer.registerHandler(PiratMoveRequest.class, this);
    }

    @Override
    public void handle(@NotNull PiratMoveRequest message, @NotNull Long forUser) throws HandleException {
        //пришел запрос на передвижение пирата
        System.out.println("К нам пришло сообщение о передвижении пирата с номером " + message.getPiratId());
        gameProgressService.movePirat(message.getPiratId(),
                new CoordPair(message.getTargetCellX(), message.getTargetCellY()), forUser);
    }
}
