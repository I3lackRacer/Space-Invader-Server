package main;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Toolkit;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 895656956971L;
	public static JPanel contentPane;
	public static JTextField input;
	public Handler handler = new Handler();
	public static JTextArea console, playerlist;
	public static int HEIGHT = 1080, WIDTH = 1920;
	public static JCheckBox logins;
	public static JButton enter;
	public static boolean stopServer = false;
	public static JFrame f;
	public static JTextArea chat;
	public static KeyInput kInput = new KeyInput();
	public static Thread t1;
	public static MultiplayerServer mps;
	public static Game game;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 f = new MainFrame();
					f.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setTitle("Space Invader Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		addKeyListener(kInput);
		requestFocus();
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setMinimumSize(new Dimension(260, 150));
		setLocationRelativeTo(null);
		JPanel panel_left = new JPanel();
		panel_left.setBackground(Color.WHITE);
		panel_left.setBounds(10, 42, 197, 183);
		contentPane.add(panel_left);
		panel_left.setLayout(null);

		JScrollPane scrollChat = new JScrollPane();
		scrollChat.setBounds(56465647, 11, 175, 161);
		JLabel l = new JLabel("Console");
		l.setFont(new Font("Arial Black", Font.PLAIN, 12));
		scrollChat.setColumnHeaderView(l);
		panel_left.add(scrollChat);
		console = new JTextArea();
		console.setForeground(Color.BLACK);
		console.append("");
		scrollChat.setViewportView(console);
		console.setBackground(Color.WHITE);

		console.setEditable(false);
		input = new JTextField();
		input.setFont(new Font("Arial Black", Font.PLAIN, 12));
		input.setBounds(5, 236, 202, 20);
		input.addKeyListener(kInput);
		contentPane.setLayout(null);
		contentPane.add(input);
		input.setColumns(10);
		
		JPanel panel_right_down = new JPanel();
		panel_right_down.setLayout(null);
		panel_right_down.setFocusable(false);
		panel_right_down.setBackground(Color.WHITE);
		panel_right_down.setBounds(217, 135, 207, 90);
		contentPane.add(panel_right_down);
		
		JScrollPane scrollPaneChat = new JScrollPane();
		scrollPaneChat.setFocusable(false);
		scrollPaneChat.setBackground(Color.WHITE);
		scrollPaneChat.setBounds(10, 11, 185, 68);
		panel_right_down.add(scrollPaneChat);
		
		JLabel lblChar = new JLabel("Chat");
		lblChar.setFont(new Font("Arial Black", Font.PLAIN, 12));
		scrollPaneChat.setColumnHeaderView(lblChar);
		
		chat = new JTextArea();
		chat.setForeground(Color.BLACK);
		chat.setFont(new Font("Arial Black", Font.PLAIN, 12));
		chat.setFocusable(false);
		chat.setEditable(false);
		chat.setBackground(Color.WHITE);
		scrollPaneChat.setViewportView(chat);

		JPanel panel_right_up = new JPanel();
		panel_right_up.setBackground(Color.WHITE);
		panel_right_up.setBounds(217, 42, 207, 90);
		contentPane.add(panel_right_up);
		panel_right_up.setLayout(null);

		JScrollPane scrollPlayer = new JScrollPane();
		scrollPlayer.setBounds(10, 11, 185, 68);
		panel_right_up.add(scrollPlayer);
		playerlist = new JTextArea();
		scrollPlayer.setViewportView(playerlist);
		console.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerlist.setFont(new Font("Arial Black", Font.PLAIN, 12));

		enter = new JButton("SEND");
		enter.setFont(new Font("Arial Black", Font.PLAIN, 12));
		enter.setFocusable(false);
		enter.setBounds(217, 236, 90, 20);
		enter.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					if (input.getText().charAt(0) != '/') {
						chat(input.getText());
					} else {
						command(input.getText());
					}
					input.setText("");
				} catch (Exception i) {
					
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		contentPane.add(enter);

		logins = new JCheckBox("new Logins");
		logins.setFont(new Font("Arial Black", Font.PLAIN, 12));
		logins.setFocusable(false);
		logins.setSelected(true);
		logins.setBackground(Color.GRAY);
		logins.setForeground(Color.BLACK);
		logins.setBounds(312, 235, 112, 23);
		contentPane.add(logins);
		logins.setFocusable(false);
		console.setFocusable(false);
		panel_left.setFocusable(false);
		panel_right_up.setFocusable(false);
		playerlist.setFocusable(false);
		scrollChat.setFocusable(false);
		scrollPlayer.setFocusable(false);
		scrollChat.setBackground(Color.WHITE);
		scrollPlayer.setBackground(Color.WHITE);
		
		JLabel lblPlayer = new JLabel("Player");
		lblPlayer.setFont(new Font("Arial Black", Font.PLAIN, 12));
		scrollPlayer.setColumnHeaderView(lblPlayer);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{scrollChat, scrollPlayer, enter, logins, input, playerlist, panel_right_up, panel_left, console}));
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				int x = (int) e.getComponent().getSize().getWidth();
				int y = (int) e.getComponent().getSize().getHeight();
				panel_left.setBounds(10, 40, (x / 2 - 20), (y - 120));
				panel_right_up.setBounds(x / 2 + 10, 40, (x / 2 - 40), ((y - 120)/2-10));
				panel_right_down.setBounds(x / 2 + 10, panel_right_up.getHeight()+panel_right_up.getY()+20, (x / 2 - 40), (y - 120)/2-10);
				scrollChat.setBounds(10, 10, panel_left.getWidth() - 20, panel_left.getHeight() - 20);
				scrollPaneChat.setBounds(10, 10, panel_right_down.getWidth() - 20, panel_right_down.getHeight() - 20);
				scrollPlayer.setBounds(10, 10, panel_right_up.getWidth() - 20, panel_right_up.getHeight() - 20);
				input.setBounds(panel_left.getX(), y - 70, panel_left.getWidth(), 20);
				enter.setBounds(input.getX() + input.getWidth() + 20, y - 70, 90, 20);
				if (x > 444) {
					logins.setBounds(enter.getWidth() + enter.getX() + 20, y - 70, x / 2 - 40, 20);
				} else {
					logins.setBounds(panel_left.getX(), 10, x / 2 - 40, 20);
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	public static void listUpdate(String text) {
		console.append(text + "\n");
		if (console.getText().length() > 1) console.setCaretPosition(console.getText().length() - 1);
	}
	
	public static void playerUpdate(String p) {
		playerlist.append(p + "\n");
		if (playerlist.getText().length() > 1) playerlist.setCaretPosition(playerlist.getText().length() - 1);
	}
	
	public static void chatUpdate(String msg) {
		chat.append(msg + "\n");
		if (chat.getText().length() > 1) chat.setCaretPosition(chat.getText().length() - 1);
	}

	public static void command(String cmd) {
		in(cmd);
		String[] all = cmd.split(" ");
		switch ((all[0].substring(1, cmd.length())).toLowerCase()) {

		case "quit":
			cmd("Shutting down");
			if(t1 == null) {
				 System.exit(0);
			}
			info("Der Server läuft noch");
			break;

		case "stop":
			if(t1 == null) {
				info("Es läuft kein Server");
			}else {
				stopServer = true;
				for(Verbindung e : mps.al) {
					try {
						e.socket.close();
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				mps = null;
//				t1.stop();
				for(int i = 0; i < MultiplayerServer.al.size(); i++) {
					MultiplayerServer.al.get(i).stop();
				}
				mps.stop();
				info("Der Server ist DOWN");
			}
			break;
			
		case "start":
			mps = new MultiplayerServer();
			game = new Game();
			break;

		case "kickall":
			cmd("Alle Spieler wurden gekickt");
			break;

		case "test":
			cmd("Funktioniert");
			break;
		
		case "addplayer":
			System.out.println("TRIGGED");
			System.out.println(all[1].length() + "---");
			if(all[1] == null) break;
			playerUpdate(all[1]);
			cmd("Imaginärer Spieler " + all[1] + " wurde hinzugefügt.");
		break;

		case "cake":
			for (int i = 0; i < 1000; i++) {
				cmd("[???] THE CAKE IS A LIE");
			}
		break;
		
		case "9gag":
			if(f.getTitle() != "9GAG Invader Server") {
			listUpdate("[Racer4308] Hello fellow 9gag-user.\n"
					+  "[Racer4308] NIGHTMODE MASTERRACE");
			f.setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\workbench\\Space Invader Server\\src\\main\\gag.png"));
			f.getContentPane().setBackground(Color.BLACK);
			f.setTitle("9GAG Invader Server");
			logins.setBackground(Color.BLACK);
			logins.setForeground(Color.WHITE);
			f.setVisible(true);
			}else {
				f.getContentPane().setBackground(Color.GRAY);
				f.setIconImage(null);
				logins.setBackground(Color.GRAY);
				logins.setForeground(Color.BLACK);
				f.setTitle("Space Invader Server");
				f.setVisible(true);
			}
		break;
		
		case "glados":
			listUpdate(
   "[GladOS] Oh...\n"
 + "[GladOS] It's you.\n"
 + "[GladOS] It's been a long time.\n"
 + "[GladOS] How have you been?\n"
 + "[GladOS] I've been really busy being dead.\n"
 + "[GladOS] You know, after you MURDERED ME.\n"
 + "[GladOS] Okay.\n"
 + "[GladOS] Look.\n"
 + "[GladOS] We both said a lot of things that you're going to regret.\n"
 + "[GladOS] But I think we can put our differences behind us.\n"
 + "[GladOS] For science.\n"
 + "[GladOS] You monster.\n"
 + "[GladOS] I will say, though, that since you went to all the trouble of waking me up, you must really, really love to test.\n"
 + "[GladOS] I love it too.\n"
 + "[GladOS] There's just one small thing we need to take care of first."
);
		break;
		
		case "42":
			cmd("Don't forget your towel.");
			break;

		case "clear":
			console.setText("");
			break;
			
		case "help":
			cmd("Ein '/' kündigt einen Befehl an.");
			cmd("Groß und kleinschreibung wird nicht beachtet.");
			cmd("Mögliche Befehle:");
			cmd("/clear");
			cmd("/start");
			cmd("/test");
			cmd("/kickall");
			cmd("/quit");
			cmd("/eastereggs");
			cmd("/addplayer");
			cmd("/credit");
			break;

		case "credit":
			listUpdate("[Tim Bang] Ich möchte mich bei allen bedanken, die mir in meinen Leben weitergeholfen haben."
				   + "\n[Tim Bang] Danke geht raus an:"
				   + "\n[Tim Bang] Alexsanda"
				   + "\n[Tim Bang] Jens"
				   + "\n[Tim Bang] Jonas"
				   + "\n[Tim Bang] Moritz"
				   + "\n[Tim Bang] Nick"
				   + "\n[Tim Bang] Silas"
				   + "\n[Tim Bang] Jannik"
				   + "\n[Tim Bang] Dieter"
				   + "\n[Tim Bang] Jutta"
				   + "\n[Tim Bang] Nina"
				   + "\n[Tim Bang] Michel"
				   + "\n[Tim Bang] Cookie"
				   + "\n[Tim Bang] Das gesammt Team Memorylife"
				   + "\n[Tim Bang] Jeden der dieses Spiel spielt"
				   + "\n[Tim Bang] ...");
			break;
		case "danger":
			listUpdate("[Old Man] It's dangerous to go alone.\n"
					+  "[Old Man] Take this.");
			break;
		
		case "war":
			listUpdate("[Narrator] War.\n"
					+  "[Narrator] War never changes.");
			break;
		
		case "finish":
			listUpdate("[Announcer] FINISH HIM!!!");
			break;
		
		case "puzzle":
			listUpdate("[Layton] Every puzzel has an answer.");
			break;
		
		case "listen":
			listUpdate("[Na'vi] Hey!\n"
					+  "[Na'vi] Listen!");
			break;
		
		case "arrow":
			listUpdate("[Whiterun Guard] I used to be an adventurer like you.\n"
					+  "[Whiterun Guard] Then I took a arrow to the knee.");
			break;
		
		case "praise":
			listUpdate("Praise the sun!");
			break;
			
		case "eastereggs":	
			cmd("Alle Easter Eggs:");
			cmd("/GladOS");
			cmd("/42");
			cmd("/arrow");
			cmd("/cake");
			cmd("/9gag");
			cmd("/war");
			cmd("/danger");
			cmd("/finish");
			cmd("/puzzle");
			cmd("/listen");
			cmd("/praise");
			cmd("/darksouls");
			cmd("/survivin");
			break;
		
		case "survivin":
			listUpdate("[Joel] I've struggled a long time with survivin', but no matter what you have to find something to fight for.");
			break;
			
		case "darksouls":
			listUpdate("[Dark Souls] YOU DIED");
			break;
			
		default:
			cmd("Befehl nicht erkannt.\nFür hilfe '/help' eingeben.");
			break;
		}
	}
	
	public static void info(String info) {
		listUpdate("[Info] " + info);
	}

	public static void sys(String text) {
		listUpdate("[System] " + text);
	}

	public static void cmd(String text) {
		listUpdate("[Command] " + text);
	}

	public static void in(String text) {
		listUpdate("[Input] " + text);
	}
	
	public static void error(String text) {
		listUpdate("[ERROR] " + text);
	}
	public static void chat(String msg) {
		for(int i = 0; i < MultiplayerServer.al.size(); i++) {
			MultiplayerServer.al.get(i).sOutput.append(".[Host]/" + msg.replace(' ', '/') + "\n");
		}
		chatUpdate("[Host] " + msg);
	}
	
	public static void setConsoleColor(Color c) {
		console.setForeground(c);
	}

	public static float clamp(float var, float min, float max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}
}
