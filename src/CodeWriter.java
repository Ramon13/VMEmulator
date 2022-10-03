

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CodeWriter {

	private BufferedWriter writer;
	private AssemblyBuilder asmBuilder;
	
	public CodeWriter(OutputStream out) {
		writer = new BufferedWriter(new OutputStreamWriter(out));
		asmBuilder = new AssemblyBuilder();
	}
	
	public void writePushPop(CommandType commandType, String segment, int index) throws IOException {
		if (commandType == CommandType.C_PUSH)
			writePush(segment, index);
		else
			writePop(segment, index);
	}
	
	public void writeArithmetic(String command) throws IOException {
		CommandBuilder builder = new ArithmeticCommandBuilder();
		
		writer.write(builder.build(asmBuilder, command, null, null));		
		writer.flush();
	}
	
	private void writePop(String segment, int index) throws IOException {
		CommandBuilder builder = new WritePopBuilder();
		
		writer.write(builder.build(asmBuilder, null, segment, index));		
		writer.flush();
	}
	
	private void writePush(String segment, int index) throws IOException {
		CommandBuilder builder = new WritePushCommandBulder();
		
		writer.write(builder.build(asmBuilder, null, segment, index));		
		writer.flush();
	}
	
	public void closeStream() {
		try {
			this.writer.flush();
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
