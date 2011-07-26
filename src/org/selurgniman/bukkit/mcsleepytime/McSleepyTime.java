/**
 * 
 */
package org.selurgniman.bukkit.mcsleepytime;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 * @author <a href="mailto:e83800@wnco.com">Chris Bandy</a>
 * Created on: Jul 26, 2011
 */
public class McSleepyTime extends JavaPlugin {
	private final Logger log = Logger.getLogger("Minecraft");
	private static final LinkedHashMap<String, String> CONFIG_DEFAULTS = new LinkedHashMap<String, String>();
	private JavaPlugin self = null;
	private Configuration config = null;
	
	static {
		CONFIG_DEFAULTS.put("exemptRadius", "2");
		CONFIG_DEFAULTS.put("prefix", ChatColor.RED + "McSleepyTime: " + ChatColor.WHITE);
	}
	
	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onDisable()
	 */
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(config.getString("prefix")
				+ config.getString("prefix")
				+ pdfFile.getName()
				+ " version "
				+ pdfFile.getVersion()
				+ " is disabled!");
	}

	/* (non-Javadoc)
	 * @see org.bukkit.plugin.Plugin#onEnable()
	 */
	@Override
	public void onEnable() {
		loadConfig();
		
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_MOVE, new PlayerBedListener(), Priority.Normal, this);
        		
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(config.getString("prefix")
				+ config.getString("prefix")
				+ pdfFile.getName()
				+ " version "
				+ pdfFile.getVersion()
				+ " is enabled!");
	}
	
	private void loadConfig() {
		config = this.getConfiguration();
		for (Entry<String, String> entry : CONFIG_DEFAULTS.entrySet()) {
			if (config.getProperty(entry.getKey()) == null) {
				config.setProperty(entry.getKey(), entry.getValue());
			}
		}
		config.save();
	}
	
	
	private class PlayerBedListener extends PlayerListener {
		 public void onPlayerMove(PlayerMoveEvent event){
			 if (!event.isCancelled()){
				 for (Player player:event.getPlayer().getWorld().getPlayers()){
					 if (!player.isSleeping()){
						 if (!playerNearSleepySign(player)){
							 
						 }
					 }
				 }
			 }
		 }
	}
}
