package testGUI;

import java.util.Scanner;

import frameConsole.ConsoleNode;
import jframes.JFrameNode;

public class TestJFrameNode {

	public static void main(String[] args) {

		System.out.println("Enable GUI? (yes/no)");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		switch (scanner.next()) {
		case "yes":
			JFrameNode jFrameNode = new JFrameNode();
			jFrameNode.setVisible(true);
			break;

		case "no":
			@SuppressWarnings("unused")
			ConsoleNode consoleNode = new ConsoleNode();
			break;
		}

	}
}
