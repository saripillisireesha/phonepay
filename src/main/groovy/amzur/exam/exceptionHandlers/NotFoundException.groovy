package amzur.exam.exceptionHandlers

class NotFoundException extends RuntimeException {

    NotFoundException(String message){
        super(message)
    }


}
