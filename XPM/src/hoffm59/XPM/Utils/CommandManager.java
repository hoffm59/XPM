package hoffm59.XPM.Utils;

import hoffm59.XPM.XPM;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandManager 
{
	private XPM plugin;
	private Map<String, Class<? extends ICommandable>> commands = new Hashtable<String, Class<? extends ICommandable>>();
	
	public CommandManager(XPM plugin) {
		this.plugin = plugin;
	}
	
	public void loadFromDescription(PluginDescriptionFile desc, ClassLoader loader) {
		Object object = desc.getCommands();
		
		if (object == null)
			return;
		
		@SuppressWarnings("unchecked")
		Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) object;
		
		for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
			String label = entry.getKey();
			String classname = entry.getValue().get("class").toString();
			
			try 
			{
				Class<?> klass = Class.forName(classname, true, loader);
				Class<? extends ICommandable> commandClass = klass.asSubclass(ICommandable.class);
				addCommand(label, commandClass);				
			} 
			catch (ClassNotFoundException e) 
			{
				System.out.println("Unable to load command class for command /" + label);
			} 
		}
	}
	
	public void addCommand(String label, Class<? extends ICommandable> klass) {
		commands.put(label, klass);
	}
	
	public boolean dispatch(CommandSender sender, Command command, String label, String[] args) {
		if (!commands.containsKey(label))
			return false;
		
		boolean handled = true;
		
		Class<? extends ICommandable> klass = commands.get(label);
		try {
			Constructor<? extends ICommandable> ctor = klass.getConstructor(XPM.class);
			ICommandable c = ctor.newInstance(plugin);
			handled = c.onCommand(sender, command, label, args);
		} catch (NoSuchMethodException e) {
			System.out.println("No constructor that accepts a Plugin.");
		} catch (InstantiationException e) {
			System.out.println("Error while creating a Commandable object.");
		} catch (IllegalAccessException e) {
			System.out.println("Illegal access to the Commandable object constructor.");
		} catch (InvocationTargetException e) {
			System.out.println(e.getCause().getMessage());
		}
		
		return handled;
	}
}