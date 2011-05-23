package hoffm59.XPM.commands;

import hoffm59.XPM.XPM;
import hoffm59.XPM.Utils.ICommandable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class DisablePlugin implements ICommandable 
{
	private final XPM plugin;

	public DisablePlugin(XPM plugin) 
	{
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if (plugin.checkAuthority(sender, "xpm.off")) 
		{
			if (args.length == 0) 
			{
				return false;
			}

			Plugin targetPlugin = plugin.getPluginManager().getPlugin(args[0]);
			if (targetPlugin == null) 
			{
				sender.sendMessage("§cPlugin could not be found!");
			} 
			else if (targetPlugin.getDescription().getName().equals("XPM")) 
			{
				sender.sendMessage("§c" + targetPlugin.getDescription().getName() + " can not be disabled!");
			}
			else 
			{
				if (!targetPlugin.isEnabled()) 
				{
					sender.sendMessage("§c" + targetPlugin.getDescription().getName() + " is already disabled!");
					return true;
				}

				plugin.getPluginManager().disablePlugin(targetPlugin);

				if (!targetPlugin.isEnabled()) 
				{
					sender.sendMessage(targetPlugin.getDescription().getName() + " successfully disabled.");
				} 
				else 
				{
					sender.sendMessage("§cFailed to disable " + targetPlugin.getDescription().getName());
				}
			}
		}

		return true;
	}
}