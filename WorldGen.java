import java.util.*;
import java.lang.Math;
class WorldGen {
  int len;
  int begin;
  double jump;
  public WorldGen(int start, double diff, int length) {
      len = length;
      begin = start;
      jump = diff;
  }
  public int[] getWorld(int seed) {
    Random rnd = new Random();
    rnd.setSeed(seed);
    double[] returnedWorld = new double[len];
    for (int i = 0; i < len; i++) {
      if (i == 0) {
        returnedWorld[0] = begin;
      } else {
        double heightDifference = (rnd.nextDouble()-0.5)*2*jump;
        returnedWorld[i] = returnedWorld[i-1] + heightDifference;
      }
    }
    int[] world = new int[len];
    for (int i = 0; i < len; i++) {
      world[i] = (int)Math.round(returnedWorld[i]);
    }
    return world;
  }
}
