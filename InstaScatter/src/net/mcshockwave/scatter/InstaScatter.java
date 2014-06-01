package net.mcshockwave.scatter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InstaScatter extends JavaPlugin {

	public static ChatColor		textcolor	= ChatColor.WHITE;
	public static String		prefix		= "�7[�cInstaScatter�7] " + textcolor;
	public static InstaScatter	ins;

	public void onEnable() {
		ins = this;
		Bukkit.getPluginManager().registerEvents(new DefaultListener(), this);

		saveDefaultConfig();

		textcolor = ChatColor.getByChar(getConfig().getString("text_color_id"));
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix")) + " " + textcolor;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender.isOp()) {

			if (args.length == 0) {
				return false;
			}

			String rad = args[0];

			if (isInteger(rad)) {
				int radius = Integer.parseInt(rad);
				World w = null;
				if (args.length > 1) {
					w = Bukkit.getWorld(args[1]);
				} else if (sender instanceof Player) {
					w = ((Player) sender).getWorld();
				}

				if (w != null) {
					int count = ScatterManager.getScatterPlayers().size();

					Bukkit.broadcastMessage(prefix + "Scattering �c" + count + textcolor + " player"
							+ (count == 1 ? "" : "s") + " in a radius of �a" + radius + textcolor + " in world �e"
							+ w.getName());

					ScatterManager.spreadPlayers(w, radius);
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			sender.sendMessage("�cYou are not op!");
		}

		return true;
	}

	public boolean isInteger(String check) {
		try {
			Integer.parseInt(check);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

}
