package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.mapped.GlobalSetting;
import hera.database.entities.mapped.Localisation;
import hera.database.types.GlobalSettingKey;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Version {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return channel.createMessage(getVersion(guild)).then();
	}

	private static String getVersion(Guild guild) {
		Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_VERSION, guild);
		List<GlobalSetting> globalSettings = STORE.globalSettings().forKey(GlobalSettingKey.VERSION);

		if (globalSettings.isEmpty()) {
			return HeraUtil.LOCALISATION_GENERAL_ERROR.getValue();
		}

		return String.format(message.getValue(), globalSettings.get(0).getValue());
	}
}
