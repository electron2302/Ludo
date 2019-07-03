package model.logic.exceptions;
@FunctionalInterface
public interface FunctionExceptionTunnel<T, R, E extends Exception> {
	<T, R, E extends Exception> T tunnel(T first, E ex);
}
