package Agencia;

import static Agencia.Interface.Caixas;
import static Agencia.Interface.Visor;

import java.time.LocalDateTime;

import javax.swing.ImageIcon;


public class Caixa extends Thread {
		private int id;
		public int te;	
		private int ClienteEmAtendimento;
		public Interface Gui;
		
		private long atual, Comp, muda =1;
		LocalDateTime datetime = LocalDateTime.now();
		
		public Caixa(int id, Interface Janela) {
			this.id = id;
			this.Gui = Janela;
		}

		public void run() {
			
			while (true) {
				
				try {
					Caixa.sleep(1500);
					if (Semaforo.Clientes.availablePermits() == 0) {
						System.out.println("TCa	Nenhum Cliente aguardando. Caixa " + id + " " + "  vai dormir ");
						Caixas[id].setIcon(new ImageIcon(Interface.class.getResource("caixa dormindo m.png")));	
						
					}
						Semaforo.Clientes.acquire();
						
															//Dorme o Caixa ate aparecer um cliente.
						Semaforo.mutex.acquire();		 //Quando o caixa acorda, só ele pode atualizar a senha a ser chamada
						Agencia.senha++;						
						ClienteEmAtendimento = Agencia.senha;
						Semaforo.mutex.release();
						
						System.out.println("TCa	Caixa " + id + " " + " Acordou ");
						System.out.println("TCa	Caixa " + id + " " + " Chama senha: "+ ClienteEmAtendimento);
						Caixas[id].setIcon(new ImageIcon(Interface.class.getResource("caixa m.png")));
						
						Semaforo.mutexVisorEscrever.acquire();
						Agencia.CaixaChamando = id;
						
						Visor.setText("Guiche "+Agencia.CaixaChamando+"  -  Senha "+Agencia.senha);
						Visor.repaint();
						Visor.revalidate();
						
						Semaforo.mutexVisorler.release();
						
						int print = 0;
						atual  = LocalDateTime.now().getNano();
						while(Gui.listaClientes.get(ClienteEmAtendimento-1).Ta>0 ) {
							
							if (print==0) {
								System.out.println("TCa	Caixa " + id + " " + " Atendendo senha: "+ClienteEmAtendimento);
								print++;
							}
							
							
				    	    
							Comp = LocalDateTime.now().getNano();
							
							//System.out.println("DEBUG		Comp - Atual "+ (Comp - atual));
						    if (Math.abs(Comp - atual) > 250000000 ) {
						    	//System.out.println("DEBUG		Atual= "+atual+" Comp= "+Comp);
						    	if (muda==1) {
						    		Caixas[id].setIcon(new ImageIcon(Interface.class.getResource("caixa m 2.png")));
						    		muda = 2;
						    	}else if(muda ==2) {
						    		Caixas[id].setIcon(new ImageIcon(Interface.class.getResource("caixa m.png")));
						    		muda = 1;
						    	}
						    	atual  = LocalDateTime.now().getNano();
						    }
						    
						}
						
						System.out.println("TCa	Caixa " + id + " " + " Atendeu senha: "+ClienteEmAtendimento);
						
						    	  				// Up em caixas libera esse caixa para novo atendimento
						//Semaforo.Clientes.acquire();													// Acordando clientes na fila de espera se houver
						sleep(1000);
						Semaforo.Caixas.release();
						//System.out.println("TCa	semaforo Clientes tem " + Semaforo.Clientes.availablePermits() + " Perm ");
					
				}catch (InterruptedException var5) {
					var5.printStackTrace();
				}
			}
		}
}
