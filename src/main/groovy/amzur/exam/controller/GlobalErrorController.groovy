package amzur.exam.controller

import amzur.exam.exceptionHandlers.AccountNotFoundException
import amzur.exam.exceptionHandlers.BankAccountNotFoundException
import amzur.exam.exceptionHandlers.ErrorResponse
import amzur.exam.exceptionHandlers.InvalidPinException
import amzur.exam.exceptionHandlers.NotFoundException
import amzur.exam.exceptionHandlers.UserNotFoundException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error

@Controller
class GlobalErrorController {
    @Error(global = true, exception=UserNotFoundException)
    HttpResponse<ErrorResponse> HandlerUserNotFoundException(UserNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.NOT_FOUND.code,"Not Found", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
    @Error(global = true, exception= InvalidPinException)
    HttpResponse<ErrorResponse> HandlerinvalidPinException(InvalidPinException ex){
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.NOT_FOUND.code,"Not Found", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
    @Error(global = true, exception= BankAccountNotFoundException)
    HttpResponse<ErrorResponse> HandlerBankAccount(BankAccountNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.NOT_FOUND.code,"Not Found", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
    @Error(global = true, exception= AccountNotFoundException)
    HttpResponse<ErrorResponse> HandlerAccount(AccountNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.NOT_FOUND.code,"Not Found", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
    @Error(global = true, exception= NotFoundException)
    HttpResponse<ErrorResponse> HandlerAccountNotFound(NotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(HttpStatus.NOT_FOUND.code,"Not Found", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }


}
