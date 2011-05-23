package hoffm59.XPM;

import hoffm59.XPM.Utils.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.Server;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * Plugin for plugin handling
 *
 * @author hoffm59
 */
public class XPM extends JavaPlugin 
{
    private Server _server;
    private Plugin _permissions;
    private PluginDescriptionFile _xpm;
    
    private final CommandManager _cmdMngr = new CommandManager(this);
    private ConfigurationManager _configMngr = new ConfigurationManager(this);
    
    private PermissionHandler _permissionHandler;
    private PluginManager _serverPM;
    
	public void onEnable() 
	{	
		_server = this.getServer();
		_serverPM = _server.getPluginManager();
		_xpm = this.getDescription();
		
		Logger.getLogger("Minecraft").log(Level.INFO, "[" + _xpm.getFullName() + "] loaded!");
		
		this.setupPermissions();
		
		_configMngr.initializeConfiguration();
        _cmdMngr.loadFromDescription(this.getDescription(), this.getClassLoader());
    }	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(this.isEnabled())
        {
        	return _cmdMngr.dispatch(sender, command, label, args);
        }
        return super.onCommand(sender, command, label, args);
    }
	
    public void onDisable() 
    {    	
    	Logger.getLogger("Minecraft").info("[" + _xpm.getFullName() + "] unloaded...");
    }
    
    /**
     * Enables acces on server's PluginManager
     * 
     * @return Reference of the server's PluginManager
     */
    public PluginManager getPluginManager()
    {
    	return this._serverPM;
    }
    
    /**
     * Checks if Permissions plugin is available
     */
    private void setupPermissions() 
    {
        _permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (_permissionHandler == null) 
        {
            if (_permissions != null) 
            {
                _permissionHandler = ((Permissions) _permissions).getHandler();
                Logger.getLogger("Minecraft").info("[" + _xpm.getName() + "] " + _permissions.getDescription().getFullName() + " found...");
            } 
            else 
            {
            	Logger.getLogger("Minecraft").info("[" + _xpm.getName() + "] " + "Permission system not detected, defaulting to OP");
            }
        }
    }

    /**
     * Checks the players permissions against the Permissions plugin if available
     * 
     * @param sender - Who sended the command
     * @param permission - String identifying the requiered permission
     * @return TRUE or FALSE wether the sender is allowed to perform the command or not
     */
    public boolean checkAuthority(CommandSender sender, String permission) {
        if (_permissions != null && _permissionHandler != null && _permissions.isEnabled()) 
        {
            if (sender instanceof Player) 
            {
                if (_permissionHandler.has((Player) sender, permission)) 
                {
                    return true;
                } 
                else 
                {
                    return false;
                }
            } 
            else if (sender instanceof ConsoleCommandSender) 
            {
                return true;
            }
            return false;
        } 
        else 
        {
            if (sender instanceof Player) 
            {
//                Player person = (Player)sender;
//                if(person.isOp())
                    return true;
            }
            else if(sender instanceof ConsoleCommandSender)
            {
                return true;
            }
            return false;
        }
    }
}