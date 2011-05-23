package hoffm59.XPM.commands;

import java.util.HashMap;
import java.util.Set;

import hoffm59.XPM.XPM;
import hoffm59.XPM.Utils.ConfigurationValueReader;
import hoffm59.XPM.Utils.ICommandable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class GetPluginCommands implements ICommandable 
{
	private final XPM plugin;
	private boolean LIST_COMMAND_DESC;
	private boolean LIST_COMMAND_USAGE;
	
	public GetPluginCommands(XPM plugin)
	{
		this.plugin = plugin;
		LIST_COMMAND_DESC = ConfigurationValueReader.getBoolean("list-command-description", true, this.plugin);
		LIST_COMMAND_USAGE = ConfigurationValueReader.getBoolean("list-command-usage", true, this.plugin);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if (plugin.checkAuthority(sender, "xpm.commands")) 
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
				getCommands(sender, targetPlugin);
			}			
		}	

		return true;
	}
	
	public void getCommands(CommandSender sender, Plugin targetPlugin)
	{
		sender.sendMessage(targetPlugin.getDescription().getFullName() + " commands:");
		HashMap<?, ?> commands = (HashMap<?, ?>) targetPlugin.getDescription().getCommands();
		
		// command names
		@SuppressWarnings("unchecked")
		Set<String> commandNames = (Set<String>) commands.keySet();
		
		for (String currentCommand : commandNames)
		{
			HashMap<?, ?> commandContent = (HashMap<?, ?>)commands.get(currentCommand);
			
			sender.sendMessage("Name: " + currentCommand);
			
			if (commandContent.containsKey("description") && LIST_COMMAND_DESC) 
			{
				sender.sendMessage("Description: " + (String)commandContent.get("description"));
			}
			if (commandContent.containsKey("usage") && LIST_COMMAND_USAGE) 
			{
				sender.sendMessage("Usage: " + (String)commandContent.get("usage"));
			}					
			
		}
	}
}