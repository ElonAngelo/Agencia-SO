package Agencia;
import java.util.concurrent.Semaphore;


public class Semaforo {
	
	public static Semaphore Caixas = new Semaphore(Agencia.Ncaixas); 		//Inicia com N caixas livres
	public static Semaphore Clientes = new Semaphore(0);    				//Inicia sem clientes
	public static Semaphore Cadeiras = new Semaphore(Agencia.Ncaixas,true); //Cadeiras dos caixas livres
	public static Semaphore mutex = new Semaphore(1);
	public static Semaphore mutexVisorler = new Semaphore(0);
	public static Semaphore mutexVisorEscrever = new Semaphore(1);
	
}
