package pas.huffman;

/**
 * Represents a leaf node in a Huffman tree.
 */
public class HuffLeafNode extends HuffBaseNode {

  private byte symbol;

  public HuffLeafNode(byte symbol, int weight) {
    super(weight);
    this.symbol = symbol;
  }

  public byte getSymbol() {
    return symbol;
  }



}
