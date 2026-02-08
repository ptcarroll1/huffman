package pas.huffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A class that implements a Huffman tree.
 * From OpenDSA: https://opendsa-server.cs.vt.edu/ODSA/Books/Everything/html/Huffman.html
 */
public class HuffTree {

  private HuffBaseNode root; // The root of the Huffman tree. May be null.
  private HashMap<Byte, Integer> frequencies; // A mapping from bytes to their frequencies.
  private HashMap<Byte, BitSequence> codes; // Maps from bytes to their Huffman codes.

  /**
   * Creates a Huffman tree from a file. Used for zipping an uncompressed file.
   *
   * @param inFile the file to read from.
   */
  public HuffTree(File inFile) {

    // UNFINISHED
    this.frequencies = new HashMap<>();

    try {
      BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));
      int b;
      while ((b = in.read()) != -1) {
        byte value = (byte) b;
        frequencies.put(value, frequencies.getOrDefault(value, 0) + 1);
      }
      in.close();
    } catch (IOException e) {
      throw new RuntimeException();
    }

    this.root = buildTree(frequencies);


    this.codes = (HashMap<Byte, BitSequence>)
    HuffMapper.mapper(this.root, new HashMap<>(), new BitSequence());



    // Remember, a constructor should initialize all instance variables.
  }

  /**
   * Creates a Huffman tree from a frequency table. Used for unzipping a compressed file.
   *
   * @param frequencies the frequency table.
   */
  public HuffTree(HashMap<Byte, Integer> frequencies) {

    // UNFINISHED
    this.frequencies = new HashMap<>(frequencies);
    this.root = buildTree(this.frequencies);
    this.codes = null;
    // Remember, a constructor should initialize all instance variables.
  }

  /**
   * Returns the frequencies of the bytes in the file.
   *
   * @return a HashMap mapping bytes to their frequencies.
   */
  public HashMap<Byte, Integer> getFrequencies() {
    return new HashMap<>(frequencies);
  }

  /**
   * Encodes a file using Huffman coding.
   *
   * @param inFile the file to encode
   * @return a BitSequence representing the encoded file
   * @throws FileNotFoundException file not found.
   */

  public BitSequence encode(File inFile) throws FileNotFoundException {

    // UNFINISHED
    BitSequence result = new BitSequence();

    try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile))) {
      int b;
      while ((b = in.read()) != -1) {
        byte byteValue = (byte) b;
        BitSequence code = codes.get(byteValue);

        if (code == null) {
          throw new IllegalArgumentException();
        }

        result.appendBits(code);
      }
    } catch (IOException e) {
      throw new RuntimeException();
    }

    return result;
  }

  /**
 * Decodes a Huffman-encoded bit sequence and writes the original byte data to the specified file.
 *
 * @param encoding the BitSequence representing the compressed file contents
 * @param outFile the file to write the decompressed output to
 * @throws FileNotFoundException if the output file cannot be created
 */

  public void decode(BitSequence encoding, File outFile) throws FileNotFoundException {

    // UNFINISHED
    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile))) {
      HuffBaseNode curr = root;

      for (int bit : encoding) {
        if (!(curr instanceof HuffInternalNode)) {
          throw new IllegalStateException();
        }

        HuffInternalNode internal = (HuffInternalNode) curr;
        if (bit == 0) {
          curr = internal.getLeft();
        } else {
          curr = internal.getRight();
        }

        if (curr instanceof HuffLeafNode) {
          byte symbol = ((HuffLeafNode) curr).getSymbol();
          out.write(symbol);
          curr = root;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  private HuffBaseNode buildTree(HashMap<Byte, Integer> frequencies) {
    HuffBaseNode tmp1;

    HuffBaseNode tmp2;
    HuffBaseNode tmp3 = null;
    PriorityQueue<HuffBaseNode> pq = new PriorityQueue<>();

    for (Byte symbol : frequencies.keySet()) {
      pq.add(new HuffLeafNode(symbol, frequencies.get(symbol)));
    }

    while (pq.size() > 1) { // While two items left
      tmp1 = pq.poll();
      tmp2 = pq.poll();
      tmp3 = new HuffInternalNode(tmp1, tmp2, tmp1.getWeight() + tmp2.getWeight());
      pq.add(tmp3); // Return new tree to heap
    }
    return tmp3; // Return the tree
  }

}
