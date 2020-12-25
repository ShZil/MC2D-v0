class Biome {
  String name;
  double diff;
  double stoneDiff;
  short skyColor = 0;
  int greenHue = 0;
  public Biome(String n) {
    name = n;
    switch (name) {
      case "plains":
        diff = 0.8;
        stoneDiff = 1.25;
      break;
      case "desert":
        diff = 0.4;
        stoneDiff = 0.7;
      break;
      case "mountains":
        diff = 1.75;
        stoneDiff = 2;
      break;
    }
  }
}

class BiomeMap {
  public BiomeMap(int seed) {

  }

  public Biome getBiome(int x) {
    if (x < 50) {
      return new Biome("plains");
    } else {
      return new Biome("mountains");
    }
  }
}
