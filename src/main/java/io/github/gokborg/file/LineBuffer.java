package io.github.gokborg.file;

public class LineBuffer
{
	private String[] lines;
	private int pointer = 0;
	
	public LineBuffer(String[] lines)
	{
		this.lines = lines;
	}
	public String next()
	{
		if(pointer >= lines.length)
		{
			return null;
		}
		String line = lines[pointer];
		pointer++;
		return line;
	}
}
