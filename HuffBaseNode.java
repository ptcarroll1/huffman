package pas.huffman;

/**
 * A class that represents a node in a Huffman tree.
 */
public abstract class HuffBaseNode implements Comparable<HuffBaseNode> {

  private int weight;

  /**
   * Creates a HuffBaseNode with the given weight.
   *
   * @param weight the weight of this node
   */
  public HuffBaseNode(int weight) {
    this.weight = weight;
  }

  /**
   * Returns the weight of this node.
   *
   * @return the weight of this node.
   *
   */
  public int getWeight() {
    return weight;
  }

  @Override
  public int compareTo(HuffBaseNode other) {
    if (getWeight() < other.getWeight()) {
      return -1;
    } else if (getWeight() == other.getWeight()) {
      int thisMin = findLowestByteValue(this);
      int otherMin = findLowestByteValue(other);

      if (thisMin < otherMin) {
        return -1;
      } else if (thisMin > otherMin) {
        return 1;
      }
      // TODO: Tie-breaker

      // When two nodes have the same weight, you should get the smallest byte value (not weight)
      // from each subtree and compare them.

      // If the left subtree has a smaller byte value, it should be considered "less than" the
      // right subtree, meaning you should return a negative number.

      // If the right subtree has a smaller byte value, it should be considered "greater than" the
      // left subtree, meaning you should return a positive number.

      return 0;
    } else {
      return 1;
    }

  }

  private byte findLowestByteValue(HuffBaseNode node) {
    if (node instanceof HuffLeafNode) {
      HuffLeafNode leaf = (HuffLeafNode) node;
      return leaf.getSymbol();
    }

    HuffInternalNode internal = (HuffInternalNode) node;

    byte leftValue = findLowestByteValue(internal.getLeft());
    byte rightValue = findLowestByteValue(internal.getRight());

    int leftUnsigned = Byte.toUnsignedInt(leftValue);
    int rightUnsigned = Byte.toUnsignedInt(rightValue);

    if (leftUnsigned <= rightUnsigned) {
      return (byte) leftUnsigned;
    } else {
      return (byte) rightUnsigned;
    }
  }

}
