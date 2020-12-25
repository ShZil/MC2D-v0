import java.awt.*;

class Player {
  Game m;
  float x;
  float y;
  float vx = 0;
  float vy = 0;
  final int w = 50;
  final int h = 100;

  final float xfriction = 2f;
  final float airRes = 2.5f;
  final float gravity = 0.7f;
  //Inventory inventory;
  HotBar hotbar = new HotBar();
  Breaking breaking;
  String name;

  Player(String name, int x, int y, Game m) {
    this.name = name;
    this.x = (float)x;
    this.y = (float)y;
    this.m = m;
  }

  public void render(Graphics g) {
    this.hotbar.render(g);
    g.setColor(Color.BLACK);
    g.fillRect((int)this.x, (int)this.y, this.w, this.h);
    g.setColor(Color.WHITE);
    g.drawRect((int)this.x, (int)this.y, this.w, this.h);
    Font font = new Font("Arial", Font.PLAIN, 15);
    g.setFont(font);
    g.setColor(Color.BLACK);
    g.drawString(this.name, (int)this.x, (int)this.y - 17);
  }

  public void update(World w) {
    try {
      Block belowLeft = w.getBlock((int)this.x / w.l, (int)this.y / w.l + this.h / w.l);
      Block belowRight = w.getBlock((int)this.x / w.l + this.w / w.l, (int)this.y / w.l + this.h / w.l);
      if (belowLeft.air() && belowRight.air()) {
        this.y += w.l/5;
      }
      this.x += this.vx;
      this.y += this.vy;
      if (this.vx - xfriction > 0) {
        this.vx -= xfriction;
      }
    } catch (NullPointerException e) {
      m.log(e);
    }
  }
}
