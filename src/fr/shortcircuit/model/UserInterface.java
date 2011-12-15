package fr.shortcircuit.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.shortcircuit.db.DbManager;
import fr.shortcircuit.utils.Parser;

public class UserInterface {
	public static String buffer;
	private boolean isGood = true;
	private static Parser parse;

	public UserInterface(DbManager db) {
		parse = new Parser(this, db);
		while  (isGood) {
			DoPrompt();
			try {
				DoRead();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void DoPrompt() {
		System.out.print("prompt-->");
	}
	public static void DoRead() throws IOException {
		BufferedReader entreeClavier = new BufferedReader(new InputStreamReader(System.in));
		buffer = entreeClavier.readLine();
		parse.DoParse(buffer);
	}
}
