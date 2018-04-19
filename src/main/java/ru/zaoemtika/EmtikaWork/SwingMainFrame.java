package ru.zaoemtika.EmtikaWork;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JProgressBar;

import javax.swing.UIManager;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Cursor;
import java.awt.Desktop;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import javax.swing.ImageIcon;
import java.util.Locale;
import javax.swing.border.MatteBorder;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import javax.swing.border.TitledBorder;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import java.awt.Insets;

public class SwingMainFrame extends JFrame {

	
	
	private static final int MORNING_OR_EVENING = 13;
	private static final long serialVersionUID = 1L;
	private static String[] needFiles;
	private JPanel contentPane;
	private Color backgroundColor;
	public static String[] getNeedFiles() {
		return needFiles;
	}

	private Color foregroundColor;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private boolean rUpd = false;
	private boolean AM_PM;
	private boolean doDeficit = true;
	private boolean doSklad = true;
	private boolean doClearDoc;

	private boolean doInvoice = true;
	private boolean doNacenka = true;
	private boolean doBS;

	private boolean vsblchkBox = true;

	private JRadioButton rMorning;
	private JRadioButton rEvening;
	private JRadioButton rUpdate;

	private JCheckBox chckbxDeficit;
	private JCheckBox chckbxSklad;
	private JCheckBox chckbxClearDoc;

	private JCheckBox chckbxInvoice;
	private JCheckBox chckbxNacenka;
	private JCheckBox chckbxBS;

	private JCheckBox clearFullCheckBox;
	private static boolean doClearOrFast = false;
	private static boolean doParadise;

	private static boolean click = false;

	private JProgressBar progressBar;
	JScrollPane scrollPane;

	private String getMyTitle;

	public String getMT() {
		return getMyTitle;
	}

	public static boolean isClick() {
		return click;
	}

	public static void setClick(boolean click) {
		SwingMainFrame.click = click;
	}
	
	static{
		try {
			System.out.println(System.getProperties().getProperty("user.home"));
			needFiles = FilialFromSQL.filialFromSQL(System.getProperties().getProperty("user.name"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Пользователь не найден");
		}
	}

	public SwingMainFrame() {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				new Delete(AllWork.TEMP_DIR);
				// System.exit(0);
			}
		});

		setUndecorated(true);
		setAutoRequestFocus(false);
		setForeground(Color.RED);
		setBackground(Color.RED);
		setLocale(new Locale("ru", "RU"));
		setOpacity(0.95f);

