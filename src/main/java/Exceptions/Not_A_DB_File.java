package Exceptions;

public class Not_A_DB_File extends  RuntimeException{

    public String toString(){
        return "The argument is not a DB file!" ;
    }
}
