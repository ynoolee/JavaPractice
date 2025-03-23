package book.oop.elegant.ch02;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Page69 {
	private static class Cash {
		private int cash;

		public Cash(int cash) {
			this.cash = cash;
		}

		public void setCash(int cash) {
			this.cash = cash;
		}

		public void mul(int operand) {
			this.cash *= operand;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Cash cash1 = (Cash)o;
			return cash == cash1.cash;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cash);
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("Cash{");
			sb.append("cash=").append(cash);
			sb.append('}');
			return sb.toString();
		}
	}
	public static void main(String[] args) {
		Map<Cash,String> map = new HashMap<>();

		Cash five = new Cash(5);
		Cash ten = new Cash(10);
		map.put(five, "five");
		map.put(ten, "ten");

		System.out.println(map);

		five.mul(2);
		System.out.println(map);
		System.out.println(map.get(five));

		map.put(five, "five");
		System.out.println(map.get(five));
		System.out.println(map.get(ten));
		System.out.println(map);
	}
}
