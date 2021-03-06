package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.mapped.Command;
import hera.database.entities.mapped.Localisation;
import hera.database.entities.mapped.ModuleSettings;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

import static hera.store.DataStore.STORE;

public class ToggleCommand {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		List<Command> commands = STORE.commands().forName(params.get(0));
		if (commands.isEmpty()) {
			return channel.createMessage(spec -> spec.setEmbed(embed -> {
				embed.setColor(Color.ORANGE);
				embed.setDescription(String.format(HeraUtil.getLocalisation(LocalisationKey.ERROR_NOT_REAL_COMMAND, guild).getValue(), params.get(0)));
			})).then();
		}
		Command cmd = commands.get(0);
		List<ModuleSettings> msList = STORE.moduleSettings().forModule(guild.getId().asLong(), cmd.getId());
		ModuleSettings ms = !msList.isEmpty() ? msList.get(0) : null;

		if (ms == null) {
			ms = new ModuleSettings(guild.getId().asLong(), cmd.getId(), false, null);
			STORE.moduleSettings().add(ms);
		} else {
			ms.setEnabled(!ms.isEnabled());
			STORE.moduleSettings().update(ms);
		}
		Localisation message;
		if (ms.isEnabled()) {
			message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_TOGGLE_ON, guild);
		} else {
			message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_TOGGLE_OFF, guild);
		}

		return channel.createMessage(spec -> spec.setEmbed(embed -> {
			embed.setColor(Color.ORANGE);
			embed.setDescription(String.format(message.getValue(), cmd.getName()));
		})).then();
	}
}
