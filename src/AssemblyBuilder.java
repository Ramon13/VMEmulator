

import java.util.LinkedList;
import java.util.Queue;

public class AssemblyBuilder {
	
	private Queue<String> commands;
	private int counter;
	
	public AssemblyBuilder() {
		this.commands = new LinkedList<String>();
	}
	
	public String build() {
		String command = null;
		StringBuilder assembly = new StringBuilder();
		
		while((command = commands.poll()) != null) {
			assembly.append(command + "\n");
			counter++;
		}
		
		return assembly.toString();
	}
	
	public int getCommandsCount() {
		return counter + commands.size();
	}
	
	public AssemblyBuilder AToD() {
		commands.add("D=A");
		return this;
	}
	
	public AssemblyBuilder MToD() {
		commands.add("D=M");
		return this;
	}
	
	public AssemblyBuilder MToA() {
		commands.add("A=M");
		return this;
	}
	
	public AssemblyBuilder DToM() {
		commands.add("M=D");
		return this;
	}
	
	public AssemblyBuilder DToA() {
		commands.add("A=D");
		return this;
	}
	
	public AssemblyBuilder selAddr(int addr) {
		commands.add("@" + addr);
		return this;
	}
	
	public AssemblyBuilder segmentOffsetToD(int segmentBase, int offset) {
		selAddr(offset)
			.AToD()
			.selAddr(segmentBase)
			.add('D')
			.DToA()
			.MToD();
		return this;
	}
	
	public AssemblyBuilder createPointer(int pointerAddr, int segmentBase, int offset) {
		selAddr(offset)
			.AToD()
			.selAddr(segmentBase)
			.add('D')
			.selAddr(pointerAddr)
			.DToM();
		return this;
	}
	
	public AssemblyBuilder pushDToStack() {
		selAddr(0).MToA().DToM();
		return this;
	}
	
	public AssemblyBuilder incStackPointer() {
		selAddr(0).incremment();
		return this;
	}
	
	public AssemblyBuilder selectTop() {
		selAddr(0).decremment().MToA();
		return this;
	}
	
	public AssemblyBuilder decremment() {
		commands.add("M=M-1");
		return this;
	}
	
	public AssemblyBuilder incremment() {
		commands.add("M=M+1");
		return this;
	}
	
	public AssemblyBuilder add(char dest) {
		commands.add(dest + "=M+D");
		return this;
	}
	
	public AssemblyBuilder sub(char dest) {
		commands.add(dest + "=M-D");
		return this;
	}
	
	public AssemblyBuilder addSubD(String command) {
		if ("add".equals(command))
			add('M');
		else 
			sub('M');
		
		return this;
	}
	
	public AssemblyBuilder neg() {
		commands.add("M=-M");
		return this;
	}
	
	public AssemblyBuilder bitwise(String command) {
		if ("not".equals(command)) {
			commands.add("M=!M");
			return this;
		}
			
		commands.add("and".equals(command)  ? "M=D&M" : "M=D|M");
		return this;
	}
	
	public AssemblyBuilder compare(String command) {
		String jump = "J" + command.toUpperCase();
		
		commands.add("D=M");
		commands.add("M=-1");
		commands.add("@" + (getCommandsCount() + 5));
		commands.add("D;" + jump);
		commands.add("@0");
		commands.add("A=M");
		commands.add("M=0");
		return this;
	}
}
