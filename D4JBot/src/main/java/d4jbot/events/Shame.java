package d4jbot.events;

import java.util.List;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;

public class Shame {

	private MessageSender ms;

	public Shame(MessageSender ms) {
		this.ms = ms;
	}
	
	@EventSubscriber
	public void timoutUser(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "shame")) {
			if(e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				
				String[] args = e.getMessage().getContent().split(" ");
				
				if(args.length == 2) {
					List<IUser> users = e.getGuild().getUsersByName(args[1], true);
					if(!users.isEmpty()) {
						
						Runnable runnable = new Runnable() {
							
							public void run() {
								while (true) {
									try {
										IRole casual = e.getGuild().getRolesByName("Casual").get(0);
										IRole shameOnYou = e.getGuild().getRolesByName("Schäm dich").get(0);
										IVoiceChannel current = e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel();
										IVoiceChannel shameCorner = e.getGuild().getVoiceChannelsByName("Sch$mdicheggli").get(0);
										
										removeCasualRoleAndMoveUser(users.get(0), casual, shameOnYou, shameCorner);
										Thread.sleep(60000);	
										addCasualRole(users.get(0), casual, shameOnYou, current);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							
						};
				
						Thread thread = new Thread(runnable);
						thread.start();
						
					} else ms.sendMessage(e.getChannel(), "No user with the name " + args[1] + " found.");
				} else ms.sendMessage(e.getChannel(), "Invalid usage of $shame\nSyntax: $shame <user>");
			} else ms.sendMessage(e.getChannel(), "You need to be an Administrator of this server to use this command.");
		}
	}
	
	private void removeCasualRoleAndMoveUser(IUser user, IRole casual, IRole shameOnYou, IVoiceChannel shameCorner) {
		user.moveToVoiceChannel(shameCorner);
		user.addRole(shameOnYou);
		user.removeRole(casual);
	}
	
	private void addCasualRole(IUser user, IRole casual, IRole shameOnYou, IVoiceChannel current) {
		user.moveToVoiceChannel(current);
		user.addRole(casual);
		user.removeRole(shameOnYou);
	}
}