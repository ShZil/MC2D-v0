import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.time.*;
import java.awt.event.*;
import java.util.Timer;

public class Game extends Canvas implements IntervalInterface, MouseListenerInterface {

  public long tickz = 0;
  World world = generateWorld(0);
  final int l = 25;
  final int tps = 3;
  final long loadingTime = 0L;
  final String title = "Java Project - Minecraft2D - by ShZil";
  Booleans states = new Booleans(new boolean[]{false});
  Breaking breakstage = null;
  public boolean isDay = true;
  int height = 1085;
  int width = 1916;
  Player[] players;
  Chat chat = new Chat();

  // Main method:
  public static void main(String[] args) {
    Game m = new Game();
    JFrame f = new JFrame(m.title);

    Game.setupJFrame(f, m);

    m.createInterval();
    m.createMouseListener();
    m.createKeyboardListener();

    m.createPlayers();
  }

  // Main Drawing & Ticks Interval:
  public void paint(Graphics g) {
    renderBreak(g);
    render(g, world);
    renderF3(g, this.isDay);
    renderBreak(g);
    renderPlayers(g);
    chat.render(g, this);
  }

  private void renderBreak(Graphics g) {
    if (breakstage != null) {
      breakstage.render(g, this);
    }
  }

  private void renderF3(Graphics g, boolean blackness) {
    if (states.f3.get()) {
      Font font = new Font("Arial", Font.PLAIN, l);
      g.setFont(font);
      if (blackness) {
        g.setColor(Color.BLACK);
      } else {
        g.setColor(Color.WHITE);
      }
      String[] f3Data = new String[]{
        "Debug Screen!!!",
        "Tickz: "+this.tickz
      };
      for (int i = 0; i < f3Data.length; i++) {
        g.drawString(f3Data[i], 0, i * l + l);
      }
    }
  }

  private void renderPlayers(Graphics g) {
    for (Player p : this.players) {
      p.render(g);
      p.update(world);
    }
  }

  // Renderer:
  public void render(Graphics g, World blockData) {
    for (int x = 0; x < world.length; x++) {
      for (int y = 0; y < world.length0; y++) {
        blockData.getBlock(x, y).render(g, this);
      }
    }
  }

  // Disable the flashing
  public void update(Graphics g) {
    paint(g);
  }

  // World Generation:
  public World generateWorld(int seed) {
    WorldProperties worldprops = new WorldProperties();
    final int width = 76; // floor(width / l)
    final int height = 42; // floor(height / l) - 1
    // BiomeMap biomeMap = new BiomeMap(seed);
    Biome biome = new Biome("plains");
    WorldGen grass = new WorldGen(worldprops.grassLine, biome.diff, width);
    int[] grassTop = grass.getWorld(seed);
    WorldGen stone = new WorldGen(worldprops.stoneLine, biome.stoneDiff, width);
    int[] stoneTop = stone.getWorld(seed * 2);
    return new World(grassTop, stoneTop, seed, width, height, l, biome /*biomeMap*/, this);
  }
  public World generateWorld(String seed) {
    int resultSeed = 0;
    for(char c : seed.toCharArray()) {
      int ascii = (int)c - 32;
      resultSeed += ascii;
    }
    return generateWorld(resultSeed);
  }

  public void interval() {
    this.repaint();
    this.time();
    this.updateBlocks();
    this.updateBreakstage();
    this.updateChatTimer();
  }

  private void time() {
    tickz++;
    this.log(tickz);
  }

  private void updateBlocks() {
    for (Block[] row : this.world.getBlockData()) {
      for (Block block : row) {
        block.update(block, block, block, block); // Should be: Block right, Block left, Block top, Block bottom. Updating not done.
      }
    }
  }

  private void updateBreakstage() {
    if (this.breakstage != null) {
      if (!this.breakstage.increment()) {
        this.world.setBlock(this.breakstage, "air");
        this.breakstage = null;
      }
    }
  }

  private void updateChatTimer() {
    if (this.chat.cooldown > 0) {
      this.chat.cooldown--;
    }
  }

  public int getMilisecondsPerTick() {
    return this.tps;
  }

  public long loadingTime() {
    return this.loadingTime;
  }

  public void click(int x, int y) {
    this.log("Mouse Clicked at X: " + x/l + " , Y: " + y/l);
    Block block = this.world.getBlock(x/l, y/l);
    this.log(block.toString());
    if (this.breakstage == null && !block.unbreakable()) {
      this.breakstage = new Breaking(x/l, y/l, block.id);
    }
  }
  public void processKey(KeyEvent e) {
    String key = e.getKeyText(e.getKeyCode());
    this.log("Key Pressed: " + key);
    switch (key) {
      case "F3":
      this.states.f3.flip();
      break;
      case "Right":
      this.players[0].vx += 20;
      this.players[0].update(this.world);
      break;
      case "Left":
      this.players[0].vx -= 20;
      this.players[0].update(this.world);
      break;
    }
  }

  private static void setupJFrame(JFrame f, Game m) {
    f.getContentPane().setBackground(Color.black);
    f.setIconImage(Toolkit.getDefaultToolkit().getImage("textures/icon.png"));
    f.add(m);
    f.setSize(m.width, m.height);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void createInterval() {
    Game m = this;
    Timer interval = new Timer();
    interval.schedule(new TimerTask() {
      @Override
      public void run() {
        m.interval();
      }
    }, this.loadingTime(), 1000/this.getMilisecondsPerTick());
  }

  private void createMouseListener() {
    Game m = this;
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        m.click(x,y);
      }
    });
  }

  private void createKeyboardListener() {
    Game m = this;
    this.addKeyListener(new KeyListener() {
      public void keyPressed(KeyEvent e) {
        m.processKey(e);
      }
      public void keyReleased(KeyEvent e) {

      }
      public void keyTyped(KeyEvent e) {

      }
    });
  }

  private void createPlayers() {
    players = new Player[]{new Player("Player 0", 0, 275, this)};
  }

  public void log(String a) {
    System.out.println(a);
  }
  public void log(Object a) {
    System.out.println(a.toString());
  }
  public void log(Exception a) {
    System.out.println("Exception: " + a.toString() + "!");
  }
}
