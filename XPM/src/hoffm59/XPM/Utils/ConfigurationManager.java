package hoffm59.XPM.Utils;

import hoffm59.XPM.XPM;

import java.io.File;

import org.bukkit.util.config.Configuration;

public class ConfigurationManager 
{
	private final XPM plugin;
	
	private final String mainDirectory = "plugins/XPM";
    private final File file = new File(mainDirectory + File.separator + "config.yml");
    
    // configuration nodes
    private final String LIST_PLUGIN_VERSION = "list-plugin-version";
    private final String LIST_COMMAND_DESC = "list-command-description";
    private final String LIST_COMMAND_USAGE = "list-command-usage";
    
    public ConfigurationManager(XPM plugin)
    {
    	this.plugin = plugin;
    }
    
    public void initializeConfiguration()
    {
    	new File(mainDirectory).mkdir();
    	
    	if(!file.exists())
    	{
            try 
            {
                file.createNewFile();
                write(LIST_PLUGIN_VERSION, false);
                write(LIST_COMMAND_DESC, true);
                write(LIST_COMMAND_USAGE, true);
            } 
            catch (Exception ex) 
            {
                ex.printStackTrace();
            }
        } 
    	else 
        {
            //load stuff on statup here
        }
    }
    
    private void write(String root, Object x)  //just so you know, you may want to write a boolean, integer or double to the file as well, therefore u wouldnt write it to the file as "String" you would change it to something else
    {
    	Configuration config = this.plugin.getConfiguration();
        config.setProperty(root, x);
        config.save();
    }
}