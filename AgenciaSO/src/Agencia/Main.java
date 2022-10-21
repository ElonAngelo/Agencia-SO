package Agencia;

import java.awt.EventQueue;
import java.io.IOException;



class Main extends Thread { 
	
	public static void main(String[] args) throws IOException{

		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}
}


