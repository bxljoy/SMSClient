package sms.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

	public static void main(String[] args) {
		System.out.println(getCounts()[1]);
	}

	public static void copyNumbers(String source) {
		FileWriter fw = null;
		BufferedReader br = null;
		try {
			fw = new FileWriter("resource/number.txt", true);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(source), "UTF-8"));
			String line = null;
			StringBuffer sumLine = new StringBuffer();
			int count = 0;
			while ((line = br.readLine()) != null) {
				System.out.println("内容为: " + line);
				sumLine.append(line);
				count ++;
				if (count >= 900) {
					fw.write(sumLine.toString());
					fw.write("\n");
					fw.flush();
					count = 0;
					sumLine = new StringBuffer();
				} else {
					sumLine.append(",");
				}
			}
			
			if (count != 0) {
				sumLine.deleteCharAt(sumLine.length() - 1);
				fw.write(sumLine.toString());
				fw.write("\n");
				fw.flush();
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Integer[] getCounts() {
		File file = new File("resource/counter.txt");
		Integer[] counts = new Integer[2];
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file));
			int count = 0;
			while ((line = br.readLine()) != null) {
				counts[count] = Integer.parseInt(line);
				count ++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return counts;
	}
	
	public static void writeCounts(Integer[] counts) {
		FileWriter fw = null;
		try {
			fw = new FileWriter("resource/number.txt", true);
			
			if (counts.length == 2) {
				fw.write(counts[0]);
				fw.write("\n");
				fw.write(counts[1]);
				fw.write("\n");
				fw.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void clearFile(String source) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(source);
			fw.write("\n");
			fw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
