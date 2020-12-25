import java.util.ArrayList;
import java.awt.*;

class Chat {
  ArrayList<String> lines = new ArrayList<String>();
  boolean isOpen = true;
  int cooldown = 0;
  final int defaultCooldown = 20;
  final int maxLinesRendered = 7;
  static final int lineHeight = 30;
  static final int lineOffset = 2;
  static final int lineWidth = 500;
  static final int bottomOffset = 70;
  static final int leftOffset = 40;

  Chat() {
    this.lines.add("Ahhh");
    this.lines.add("Ahhhh#2");
  }

  public void render(Graphics g, Game m) {
    int height = m.height;
    if (this.isOpen || this.cooldown > 0) {
      if (this.isOpen) {
        g.setColor(new Color(0f, 0f, 0f, 0.8f));
        g.fillRect(
          Chat.leftOffset,
          height-Chat.bottomOffset,
          Chat.lineWidth,
          Chat.lineHeight
        );
      }
        //this.renderText();
        for (int i = 0; i < Math.min(this.maxLinesRendered, this.lines.size()); i++) {
          g.setColor(new Color(0f, 0f, 0f, (float)(
            this.isOpen
            ? 0.6
            : 0.6 * (this.cooldown/this.defaultCooldown)
          )));
          g.fillRect(
            Chat.leftOffset,
            height-Chat.bottomOffset-(Chat.lineHeight + Chat.lineOffset)*i,
            Chat.lineWidth,
            Chat.lineHeight
          );
          g.setFont(new Font("Arial", Font.PLAIN, Chat.lineHeight));
          g.setColor(new Color(1f, 1f, 1f, (float)(
            this.isOpen
            ? 1
            : this.cooldown/this.defaultCooldown
          )));
          g.drawString(
            "<Player1> " + this.lines.get(i),
            Chat.leftOffset + Chat.lineOffset,
            height-Chat.bottomOffset-(Chat.lineHeight + Chat.lineOffset)*i
          );
          m.log("Text rendered at ("+(Chat.leftOffset + Chat.lineOffset)+", "+(height-Chat.bottomOffset-(Chat.lineHeight + Chat.lineOffset)*i)+"): \"" + "<Player1> " + this.lines.get(i) + "\"");
        }
    }
  }
}
