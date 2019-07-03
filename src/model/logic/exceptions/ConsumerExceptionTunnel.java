package model.logic.exceptions;
@FunctionalInterface
public interface ConsumerExceptionTunnel<T> {
	void tunnel(T element);
}
