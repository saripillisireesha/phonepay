//package amzur.exam.service
//
//import amzur.exam.domain.UserDomain
//import grails.gorm.transactions.Transactional
//
//import javax.inject.Singleton
//import java.text.SimpleDateFormat
//
//@Singleton
//class TransactionService {
//    @Transactional
//    def transferMoney(String senderMobileNumber, String receiverMobileNumber, BigDecimal amount, String upiPin)
//    {
//        UserManagement sender = UserDomain.findByPhoneNumber(senderMobileNumber)
//        UserManagement receiver = UserDomain.findByPhoneNumber(receiverMobileNumber)
//
//        if (!sender || !receiver) {
//            return "Sender or Receiver NotFound"
//        }
//        AccountManagement senderAccount = AccountManagement.findByUserAndUpiPin(sender, upiPin)
//        if (!senderAccount) {
//            return "Invalid UPI Pin"
//        }
//
//        AccountManagement receiverAccount = AccountManagement.findByUser(receiver)
//
//        if (!receiverAccount) {
//            return "Receiver Not Found"
//        }
//        if (senderAccount.bankBalance < amount) {
//            return "Insufficient Balance"
//        }
//
//        def transaction
//        try {
//            senderAccount.bankBalance = senderAccount.bankBalance - amount
//            senderAccount.save()
//
//            receiverAccount.bankBalance = receiverAccount.bankBalance + amount
//            receiverAccount.save()
//
//            transaction = new TransactionManagement(
//                    sender: sender,
//                    receiver: receiver,
//                    sourceAccount: senderAccount,
//                    destinationAccount: receiverAccount,
//                    amount: amount,
//                    transactionDate: new Date(),
//                    status: 'Completed',
//                    transactionId: UUID.randomUUID().toString()
//            )
//            transaction.save()
//        } catch (Exception e) {
//            transaction?.status = 'Failed'
//            transaction?.save()
//            throw e
//        }
//        return new TransactionModel(
//                transactionId: transaction.transactionId,
//                amount: transaction.amount,
//                senderMobileNumber: senderMobileNumber,
//                receiverMobileNumber: receiverMobileNumber,
//                transactionDate: transaction.transactionDate,
//                status: transaction.status
//        )
//    }
//    @Transactional
//    def getTransactionHistory(Long phoneNumber) {
//        def transactions = TransactionManagement.createCriteria().list {
//            or {
//                sender {
//                    eq("phoneNumber", phoneNumber)
//                }
//                receiver {
//                    eq("phoneNumber", phoneNumber)
//                }
//            }
//        }
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//
//        def transactionHistory = transactions.collect { transaction ->
//            new TransactionModel(
//                    senderMobileNumber: transaction.sender.phoneNumber,
//                    receiverMobileNumber: transaction.receiver?.phoneNumber,
//                    amount: transaction.amount,
//                    transactionId: transaction.transactionId,
//                    transactionDate: formatter.format(new Date(transaction.transactionDate.time)), // Convert to formatted String
//                    status: transaction.status
//            )
//        }
//        return transactionHistory
//    }
//
//
//}
