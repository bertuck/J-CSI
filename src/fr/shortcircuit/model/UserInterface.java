package fr.shortcircuit.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface {
	
	private static String	buffer;
	private boolean isGood = true;

	public UserInterface() {
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
		System.out.println(buffer);
	}
}
