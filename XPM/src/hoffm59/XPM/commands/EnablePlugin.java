package hoffm59.XPM.commands;

import hoffm59.XPM.XPM;
import hoffm59.XPM.Utils.ICommandable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class EnablePlugin implements ICommandable 
{
	private final XPM plugin;

	public EnablePlugin(XPM plugin) 
	{
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if (plugin.checkAuthority(sender, "xpm.on")) 
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
			else
			{
				if (targetPlugin.isEnabled()) 
				{
					sender.sendMessage("§c" + targetPlugin.getDescription().getName() + " is already enabled!");
					return true;
				}
				
				plugin.getPluginManager().enablePlugin(targetPlugin);
				
				if (targetPlugin.isEnabled()) 
				{
					sender.sendMessage(targetPlugin.getDescription().getName() + " successfully enabled.");
				}
				else
				{
					sender.sendMessage("§cFailed to enable " + targetPlugin.getDescription().getName());
				}
			}			
		}	

		return true;
	}
}