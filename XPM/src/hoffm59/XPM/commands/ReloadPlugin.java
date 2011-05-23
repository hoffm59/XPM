package hoffm59.XPM.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import hoffm59.XPM.XPM;
import hoffm59.XPM.Utils.ICommandable;

public class ReloadPlugin implements ICommandable 
{
	private final XPM plugin;

	public ReloadPlugin(XPM plugin) 
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if (plugin.checkAuthority(sender, "xpm.reload")) 
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
				sender.sendMessage("§c" + targetPlugin.getDescription().getName() + " can not be reloaded ingame!");
				sender.sendMessage("§cYou should perform a server reload!");
			}
			else 
			{
				if (!targetPlugin.isEnabled()) 
				{
					sender.sendMessage("§c" + targetPlugin.getDescription().getName() + " is already disabled!");
					sender.sendMessage("§cYou should perform §f/xpmon " + targetPlugin.getDescription().getName());
					return true;
				}

				plugin.getPluginManager().disablePlugin(targetPlugin);
				if (!targetPlugin.isEnabled()) 
				{
					plugin.getPluginManager().enablePlugin(targetPlugin);
					if (targetPlugin.isEnabled()) 
					{
						sender.sendMessage(targetPlugin.getDescription().getName() + "successfully reloaded.");
					}
					else
					{
						sender.sendMessage("§cFailed to re-enable " + targetPlugin.getDescription().getName());
					}
				} 
				else 
				{
					sender.sendMessage("§cFailed to disable " + targetPlugin.getDescription().getName());
				}
			}
			
			return true;
		}
		else
		{
			sender.sendMessage("§cYou do not have the permission to do that!");
			return true;
		}
	}
}