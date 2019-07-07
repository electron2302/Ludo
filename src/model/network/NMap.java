package model.network;

public interface NMap<T, U> {
	U accept(T... args);
}
