//package amzur.exam.service
//
//import amzur.exam.domain.BankAccountDomain
//import amzur.exam.domain.TransactionDomain
//import amzur.exam.domain.UserDomain
//import amzur.exam.exceptionHandlers.AccountNotFoundException
//import amzur.exam.exceptionHandlers.NotFoundException
//import amzur.exam.model.BankAccountModel
//import amzur.exam.model.UserAccountDetails
//import amzur.exam.model.UserModel
//import grails.gorm.transactions.Transactional
//import javax.inject.Singleton
//
//@Singleton
//class BankAccountService {
//
//    @Transactional
//    BankAccountModel createAccount(Long userId, BankAccountModel accountModel) {
//        UserDomain user = UserDomain.findById(userId)
//        if (!user) {
//            throw new IllegalArgumentException("User with id ${userId} not found")
//        }
//
//        BankAccountDomain existingAccount = BankAccountDomain.findByAccountNumber(accountModel.accountNumber)
//        if (existingAccount) {
//            throw new IllegalArgumentException("Account with number ${accountModel.accountNumber} already exists")
//        }
//
//        BankAccountDomain account = new BankAccountDomain(
//                bankName: accountModel.bankName,
//                accountNumber: accountModel.accountNumber,
//                upiPin: accountModel.upiPin, // Consider hashing the UPI PIN before saving
//                balance: accountModel.balance,
//                transactionLimit: accountModel.transactionLimit,
//                user: user // Set the user association correctly
//        )
//        account.save(flush: true)
//
//        return convertEntityToModel(account)
//    }
//
//    @Transactional(readOnly = true)
//    BankAccountModel getById(Long id) {
//        BankAccountDomain bankAccount = BankAccountDomain.findById(id)
//        if (!bankAccount) {
//            throw new AccountNotFoundException("Bank account not found")
//        }
//
//
//        return convertEntityToModel(bankAccount)
//    }
//
//        @Transactional(readOnly = true)
//        UserAccountDetails getUserAccountDetails(Long userId) {
//            def user = UserDomain.findById(userId)
//                    if(!user){
//                new IllegalArgumentException("User with id ${userId} not found")
//            }
//
//            def accounts = BankAccountDomain.findAllByUser(user)
//            def accountModels = accounts.collect { bankAccount ->
//                convertEntityToModel(bankAccount)
//            }
//
//            def userModel = convertUserEntityToModel(user)
//            return new UserAccountDetails(user: userModel, accounts: accountModels)
//        }
//
//         UserModel convertUserEntityToModel(UserDomain user) {
//            new UserModel(
//                   id: user.id,
//                    firstName: user.firstName,
//                    lastName:user.lastName,
//                    mobileNumber: user.mobileNumber,
//                    email: user.email
//                    // Populate other fields
//            )
//            // return UserModel
//        }
//        @Transactional(readOnly = true)
//        List<String> getAllDistinctBankNames() {
//            return BankAccountDomain.createCriteria().list {
//                projections {
//                    distinct("bankName")
//                }
//            }
//
//    }
//    @Transactional(readOnly = true)
//    List<String> listDistinctBankAccountNumbers(Long userId) {
//        def bankAccountNumbers = BankAccountDomain.createCriteria().list {
//            projections {
//                distinct("accountNumber")
//            }
//            eq("user", UserDomain.load(userId)) // Adjust according to your user relation
//        }
//        return bankAccountNumbers
//    }
//
//    @Transactional
//    def sendMoney(Long accountId, String upiPin, BigDecimal amount, String receiverMobileNumber) {
//        // Step 1: Validate the sender's account and UPI PIN
//        def senderAccount = BankAccountDomain.findById(accountId)
//        if (!senderAccount) {
//            throw new NotFoundException("Sender's account not found")
//        }
//        if (senderAccount.upiPin != upiPin) {
//            throw new IllegalArgumentException("Invalid UPI PIN")
//        }
//        if (senderAccount.amount < amount) {
//            throw new IllegalArgumentException("Insufficient balance")
//        }
//
//        // Step 2: Find the receiver by mobile number and get their primary account
//        def receiverUser = UserDomain.findByMobileNumber(receiverMobileNumber)
//        if (!receiverUser) {
//            throw new NotFoundException("Receiver not found")
//        }
//        def receiverPrimaryAccount = BankAccountDomain.findByUserAndIsPrimary(receiverUser, true)
//        if (!receiverPrimaryAccount) {
//            throw new NotFoundException("Receiver's primary account not found")
//        }
//
//        // Step 3: Deduct the amount from the sender's account
//        senderAccount.amount = senderAccount.amount - amount
//        senderAccount.save(flush: true)
//
//        // Step 4: Add the amount to the receiver's primary account
//        receiverPrimaryAccount.amount = receiverPrimaryAccount.amount + amount
//        receiverPrimaryAccount.save(flush: true)
//        def transaction = new TransactionDomain(
//                account: senderAccount,
//                user: senderAccount.user,  // The user who owns the sender account
//                amount: amount,
//                recipient: receiverMobileNumber
//        )
//        transaction.save(flush: true)
//        // Return a success message or object indicating the transaction is complete
//        return [message: "Money sent successfully", senderBalance: senderAccount.amount, receiverBalance: receiverPrimaryAccount.amount]
//
//
//
//}
//
//
//
////    @Transactional
////    BankAccountDomain updateAccount(Long accountId, BankAccountModel accountModel) {
////        BankAccountDomain account = BankAccountDomain.findById(accountId)
////        if (account) {
////            account.upiPin = accountModel.upiPin // Consider hashing the UPI PIN before saving
////            account.transactionLimit = accountModel.transactionLimit
////            account.save(flush: true)
////            return account
////        } else {
////            throw new AccountNotFoundException("Account with id ${accountId} not found")
////        }
////    }
//
//     BankAccountModel convertEntityToModel(BankAccountDomain bankAccount) {
//        BankAccountModel bankAccountModel = new BankAccountModel()
//        bankAccountModel.id = bankAccount.id
//         bankAccountModel.bankName=bankAccount.bankName
//        bankAccountModel.accountNumber = bankAccount.accountNumber
//        bankAccountModel.balance = bankAccount.balance
//        bankAccountModel.transactionLimit = bankAccount.transactionLimit
//        bankAccountModel.userId = bankAccount.user?.id // Safeguard against null user
//        return bankAccountModel
//    }
//}
package amzur.exam.service

