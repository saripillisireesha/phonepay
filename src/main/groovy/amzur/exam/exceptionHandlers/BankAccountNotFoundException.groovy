package amzur.exam.exceptionHandlers

class BankAccountNotFoundException extends RuntimeException {
    BankAccountNotFoundException(String message){
        super(message)
    }


}
