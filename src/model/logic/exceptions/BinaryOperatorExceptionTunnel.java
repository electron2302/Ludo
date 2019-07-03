package model.logic.exceptions;

@FunctionalInterface
public interface BinaryOperatorExceptionTunnel<T, E extends Exception> {
	<T, E extends Exception> T tunnel(T first, T second, E ex);
}
