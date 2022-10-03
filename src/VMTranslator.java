import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VMTranslator {

	public static void main(String[] args) throws IOException {
		try {
			File input = new File(args[0]);
			String outputAsm = input.getAbsolutePath().split(".vm")[0] + ".asm";
			
			File asmFile = new File(outputAsm);
			asmFile.createNewFile();
			System.out.println("File created at: " + asmFile.getAbsolutePath());
			
			try(FileInputStream in = new FileInputStream(input); FileOutputStream out = new FileOutputStream(asmFile)) {
				parse(in, out);
			}
			
			
		}catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Command error. Usage: VMEmulator vmfile.vm");
		}
	}
	
	public static void parse(InputStream in, OutputStream out) throws IOException {
		Parser parser = new Parser(in);
		CodeWriter writer = new CodeWriter(out);
		
		while(parser.hasMoreCommands()) {
			parser.advance();
			String command = parser.getCurrentCommand();
			
			if (parser.getCommandType() == CommandType.C_ARITHMETIC) {
				writer.writeArithmetic(command);
			
			} else {
				writer.writePushPop(parser.getCommandType(), parser.getArg1(), parser.getArg2());
			}
		}
	}
}
