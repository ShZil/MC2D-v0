import java.awt.*;

class Slot {
  Item item;
  int count;

  Slot(String id, int count) {
    this.item = new Item(id);
    this.count = count;
  }

  Slot(Item item, int count) {
    this.item = item;
    this.count = count;
  }

  public void render(Graphics g, int x, int y, int l) {
    g.setColor(new Color(0, 255, 0, 220));
    g.fillRect(x, y, l, l);
  }
}
