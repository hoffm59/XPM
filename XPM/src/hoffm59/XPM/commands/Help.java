package hoffm59.XPM.commands;

import hoffm59.XPM.XPM;
import hoffm59.XPM.Utils.ICommandable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Help implements ICommandable {
	
	private XPM plugin;
	
	public Help(XPM plugin) 
	{ 
		this.plugin = plugin;
	}

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
    	if (args.length == 0 || args[0].equals("?") || args[0].equals("help")) 
    	{
    		new GetPluginCommands(this.plugin).getCommands(sender, this.plugin);
		}
    	else
    	{
    		return false;
    	}
    	
        return true;
    }
}