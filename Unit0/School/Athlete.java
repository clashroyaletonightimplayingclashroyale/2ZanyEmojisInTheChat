package Unit0.School;

public class Athlete {
        
    private double _height;
    private int _age;
    private String _career;

    public Athlete(double height, int age, String career){
        _height = height;
        _age = age;
        _career = career;
    }


    public double getHeight(){
        return this._height;
    }

    public int getAge(){
        return this._age;
    }

    public String getCareer(){
        return this._career;
    }

    public void changeCareer(String career){
        if(getAge() > 30){
            System.out.println("Im too old to change jobs!");
        }else{
            this._career = career;
        }
        
    }

    public boolean isValid(){
        if(_height < 0){
            return false;
        }

        if(_age < 0 ){
            return false;
        }

        if(_career.trim().length() == 0 || _career == null){
            return false;
        }

        return true;
    }

}
