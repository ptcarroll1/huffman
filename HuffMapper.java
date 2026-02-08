package pas.huffman;

import java.util.Map;


/**
 * Builds a Huffman encoding map from a Huffman tree.
 */
public class HuffMapper {

  /**
   * Recursively creates a map that assigns each byte (symbol) a BitSequence
   * based on its path in the Huffman tree.
   *
   * @param node the current node in the Huffman tree
   * @param map the map to store symbol-to-BitSequence mappings
   * @param bits the current BitSequence path to this node
   * @return the completed map with all symbol encodings
   */

  public static Map<Byte, BitSequence> mapper(
      HuffBaseNode node, Map<Byte, BitSequence> map, BitSequence bits) {


    if (node instanceof HuffLeafNode) {
      HuffLeafNode leaf = (HuffLeafNode) node; // casting node as leaf node so I can use getSymbol()
      map.put(leaf.getSymbol(), bits);
    } else if (node instanceof HuffInternalNode) {
      HuffInternalNode internal = (HuffInternalNode) node;
      // casting node as internal so I can use left and right methods

      if (internal.getLeft() != null) {
        BitSequence leftBits = new BitSequence(bits);
        leftBits.appendBit(0);
        mapper(internal.getLeft(), map, leftBits);
      }

      if (internal.getRight() != null) {
        BitSequence rightBits = new BitSequence(bits);
        rightBits.appendBit(1);
        mapper(internal.getRight(), map, rightBits);
      }
    }

    return map;
  }
}
