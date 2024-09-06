package amzur.exam.exceptionHandlers

class AccountNotFoundException extends RuntimeException {

    AccountNotFoundException(String message){
        super(message)
    }


}
