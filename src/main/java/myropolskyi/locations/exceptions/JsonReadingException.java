package myropolskyi.locations.exceptions;

/*Throws by reading/compose/writing json*/
public class JsonReadingException extends Exception{
    public JsonReadingException(String message) {
        super(message);
    }
}
