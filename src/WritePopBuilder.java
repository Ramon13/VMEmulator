

public class WritePopBuilder extends CommandBuilder{
	
	@Override
	public String build(AssemblyBuilder builder, String command, String segment, Integer index) {
		if ("temp".equals(segment) || "stack".equals(segment) || "pointer".equals(segment)) {
			int startAddr = segments.get(segment);
			
			return builder
				.selectTop()
				.MToD()
				.selAddr(startAddr + index)
				.DToM()
				.build();
		
		} else {
			int pointerAddr = segments.get("gp0");
			int segPtr = segments.get(segment);
			
			return builder
				.createPointer(pointerAddr, segPtr, index)
				.selectTop()
				.MToD()
				.selAddr(pointerAddr)
				.MToA()
				.DToM()
				.build();
		}
	}

}
