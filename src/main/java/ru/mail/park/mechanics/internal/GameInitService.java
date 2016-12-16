package ru.mail.park.mechanics.internal;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mail.park.messageSystem.Address;
import ru.mail.park.messageSystem.MessageSystem;
import ru.mail.park.messageSystem.MessagesToGameMechanics.InitGameMessage;
import ru.mail.park.model.UserProfile;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class GameInitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameInitService.class);

    private Address myAddress = new Address();

    private Address gameMechanincsAddress;

    private MessageSystem ms;

    public GameInitService(@NotNull GameMechanicsInNewThread gameMechanicsInNewThread,
                           @NotNull MessageSystem messageSystem) {
        this.ms = messageSystem;
        this.gameMechanincsAddress = gameMechanicsInNewThread.getAddress();
    }

    public void initGameFor(UserProfile firstPlayer, UserProfile secondPLayer) {
        final Collection<UserProfile> players = new ArrayList<>();
        players.add(firstPlayer);
        players.add(secondPLayer);
        ms.sendMessage(new InitGameMessage(myAddress,gameMechanincsAddress,firstPlayer, secondPLayer));
    }

}
