package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.database.entities.mapped.GuildSetting;
import hera.database.entities.mapped.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

import static hera.store.DataStore.STORE;

public class Prefix {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		GuildSetting guildSetting = new GuildSetting(guild.getId().asLong(), GuildSettingKey.COMMAND_PREFIX, params.get(0));
		STORE.guildSettings().upsert(guildSetting);
		Localisation message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_PREFIX, guild);
		return channel.createMessage(spec -> spec.setEmbed(embed -> {
			embed.setColor(Color.ORANGE);
			embed.setDescription(String.format(message.getValue(), guildSetting.getValue()));
		})).then();
	}
}
