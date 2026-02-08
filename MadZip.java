package pas.huffman;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * A class that compresses and decompresses files using Huffman coding.
 * Honor Code Statement: All the work I did on this project was my own.
 */
public class MadZip {

  /**
   * Compresses a file and writes the output to another file.
   *
   * @param inFile file that needs to be compressed
   * @param outFile the compressed file
   * @throws IOException thrown if there is an error during reading or writing
   */
  public static void zip(File inFile, File outFile) throws IOException {


    // TODO
    HashMap<Byte, Integer> frequencyMap = new HashMap<>();
    try (InputStream input = new BufferedInputStream(new FileInputStream(inFile))) {
      int b;
      while ((b = input.read()) != -1) {
        byte byteValue = (byte) b;
        frequencyMap.put(byteValue, frequencyMap.getOrDefault(byteValue, 0) + 1);
      }
    }
    if (frequencyMap.isEmpty()) {
      new FileOutputStream(outFile).close();
      return;
    }
    HuffTree tree = new HuffTree(inFile);
    BitSequence encodedTree = tree.encode(inFile);
    HuffmanSave hs = new HuffmanSave(encodedTree, tree.getFrequencies());
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFile))) {
      out.writeObject(hs);
    }
  }

  /**
   * Decompresses a file and writes the output to another file.
   *
   * @param inFile file that needs to be decompressed
   * @param outFile the decompressed file
   * @throws IOException thrown if there is an error during reading or writing
   * @throws ClassNotFoundException thrown if an issue occurs during deserialization
   */
  public static void unzip(File inFile, File outFile) throws IOException, ClassNotFoundException {
    // TODO
    if (inFile.length() == 0) {
      new FileOutputStream(outFile).close();
      return;
    }
    HuffmanSave hs;
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(inFile))) {
      hs = (HuffmanSave) in.readObject();
    }
    HuffTree tree = new HuffTree(hs.getFrequencies());
    tree.decode(hs.getEncoding(), outFile);
  }
}
