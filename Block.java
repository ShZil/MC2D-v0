import java.awt.*;

class Block {
  int x;
  int y;
  String id;

  public Block(int x, int y, String id) {
    this.x = x;
    this.y = y;
    this.id = id;
  }

  public String toString() {
    return "[Object Block, int x = "+x+", int y = "+y+", String id = "+id+"]";
  }

  private double daySky(long x) {
    return 185 + (40/(1+(Math.exp(-0.1*x + 6))));
  }

  private Color nightSky(long x) {
    return new Color(0, 0, 0);
  }

  private Color sky(long x, Game local) {
    if ((x/100.0) % 2.0 < 1.0) {
      local.isDay = true;
      return Color.getHSBColor((float)daySky(x)/360, 1f, 1f);
    } else {
      local.isDay = false;
      return nightSky(x);
    }
  }

  public boolean air() {
    return this.id == "air";
  }

  public void set(String id) {
    this.id = id;
  }

  public void render(Graphics g, Game m) {
    long time = m.tickz;
    int l = m.l;
    switch (this.id) {
      case "air":
        Color air = sky(time, m);
        fill(g, air, this.x*l, this.y*l, l, l);
      break;
      default:
        draw(g, m, this.id, this.x*l, this.y*l, l, l);
      break;
    }
  }

  public void update(Block right, Block left, Block top, Block bottom) {

  }

  public boolean unbreakable() {
    return this.id == "bedrock";
  }

  private final void draw(Graphics graph, Game m, String image, int x, int y, int w, int h) {
    graph.drawImage(Toolkit.getDefaultToolkit().getImage("textures/"+image+".png"), x, y, w, h, m);
  }

  private final void fill(Graphics graph, Color color, int x, int y, int w, int h) {
    graph.setColor(color);
    graph.fillRect(x, y, w, h);
  }
}
