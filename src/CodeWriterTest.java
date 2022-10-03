

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.jupiter.api.Test;

public class CodeWriterTest {

	private CodeWriter writer;
	private ByteArrayOutputStream out;
	
	public CodeWriterTest() {
		out = new ByteArrayOutputStream();
		writer = new CodeWriter(out);
	}
	
	@Test
	void whenPushConstant_ExpectPushToStack() throws IOException {
		int index = 10;
		writer.writePushPop(CommandType.C_PUSH, "constant", index);
		assertEquals(out.toString(), String.format("@%d\nD=A\n@0\nA=M\nM=D\n@0\nM=M+1\n", index));
	}
	
	@Test
	void whenPushFromTemp_ExpectPushToStack() throws IOException {
		final int index = 6;
		final int tempStartAddr = 5;
		
		writer.writePushPop(CommandType.C_PUSH, "temp", index);
		assertEquals(out.toString(), String.format("@%d\nD=M\n@0\nA=M\nM=D\n@0\nM=M+1\n", tempStartAddr + index));
	}
	
	@Test
	void whenPopToTemp_ExpectPopFromStack() throws IOException {
		final int index = 6;
		final int tempStartAddr = 5;
		
		writer.writePushPop(CommandType.C_POP, "temp", index);
		assertEquals(out.toString(), String.format("@0\nM=M-1\nA=M\nD=M\n@%d\nM=D\n", tempStartAddr + index));
	}
	
	@Test
	void whenPopToLocal_ExpectPopFromStack() throws IOException {
		final int index = 0;
		
		writer.writePushPop(CommandType.C_POP, "local", index);
		assertEquals(out.toString(), String.format("@0\nD=A\n@1\nD=M+D\n@13\nM=D\n@0\nM=M-1\nA=M\nD=M\n@13\nA=M\nM=D\n", index));
	}
	
	@Test
	void whenAdd_ExpectRightResult() throws IOException {
		writer.writeArithmetic("add");
		assertEquals(out.toString(), "@0\nM=M-1\nA=M\nD=M\n@0\nM=M-1\nA=M\nM=M+D\n@0\nM=M+1\n");
	}
	
	@Test
	void whenSub_ExpectRightResult() throws IOException {
		writer.writeArithmetic("sub");
		assertEquals(out.toString(), "@0\nM=M-1\nA=M\nD=M\n@0\nM=M-1\nA=M\nM=M-D\n@0\nM=M+1\n");
	}
	
	@Test
	void whenNeg_ExpectNegateTheStackTop() throws IOException {
		writer.writeArithmetic("neg");
		assertEquals(out.toString(), "@0\nM=M-1\nA=M\nM=-M\n@0\nM=M+1\n");	
	}
	
	@Test
	void testEq() throws IOException {
		writer.writeArithmetic("not");
		//writer.writeTrueFalseLabels();
		System.out.println(out.toString());
	}
	
	@After
	void afterEach() {
		out.reset();
	}
	
	void afterAll() throws IOException {
		out.close();
	}
}
