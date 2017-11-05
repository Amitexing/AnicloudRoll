package com.vandendaelen.roll;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRoll implements CommandExecutor {

	private Roll plugin;

	public CommandRoll(Roll pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player pSender = (Player)sender;
		int randomInt= 0;
		int bonus = 0;
		int faces = plugin.getConfig().getInt("Faces");
		int radius = plugin.getConfig().getInt("Radius local");
		boolean localRadius = plugin.getConfig().getBoolean("Enable local");
		String result = plugin.getConfig().getString("Result");
		String resultWithBonus = plugin.getConfig().getString("Result with bonus");

		if(args.length != 0) {
			int nbrMax = Integer.parseInt(args[0]);
			faces = nbrMax;
			if(args.length!=1) {
				//Cas du /roll avec malus ou bonus
				bonus = Integer.parseInt(args[1]);
				randomInt = randomRoll(nbrMax, bonus);
			} else {
				// cas du / roll avec un nbr autre que 20
				randomInt = randomRoll(nbrMax);
			}

		}else {
			//Cas classique
			randomInt = randomRoll();
		}
		if(localRadius) {
			if(bonus!=0) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.getLocation().distance(pSender.getLocation()) < radius) {
						player.sendMessage(getSentence(resultWithBonus,pSender.getDisplayName(),faces, bonus, randomInt));
					}
				}
			} else {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.getLocation().distance(pSender.getLocation()) < radius) {
						player.sendMessage(getSentence(result,pSender.getDisplayName(),faces, bonus, randomInt));
					}
				}
			}
		} else {
			if(bonus!=0) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.sendMessage(getSentence(resultWithBonus,pSender.getDisplayName(),faces, bonus, randomInt));

				}
			} else {
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.sendMessage(getSentence(result,pSender.getDisplayName(),faces, bonus, randomInt));
				}
			}
		}



		return true;
	}

	public int randomRoll() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(20)+1;
	}

	public int randomRoll(int nbr) {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(nbr)+1;
	}

	public int randomRoll(int nbr,int bonus) {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(nbr) + bonus+1;
	}

	public String getSentence(String s, String playerUsername, int faces, int bonus, int randomInt) {
		String a, b , c, d;
		a = s.replaceAll("&p",playerUsername);
		b = a.replaceAll("&f", faces+"");
		c = b.replaceAll("&b", bonus+"");
		d = c.replaceAll("&n", randomInt+"");
		return d;
	}
}