package sms.client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SMSClient extends JFrame {
	
	private static final long serialVersionUID = -2325474103201229323L;
	private JPanel mainPanel = new JPanel();
	private JPanel subPanel1 = new JPanel();
	private JPanel subPanel2 = new JPanel();
	private JPanel subPanel3 = new JPanel();
	private JPanel subPanel4 = new JPanel();
	private JPanel subPanel5 = new JPanel();
	private JLabel lab1 = new JLabel("选择文件: ");
	private JLabel lab2 = new JLabel("输入短信: ");
	private JLabel lab3 = new JLabel("输入签名: ");
	private JLabel lab4 = new JLabel("成功条数: ");
	private JLabel lab5 = new JLabel("失败条数: ");
	private JTextField textField1 = new JTextField(12);
	private JTextField textField3 = new JTextField(20);
	private JTextField textField4 = new JTextField(5);
	private JTextField textField5 = new JTextField(5);
	private JButton button1 = new JButton("上传文件");
	private JButton button2 = new JButton("发送信息");
	private JButton button3 = new JButton("查看记录");

	private JTextArea testArea1 = new JTextArea(10, 20);
	private JFrame frame = new JFrame();

	public static void main(String[] args) {
		SMSClient app = new SMSClient();
		app.drawFrame();
	}

	// 画图形界面
	public void drawFrame() {
		frame.setLayout(new FlowLayout());
		subPanel1.setLayout(new FlowLayout());
		subPanel1.add(lab1);
		subPanel1.add(textField1);
		subPanel1.add(button1);

		subPanel2.setLayout(new FlowLayout());
		subPanel2.add(lab2);
		subPanel2.add(testArea1);

		subPanel3.setLayout(new FlowLayout());
		subPanel3.add(lab3);
		subPanel3.add(textField3);

		subPanel4.setLayout(new FlowLayout());
		subPanel4.add(button2);
		subPanel4.add(button3);

		subPanel5.setLayout(new FlowLayout());
		subPanel5.add(lab4);
		subPanel5.add(textField4);
		subPanel5.add(lab5);
		subPanel5.add(textField5);

		mainPanel.add(subPanel1);
		mainPanel.add(subPanel2);
		mainPanel.add(subPanel3);
		mainPanel.add(subPanel4);
		mainPanel.add(subPanel5);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		frame.add(mainPanel);

		frame.setTitle("短信客户端");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 450);
		frame.setLocation(200, 200);
		frame.setVisible(true);

		// 上传按钮响应
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showOpenDialog(SMSClient.this) == JFileChooser.APPROVE_OPTION) {
					String filePath = jfc.getSelectedFile().getAbsolutePath();
//					System.out.println(filePath);
					textField1.setText(filePath);
				}
			}

		});
		
		// 发送按钮响应
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String source = textField1.getText();
				String message = testArea1.getText();
				String sign = textField3.getText();
				
				if ("".equals(source) || !source.endsWith(".txt")) {
					JOptionPane.showMessageDialog(SMSClient.this, "请选择合法的文本文件！", "警告", JOptionPane.WARNING_MESSAGE);
				} else if ("".equals(message)) {
					JOptionPane.showMessageDialog(SMSClient.this, "短信内容不能为空！", "警告", JOptionPane.WARNING_MESSAGE); 
				} else if ("".equals(sign)) {
					JOptionPane.showMessageDialog(SMSClient.this, "请输入合法签名！", "提示", JOptionPane.WARNING_MESSAGE); 
				} else {
					FileUtils.copyNumbers(source);
					BufferedReader br = null;
					try {
						br = new BufferedReader(new InputStreamReader(new FileInputStream("resource/number.txt"), "UTF-8"));
						String line = null;
						while ((line = br.readLine()) != null) {
							System.out.println("文件内容: " + line);
							String res = SMSSender.sendPost(line, message, sign);
							Integer[] counts = FileUtils.getCounts();
							int lineCount = line.length();
							if (res.startsWith("success")) {
								counts[0] += lineCount;
							} else {
								counts[1] += lineCount;
							}
							FileUtils.writeCounts(counts);
						}
						br.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					} finally {
						FileUtils.clearFile("resource/number.txt");
					}
				}
			}
		});
		
		// 查看按钮响应
		button3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer[] counts = FileUtils.getCounts();
				if (counts.length == 2) {
					textField4.setText(counts[0].toString());
					textField5.setText(counts[1].toString());
				}
			}

		});
	}

}
