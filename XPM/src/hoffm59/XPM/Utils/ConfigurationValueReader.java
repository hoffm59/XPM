package hoffm59.XPM.Utils;

import hoffm59.XPM.XPM;

public class ConfigurationValueReader 
{
	public static boolean getBoolean(String path, boolean def, XPM owner)
	{
		return owner.getConfiguration().getBoolean(path, def);
	}
	
	public static String getString(String path, String def, XPM owner)
	{
		return owner.getConfiguration().getString(path, def);
	}
}