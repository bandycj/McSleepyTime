/**
 * 
 */
package org.selurgniman.bukkit.mcsleepytime;

import java.util.LinkedHashMap;
import java.util.List;
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
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerListener;
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
        pm.registerEvent(Event.Type.PLAYER_BED_ENTER, new PlayerBedListener(), Priority.Normal, this);

		
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
		 public void onPlayerBedEnter(PlayerBedEnterEvent event){
			 if (!event.isCancelled()){
				 List<Player> players = event.getPlayer().getWorld().getPlayers();
				 for (Player player:event.getPlayer().getWorld().getPlayers()){
					 if (!player.isSleeping()){
						 if (!playerNearSleepySign(player)){
							 
						 }
					 }
				 }
			 }
		 }
		 private boolean playerNearSleepySign(Player player){
			for (int i=0;i<config.getInt("exemptRadius", 2);i++){
				Block block = player.getLocation().getBlock();
				for (BlockFace blockFace:BlockFace.values()){
					Block neighbor = block.getRelative(blockFace, i);
					if (neighbor.getType() == Material.SIGN){
						Sign sign = (Sign)neighbor;
						for (String line:sign.getLines()){
							if (line.toUpperCase().equals("[SLEEPY TIME]")){
								return true;
							}
						}
					}
				}
			}
			return false;
		}
	}
}
