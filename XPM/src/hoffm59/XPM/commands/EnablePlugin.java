package hoffm59.XPM.commands;

import java.io.File;

import hoffm59.XPM.XPM;
import hoffm59.XPM.Utils.ICommandable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

public class EnablePlugin implements ICommandable 
{
	private final XPM plugin;
	
	private final String mainDirectory = "plugins";

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
				File file = new File(mainDirectory + File.separator + args[0] + ".jar");
				
				if (!file.exists()) 
				{
					sender.sendMessage("§cPlugin could not be found!");
					return true;
				}			
				
				try 
				{
					Plugin loadedPlugin = plugin.getPluginManager().loadPlugin(file);
					plugin.getPluginManager().enablePlugin(loadedPlugin);
				} 
				catch (InvalidPluginException e) 
				{
					e.printStackTrace();
				} 
				catch (InvalidDescriptionException e) 
				{
					e.printStackTrace();
				} 
				catch (UnknownDependencyException e) 
				{
					e.printStackTrace();
				}
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