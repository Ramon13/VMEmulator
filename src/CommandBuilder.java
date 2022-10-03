

import java.util.HashMap;
import java.util.Map;

public abstract class CommandBuilder {
	
	protected Map<String, Integer> segments;
	
	public CommandBuilder() {
		segments = new HashMap<>();
		segments.put("local", 1);
		segments.put("argument", 2);
		segments.put("this", 3);
		segments.put("that", 4);
		segments.put("temp", 5);
		segments.put("static", 16);
		segments.put("pointer", 3);
		segments.put("gp0", 13);
	}
	
	public abstract String build(AssemblyBuilder builder, String command, String segment, Integer index);
}
