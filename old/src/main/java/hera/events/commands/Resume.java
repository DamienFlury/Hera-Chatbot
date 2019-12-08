package hera.events.commands;

import hera.enums.BoundChannel;
import hera.events.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Resume extends AbstractCommand {

    private static final Logger LOG = LoggerFactory.getLogger(Resume.class);

    private MessageSender ms;
    private GuildAudioPlayerManager gapm;

    // constructor
    Resume() {
        super(null, 0, false);
        this.ms = MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Resume.execute");
        if (gapm.getGuildAudioPlayer(e.getGuild()).player.isPaused()) {
            gapm.getGuildAudioPlayer(e.getGuild()).player.setPaused(false);
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Player resumed.");
            LOG.info(e.getAuthor() + " resumed the audio player");
        } else {
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Player is not paused.");
            LOG.debug(e.getAuthor() + " used commands resume although the player is not paused");
        }
        LOG.debug("End of: Resume.execute");
    }
}
