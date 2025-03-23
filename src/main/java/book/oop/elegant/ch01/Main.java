package book.oop.elegant.ch01;

import java.util.function.Supplier;

public class Main {
	private void measureTime(Supplier<Number> factory) {
		long start = System.nanoTime();
		Number number = factory.get();
		long end = System.nanoTime();

		System.out.println("TIME "+ (start - end));
	}

	public static void main(String[] args) {
		Main main = new Main();

		String text = "10";

		main.measureTime(() -> main.makeAndConsume2(text));
		main.measureTime(() -> main.makeAndConsume2(text));
		main.measureTime(() -> main.makeAndConsume2(text));
		main.measureTime(() -> main.makeAndConsume2(text));
		main.measureTime(() -> main.makeAndConsume(text));
		main.measureTime(() -> main.makeAndConsume(text));
		main.measureTime(() -> main.makeAndConsume(text));
		main.measureTime(() -> main.makeAndConsume(text));
	}

	private Number makeAndConsume(String text) {
		StringAsInteger number = new StringAsInteger(text);
		int i = number.intValue();

		return number;
	}

	private Number makeAndConsume2(String text) {
		StringAsInteger2 number = new StringAsInteger2(text);
		int i = number.intValue();

		return number;
	}

}
