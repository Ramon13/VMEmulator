
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Parser {

	private Queue<String> commands;
	
	private String currentCommand;
	
	private Map<String, CommandType> commandTypes;
	
	public Parser(InputStream in) {
		try (Scanner stream = new Scanner(in)) {
			commands = new LinkedList<>();
			
			while (stream.hasNextLine()) {
				String command = stream.nextLine();
				
				if (!command.isBlank() && command.charAt(0) != '/') {
					commands.add(command);
				}
			}
		}
		
		commandTypes = new HashMap<>();
		
		commandTypes.put("add", CommandType.C_ARITHMETIC);
		commandTypes.put("sub", CommandType.C_ARITHMETIC);
		commandTypes.put("neg", CommandType.C_ARITHMETIC);
		commandTypes.put("eq", CommandType.C_ARITHMETIC);
		commandTypes.put("gt", CommandType.C_ARITHMETIC);
		commandTypes.put("lt", CommandType.C_ARITHMETIC);
		commandTypes.put("and", CommandType.C_ARITHMETIC);
		commandTypes.put("or", CommandType.C_ARITHMETIC);
		commandTypes.put("not", CommandType.C_ARITHMETIC);
		commandTypes.put("pop", CommandType.C_POP);
		commandTypes.put("push", CommandType.C_PUSH);
	}
	
	public void advance() {
		currentCommand = commands.poll();
	}
	
	public boolean hasMoreCommands() {
		return commands.size() > 0;
	}
	
	public CommandType getCommandType() {
		return commandTypes.get(currentCommand.split(" ")[0]);
	}
	
	public String getArg1() {
		try {
			String[] args = this.currentCommand.split(" ");
			
			return (getCommandType() == CommandType.C_ARITHMETIC) ? args[0] : args[1];
			
		} catch (NullPointerException e) {
			throw new RuntimeException("The current command is empty. Try advance");
		}
	}
	
	public int getArg2() {
		try {
			return Integer.parseInt(this.currentCommand.split(" ")[2]);
		} catch (NullPointerException e) {
			throw new RuntimeException("The current command is empty. Try advance");
		}
	}
	
	public String getCurrentCommand() {
		return currentCommand;
	}
}
