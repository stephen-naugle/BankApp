//Created by Stephen Naugle @ Revature

package com.app;

import org.apache.log4j.Logger;

public class App {
	
	static final Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("info level - System start");
		
		System.out.println("Welcome to Rev-Virtual Bank©\n");
		menus.loginPrompt();
		

	}

}