		MouseAdapter adapter = new MouseAdapter() {
			int x, y;

			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					x = e.getX();
					y = e.getY();
				}
			}

			public void mouseDragged(MouseEvent e) {
				if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
					setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
				}
			}
		};

		setResizable(false);
		setFont(new Font("Comic Sans MS", Font.PLAIN, 12));

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Octopus100.png")));
		// setBackGroundColor(new Color(102,26,42));
		setBackGroundColor(new Color(51, 51, 51));
		setForegroundColor(SystemColor.desktop);
		setTitle("Emtika Work (\u0412\u0435\u0440\u0441\u0438\u044F " + Program.VERSION + ")  by Vadim M. ft. Mark A.");

		Color color = Color.WHITE;
		getMyTitle = getTitle();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 587);
		contentPane = new JPanel();
		contentPane.setBackground(getBackGroundColor());
		contentPane.setRequestFocusEnabled(false);
		contentPane.setAutoscrolls(true);
		setContentPane(contentPane);
		/*
		 * contentPane.addComponentListener(new ComponentAdapter() { public void
		 * componentResized(ComponentEvent e) { setShape(new
		 * RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); }
		 * });
		 */
		contentPane.setLayout(null);

		contentPane.addMouseListener(adapter);
		contentPane.addMouseMotionListener(adapter);
		

		
		
		if (!needFiles[8].equals("6101")) {
			doClearDoc = true;
		} else {
			doClearDoc = false;
		}
		DateFormat dateFormat = new SimpleDateFormat("HH");
		Date curDate = new Date();
		if (Integer.parseInt(dateFormat.format(curDate)) < MORNING_OR_EVENING) {
			setAM_PM(true);
		} else
			setAM_PM(false);

		setVsblchkBox(!isAM_PM());
		setDoParadise(isAM_PM());

		JButton helpButton = new JButton("");
		helpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		helpButton.setFocusPainted(false);
		helpButton.setFocusTraversalKeysEnabled(false);
		helpButton.setFocusable(false);
		helpButton.setContentAreaFilled(false);
		helpButton.setBorderPainted(false);
		helpButton.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/help_mov.png")));
		helpButton.setBounds(0, 1, 26, 23);
		helpButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				helpButton
						.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/help_mov.png")));
			}

			public void mousePressed(MouseEvent e) {
				helpButton.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/help_def_press.png")));
			}

			public void mouseExited(MouseEvent e) {
				helpButton
						.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/help_mov.png")));
			}

			public void mouseEntered(MouseEvent e) {
				helpButton
						.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/help_def.png")));
			}

			public void mouseClicked(MouseEvent e) {
				helpButton
						.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/help_mov.png")));
			}
		});
		helpButton.addActionListener((e) -> {
			try {
				Desktop.getDesktop().browse(new URL("http://wiki.emtika.ru/doku.php/wiki:paradox:emtsave").toURI());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		contentPane.add(helpButton);

		JButton minimizedFrame = new JButton("");
		minimizedFrame.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Minimized.png")));
		minimizedFrame.setFocusPainted(false);
		minimizedFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		minimizedFrame.setContentAreaFilled(false);
		minimizedFrame.setBorderPainted(false);
		minimizedFrame.setOpaque(false);
		minimizedFrame.setBounds(324, 1, 17, 17);
		minimizedFrame.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				minimizedFrame.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Minimized.png")));
			}

			public void mousePressed(MouseEvent e) {
				setState(JFrame.ICONIFIED);
			}

			public void mouseExited(MouseEvent e) {
				minimizedFrame.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Minimized.png")));
			}

			public void mouseEntered(MouseEvent e) {
				minimizedFrame.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Minimized_move.png")));
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		contentPane.add(minimizedFrame);

		JLabel helloLabel = new JLabel();
		helloLabel.setFont(new Font("Calibri", Font.PLAIN, 11));
		helloLabel.setForeground(SystemColor.text);
		helloLabel.setBorder(UIManager.getBorder("Button.border"));
		helloLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		helloLabel.setBounds(10, 36, 340, 17);

		if (Integer.parseInt(dateFormat.format(curDate)) > 7 && Integer.parseInt(dateFormat.format(curDate)) < 13) {
			helloLabel.setText("\u0414\u043e\u0431\u0440\u043e\u0435 \u0443\u0442\u0440\u043e, " + needFiles[7]);
		}
		if (Integer.parseInt(dateFormat.format(curDate)) > 12 && Integer.parseInt(dateFormat.format(curDate)) < 19) {
			helloLabel.setText("\u0414\u043e\u0431\u0440\u044b\u0439 \u0434\u0435\u043d\u044c, " + needFiles[7]);
		}
		if (Integer.parseInt(dateFormat.format(curDate)) > 17 && Integer.parseInt(dateFormat.format(curDate)) <= 23) {
			helloLabel.setText("\u0414\u043e\u0431\u0440\u044b\u0439 \u0432\u0435\u0447\u0435\u0440, " + needFiles[7]);
		}
		if (Integer.parseInt(dateFormat.format(curDate)) >= 0 && Integer.parseInt(dateFormat.format(curDate)) < 9) {
			helloLabel.setText(
					"\u0427\u0435\u0433\u043e \u043d\u0435 \u0441\u043f\u0438\u0442\u0435,  " + needFiles[7] + " ?");
		}
		
				JButton exitTop = new JButton();
				exitTop.setForeground(Color.WHITE);
				exitTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				exitTop.setFocusable(false);
				exitTop.setFocusTraversalKeysEnabled(false);
				exitTop.setFocusPainted(false);
				exitTop.setBorder(null);
				exitTop.setBorderPainted(false);
				exitTop.setContentAreaFilled(false);
				exitTop.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/exit_new_17.png")));
				exitTop.setBounds(342, 1, 18, 17);
				exitTop.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
						exitTop.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/exit_new_17.png")));
					}

					public void mousePressed(MouseEvent e) {
					}

					public void mouseExited(MouseEvent e) {
						exitTop.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/exit_new_17.png")));
					}

					public void mouseEntered(MouseEvent e) {
						exitTop.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/exit_new_move.png")));
					}

					public void mouseClicked(MouseEvent e) {
					}
				});
				exitTop.addActionListener(e -> {
					new Delete(AllWork.TEMP_DIR);
					System.exit(0);
				});
				
						contentPane.add(exitTop);
		contentPane.add(helloLabel);

		JLabel filialLabel = new JLabel("");
		filialLabel.setFont(new Font("Calibri", Font.PLAIN, 11));
		filialLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filialLabel.setForeground(Color.WHITE);
		filialLabel.setBounds(10, 50, 340, 17);
		filialLabel.setText("\u0412\u0430\u0448  \u0444\u0438\u043B\u0438\u0430\u043B :  " + needFiles[0]);
		contentPane.add(filialLabel);

		JLabel programNameLabel = new JLabel("EmtikaWork");
		programNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		programNameLabel.setForeground(Color.WHITE);
		programNameLabel.setFont(new Font("Calibri", Font.PLAIN, 26));
		programNameLabel.setBounds(110, 9, 150, 30);
		contentPane.add(programNameLabel);

		JLabel lblNewLabel = new JLabel("\u00A9");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(250, 8, 19, 14);
		contentPane.add(lblNewLabel);

		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setOpaque(true);
		backgroundLabel.setBackground(new Color(37, 37, 38));
		backgroundLabel.setBounds(1, 1, 357, 68);
		contentPane.add(backgroundLabel);

		JTextArea textArea = new JTextArea();
		textArea.setMargin(new Insets(3, 3, 3, 3));
		textArea.setAutoscrolls(false);
		textArea.setRequestFocusEnabled(false);
		textArea.setLocale(new Locale("ru", "RU"));
		textArea.setWrapStyleWord(true);
		textArea.setForeground(Color.WHITE);
		// textArea.setBackground(new Color(126,21,20));
		textArea.setBackground(new Color(51, 51, 51));
		textArea.setBorder(null);

		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		scrollPane.setLocale(new Locale("ru", "RU"));
		scrollPane.setBounds(10, 385, 340, 181);
		contentPane.add(scrollPane);

		rMorning = new JRadioButton("", isAM_PM());
		rMorning.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
		rEvening = new JRadioButton("", !isAM_PM());
		rEvening.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		rUpdate = new JRadioButton(
				"\u041E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u0435 (\u0432\u043E \u0432\u0440\u0435\u043C\u044F \u0440\u0430\u0431\u043E\u0447\u0435\u0433\u043E \u0434\u043D\u044F)",
				isrUpd());
		rUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rUpdate.setActionCommand(
				"\u041E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u0435 (\u0432\u043E \u0432\u0440\u0435\u043C\u044F \u0440\u0430\u0431\u043E\u0447\u0435\u0433\u043E \u0434\u043D\u044F)");
		if (needFiles[8].equals("6101")) {
			rMorning.setText("\u0414\u0435\u0444\u0438\u0446\u0438\u0442\u044B \u0438 \u0441\u043A\u043B\u0430\u0434\u044B \u0444\u0438\u043B\u0438\u0430\u043B\u043E\u0432");
			rEvening.setText("\u0420\u0430\u0437\u0434\u0430\u0447\u0430 \u0438 \u0441\u0432\u0435\u0440\u0442\u043A\u0430");
		} else{
			rMorning.setText("\u0423\u0442\u0440\u0435\u043D\u043D\u044F\u044F \u0440\u0430\u0431\u043E\u0442\u0430");
			rEvening.setText("\u0412\u0435\u0447\u0435\u0440\u043D\u044F\u044F \u0440\u0430\u0431\u043E\u0442\u0430");
		}
		if (rMorning.isSelected()) {
			rMorning.setIcon(new ImageIcon(
					SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_default.png")));
			rEvening.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			rUpdate.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
		}
		rMorning.setContentAreaFilled(false);
		rMorning.setFocusPainted(false);
		rMorning.setOpaque(false);
		rMorning.setToolTipText(
				"\u0417\u0430\u0433\u0440\u0443\u0437\u043A\u0430 \u0434\u0430\u043D\u043D\u044B\u0445 \u0441 \u0424\u0422\u041F");
		rMorning.setForeground(color);
		rMorning.setFont(new Font("SansSerif", Font.BOLD, 13));
		rMorning.setBounds(10, 75, 277, 23);
		rMorning.addMouseListener(new RadioButtonMouseAction(rMorning, rEvening, rUpdate));

		rMorning.addActionListener((e) -> {
			rMorning.setIcon(new ImageIcon(
					SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_default.png")));
			rEvening.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			rUpdate.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			setAM_PM(true);
			setrUpdate(false);
			setDoParadise(true);
			setVsblchkBox(false);
			chckbxDeficit.setEnabled(false);
			chckbxSklad.setEnabled(false);
			chckbxClearDoc.setEnabled(false);
			chckbxInvoice.setEnabled(false);
			chckbxNacenka.setEnabled(false);
			chckbxBS.setEnabled(false);
		});
		buttonGroup.add(rMorning);
		contentPane.add(rMorning);

		if (rEvening.isSelected()) {
			rMorning.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			rEvening.setIcon(new ImageIcon(
					SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_default.png")));
			rUpdate.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
		}
		rEvening.setContentAreaFilled(false);
		rEvening.setFocusPainted(false);
		rEvening.setOpaque(false);
		rEvening.setForeground(color);
		rEvening.setToolTipText(
				"\u0412\u044B\u0433\u0440\u0443\u0437\u043A\u0430 \u0434\u0435\u0444\u0438\u0446\u0438\u0442\u0430"
						+ " \u0438 \u0441\u043A\u043B\u0430\u0434\u0430 \u043D\u0430 \u0424\u0422\u041F");
		rEvening.setFont(new Font("SansSerif", Font.BOLD, 13));
		rEvening.setBounds(10, 112, 277, 23);
		rEvening.addMouseListener(new RadioButtonMouseAction(rEvening, rMorning, rUpdate));

		rEvening.addActionListener((e) -> {
			rEvening.setIcon(new ImageIcon(
					SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_default.png")));
			rMorning.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			rUpdate.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			setAM_PM(false);
			setrUpdate(false);
			setDoParadise(false);
			setVsblchkBox(true);
			chckbxDeficit.setEnabled(ifDirExist("c:" + File.separator + "pric1b_64" + File.separator));
			chckbxSklad
					.setEnabled(ifDirExist("c:" + File.separator + "base" + File.separator + "aaa1" + File.separator));
			chckbxClearDoc.setEnabled(isVsblchkBox());
			chckbxInvoice.setEnabled(false);
			chckbxNacenka.setEnabled(false);
			chckbxBS.setEnabled(false);
		});

		buttonGroup.add(rEvening);
		contentPane.add(rEvening);

		rUpdate.setOpaque(false);
		rUpdate.setContentAreaFilled(false);
		rUpdate.setFocusPainted(false);
		rUpdate.setToolTipText(
				"<HTML>\u041E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u0435 \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u044B"
						+ " \u0414\u0435\u0444\u0438\u0446\u0438\u0442-\u041F (\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043A\u0430)<br> \u0418 \u043A\u043E\u043F\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435 bs-\u0444\u0430\u0439\u043B\u043E\u0432");
		rUpdate.setForeground(color);
		rUpdate.setFont(new Font("SansSerif", Font.BOLD, 13));
		rUpdate.setBounds(10, 216, 277, 23);
		rUpdate.setEnabled(false);
		if (!needFiles[8].equals("6101")) {
			rUpdate.setEnabled(true);
			rUpdate.addMouseListener(new RadioButtonMouseAction(rUpdate, rMorning, rEvening));

			rUpdate.addActionListener((e) -> {
				rEvening.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
				rMorning.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
				rUpdate.setIcon(new ImageIcon(
						SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_default.png")));
				setAM_PM(isAM_PM());
				setrUpdate(true);
				setDoParadise(true);
				setVsblchkBox(false);
				chckbxDeficit.setEnabled(isVsblchkBox());
				chckbxSklad.setEnabled(isVsblchkBox());
				chckbxClearDoc.setEnabled(isVsblchkBox());
				chckbxInvoice.setEnabled(!isVsblchkBox());
				chckbxNacenka.setEnabled(!isVsblchkBox());
				chckbxBS.setEnabled(
						ifFileExist(AllWork.Z_TO_ALL + File.separator + "BS" + CurrentDate.currentDate(true) + ".ZIP"));
			});
		}
		buttonGroup.add(rUpdate);
		contentPane.add(rUpdate);

		progressBar = new JProgressBar();
		progressBar.setBounds(new Rectangle(0, 1, 0, 0));
		progressBar.setStringPainted(true);
		progressBar.setFocusTraversalKeysEnabled(false);
		progressBar.setFocusable(false);
		progressBar.setOpaque(false);
		progressBar.setBorderPainted(false);
		progressBar.setBorder(null);
		progressBar.setFont(new Font("Calibri", Font.PLAIN, 11));
		progressBar.setForeground(new Color(205,0,0));
		progressBar.setBackground(Color.WHITE);
		//progressBar.setBackground(getBackGroundColor());
		progressBar.setBounds(10, 371, 340, 10);
		contentPane.add(progressBar);

		chckbxDeficit = new JCheckBox("\u0414\u0435\u0444\u0438\u0446\u0438\u0442  ("
				+ new StringBuilder(needFiles[2]).delete(needFiles[2].length() - 3, needFiles[2].length()) + ".db, "
				+ needFiles[4].toUpperCase() + ")");

		if (needFiles[8].equals("6101"))
			chckbxDeficit = new JCheckBox("\u0414\u0435\u0444\u0438\u0446\u0438\u0442  (def_basa.zip)");

		chckbxDeficit.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
		chckbxDeficit.setFocusPainted(false);
		chckbxDeficit.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		chckbxDeficit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxDeficit.setOpaque(false);
		chckbxDeficit.setToolTipText("\u0412\u044B\u0433\u0440\u0443\u0437\u043A\u0430 \u0432 z:\\defs");
		chckbxDeficit.setForeground(color);
		chckbxDeficit.setBounds(61, 138, 226, 23);
		chckbxDeficit.setEnabled(false);
		if (ifDirExist(AllWork.C_PRIC1B_64)) {
			chckbxDeficit
					.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));

			chckbxDeficit.addMouseListener(new CheckBoxAction(chckbxDeficit));

			chckbxDeficit.setEnabled(isVsblchkBox());
			chckbxDeficit.setSelected(true);
			chckbxDeficit.addActionListener((e) -> {
				if (chckbxDeficit.isSelected()) {
					setDoDeficit(true);
				} else {
					setDoDeficit(false);
				}
			});
		} else {
			setDoDeficit(false);
			chckbxDeficit
					.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable.png")));
			chckbxDeficit.setEnabled(false);
			chckbxDeficit.setSelected(false);
		}

		contentPane.add(chckbxDeficit);

		chckbxSklad = new JCheckBox("\u0421\u043A\u043B\u0430\u0434  (" + (needFiles[5] == ""
				? new StringBuilder(needFiles[1]).delete(needFiles[1].length() - 3, needFiles[1].length()) + ".DB)"
				: new StringBuilder(needFiles[1]).delete(needFiles[1].length() - 3, needFiles[1].length()) + ".DB, "
						+ needFiles[5].substring(0, 1).toUpperCase()
						+ needFiles[5].substring(1, needFiles[5].length()).toLowerCase() + ")"));

		if (needFiles[8].equals("6101"))
			chckbxSklad.setText("\u0421\u043A\u043B\u0430\u0434  (SK_BASA.ZIP)");

		chckbxSklad.setForeground(color);
		chckbxSklad.setFocusPainted(false);
		chckbxSklad.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		chckbxSklad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxSklad.setOpaque(false);
		chckbxSklad.setToolTipText("\u0412\u044B\u0433\u0440\u0443\u0437\u043A\u0430 \u0432 z:\\sklads");
		chckbxSklad.setBounds(61, 164, 226, 23);

		if (ifDirExist("C:" + File.separator + "base" + File.separator + "aaa1" + File.separator)) {
			chckbxSklad
					.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
			chckbxSklad.setEnabled(isVsblchkBox());
			chckbxSklad.setSelected(true);
			chckbxSklad.addActionListener((e) -> {
				if (chckbxSklad.isSelected()) {
					setDoSklad(true);
				} else {
					setDoSklad(false);
				}
			});

		} else {
			setDoSklad(false);
			chckbxSklad
					.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable.png")));
			chckbxSklad.setEnabled(false);
			chckbxSklad.setSelected(false);
		}

		contentPane.add(chckbxSklad);

		chckbxClearDoc = new JCheckBox();
		chckbxClearDoc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		if (!needFiles[8].equals("6101")) {
			chckbxClearDoc
					.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
			chckbxClearDoc.setText(
					"\u0421\u0432\u0435\u0440\u0442\u043A\u0430 i:\\base\\doc \u0441 \u043e\u0447\u0438\u0441\u0442\u043a\u043e\u0439");
		} else {
			chckbxClearDoc
					.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable.png")));
			chckbxClearDoc.setText(
					"\u0421\u0432\u0435\u0440\u0442\u043A\u0430 i:\\base\\doc \u0431\u0435\u0437 \u043e\u0447\u0438\u0441\u0442\u043a\u0438");
		}
		chckbxSklad.addMouseListener(new CheckBoxAction(chckbxSklad));

		chckbxClearDoc.setFocusPainted(false);
		chckbxClearDoc.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		chckbxClearDoc.setOpaque(false);
		chckbxClearDoc.setSelected(false);
		if (!needFiles[8].equals("6101"))
			chckbxClearDoc.setSelected(true);

		chckbxClearDoc.setEnabled(isVsblchkBox());
		chckbxClearDoc.setForeground(color);
		chckbxClearDoc.setBounds(61, 190, 272, 23);

		chckbxClearDoc.addMouseListener(new CheckBoxAction(chckbxClearDoc));

		chckbxClearDoc.addActionListener((e) -> {
			if (chckbxClearDoc.isSelected()) {
				setDoClearDoc(true);
				chckbxClearDoc.setText(
						"\u0421\u0432\u0435\u0440\u0442\u043A\u0430 i:\\base\\doc \u0441 \u043e\u0447\u0438\u0441\u0442\u043a\u043e\u0439");
			} else {
				setDoClearDoc(false);
				chckbxClearDoc.setText(
						"\u0421\u0432\u0435\u0440\u0442\u043A\u0430 i:\\base\\doc \u0431\u0435\u0437 \u043e\u0447\u0438\u0441\u0442\u043a\u0438");
			}
		});
		contentPane.add(chckbxClearDoc);

		JButton bNo = new JButton("");
		bNo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bNo.setFocusTraversalKeysEnabled(false);
		bNo.setBorderPainted(false);
		bNo.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/quit.png")));
		bNo.setFocusPainted(false);
		bNo.setContentAreaFilled(false);
		bNo.setFocusable(false);
		bNo.setFont(new Font("Calibri", Font.PLAIN, 14));
		bNo.setForeground(Color.WHITE);
		// bNo.setBackground(new Color(45, 45, 48));
		bNo.setBounds(314, 337, 36, 30);
		bNo.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				bNo.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/quit.png")));
			}

			public void mousePressed(MouseEvent e) {
				bNo.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/quit_pressed.png")));
			}

			public void mouseExited(MouseEvent e) {
				bNo.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/quit.png")));
			}

			public void mouseEntered(MouseEvent e) {
				bNo.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/quit_mov.png")));
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		bNo.addActionListener((e) -> {
			new Delete(AllWork.TEMP_DIR);
			// Thread.interrupted();
			System.exit(0);
		});
		contentPane.add(bNo);

		JButton bYes = new JButton("");
		bYes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bYes.setFocusable(false);
		bYes.setFocusTraversalKeysEnabled(false);
		bYes.setFocusPainted(false);
		bYes.setBorderPainted(false);
		bYes.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Play_def.png")));
		bYes.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bYes.setContentAreaFilled(false);
		bYes.setRequestFocusEnabled(false);
		bYes.setOpaque(false);
		bYes.setFont(new Font("Calibri", Font.PLAIN, 14));
		bYes.setForeground(Color.WHITE);
		bYes.setEnabled(true);
		bYes.setBounds(284, 337, 26, 30);
		bYes.repaint();
		bYes.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				bYes.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Play_def.png")));
			}

			public void mousePressed(MouseEvent e) {
				bYes.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Play_pres.png")));
			}

			public void mouseExited(MouseEvent e) {
				bYes.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Play_def.png")));
			}

			public void mouseEntered(MouseEvent e) {
				bYes.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Play_mov.png")));
			}

			public void mouseClicked(MouseEvent e) {
				bYes.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Play_mov.png")));
			}
		});
		bYes.addActionListener((e) -> {
			setClick(true);
			if (isAM_PM() && !rUpd && RunnerEvening.getInstance() == null && RunnerEvening_Podolsk.getInstance() == null
					&& RunnerUpdate.getInstance() == null) {
				if (!needFiles[8].equals("6101")) {
					new Thread(RunnerMorning.runnerMorning(needFiles, progressBar, textArea)).start();
				} else {
					new Thread(RunnerMorning_Podolsk.runnerMorning_Podolsk(needFiles, progressBar, textArea)).start();
				}
			} else if (!isAM_PM() && !rUpd && RunnerMorning.getInstance() == null
					&& RunnerMorning_Podolsk.getInstance() == null && RunnerUpdate.getInstance() == null) {
				if (!needFiles[8].equals("6101")) {
					new Thread(RunnerEvening.runnerEvening(needFiles, progressBar, textArea, isDoDeficit(), isDoSklad(),
							isDoClearDoc())).start();
				} else {
					new Thread(RunnerEvening_Podolsk.runnerEvening_Podolsk(needFiles, progressBar, textArea,
							isDoDeficit(), isDoSklad(), isDoClearDoc())).start();
				}
			} else if (rUpd && RunnerMorning.getInstance() == null && RunnerMorning_Podolsk.getInstance() == null
					&& RunnerEvening.getInstance() == null && RunnerEvening_Podolsk.getInstance() == null) {
				new Thread(RunnerUpdate.runnerUpdate(needFiles, progressBar, textArea, doInvoice, doNacenka, isDoBS()))
						.start();
			}

		});
		bYes.removeActionListener(null);

		contentPane.add(bYes);
		// чекбокс ОБНОВЛЕНИЕ СЧЕТОВ
		StringBuilder sb = new StringBuilder();
		String fileAttr = null;
		try {
			fileAttr = Files.getLastModifiedTime(Paths.get(AllWork.Z_TO_ALL + File.separator + "update.zip"),
					LinkOption.NOFOLLOW_LINKS).toString();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sb.append("  ( \u043e\u0442 ").append(fileAttr.substring(8, 10)).append("-").append(fileAttr.substring(5, 7))
				.append("-").append(fileAttr.substring(0, 4)).append("  ").append(Integer.parseInt(fileAttr.substring(11, 13)) + 3)
				.append(":").append(fileAttr.substring(14, 16)).append(" )");
		chckbxInvoice = new JCheckBox(" \u0421\u0447\u0435\u0442\u0430     " + sb);
		chckbxInvoice.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxInvoice.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
		chckbxInvoice.setForeground(color);
		chckbxInvoice.setFocusPainted(false);
		chckbxInvoice.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		chckbxInvoice.setOpaque(false);
		chckbxInvoice.setSelected(true);
		chckbxInvoice.setEnabled(false);
		chckbxInvoice.setBounds(61, 242, 243, 23);
		if (!needFiles[8].equals("6101")) {
			chckbxInvoice.addMouseListener(new CheckBoxAction(chckbxInvoice));

			chckbxInvoice.addActionListener((e) -> {
				if (chckbxInvoice.isSelected()) {
					setDoInvoice(true);
				} else {
					setDoInvoice(false);
				}
			});
		}
		contentPane.add(chckbxInvoice);

		// чекбокс ОБНОВЛЕНИЕ НАЦЕНКИ
		sb = new StringBuilder();
		try {
			fileAttr = Files.getLastModifiedTime(Paths.get(AllWork.Z_TO_ALL + File.separator + "nacen.zip"),
					LinkOption.NOFOLLOW_LINKS).toString();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sb.append("( \u043e\u0442 ").append(fileAttr.substring(8, 10)).append("-").append(fileAttr.substring(5, 7))
				.append("-").append(fileAttr.substring(0, 4)).append("  ").append(Integer.parseInt(fileAttr.substring(11, 13)) + 3)
				.append(":").append(fileAttr.substring(14, 16)).append(" )");
		// sb.append(" ").append(fileAttr.substring(11, 19)).append(")");
		chckbxNacenka = new JCheckBox(" \u041D\u0430\u0446\u0435\u043D\u043A\u0430  " + sb);
		chckbxNacenka.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNacenka.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
		chckbxNacenka.setForeground(color);
		chckbxNacenka.setFocusPainted(false);
		chckbxNacenka.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		chckbxNacenka.setOpaque(false);
		chckbxNacenka.setSelected(true);
		chckbxNacenka.setEnabled(false);
		chckbxNacenka.setBounds(61, 268, 244, 23);
		if (!needFiles[8].equals("6101")) {
			chckbxNacenka.addMouseListener(new CheckBoxAction(chckbxNacenka));

			chckbxNacenka.addActionListener((e) -> {
				if (chckbxNacenka.isSelected()) {
					setDoNacenka(true);
				} else {
					setDoNacenka(false);
				}
			});
		}
		contentPane.add(chckbxNacenka);

		chckbxBS = new JCheckBox(" BS"
				+ CurrentDate.currentDate(true) + ".ZIP");
		chckbxBS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxBS.setForeground(color);
		chckbxBS.setFocusPainted(false);
		chckbxBS.setFont(new Font("Calibri Light", Font.PLAIN, 13));
		chckbxBS.setOpaque(false);
		chckbxBS.setBounds(61, 294, 180, 23);
		setDoBS(true);
		if (ifFileExist(AllWork.Z_TO_ALL + File.separator + "BS" + CurrentDate.currentDate(true) + ".ZIP")) {
			chckbxBS.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
			chckbxBS.setSelected(true);
			chckbxBS.setEnabled(false);
			setDoBS(true);
			if (!needFiles[8].equals("6101")) {
				chckbxBS.addMouseListener(new CheckBoxAction(chckbxBS));
				chckbxBS.addActionListener((e) -> {
					if (chckbxBS.isSelected()) {
						setDoBS(true);
					} else {
						setDoBS(false);
					}
				});
			}
		} else {
			chckbxBS.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable.png")));
			chckbxBS.setSelected(false);
			chckbxBS.setEnabled(false);
			setDoBS(false);
		}
		contentPane.add(chckbxBS);

		JLabel copyRight = new JLabel();
		copyRight.setText(getTitle());
		copyRight.setFont(new Font("Calibri", Font.PLAIN, 11));
		copyRight.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		copyRight.setBounds(new Rectangle(1, 0, 0, 0));
		copyRight.setForeground(Color.GRAY);
		copyRight.setBounds(10, 567, 340, 18);
		contentPane.add(copyRight);

		clearFullCheckBox = new JCheckBox(
				"\u0410\u0432\u0430\u0440\u0438\u0439\u043D\u044B\u0439 \u0437\u0430\u043F\u0443\u0441\u043A \u0443\u0442\u0440\u0435\u043D\u043D\u0435\u0439/\u043E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u044F");
		clearFullCheckBox.setToolTipText(
				"\u0415\u0441\u043B\u0438 \u041F\u0430\u0440\u0430\u0434\u043E\u043A\u0441\u044B \u043F\u043E\u043A\u0430\u0437\u044B\u0432\u0430\u044E\u0442 \u043D\u0435\u043F\u0440\u0430\u0432\u0438\u043B\u044C\u043D\u043E");
		clearFullCheckBox.setActionCommand(
				"\u0410\u0432\u0430\u0440\u0438\u0439\u043D\u043E\u0435 \u0432\u044B\u043F\u043E\u043B\u043D\u0435\u043D\u0438\u0435 \u0443\u0442\u0440\u0435\u043D\u043D\u0435\u0439/\u043E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u044F");
		clearFullCheckBox.setOpaque(false);
		clearFullCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		clearFullCheckBox
				.setIcon(new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable.png")));
		clearFullCheckBox.setFocusPainted(false);
		clearFullCheckBox.setFont(new Font("Calibri", Font.PLAIN, 12));
		clearFullCheckBox.setForeground(Color.WHITE);
		clearFullCheckBox.setBounds(10, 341, 268, 23);
		if (!needFiles[8].equals("6101")) {
			clearFullCheckBox.addActionListener((e) -> {
				if (clearFullCheckBox.isSelected()) {
					doClearOrFast = true;
					clearFullCheckBox.setIcon(
							new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
				} else {
					doClearOrFast = false;
					clearFullCheckBox.setIcon(
							new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable.png")));
				}
			});

			clearFullCheckBox.addMouseListener(new CheckBoxAction(clearFullCheckBox));
		} else {
			clearFullCheckBox.setEnabled(false);
		}

		contentPane.add(clearFullCheckBox);

		JSeparator separator = new JSeparator();
		separator.setBorder(null);
		separator.setEnabled(false);
		separator.setFocusable(true);
		separator.setOpaque(true);
		separator.setBackground(Color.DARK_GRAY);
		separator.setForeground(Color.GRAY);
		separator.setBounds(10, 324, 340, 1);
		contentPane.add(separator);

		JLabel borderMainFrame = new JLabel("");
		borderMainFrame.setForeground(Color.RED);
		borderMainFrame.setBorder(new LineBorder(new Color(255, 0, 0)));
		borderMainFrame.setBackground(Color.RED);
		borderMainFrame.setBounds(0, 0, 360, 587);
		contentPane.add(borderMainFrame);

		/*
		 * JLabel lblNewLabel_1 = new JLabel(""); lblNewLabel_1.setIcon(new
		 * ImageIcon(SwingMainFrame.class.getResource(
		 * "/ru/zaoemtika/images/spartak.png"))); lblNewLabel_1.setBounds(0, 73,
		 * 360, 512); contentPane.add(lblNewLabel_1);
		 */

		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { rMorning, rEvening,
				chckbxDeficit, chckbxSklad, chckbxClearDoc, rUpdate, chckbxInvoice, chckbxNacenka, chckbxBS, bYes, bNo,
				backgroundLabel, progressBar, scrollPane, textArea, helloLabel }));

		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { bYes, bNo }));
	}

	public boolean isDoSklad() {
		if (!Files.isDirectory(Paths.get("c:" + File.separator + "base" + File.separator + "aaa1" + File.separator),
				LinkOption.NOFOLLOW_LINKS)) {
			return false;
		} else {
			return doSklad;
		}
	}

	public void setDoSklad(boolean doSklad) {
		if (!Files.isDirectory(Paths.get("c:" + File.separator + "base" + File.separator + "aaa1" + File.separator),
				LinkOption.NOFOLLOW_LINKS)) {
			this.doSklad = false;
		} else {
			this.doSklad = doSklad;
		}
	}

	public boolean isDoDeficit() {
		return doDeficit;
	}

	public void setDoDeficit(boolean doDeficit) {
		this.doDeficit = doDeficit;
	}

	public boolean isDoClearDoc() {
		return doClearDoc;
	}

	public void setDoClearDoc(boolean doClearDoc) {
		this.doClearDoc = doClearDoc;
	}

	public boolean isrUpd() {
		return rUpd;
	}

	void setrUpdate(boolean rUpdate) {
		rUpd = rUpdate;
	}

	void setAM_PM(boolean aM_PM) {
		AM_PM = aM_PM;
	}

	boolean isAM_PM() {
		return AM_PM;
	}

	boolean isDoParadise() {
		return doParadise;
	}

	void setDoParadise(boolean doParadise) {
		SwingMainFrame.doParadise = doParadise;
	}

	public boolean isVsblchkBox() {
		return vsblchkBox;
	}

	public void setVsblchkBox(boolean vsblchkBox) {
		this.vsblchkBox = vsblchkBox;
	}

	public Color getBackGroundColor() {
		return backgroundColor;
	}

	public void setBackGroundColor(Color backGroundColor) {
		this.backgroundColor = backGroundColor;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public boolean ifDirExist(String paths) {
		return (Files.isDirectory(Paths.get(paths), LinkOption.NOFOLLOW_LINKS));
	}

	public boolean ifFileExist(String paths) {
		return (Files.exists(Paths.get(paths), LinkOption.NOFOLLOW_LINKS));
	}
	/*
	 * public boolean ifFileExist(String paths) { if
	 * (Files.notExists(Paths.get(paths), LinkOption.NOFOLLOW_LINKS)) { return
	 * false; } else { return isVsblchkBox(); //return true; } }
	 */

	public boolean isDoInvoice() {
		return doInvoice;
	}

	public void setDoInvoice(boolean doInvoice) {
		this.doInvoice = doInvoice;
	}

	public boolean isDoNacenka() {
		return doNacenka;
	}

	public void setDoNacenka(boolean doNacenka) {
		this.doNacenka = doNacenka;
	}

	public boolean isDoBS() {
		return doBS;
	}

	public void setDoBS(boolean doBS) {
		this.doBS = doBS;
	}

	public static boolean isClearOrFast() {
		return doClearOrFast;
	}

	// реализация событий для чекбоксов и мыши
	class CheckBoxAction implements MouseListener {

		JCheckBox checkBox;

		public CheckBoxAction(JCheckBox checkBox) {
			this.checkBox = checkBox;
		}

		public void mouseReleased(MouseEvent e) {
			if (checkBox.isSelected()) {
				checkBox.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
			} else {
				checkBox.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable.png")));
			}
		}

		public void mousePressed(MouseEvent e) {
			if (checkBox.isSelected()) {
				checkBox.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable_moved.png")));
			} else {
				checkBox.setIcon(new ImageIcon(
						SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable_moved.png")));
			}
		}

		public void mouseExited(MouseEvent e) {
			if (checkBox.isSelected()) {
				checkBox.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable.png")));
			} else {
				checkBox.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable.png")));
			}
		}

		public void mouseEntered(MouseEvent e) {
			if (checkBox.isSelected()) {
				checkBox.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_enable_moved.png")));
			} else {
				checkBox.setIcon(new ImageIcon(
						SwingMainFrame.class.getResource("/ru/zaoemtika/images/check_disable_moved.png")));
			}
		}

		public void mouseClicked(MouseEvent e) {

		}

	}

	// реализация событий для радиобаттона и мыши
	class RadioButtonMouseAction implements MouseListener {

		JRadioButton buttonThis;
		JRadioButton button1;
		JRadioButton button2;

		public RadioButtonMouseAction(JRadioButton buttonThis, JRadioButton button1, JRadioButton button2) {
			this.buttonThis = buttonThis;
			this.button1 = button1;
			this.button2 = button2;
		}

		public void mouseReleased(MouseEvent e) {
			if (buttonThis.isSelected()) {
				buttonThis.setIcon(new ImageIcon(
						SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_default.png")));
			} else {
				buttonThis.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			}
		}

		public void mousePressed(MouseEvent e) {
			button1.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			button2.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			buttonThis.setIcon(
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_moved.png")));
		}

		public void mouseExited(MouseEvent e) {
			if (buttonThis.isSelected()) {
				buttonThis.setIcon(new ImageIcon(
						SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_default.png")));
			} else {
				buttonThis.setIcon(
						new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default.png")));
			}
		}

		public void mouseEntered(MouseEvent e) {
			if (buttonThis.isSelected()) {
				buttonThis.setIcon(new ImageIcon(
						SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_pressed_moved.png")));
			} else {
				buttonThis.setIcon(new ImageIcon(
						SwingMainFrame.class.getResource("/ru/zaoemtika/images/rButton_default_moved.png")));
			}
		}

		public void mouseClicked(MouseEvent arg0) {
		}
	}
}
