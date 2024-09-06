package amzur.exam.controller

import amzur.exam.exceptionHandlers.NotFoundException
import amzur.exam.model.BankAccountModel
import amzur.exam.service.BankAccountService
import io.micronaut.http.HttpResponse

//
//import amzur.exam.model.BankAccountModel
//import amzur.exam.service.BankAccountService
//import io.micronaut.http.annotation.Body
//import io.micronaut.http.annotation.Controller
//import io.micronaut.http.annotation.Get
//import io.micronaut.http.annotation.PathVariable
//import io.micronaut.http.annotation.Post
//
//import javax.inject.Inject
//
//@Controller("/banks")
//class BankAccountController {
//    @Inject
//    BankAccountService bankAccountService
//    @Post
//    def saveBanks(@Body BankAccountModel bankAccountModel){
//        return bankAccountService.saveAccount(bankAccountModel)
//    }
////    @Get
////    def getAllBanks() {
////        return bankAccountService.getAllData()
////    }
//
//    @Get("/{id}")
//    def show(@PathVariable Long id) {
//        // Get a specific bank account by ID
//         bankAccountService.getUserById(id)
//        return bankAccount
//    }
//
//}

import io.micronaut.http.annotation.*
import javax.inject.Inject

@Controller("/bank-accounts")
class BankAccountController {

    @Inject
    BankAccountService bankAccountService

    @Post("/{userId}")
    def createAccount(@PathVariable Long userId, @Body BankAccountModel accountModel) {
        return bankAccountService.createAccount(userId, accountModel)
        }

    @Get("/userId/{userId}")
    def getUser(@PathVariable Long userId) {
        return bankAccountService.getById(userId)
    }

    @Get("/user/{userId}")
    def getUserAccounts(@PathVariable Long userId) {
        return bankAccountService.getUserAccountDetails(userId)
    }

    @Put("/{accountId}")
    def updateAccount(@PathVariable Long accountId, @Body BankAccountModel accountModel) {
        return bankAccountService.updateAccount(accountId, accountModel)
    }

    @Get("/names")
    List<String> getBankNames() {
        return bankAccountService.getAllDistinctBankNames()

    }

    @Get("/accounts/{userId}")
    List<String> getAccountNumbers(@PathVariable Long userId) {
        return bankAccountService.listDistinctBankAccountNumbers(userId)


    }
    @Post("/sendMoney/{accountId}")
    def sendMoney(@PathVariable Long accountId, @Body Map<String, Object> request) {
        try {
            String upiPin = request.upiPin
            BigDecimal amount = new BigDecimal(request.amount as BigInteger)
            String receiverMobileNumber = request.receiverMobileNumber

            def transactionResult = bankAccountService.sendMoney(accountId, upiPin, amount, receiverMobileNumber)
            return "transaction successfully"
        } catch (NotFoundException e) {
            return (e.message)
        } catch (IllegalArgumentException e) {
            return (e.message)
        } catch (Exception e) {
            return "exception are:${e.message}"
          //  return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }
//    @Put("/setPrimary/{accountId}")
//    def  setPrimaryAccount(@PathVariable Long accountId) {
//             bankAccountService.setPrimaryAccount(accountId)
//            return "updated successfully"
//
//    }
    @Put("/setPrimary/{accountId}")
    HttpResponse<?> setPrimaryAccount(@PathVariable Long accountId) {
        try {
            def result = bankAccountService.setPrimaryAccount(accountId)
            return HttpResponse.ok(result)
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.message)
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }


    @Get("/balance/{accountId}")
    HttpResponse<BigDecimal> getLatestBalance(@PathVariable Long accountId) {
        try {
            BigDecimal balance = bankAccountService.getLatestBalance(accountId)
            return HttpResponse.ok(balance)
        } catch (IllegalArgumentException e) {
            return HttpResponse.notFound()
        }
    }

}



