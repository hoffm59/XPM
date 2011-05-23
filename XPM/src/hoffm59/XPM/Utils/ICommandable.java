package hoffm59.XPM.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ICommandable 
{
	boolean onCommand(CommandSender sender, Command command, String label, String[] args);
}