import amzur.exam.domain.BankAccountDomain
import amzur.exam.domain.TransactionDomain
import amzur.exam.domain.UserDomain
import amzur.exam.exceptionHandlers.AccountNotFoundException
import amzur.exam.exceptionHandlers.NotFoundException
import amzur.exam.model.BankAccountModel
import amzur.exam.model.UserAccountDetails
import amzur.exam.model.UserModel
import grails.gorm.transactions.Transactional
import javax.inject.Singleton

@Singleton
class BankAccountService {


    @Transactional
    BankAccountModel createAccount(Long userId, BankAccountModel accountModel) {
        UserDomain user = UserDomain.findById(userId)
        if (!user) {
            throw new IllegalArgumentException("User with id ${userId} not found")
        }

        BankAccountDomain existingAccount = BankAccountDomain.findByAccountNumber(accountModel.accountNumber)
        if (existingAccount) {
            throw new IllegalArgumentException("Account with number ${accountModel.accountNumber} already exists")
        }

        BankAccountDomain account = new BankAccountDomain(
                bankName: accountModel.bankName,
                accountNumber: accountModel.accountNumber,
                upiPin: accountModel.upiPin, // Consider hashing the UPI PIN before saving
                balance: accountModel.balance,
                transactionLimit: accountModel.transactionLimit,
                user: user // Set the user association correctly
        )
        account.save(flush: true)

        return convertEntityToModel(account)
    }
    @Transactional(readOnly = true)
    BankAccountModel getById(Long id) {
        BankAccountDomain bankAccount = BankAccountDomain.findById(id)
        if (!bankAccount) {
            throw new AccountNotFoundException("Bank account not found")
        }

        return convertEntityToModel(bankAccount)
    }

