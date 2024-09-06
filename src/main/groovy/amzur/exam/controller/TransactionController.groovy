//package amzur.exam.controller
//
//import amzur.exam.model.TransactionModel
//import amzur.exam.service.TransactionService
//import io.micronaut.http.annotation.*
//import javax.inject.Inject
//
//@Controller("/transactions")
//class TransactionController {
//
//    @Inject
//    TransactionService transactionService
//
//    @Post("/transfer")
//    def transferMoney(@Body TransactionModel transactionModel, @QueryValue String upiPin) {
//        return transactionService.transferMoney(transactionModel, upiPin)
//    }
//
//    @Get("/{accountId}")
//    def getTransactionHistory(@PathVariable Long accountId) {
//        return transactionService.getTransactionHistory(accountId)
//    }
//}
