import java.util.ArrayList;

public class Course
{
    private String id;
    private String name;
    private Professor professor;
    private ArrayList<Student>students;
    
    public Course( String id, String name) {
    	this.id=id;
    	this.name=name;
    };
    
    public String getId() { return this.id; }
    public String getName( ) { return this.name; }

    public String toString() { 
    	String thisstring=id+name;
    	
    	return thisstring; }
    
    
    public boolean equals(Object o) { 
    	if(! (o instanceof Course)){
    		return false;
    	}
    	Course other = (Course)o;
    	if(this.id==other.getId()&&this.name==other.name){
    	return true; 
    	}else return false;
    	}

    public void setProfessor(Professor professor) {
    	this.professor= professor;
    };
    
    public Professor getProfessor() { return this.professor; }

    public void addStudent( Student student ) {
    	this.students.add(student);
    };
    
    public void removeStudent( Student student ) {
    	this.students.remove(student);
    };
    public Student[] getStudents() { return (Student[]) this.students.toArray(); }
    
    
}
