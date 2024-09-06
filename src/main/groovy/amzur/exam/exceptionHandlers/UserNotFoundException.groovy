package amzur.exam.exceptionHandlers


class UserNotFoundException extends RuntimeException{
    UserNotFoundException(String message){
        super(message)
    }
}
