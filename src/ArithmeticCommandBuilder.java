

public class ArithmeticCommandBuilder extends CommandBuilder{
	
	@Override
	public String build(AssemblyBuilder builder, String command, String segment, Integer index) {
		if (isArithmetic(command)) {
			if ("neg".equals(command)) {
				return builder
					.selectTop()
					.neg()
					.incStackPointer()
					.build();
			
			} else {
				return builder
					.selectTop()
					.MToD()
					.selectTop()
					.addSubD(command)
					.incStackPointer()
					.build();
			}
		
		} else if (isConditional(command)) {
			return builder
					.selectTop()
					.MToD()
					.selectTop()
					.addSubD(command)
					.compare(command)
					.incStackPointer()
					.build();
		
		} else if (isBitwise(command)) {
			builder.selectTop();
			
			if ("not".equals(command)) {
				builder.bitwise(command);
			
			} else {
				builder
					.MToD()
					.selectTop()
					.bitwise(command);
			}
			
			return builder
				.incStackPointer()
				.build();	
		}
		
		throw new IllegalStateException("Arithmetic command not found: " + command);
	}
	
	private boolean isArithmetic(String command) {
		return "add".equals(command) || "sub".equals(command) || "neg".equals(command);
	}
	
	private boolean isConditional(String command) {
		return "eq".equals(command) || "gt".equals(command) || "lt".equals(command);
	}
	
	private boolean isBitwise(String command) {
		return "and".equals(command) || "or".equals(command) | "not".equals(command);
	}
}
