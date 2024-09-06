package amzur.exam.exceptionHandlers


class ErrorResponse {
    int status
    String error
    String message
    ErrorResponse(int status, String error, String message){
        this.status=status
        this.error=error
        this.message=message

    }
}
