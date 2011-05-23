package hoffm59.XPM.commands;

import hoffm59.XPM.XPM;
import hoffm59.XPM.Utils.ICommandable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class PluginVersion implements ICommandable 
{
	private final XPM plugin;

	public PluginVersion(XPM plugin) 
	{
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if (plugin.checkAuthority(sender, "xpm.v")) 
		{
			if (args.length == 0) 
			{
				return false;
			}
			
			String pluginName = args[0];

			Plugin targetPlugin = plugin.getPluginManager().getPlugin(pluginName);
			if (targetPlugin == null) 
			{
				sender.sendMessage("§cPlugin could not be found!");
			}
			else
			{
				sender.sendMessage("Version of "
						+ targetPlugin.getDescription().getName() + " is "
						+ targetPlugin.getDescription().getVersion());
			}			
		}	

		return true;
	}
}