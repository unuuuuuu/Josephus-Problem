import java.util.*;
import java.io.*;

public class JosephusSim {
   private PersonNode front;      // a PersonNode pointer that tracks first node in the list
   private int size;              // the number of people in the circle
   private int eliminationCount;  // the number to count to for elimination       

   public JosephusSim(String fileName) {
      try {
         // load names from the file in order, generating a singly linked list of PersonNodes
         Scanner file = new Scanner(new File(fileName));
         front = new PersonNode(file.nextLine());
         PersonNode temp = front;
         size += 1;
         while (file.hasNextLine()) {
            temp.next = new PersonNode(file.nextLine());
            temp = temp.next;
            size += 1;
            
         }
         
         file.close();
         
         // make the ring circular by attaching last node's next to front
         temp.next = front;
         for (int i = 0; i < size - 1; i++) {
            temp = temp.next;
         }
         temp.next = front;
         // generate, print, and save the random elimination count
         Random rand = new Random();
         eliminationCount = rand.nextInt(Math.max(1, size/2)) + 1;
         System.out.println("=== Elimination count is " + eliminationCount + " ===");

      } catch (FileNotFoundException e) {
         System.out.println("Something went wrong with " + fileName);
      }
   }
   
   // optional helper method for constructing the circle
   // private void add(String val) {
   // }
   
   public void eliminate() {
      // count to the elimination count
      PersonNode temp = front;
      while (temp.next != front) {
         temp = temp.next;
      }

      for (int i = 1; i < eliminationCount; i++) {
         temp = temp.next;
      }
      // print who will be eliminated
      System.out.println(temp.next.name + " eliminated!");
      // eliminate the person and update "front" of the circle and size
      temp.next = temp.next.next;
      front = temp.next;
      size--;
   }
   
   public boolean isOver() {
      // check if there's only one person left in the circle
      return size == 1;
   }
   
   public String toString() {
      // if there's only one person left, print them as the last survivor
      if (isOver()) {
         return front.name + " is the last survivor!";
      }
      String s = "Remaining survivors: ";
      PersonNode temp = front;
      // if many, print survivors (watch out for infinite loop since list is circular)
      for (int i = 1; i < size + 1; i++) {
         s += i + "-" + temp.name;
         if (i < size) {
            s += ", ";
         }
         temp = temp.next;
      }
      return s;
   }

}