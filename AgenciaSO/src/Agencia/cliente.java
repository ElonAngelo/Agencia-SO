package Agencia;

import static Agencia.Interface.Clientes;
import static Agencia.Interface.LblClientes;
import java.time.LocalDateTime;
import javax.swing.ImageIcon;

public class cliente extends Thread {
	private String idNome;
	public int senhaCliente;
	public long Ta;
	public int Atendido = 0;
	private int Temp;
	private int atual, segundos;
	
	public Interface Gui;
	LocalDateTime datetime = LocalDateTime.now();
	 
	
	public cliente(String id, long TempoA, int senha, Interface Janela) {
		this.idNome = id;
		this.Ta = TempoA;
		this.senhaCliente = senha;
		this.Gui = Janela;
	}

	public void run() {
		while (Atendido==0) {
			try {
				
				
				//Cliente Entra na agencia e recebe sua senha de atendimento
				System.out.println("TCl		Cliente " + idNome + " " + "Pega senha de numero: "+  senhaCliente);
				Semaforo.Clientes.release();
				
				
				//Se todos os caixas estiverem ocupados, o Cliente dorme no semaforo Caixas, esperando o proximo livre.
				if (Semaforo.Cadeiras.availablePermits() == 0) {
					System.out.println("TCl		Nenhum Caixa livre. Cliente " + senhaCliente + " " + "  vai dormir ");
					Clientes[senhaCliente].setIcon(new ImageIcon(Interface.class.getResource("Cliente dormindo m.png")));
				}
				
				Semaforo.Cadeiras.acquire();		//Tem cadeira livre e senta pra começar o atendimento com um caixa
				Semaforo.Caixas.acquire();			
				//System.out.println("DEBUG		Cliente " + senhaCliente+" Dormiu aqui");
				
				System.out.println("TCl		Caixa Livre. Cliente "+idNome+", senha: " + senhaCliente + " " + " acordou pra atendimento ");
				Clientes[senhaCliente].setIcon(new ImageIcon(Interface.class.getResource("Cliente m.png")));
				sleep(1000);
				
				Semaforo.mutexVisorler.acquire();
				Temp = Agencia.CaixaChamando;
				clienteVaiCaixa();
				Semaforo.mutexVisorEscrever.release();
				
				clienteEmAtendimento();
				
								
				System.out.println("TCl		Cliente " + idNome + " " + "foi atendido");
				clienteVaiEmbora();
				
				//Semaforo.Caixas.release();
				Semaforo.Cadeiras.release();	
				Atendido = 1;

				
			} catch (InterruptedException var5) {
				var5.printStackTrace();
			}
		}
		System.out.println("TCl		Cliente " + idNome + " " + "finalizou Thread");
	}
	public void clienteVaiCaixa() {
		
		System.out.println("Gui		Cliente " + senhaCliente + " dirigindo-se ao caixa "+Temp);
		for (int i=1; i<20;i++) {
			Clientes[senhaCliente].setBounds(20 + 110*Temp, 10*i, 100,150);
			LblClientes[senhaCliente].setBounds(40 + 110 *Temp,10*i-2, 90, 30);
			Clientes[senhaCliente].repaint();
			Clientes[senhaCliente].revalidate();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void clienteVaiEmbora() {
		Clientes[senhaCliente].setIcon(new ImageIcon(Interface.class.getResource("Cliente atendido m.png")));
		for (int i=1; i<12;i++) {
			Clientes[senhaCliente].setBounds(30+(senhaCliente*10) + 60*i, 190+(senhaCliente*10), 96,150);
			LblClientes[senhaCliente].setBounds(20+(senhaCliente*10) + 70 *i,190+(senhaCliente*10), 90, 30);
			Clientes[senhaCliente].repaint();
			Clientes[senhaCliente].revalidate();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void clienteEmAtendimento() throws InterruptedException {
		long TempoRestante = (Gui.listaClientes.get(senhaCliente-1).Ta/1000);
		
		while(TempoRestante>=0) {
			LblClientes[senhaCliente].setText(Gui.listaClientes.get(senhaCliente-1).idNome +" Ta "+TempoRestante+"s");
			Clientes[senhaCliente].repaint();
			Clientes[senhaCliente].revalidate();
			TempoRestante--;
			Gui.listaClientes.get(senhaCliente-1).Ta = TempoRestante*1000;
						 
		    atual  = LocalDateTime.now().getSecond();
		    	    
		    while(true) {
			    segundos = LocalDateTime.now().getSecond();
			    //System.out.println("DEBUG		Atual= "+atual+" Segundos= "+segundos);
		    	if (segundos - atual >= 1 )break;
		    	if (atual == 59 && segundos == 0 )break;
		    
		    }
		}
	}
}