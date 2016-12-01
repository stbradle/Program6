/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.nio.file.*;
import org.junit.Rule;
import org.junit.rules.*;
import org.junit.runner.*;
/**
 * JUnit tests for Huffman Coding
 * 
 * @author Steven Bradley
 * @date November 29 2016
 * @version Program 6 - Huffman File Compression
 */
public class HuffmanTest
{
   private String testResult;

   @Rule
   public TestRule watcher;

   public HuffmanTest()
   {
      this.watcher = new TestWatcher() {
         @Override
         protected void starting(Description description) {
            System.out.printf("\b\bStarting test: %-60s", description.getMethodName());
         }
         @Override
         protected void finished(Description description){
            System.out.print(testResult);
         }
         @Override
         protected void succeeded(Description description){
            testResult = "SUCCESS\n";
         }
         @Override
         protected void failed(Throwable e, Description description){
            testResult = "FAILED\n";
         }
      }; 
   }
   /**
    * Test of compress method, of class Huffman.
    */
   @Test
   public void test01ToStringAtConstruction() throws Exception{
      Huffman h = new Huffman("simpleTestFrequencies.txt");
      String expected = "|acb|";
      String result = h.toString();
      assertEquals(expected, result);
   }

   @Test
   public void test02ToString_constuctionMoreFrequencies() throws Exception{
      Huffman h = new Huffman("longTestFrequencies.txt");
      String expected = "|aBcDefG!h|";
      String result = h.toString();
      assertEquals(expected,result);
   }
   /**
    * Test of decompress method, of class Huffman.
    */
   @Test
   public void test03Decompress_simple() throws Exception{
      Huffman h = new Huffman("simpleFrequencies.txt");
      h.decompress("simpleDecompressionTest.txt", "simpleDecompressionResult.txt");
      byte[] expected = Files.readAllBytes(new File("expectedDecompressionResult.txt").toPath());
      byte[] result = Files.readAllBytes(new File("simpleDecompressionResult.txt").toPath());
      assertArrayEquals(expected, result);
   }
   
   @Test
   public void testCompress_simple() throws Exception{
      Huffman h = new Huffman("simpleFrequencies.txt");
      h.compress("simpleCompressionTest.txt", "simpleCompressionResult.txt");
      byte[] expected = Files.readAllBytes(new File("expectedCompressionResult.txt").toPath());
      byte[] result = Files.readAllBytes(new File("simpleCompressionResult.txt").toPath());
      assertArrayEquals(expected, result);
   }
}
