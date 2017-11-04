package com.vandendaelen.roll;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;


public class Roll extends JavaPlugin {

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onEnable() {
		System.out.println("Bravo ! Tu es l'heureux proprietaire d'un plugin incroyable, felicitation ! By LotuxPunk");
		this.getCommand("roll").setExecutor(new CommandRoll(this));
		createConfig();
	}

	private void createConfig() {
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists()) {
				getLogger().info("Config.yml not found, creating!");
				saveDefaultConfig();
			} else {
				getLogger().info("Config.yml found, loading!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
