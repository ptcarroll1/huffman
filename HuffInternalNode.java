package pas.huffman;

/**
 * Represents an internal (non-leaf) node in a Huffman tree.
 * An internal node connects to two child nodes: left and right. It does not hold a symbol
 * but has a weight that is typically the sum of its children's weights.
 */
public class HuffInternalNode extends HuffBaseNode {

  private HuffBaseNode left;
  private HuffBaseNode right;
  /**
   * Constructs an internal node with the given left and right children and total weight.
   *
   * @param left the left child node
   * @param right the right child node
   * @param weight the combined weight of this internal node
   */

  public HuffInternalNode(HuffBaseNode left, HuffBaseNode right, int weight) {
    super(weight);
    this.left = left;
    this.right = right;
  }

  public HuffBaseNode getLeft() {
    return left;
  }

  public HuffBaseNode getRight() {
    return right;
  }

}