    @Transactional(readOnly = true)
    UserAccountDetails getUserAccountDetails(Long userId) {
        def user = UserDomain.findById(userId)
        if (!user) {
            throw new IllegalArgumentException("User with id ${userId} not found")
        }

        def accounts = BankAccountDomain.findAllByUser(user)
        def accountModels = accounts.collect { bankAccount ->
            convertEntityToModel(bankAccount)
        }

        def userModel = convertUserEntityToModel(user)
        return new UserAccountDetails(user: userModel, accounts: accountModels)
    }

    UserModel convertUserEntityToModel(UserDomain user) {
        new UserModel(
                id: user.id,
                firstName: user.firstName,
                lastName: user.lastName,
                mobileNumber: user.mobileNumber,
                email: user.email
                // Populate other fields
        )
    }

    @Transactional(readOnly = true)
    List<String> getAllDistinctBankNames() {
        return BankAccountDomain.createCriteria().list {
            projections {
                distinct("bankName")
            }
        }
    }

    @Transactional(readOnly = true)
    List<String> listDistinctBankAccountNumbers(Long userId) {
        def bankAccountNumbers = BankAccountDomain.createCriteria().list {
            projections {
                distinct("accountNumber")
            }
            eq("user", UserDomain.load(userId)) // Adjust according to your user relation
        }
        return bankAccountNumbers
    }
    @Transactional
    def setPrimaryAccount(Long accountId) {
        // Find the account by ID
        def account = BankAccountDomain.findById(accountId)
        if (!account) {
            throw new IllegalArgumentException("Account not found")
        }

        // Fetch the user associated with this account
        def user = account.user

        // Set all other accounts of this user as non-primary
        def allAccounts = BankAccountDomain.findAllByUser(user)
        allAccounts.each { acc ->
            acc.isPrimary = acc.id == accountId
            acc.save(flush: true) // Persist changes
        }

        return [success: true, message: "Primary account set successfully."]
    }


    @Transactional
    def sendMoney(Long accountId, String upiPin, BigDecimal amount, String receiverMobileNumber) {
        // Step 1: Validate the sender's account and UPI PIN
        def senderAccount = BankAccountDomain.findById(accountId)
        if (!senderAccount) {
            throw new NotFoundException("Sender's account not found")
        }
        if (senderAccount.upiPin != upiPin) {
            throw new IllegalArgumentException("Invalid UPI PIN")
        }
        if (senderAccount.balance < amount) { // Changed amount to balance
            throw new IllegalArgumentException("Insufficient balance")
        }

        // Step 2: Find the receiver by mobile number and get their primary account
        def receiverUser = UserDomain.findByMobileNumber(receiverMobileNumber)
        if (!receiverUser) {
            throw new NotFoundException("Receiver not found")
        }
        def receiverPrimaryAccount = BankAccountDomain.findByUserAndIsPrimary(receiverUser, true)
        if (!receiverPrimaryAccount) {
            throw new NotFoundException("Receiver's primary account not found")
        }

        // Step 3: Deduct the amount from the sender's account
        senderAccount.balance -= amount // Changed amount to balance
        senderAccount.save(flush: true)

        // Step 4: Add the amount to the receiver's primary account
        receiverPrimaryAccount.balance += amount // Changed amount to balance
        receiverPrimaryAccount.save(flush: true)

        def transaction = new TransactionDomain(
                account: senderAccount,
                user: senderAccount.user,  // The user who owns the sender account
                amount: amount,
                recipient: receiverMobileNumber
        )
        transaction.save(flush: true)

        // Return a success message or object indicating the transaction is complete
        return [message: "Money sent successfully", senderBalance: senderAccount.balance, receiverBalance: receiverPrimaryAccount.balance]
    }

    BankAccountModel convertEntityToModel(BankAccountDomain bankAccount) {
        new BankAccountModel(
                id: bankAccount.id,
                bankName: bankAccount.bankName,
                accountNumber: bankAccount.accountNumber,
                balance: bankAccount.balance, // Changed amount to balance
                transactionLimit: bankAccount.transactionLimit,
                userId: bankAccount.user?.id // Safeguard against null user
        )
    }

    @Transactional(readOnly = true)
    BigDecimal getLatestBalance(Long accountId) {
        def account = BankAccountDomain.findById(accountId)
        if (!account) {
            throw new IllegalArgumentException("Account with id ${accountId} not found")
        }
        return account.balance
    }

}
