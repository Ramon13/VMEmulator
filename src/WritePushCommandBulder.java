

public class WritePushCommandBulder extends CommandBuilder{

	@Override
	public String build(AssemblyBuilder builder, String command, String segment, Integer index) {
		if ("constant".equals(segment)) {
			builder
				.selAddr(index)
				.AToD();
			
		} else if ("temp".equals(segment) || "stack".equals(segment) || "pointer".equals(segment)) {
			int startAddr = segments.get(segment);
			builder
				.selAddr(startAddr + index)
				.MToD();
			
		} else {
			int segmentBase = segments.get(segment);
			builder.segmentOffsetToD(segmentBase, index);
		}
		
		return builder
			.pushDToStack()
			.incStackPointer()
			.build();
	}

}
