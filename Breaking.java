import java.awt.Graphics;
import java.awt.Toolkit;

class Breaking extends Block {
  byte breakLevel;
  Breaking(int x, int y, String id) {
    super(x, y, id);
    this.breakLevel = 0;
  }
  public void render(Graphics g, Game m) {
    int l = m.l;
    super.render(g, m);
    draw(g, m, this.x*l, this.y*l, l, l);
  }

  private final void draw(Graphics graph, Game t, int x, int y, int w, int h) {
    graph.drawImage(
      Toolkit.getDefaultToolkit().getImage("../textures/destroy/"+this.breakLevel+".png"),
      x,
      y,
      w,
      h,
      t
    );
  }

  public boolean increment() {
    this.breakLevel++;
    if (this.breakLevel > 9) {
      this.breakLevel = 0;
      return false;
    }
    return true;
  }
}
