package book.modernjavainaction.ch15;

public class ThreadExample {

    public static class Result {

        private int left;

        private int right;

        public int left() {
            return this.left;
        }

        public int right() {
            return this.right;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public void setRight(int right) {
            this.right = right;
        }
    }

}
