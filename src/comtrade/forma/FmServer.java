package comtrade.forma;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import comtrade.server.NitServer;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FmServer extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton btnStartServer;
	private JPanel panel;
	private JTextArea taServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FmServer frame = new FmServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FmServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 441, 290);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblPokreniServer = new JLabel("Pokreni server");
		lblPokreniServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPokreniServer.setBounds(101, 11, 199, 14);
		panel.add(lblPokreniServer);
		
		btnStartServer = new JButton("Start");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NitServer server= new NitServer();
				server.start();
				taServer.setText("**   Server je startovan.  **");
				btnStartServer.setVisible(false);
				
			}
		});
		btnStartServer.setBounds(156, 36, 89, 23);
		panel.add(btnStartServer);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 83, 421, 196);
		panel.add(scrollPane);
		
		taServer = new JTextArea();
		scrollPane.setViewportView(taServer);
	}

	public void postaviText(String string) {
		taServer.append(string);
		
	}
}
