import java.util.*;
import java.io.*;

/**
 * Class that uses Huffman algorithm for lossless compression
 * 
 * @author Steven Bradley
 * @date November 28 2016
 * @version Program 6 - Huffman Encoding
 */
public class Huffman
{
   //fields
   private PriorityQueue huffmanTree;
   private HashMap occurenceTracker;
   private ArrayList<Node> charNodes;
   //constructor
   public Huffman(String filename)throws FileNotFoundException, IOException{
      File file = new File(filename);
      FileReader fileReader = new FileReader(file);
      int nextChar = fileReader.read();
      occurenceTracker = new HashMap(257); //hashMap size is nextPrime(256)
      while(nextChar != -1){//while there is a character left in the file
         int hashKey = ((Integer)nextChar).hashCode();
         if(occurenceTracker.get(hashKey) == null){ //if character is not in HashMap
            occurenceTracker.put(hashKey, new Node((char)nextChar)); //create new node for current Character
         }
         else{//if character is in hashMap 
            ((Node)occurenceTracker.get(hashKey)).occurences++; //update occurences 
         }
         nextChar = fileReader.read(); //move to next character in frequency file
      }
      Collection<Node> nodes = occurenceTracker.values();
      huffmanTree = new PriorityQueue();
      for(Node n: nodes){ //Adding all nodes to Priority Queue
         huffmanTree.enqueue(n);
      }
      charNodes = new ArrayList(256);
      while(huffmanTree.size() > 0){
         //Algorithm for creating huffmanTree from Priority Queue
         Node node1 = (Node)(huffmanTree.dequeue());
         Node node2 = (Node)(huffmanTree.dequeue());
         char asciiValue =  (char) Math.min(node1.character, node2.character);
         int frequency = node1.occurences + node2.occurences;
         //Creates Parent Node for 2 characters
         Node parent = new Node(asciiValue, frequency);
         node1.parent = parent;
         node2.parent = parent;
         parent.left = (node1.compareTo(node2) < 0) ? node1:node2;
         parent.right = (node1.compareTo(node2) > 0) ? node1:node2;
         if(node1.hasChar()){
            charNodes.add(node1);
         }
         if(node2.hasChar()){
            charNodes.add(node2);
         }
         //update all children binary
         huffmanTree.enqueue(parent);
         if(huffmanTree.size() == 1){
            updateAllBinary();
//            for(Node n: charNodes)
//               System.out.println(n);
            break;
         }
      }
   }
   
   public void compress(String infileName, String outfileName)throws FileNotFoundException, IOException{
      
      throw new RuntimeException("Finish this method");
   }
   public void decompress(String infileName, String outfileName)throws FileNotFoundException, IOException{
      FileReader fr = new FileReader(infileName);  //Reads Binary String from infile
      FileWriter fw = new FileWriter(outfileName); //Writes Character String to outfile
      fw.write(binaryToChar(fr).toString());
      fw.close(); //MUST CLOSE WRITER TO WRITE TO FILE
      fr.close(); //CLOSE TO PREVENT MEMORY LEAK
   }
   
   @Override
   public String toString(){
      return '|' + (preorderHuffmanString(new StringBuilder(), (Node)(huffmanTree.peek()))).toString() + '|';
   }
   
   //private methods
   private StringBuilder binaryToChar(FileReader fileReader) throws IOException{
      Node node;
      StringBuilder alphaString = new StringBuilder(); //StringBuilder used for efficiency with large strings
      int next = fileReader.read(); //initialize next character
      while(next != -1){ //While there is still a character in the inputStream
         node = (Node)huffmanTree.peek(); //Set node to the root
         while(!node.hasChar()){ //While current Node does not contain a character
            node = ((char)next == '1') ? node.right : node.left; //if next is '1' reassign node to right otherwise reassign to left
            next = fileReader.read(); //update binary character
         }
         alphaString.append(node.character); //Once a node that contains a character is reached, character is appended to StringBuilder
      }
      return alphaString;
   }
   private StringBuilder readString(String string) throws IOException{
      InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(string.getBytes())); //Allows me to read a string by character
      BufferedReader stringReader = new BufferedReader(in); //reads InputStream by character
      int nextChar = stringReader.read(); //initialize next character
      StringBuilder outString;
      while(nextChar != -1){ // while there is an unprocessed character in the string
         for(Node n: charNodes){
            if(n.character.equals(nextChar)){
              // outString.append(n);
            }
         }
      }
      //return binary string
      throw new RuntimeException("Finish this method");
   }
   private StringBuilder preorderHuffmanString(StringBuilder s, Node node){
      if(node.hasChar()){
         s.append(node.character);
         return s;
      }
      else{
         preorderHuffmanString(s, node.left);
         preorderHuffmanString(s, node.right);
      }
      return s;
   }
   private void updateAllBinary(){
      ((Node)huffmanTree.peek()).updateBinary();
   }
   //private Class
   private class Node implements Comparable<Node>{
      //fields
      private final Character character;
      private Node right;
      private Node left;
      private Integer occurences;
      private StringBuilder binary;
      private Node parent;
      public Node(Character character){
         this.character = character;
         occurences = 1;
         binary = new StringBuilder("");
      }
      public Node(Character character, Integer occurences){
         this.character = character;
         this.occurences = occurences;
         binary = new StringBuilder("");
      }
      
      public boolean hasChar(){
         return (left == null && right == null);
      }
      public void updateLeftBinary(StringBuilder current){
         System.out.println("Current Binary: " + binary);
         left.binary = current;
         left.binary.append('0');
      }
      public void updateRightBinary(StringBuilder current){
         System.out.println("Current Binary: " + binary);
         right.binary = current;
         right.binary.append('1');
      }
      public void updateBinary(){
         System.out.println("Next node: " + occurences + " Binary: " + binary);
//         if(!hasChar()){
//            System.out.println("Left node: " + left.occurences + "\nRight Node: " + right.occurences);
//            StringBuilder currentBinary = this.binary;
//            updateLeftBinary(currentBinary);
//            left.updateBinary();
//            updateRightBinary(currentBinary);
//            right.updateBinary();
//         }
      }
      @Override
      public int compareTo(Node other){
         int retVal = (this.occurences).compareTo(other.occurences);
         if(retVal == 0){
            return (this.character).compareTo(other.character);
         }  
         return retVal;
      }
      @Override 
      public String toString(){
         return("\n-------Node--------\nCharacter: " + this.character + "\nOccurences: " + this.occurences + "\nBinary: " + this.binary);
      }
   }
}
