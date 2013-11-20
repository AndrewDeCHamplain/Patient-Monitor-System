public class Student extends Person
{
   private static int nextFreeNumber = 0;
   private int number;
   public Student(String first, String last) {super(first, last);};

   public int getNumber() { return -1; }

   // You decide if you need toString() and equals()
}
