package Agencia;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

public class Interface extends JFrame {
	JFrame frame;
	private JButton btCriarCliente;

	public static JLabel[] Caixas;
	public static JLabel[] Clientes;
	public static JLabel[] LblClientes;
	public static JTextField[] rotulos;
	
	public static JLabel label, cliente, Guiche, labelcliente, Visor;
	
	public static JTextField rotuloCliente;
	
	
	private String nomeCliente, CaixasTrabalhando;
	//private int clientes = 0;
	private int TempoAtendimento;
	
	public List<cliente> listaClientes = new ArrayList();
	List<Caixa> listaCaixas = new ArrayList();

	public static void main(String[] args) {
	}

	public Interface() {
		this.initialize();
	}

	public void initialize() {
		
		this.frame = new JFrame();
		this.frame.setBounds(0, 0, 1000, 600);
		this.frame.getContentPane().setBackground(Color.WHITE);
		this.frame.setDefaultCloseOperation(3);
		this.frame.setResizable(false);
		this.frame.getContentPane().setLayout((LayoutManager) null);
		this.frame.getContentPane().setLayout(null);
				
		
		Interface.Caixas = new JLabel[10];
		Interface.Clientes = new JLabel[100];
		Interface.LblClientes = new JLabel[100];
		Interface.rotulos = new JTextField[100];
		
		
		CaixasTrabalhando = JOptionPane.showInputDialog("Quantos Caixas trabalhando Hoje?");
		Agencia.Ncaixas = Integer.parseInt(CaixasTrabalhando);
		
		//Agencia.Ncaixas= 1;
		
		
		for (int i = 0; i < Agencia.Ncaixas; ++i) {
			label = new JLabel("");
			Guiche = new JLabel();
			Guiche.setText("Guiche "+i);
			label.setIcon(new ImageIcon(Interface.class.getResource("caixa m.png")));
			label.setBounds(20 + 110 * i, 320, 100, 150);
			Guiche.setBounds(30 + 110 * i, 410, 100, 150);
			this.frame.getContentPane().add(label);
			this.frame.getContentPane().add(Guiche);
			Interface.Caixas[i] = label;
		}
		
		
		for (int i = 0; i < Agencia.Ncaixas; ++i) {
			try {
				listaCaixas.add(new Caixa(i,Interface.this));
			} catch (NumberFormatException var6) {
				System.exit(0);
			}
		}

		
		Iterator<Caixa> var9 = listaCaixas.iterator();

		while (var9.hasNext()) {
			Caixa caixaTemp = (Caixa) var9.next();
			caixaTemp.start();
		}
			
		
		Visor=new JLabel("Guiche  - Senha ");
		Visor.setFont(new Font("SansSerif", Font.PLAIN, 20));
		Visor.setBounds(450, 10, 200, 25);
		this.frame.getContentPane().add(Visor);
		
		btCriarCliente = new JButton("Novo Cliente");
		btCriarCliente.setFont (new Font("Arial",Font.BOLD,14));
		btCriarCliente.setBounds(800, 500, 140, 30);
		this.frame.getContentPane().add(btCriarCliente);
				
		JLabel Cenario = new JLabel("");
		Cenario.setIcon(new ImageIcon(Interface.class.getResource("fundo leao.png")));
		Cenario.setBounds(0, -130, 1024, 768);
		this.frame.getContentPane().add(Cenario);
					
		ImageIcon icone = new ImageIcon("icone leao.png");
		setIconImage(icone.getImage());
		
		btCriarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				if (  btCriarCliente == (JButton) e.getSource()) {
					try {								
						nomeCliente = JOptionPane.showInputDialog("Digite o nome do Cliente:");
						TempoAtendimento = Integer.parseInt(JOptionPane.showInputDialog("Qual tempo de atendimento? (segundos)"));
						//Agencia.senha++;
						Agencia.clientes++;
						listaClientes.add(new cliente(nomeCliente,TempoAtendimento*1000,Agencia.clientes, Interface.this));
						JOptionPane.showMessageDialog(null, "Novo Cliente Criado. Sua Senha:"+Agencia.clientes);
						
						cliente = new JLabel("");
						labelcliente = new JLabel("");
						rotuloCliente = new JTextField();
						
						cliente.setIcon(new ImageIcon(Interface.class.getResource("Cliente m.png")));
						cliente.setBounds(30 + 60*Agencia.clientes,25, 96, 150);
						
						labelcliente.setText(nomeCliente+" Ta "+TempoAtendimento);
						labelcliente.setBounds(20 + 70 * Agencia.clientes, 25, 90, 30);
						
					
						Interface.Clientes[Agencia.clientes]=cliente;
						Interface.LblClientes[Agencia.clientes]=labelcliente;
						
						adicionarLabel(cliente);
						adicionarLabel(labelcliente);
						
						cliente ClienteTemp = (cliente) (listaClientes.get(Agencia.clientes -1));
						ClienteTemp.start();
					}catch (NumberFormatException var7) {
						JOptionPane.showMessageDialog((Component) null, "Nenhum Cliente Criado. Cancelado.");
					}
				}
			}
		});
	}
	
	public void adicionarLabel(Component temp) {
        frame.getContentPane().add(temp);
        frame.revalidate();
		frame.repaint();
      
    }
}