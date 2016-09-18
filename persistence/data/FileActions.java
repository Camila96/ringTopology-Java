package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileActions {


	private	File file;

	private FileWriter fileWriter;

	private PrintWriter printWriter;
	private BufferedWriter bufferedWriter;

	public FileActions(String fileName){

		this.file = new File(fileName);
		try {
			this.fileWriter = new FileWriter(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.bufferedWriter = new BufferedWriter(this.fileWriter);
		this.printWriter = new PrintWriter(this.bufferedWriter);
	}

	public ArrayList<String> read(String path){

		ArrayList<String> arrayList = new ArrayList<>();
		try {
			FileReader fileReader = new FileReader(path);
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String string;
			while((string = bufferedReader.readLine()) != null){
				arrayList.add(string);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return arrayList;
	}

	public void write(String string){

		this.printWriter.write(string + "\n");
		this.printWriter.flush();
		try {
			this.bufferedWriter.flush();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileWriter getFileWriter() {
		return fileWriter;
	}

	public void setFileWriter(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}

	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}
}

