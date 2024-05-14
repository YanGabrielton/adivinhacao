package me.gamerandom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

//alunos: Yan Gabrielton, Alana leitiely, ivan oliveira 
public class Core {

	public static void main(String[] args) {
		System.out.println("Iniciando o Aplicativo.");
		JFrame frame = new JFrame();
		frame.setTitle("REDHAT");
		frame.setSize(720, 480); // tamanho da janela
		frame.setResizable(false); // proibir que a janela seja modificada no seu tamanho
		frame.setLocationRelativeTo(null); // Setar a janela para o centro da tela.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cancelar a tarefa do JFrame.

		JPanel panel = new JPanel(new GridLayout(3, 0)); // criar um gridlayout, dividir a tela em 3 divisões sem colunas
		panel.setBackground(Color.LIGHT_GRAY);
		
		// Adicionando a pergunta da tela.
		JLabel titlePage = new JLabel();
		titlePage.setText("Qual será o próximo número ?");
		titlePage.setHorizontalAlignment(SwingConstants.CENTER);
		titlePage.setFont(getFont("PressStart", 20)); // setar a fonte do texto
		titlePage.setOpaque(true); // deixando a cor solida
		titlePage.setBackground(Color.BLACK); // setando a cor do background
		titlePage.setForeground(Color.WHITE); // setando a cor do texto
		panel.add(titlePage);
		
		
		// Adicionando o texto aonde vai escrever o numero
		JPanel panelText = new JPanel();
		panelText.setOpaque(true);
		panelText.setBackground(Color.BLACK);
		panelText.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelText.setAlignmentY(Component.CENTER_ALIGNMENT); // alinhar o painel do texto no centro, para deixar o texto no centro
		JTextPane texto = new JTextPane();
		texto.setOpaque(true);
		texto.setBackground(Color.WHITE);
		texto.setForeground(Color.RED.darker());
		texto.setFont(getFont("PressStart", 40));
		texto.addKeyListener(new KeyListener() { // adicionar evento para as teclas
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_ENTER) return; // se ele não apertar enter, retorna!
				runAction(texto); //chamar o metodo runAction
			}
			
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		panelText.add(texto);
		panel.add(panelText);
		
		// Adicionando o botão de gerar o numero aleatorio
		JButton button = new JButton();
		button.setText("ROLL");
		button.setFont(getFont("PressStart", 30));
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.BLACK);
		button.addMouseListener(new MouseListener() { // adicionando o evento de passar o mouse no botao
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(Color.GRAY);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.WHITE);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		button.addActionListener(new ActionListener() { // adicionando evento de acionar o botao
			@Override
			public void actionPerformed(ActionEvent e) {
				runAction(texto); // chamar o metodo runAction
			}
		});
		panel.add(button);
		
		frame.add(panel); // adicionar o painel á janela.
		frame.setVisible(true); // Deixar a janela criada visível.
		System.out.println("Aplicativo Iniciado.");
	}

	private static void runAction(JTextPane texto) {
		int randomNumber = (int) Math.ceil(Math.random()* 5); // (new Random().nextInt(5))+1
		String text = texto.getText();
		if (text.isEmpty()) {
			// Criando caixa de dialogo
			JOptionPane.showMessageDialog(null, "Você precisa escrever algum número no campo de texto!", "Erro", JOptionPane.ERROR_MESSAGE, null);
			texto.grabFocus();
			return;
		} else if(!isInteger(text)) {
			// Criando caixa de dialogo
			JOptionPane.showMessageDialog(null, "Você precisa escrever apenas números!", "Erro", JOptionPane.ERROR_MESSAGE, null);
			texto.setText(new String());
			texto.grabFocus();
			return;
		}
		
		int number = Integer.valueOf(text);
		if (number == randomNumber) {
			// Criando caixa de dialogo
			JOptionPane.showMessageDialog(null, "Você acertou o número parabéns! (" + randomNumber + ")", "Você Acertou!", JOptionPane.INFORMATION_MESSAGE, null);
			texto.setText(new String());
		}else {
			// Criando caixa de dialogo
			JOptionPane.showMessageDialog(null, "O Número era " + randomNumber + " e você errou :(", "Erro", JOptionPane.ERROR_MESSAGE, null);
			texto.setText(new String());
		}
		texto.grabFocus(); // colocar o foco na caixa de texto
	}
	
	// verificar se o texto é do tipo inteiro
	private static boolean isInteger(String txt) {
		try {
			Integer.valueOf(txt);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// Pegar uma fonte baixada atraves de um nome
	private static Font getFont(String name, int size) {
		try {
			InputStream is = Core.class.getResourceAsStream("fonts/"+name + ".ttf"); //ler a classe dentro do package e transformar em um file
			
			byte[] buffer = new byte[4096]; 
			int bytesRead;
			File file = new File("fonte.ttl"); //transformando o file
			try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			    while ((bytesRead = is.read(buffer)) != -1) {
			        fileOutputStream.write(buffer, 0, bytesRead);
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
			Font font = Font.createFont(Font.TRUETYPE_FONT, file); // criando a fonte com o arquivo
			Font resize = font.deriveFont(0, size);
			return resize;
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}