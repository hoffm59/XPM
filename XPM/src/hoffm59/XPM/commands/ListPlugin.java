package hoffm59.XPM.commands;

import hoffm59.XPM.XPM;
import hoffm59.XPM.Utils.ConfigurationValueReader;
import hoffm59.XPM.Utils.ICommandable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ListPlugin implements ICommandable 
{
	private final XPM plugin;
	private boolean LIST_PLUGIN_VERSION;

	public ListPlugin(XPM plugin) 
	{
		this.plugin = plugin;
		LIST_PLUGIN_VERSION = ConfigurationValueReader.getBoolean("list-plugin-version", false, this.plugin);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if (args.length != 0) 
		{
			String option = args[0];

			if (args[0].equals("all")) 
			{
				if (plugin.checkAuthority(sender, "xpmlist.all")) 
				{
					getAllPlugins(sender);
				}
			} 
			else if (option.equals("on")) 
			{
				if (plugin.checkAuthority(sender, "xpmlist.on")) 
				{
					getEnabledPlugins(sender);
				}
			} 
			else if (option.equals("off")) 
			{
				if (plugin.checkAuthority(sender, "xpmlist.off")) 
				{
					getDisabledPlugins(sender);
				}
			}
			
			return true;
			
		} 
		else 
		{
			return false;
		}		
	}

	private void getAllPlugins(CommandSender sender) 
	{
		Plugin[] availablePlugins = plugin.getPluginManager().getPlugins();
		sender.sendMessage("All Plugins (enabled + disabled):");

		for (int i = 0; i < availablePlugins.length; i++) 
		{
			Plugin currentPlugin = availablePlugins[i];
			sender.sendMessage(LIST_PLUGIN_VERSION ? currentPlugin.getDescription().getFullName() : currentPlugin.getDescription().getName());
		}
	}

	private void getEnabledPlugins(CommandSender sender) 
	{
		Plugin[] availablePlugins = plugin.getPluginManager().getPlugins();
		sender.sendMessage("All enabled Plugins:");

		for (int i = 0; i < availablePlugins.length; i++) 
		{
			Plugin currentPlugin = availablePlugins[i];
			if (currentPlugin.isEnabled()) 
			{
				sender.sendMessage(LIST_PLUGIN_VERSION ? currentPlugin.getDescription().getFullName() : currentPlugin.getDescription().getName());
			}
		}
	}

	private void getDisabledPlugins(CommandSender sender) 
	{
		Plugin[] availablePlugins = plugin.getPluginManager().getPlugins();
		sender.sendMessage("All disabled Plugins:");

		for (int i = 0; i < availablePlugins.length; i++) 
		{
			Plugin currentPlugin = availablePlugins[i];
			if (!currentPlugin.isEnabled()) 
			{
				sender.sendMessage(LIST_PLUGIN_VERSION ? currentPlugin.getDescription().getFullName() : currentPlugin.getDescription().getName());
			}
		}
	}
}