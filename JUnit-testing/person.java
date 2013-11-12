import java.util.ArrayList;
public class Person
{
   private Name name;
   private Address address;
   private ArrayList<Course> courses;
   
   public Person( String first, String last) {};
   
   public void setLastName( String name) {};
   public String getLastName() { return null; };
   public String getFirstName() { return null; };
   public String getFullName() { return null; };

   public void setAddress(Address address) {};
   public Address getAddress() { return null; };

   public Course[] getCourses() { return null; }
   public void addCourse( Course course ) {};
   public void remove(Course course) {};

    public String toString() { return null; }
    public boolean equals(Object o) { return false; }

}     
