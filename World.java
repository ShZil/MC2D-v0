import java.awt.*;
import java.util.*;
import java.text.*;

class World {
  Block[][] wld;
  public int length;
  public int length0;
  public int l;
  public Biome biome;
  public Game m;

  public World(int[] grassLine, int[] stoneLine, int seed, int w, int h, int l, Biome b, Game m) {
    wld = new Block[w][h];
    length = w;
    length0 = h;
    this.l = l;
    this.biome = b;
    this.m = m;
    Random rnd = new Random();
    rnd.setSeed(seed);
    for (int x = 0; x < wld.length; x++) {
      for (int y = 0; y < wld[x].length; y++) {
        if (y == grassLine[x]) {
          wld[x][y] = new Block(x, y, "grass");
        } else if (y == 41) {
          wld[x][y] = new Block(x, y, "bedrock");
        } else if (y == 40 && rnd.nextDouble() > 0.2) {
          wld[x][y] = new Block(x, y, "bedrock");
        } else if (y == 39 && rnd.nextDouble() > 0.4) {
          wld[x][y] = new Block(x, y, "bedrock");
        } else if (y == 38 && rnd.nextDouble() > 0.6) {
          wld[x][y] = new Block(x, y, "bedrock");
        } else if (y == 37 && rnd.nextDouble() > 0.8) {
          wld[x][y] = new Block(x, y, "bedrock");
        } else if (y > stoneLine[x]) {
          wld[x][y] = new Block(x, y, "stone");
        } else if (y > grassLine[x]) {
          wld[x][y] = new Block(x, y, "dirt");
        } else {
        wld[x][y] = new Block(x, y, "air");
        }
      }
    }
    for (int x = 0; x < wld.length; x++) {
      if (canGenerateTree(x, wld, grassLine) && rnd.nextDouble() < 0.1) {
        wld = generateTree(x, wld, grassLine, rnd);
      }
    }
  }

  private boolean canGenerateTree(int x, Block[][] w, int[] ground) {
    int y = ground[x] - 1;
    boolean exeption0 = true;
    if (x > 0) {
      exeption0 = w[x-1][y-3].air();
    }
    boolean exeption1 = true;
    if (x < w.length - 1) {
      exeption1 = w[x+1][y-3].air();
    }
    boolean exeption2 = true;
    if (x > 0) {
      exeption0 = w[x-1][y-4].air();
    }
    boolean exeption3 = true;
    if (x < w.length - 1) {
      exeption1 = w[x+1][y-4].air();
    }
    boolean exeption4 = true;
    if (x > 1) {
      exeption0 = w[x-2][y-3].air();
    }
    boolean exeption5 = true;
    if (x < w.length - 2) {
      exeption1 = w[x+2][y-3].air();
    }
    return w[x][y].air() && w[x][y-1].air() && w[x][y-2].air() && w[x][y-3].air() && w[x][y-4].air() && w[x][y-5].air() && exeption0 && exeption1 && exeption2 && exeption3 && exeption4 && exeption5;
  }

  private Block[][] generateTree(int x, Block[][] w, int[] ground, Random r) {
    int y = ground[x] - 1;
    w[x+0][y+0].set("oak");
    w[x+0][y-1].set("oak");
    w[x+0][y-2].set("oak");
    w[x+0][y-3].set("oak_leaf");
    w[x+0][y-4].set("oak_leaf");
    w[x+0][y-5].set("oak_leaf");
    if (x < w.length - 1) {
      w[x+1][y-3].set("oak_leaf");
    }
    if (x > 0) {
      w[x-1][y-3].set("oak_leaf");
    }
    if (x < w.length - 1) {
      w[x+1][y-4].set("oak_leaf");
    }
    if (x > 0) {
      w[x-1][y-4].set("oak_leaf");
    }
    if (x < w.length - 2 && r.nextDouble() < 0.75) {
      w[x+2][y-3].set("oak_leaf");
    }
    if (x > 1 && r.nextDouble() < 0.75) {
      w[x-2][y-3].set("oak_leaf");
    }
    return w;
  }

  public Block setBlock(int x, int y, String id) {
    wld[x][y] = new Block(x, y, id);
    return wld[x][y];
  }

  public Block setBlock(Block pos, String id) {
    wld[pos.x][pos.y] = new Block(pos.x, pos.y, id);
    return wld[pos.x][pos.y];
  }

  public boolean fill(Point start, Point end, String id) {
    if (start.x >= end.x || start.y >= end.y) {
      return false;
    }
    for (int x = start.x; x <= end.x; x++) {
      for (int y = start.y; x <= end.y; x++) {
        this.wld[x][y].id = id;
        if (x + y - start.x - start.y > 1000) {
          return false;
        }
      }
    }
    return true;
  }

  public Block getBlock(int x, int y) {
    try {
      return wld[x][y];
    } catch (ArrayIndexOutOfBoundsException e) {
      m.log(e);
      return null;
    }

  }

  public String toString() {
    String text = "";
    DecimalFormat integerWriting = new DecimalFormat("00");
    for (int x = 0; x < length; x++) {
      for (int y = 0; y < length0; y++) {
        text += "At ["+integerWriting.format(x)+","+integerWriting.format(y)+"]: "+wld[x][y].id+"\n";
      }
    }
    return text;
  }

  public Block[][] getBlockData() {
    return this.wld;
  }
}
