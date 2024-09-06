package amzur.exam.model

import amzur.exam.domain.TransactionDomain

//
//class TransactionModel {
//    Long fromAccountId
//    Long toAccountId
//    BigDecimal amount
//    String recipientMobileNumber
//}


import java.text.SimpleDateFormat

class TransactionModel {
    Long id
    Date date
    BigDecimal amount
    String recipient

    // Method to convert a Transaction domain object to TransactionModel
    static TransactionModel toModel(TransactionDomain transaction) {
        if (transaction == null) {
            return null
        }
        return new TransactionModel(
                id: transaction.id,
                date: transaction.date,
                amount: transaction.amount,
                recipient: transaction.recipient
        )
    }

    // Method to convert a list of Transaction domain objects to a list of TransactionModel
    static List<TransactionModel> toModelList(List<TransactionDomain> transactions) {
        return transactions.collect { transaction -> toModel(transaction) }
    }
}