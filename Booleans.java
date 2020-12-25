class Booleans {
  public Bool f3;

  Booleans(boolean[] input) {
    this.f3 = new Bool(input[0]);
  }

  public String constructor() {
    return "[f3]";
  }

  public class Bool {
    boolean value;

    Bool(boolean b) {
      this.value = b;
    }

    public boolean get() {
      return this.value;
    }

    public void set(boolean value) {
      this.value = value;
    }

    public boolean flip() {
      this.value = !this.value;
      return this.value;
    }
  }
}
