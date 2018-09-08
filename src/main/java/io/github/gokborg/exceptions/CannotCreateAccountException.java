package io.github.gokborg.exceptions;

@SuppressWarnings("serial")
public class CannotCreateAccountException extends Exception
{
	public CannotCreateAccountException(String message)
	{
		super(message);
	}
}
