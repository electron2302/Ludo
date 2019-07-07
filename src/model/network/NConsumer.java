package model.network;

import java.util.List;

@FunctionalInterface
public interface NConsumer<T> {
	void accept(T first, T second, T third, List<T> others);
}
