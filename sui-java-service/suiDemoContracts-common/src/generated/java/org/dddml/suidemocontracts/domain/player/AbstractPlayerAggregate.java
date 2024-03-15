// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.domain.player;

import java.util.*;
import java.math.BigInteger;
import java.util.Date;
import org.dddml.suidemocontracts.domain.*;
import org.dddml.suidemocontracts.specialization.*;

public abstract class AbstractPlayerAggregate extends AbstractAggregate implements PlayerAggregate {
    private PlayerState.MutablePlayerState state;

    private List<Event> changes = new ArrayList<Event>();

    public AbstractPlayerAggregate(PlayerState state) {
        this.state = (PlayerState.MutablePlayerState)state;
    }

    public PlayerState getState() {
        return this.state;
    }

    public List<Event> getChanges() {
        return this.changes;
    }

    public void throwOnInvalidStateTransition(Command c) {
        PlayerCommand.throwOnInvalidStateTransition(this.state, c);
    }

    protected void apply(Event e) {
        onApplying(e);
        state.mutate(e);
        changes.add(e);
    }


    ////////////////////////

    public static class SimplePlayerAggregate extends AbstractPlayerAggregate {
        public SimplePlayerAggregate(PlayerState state) {
            super(state);
        }

        @Override
        public void create(String nickname, String intro, Long offChainVersion, String commandId, String requesterId, PlayerCommands.Create c) {
            java.util.function.Supplier<PlayerEvent.PlayerCreated> eventFactory = () -> newPlayerCreated(nickname, intro, offChainVersion, commandId, requesterId);
            PlayerEvent.PlayerCreated e;
            try {
                e = verifyCreate(eventFactory, nickname, intro, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void update(String nickname, String intro, Long offChainVersion, String commandId, String requesterId, PlayerCommands.Update c) {
            java.util.function.Supplier<PlayerEvent.PlayerUpdated> eventFactory = () -> newPlayerUpdated(nickname, intro, offChainVersion, commandId, requesterId);
            PlayerEvent.PlayerUpdated e;
            try {
                e = verifyUpdate(eventFactory, nickname, intro, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        @Override
        public void delete(Long offChainVersion, String commandId, String requesterId, PlayerCommands.Delete c) {
            java.util.function.Supplier<PlayerEvent.PlayerDeleted> eventFactory = () -> newPlayerDeleted(offChainVersion, commandId, requesterId);
            PlayerEvent.PlayerDeleted e;
            try {
                e = verifyDelete(eventFactory, c);
            } catch (Exception ex) {
                throw new DomainError("VerificationFailed", ex);
            }

            apply(e);
        }

        protected PlayerEvent.PlayerCreated verifyCreate(java.util.function.Supplier<PlayerEvent.PlayerCreated> eventFactory, String nickname, String intro, PlayerCommands.Create c) {
            String Nickname = nickname;
            String Intro = intro;

            PlayerEvent.PlayerCreated e = (PlayerEvent.PlayerCreated) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.player.CreateLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, PlayerState.class, String.class, String.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), nickname, intro, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.player;
//
//public class CreateLogic {
//    public static PlayerEvent.PlayerCreated verify(java.util.function.Supplier<PlayerEvent.PlayerCreated> eventFactory, PlayerState playerState, String nickname, String intro, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected PlayerEvent.PlayerUpdated verifyUpdate(java.util.function.Supplier<PlayerEvent.PlayerUpdated> eventFactory, String nickname, String intro, PlayerCommands.Update c) {
            String Nickname = nickname;
            String Intro = intro;

            PlayerEvent.PlayerUpdated e = (PlayerEvent.PlayerUpdated) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.player.UpdateLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, PlayerState.class, String.class, String.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), nickname, intro, VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.player;
//
//public class UpdateLogic {
//    public static PlayerEvent.PlayerUpdated verify(java.util.function.Supplier<PlayerEvent.PlayerUpdated> eventFactory, PlayerState playerState, String nickname, String intro, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected PlayerEvent.PlayerDeleted verifyDelete(java.util.function.Supplier<PlayerEvent.PlayerDeleted> eventFactory, PlayerCommands.Delete c) {

            PlayerEvent.PlayerDeleted e = (PlayerEvent.PlayerDeleted) ReflectUtils.invokeStaticMethod(
                    "org.dddml.suidemocontracts.domain.player.DeleteLogic",
                    "verify",
                    new Class[]{java.util.function.Supplier.class, PlayerState.class, VerificationContext.class},
                    new Object[]{eventFactory, getState(), VerificationContext.forCommand(c)}
            );

//package org.dddml.suidemocontracts.domain.player;
//
//public class DeleteLogic {
//    public static PlayerEvent.PlayerDeleted verify(java.util.function.Supplier<PlayerEvent.PlayerDeleted> eventFactory, PlayerState playerState, VerificationContext verificationContext) {
//    }
//}

            return e;
        }
           

        protected AbstractPlayerEvent.PlayerCreated newPlayerCreated(String nickname, String intro, Long offChainVersion, String commandId, String requesterId) {
            PlayerEventId eventId = new PlayerEventId(getState().getPlayerId(), null);
            AbstractPlayerEvent.PlayerCreated e = new AbstractPlayerEvent.PlayerCreated();

            e.setNickname(nickname);
            e.setIntro(intro);
            e.setSuiTimestamp(null);
            e.setSuiTxDigest(null);
            e.setSuiEventSeq(null);
            e.setSuiPackageId(null);
            e.setSuiTransactionModule(null);
            e.setSuiSender(null);
            e.setSuiType(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setPlayerEventId(eventId);
            return e;
        }

        protected AbstractPlayerEvent.PlayerUpdated newPlayerUpdated(String nickname, String intro, Long offChainVersion, String commandId, String requesterId) {
            PlayerEventId eventId = new PlayerEventId(getState().getPlayerId(), null);
            AbstractPlayerEvent.PlayerUpdated e = new AbstractPlayerEvent.PlayerUpdated();

            e.setNickname(nickname);
            e.setIntro(intro);
            e.setSuiTimestamp(null);
            e.setSuiTxDigest(null);
            e.setSuiEventSeq(null);
            e.setSuiPackageId(null);
            e.setSuiTransactionModule(null);
            e.setSuiSender(null);
            e.setSuiType(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setPlayerEventId(eventId);
            return e;
        }

        protected AbstractPlayerEvent.PlayerDeleted newPlayerDeleted(Long offChainVersion, String commandId, String requesterId) {
            PlayerEventId eventId = new PlayerEventId(getState().getPlayerId(), null);
            AbstractPlayerEvent.PlayerDeleted e = new AbstractPlayerEvent.PlayerDeleted();

            e.setSuiTimestamp(null);
            e.setSuiTxDigest(null);
            e.setSuiEventSeq(null);
            e.setSuiPackageId(null);
            e.setSuiTransactionModule(null);
            e.setSuiSender(null);
            e.setSuiType(null);
            e.setStatus(null);

            e.setCommandId(commandId);
            e.setCreatedBy(requesterId);
            e.setCreatedAt((java.util.Date)ApplicationContext.current.getTimestampService().now(java.util.Date.class));

            e.setPlayerEventId(eventId);
            return e;
        }

    }

}
