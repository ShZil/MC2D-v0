import java.awt.*;
import java.util.Arrays;

class HotBar {
  Slot[] slots = new Slot[9];
  byte current = 0;
  final int width = 500;
  final Point loc = new Point(650, 900);

  HotBar() {
    Arrays.fill(this.slots, new Slot("none", 1));
  }

  public void setSlot(byte index, Item new_, int count) {
    this.slots[index] = new Slot(new_, count);
  }

  public Slot getSlot(byte index) {
    return this.slots[index];
  }

  public Slot getSelected() {
    return this.slots[this.current];
  }

  public void scrollTo(byte indexTo) {
    this.current = indexTo;
  }

  public void render(Graphics g) {
    int perSlot = this.width / 9;
    g.setColor(new Color(100, 100, 100, 220));
    g.fillRect(this.loc.x, this.loc.y, this.width - 5, perSlot);
    for (int i = 0; i < this.slots.length; i++) {
      slots[i].render(
        g,
        this.loc.x + i * perSlot,
        this.loc.y, perSlot - 2
      );
    }
  }

  public boolean give(Item item, int count) {
    for (int i = 0; i < 9; i++) {
      if (this.slots[i].item.id == "none") {
        this.slots[i] = new Slot(item, count);
        return true;
      }
    }
    return false;
  }
}
