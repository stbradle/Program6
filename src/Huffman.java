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
   //constructor
   public Huffman(String filename)throws FileNotFoundException, IOException{
      File file = new File(filename);
      Scanner fileScanner = new Scanner(file);
      FileReader fileReader = new FileReader(file);
      char nextChar = (char)fileReader.read();
      occurenceTracker = new HashMap(257);
      //while there is a character left in the file
      while(nextChar != -1){
         int hashKey = ((Character)nextChar).hashCode();
         //Checks if character is already in HashMap
         if(occurenceTracker.get(hashKey) == null){
            occurenceTracker.put(hashKey, new Node(nextChar));
         }
         else{
            ((Node)occurenceTracker.get(hashKey)).occurences++;
         }
         nextChar = (char)(fileReader.read());
      }
      //Reads through the file
      
//      while(fileScanner.hasNextLine()){
//         Scanner lineScanner = new Scanner(fileScanner.nextLine());
//         lineScanner.useDelimiter("");
//         //Reads through each line of the file
//         while(lineScanner.hasNext()){
//            //Checks to see if Character is already in HashMap
//            Character character = (lineScanner.next()).charAt(0);
//            int hashKey = character.hashCode();
//            //If Character is not in HashMap then Make new Node for chracter
//            if(occurenceTracker.get(hashKey) == null){
//               Node node = new Node(character);
//               occurenceTracker.put(hashKey, node);
//            }
//            //If it is already in the HashMap then retrieve Node object and increment occurences
//            else{
//               Node node = (Node)(occurenceTracker.get(hashKey));
//               node.occurences++;
//            }
//         }
      Collection<Node> nodes = occurenceTracker.values();
      huffmanTree = new PriorityQueue();
      //After Counting Occurences Create Huffman tree 
      for(Node n: nodes){
         huffmanTree.enqueue(n);
      }
      while(huffmanTree.size() > 0){
         Node node1 = (Node)(huffmanTree.dequeue());
         Node node2 = (Node)(huffmanTree.dequeue());
         char asciiValue =  (char) Math.min(node1.character, node2.character);
         int frequency = node1.occurences + node2.occurences;
         //Creates Parent Node for 2 characters
         Node parent = new Node(asciiValue, frequency);
         parent.left = (node1.compareTo(node2) < 0) ? node1:node2;
         parent.right = (node1.compareTo(node2) > 0) ? node1:node2;
         huffmanTree.enqueue(parent);
         if(huffmanTree.size() == 1)
            break;
      }
   }
   
   public void compress(String infileName, String outfileName)throws FileNotFoundException, IOException{
      
      throw new RuntimeException("Finish this method");
   }
   public void decompress(String infileName, String outfileName)throws FileNotFoundException, IOException{
      FileReader fr = new FileReader(infileName);
      Character next = (char)fr.read();
      FileWriter fw = new FileWriter(outfileName);
      while(next != -1){
         fw.write(readBinary(next));
         next = (char)(fr.read());
      }
   }
   
   @Override
   public String toString(){
      return '|' + (preorderString(new StringBuilder(), (Node)(huffmanTree.peek()))).toString() + '|';
   }
   
   //private methods
   private Character readBinary(char character){
      Node node = (Node)huffmanTree.peek();
      while(!(node.hasChar())){
         if(character == '1'){
            node = node.right;
         }
         else{
            node = node.left;
         }
      }
      return node.character;
   }
   private String readString(String string) throws IOException{
      InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(string.getBytes()));
      BufferedReader stringReader = new BufferedReader(in);
      int nextChar = stringReader.read();
      while(nextChar != -1){
         
      }
      throw new RuntimeException("Finish this method");
   }
   private StringBuilder preorderString(StringBuilder s, Node node){
      while(!(node.hasChar())){
         preorderString(s, node.left);
         preorderString(s, node.right);
      }
      s.append(node.character);
      return s;
   }
   //private Class
   private class Node implements Comparable<Node>{
      //fields
      private final Character character;
      private Node right;
      private Node left;
      private Integer occurences;
      private Character ascii;
      
      public Node(Character character){
         this.character = character;
         occurences = 1;
      }
      public Node(Character ascii, Integer occurences){
         this.ascii = ascii;
         this.occurences = occurences;
         character = null;
      }
      
      public boolean hasChar(){
         return !(character==null);
      }
      @Override
      public int compareTo(Node other){
         int retVal = (this.occurences).compareTo(other.occurences);
         if(retVal == 0){
            return (this.character).compareTo(other.character);
         }
         return retVal;
      }
   }
}
