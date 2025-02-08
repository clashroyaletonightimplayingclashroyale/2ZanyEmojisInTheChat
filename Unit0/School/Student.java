package Unit0.School;

public class Student{
    //fields
    private int _grade; 
    private String _name;
    private int _id; 

    //constructor
    public Student(int grade, String name, int id){
        _grade = grade;
        _name = name;
        _id = id; 
    }

    //methods
    public int getID(){
        return _id;
    }
    
    public void setName(String name){
        _name = name;
    }

    public String getName(){
        return _name;
    }
    public int getGrade(){
        return _grade; 
    }

    public boolean isValid(){
        if(_id < 1){
            return false;
        }

        if(_grade > 12 || _grade < 9){
            return false;
        }

        if(_name == null || _name.trim().length() == 0){
            return false;
        }

        return true;
    }

    public String toString(){
        return "id: " + getID() + ", name:" + getName() + ", grade:" + getGrade(); 
    }

}

