package amzur.exam.model

import amzur.exam.domain.UserDomain

//
//class BankAccountModel {
//   Long userId
//    String bankName
//    String accountNumber
//    String upiPin
//    Long transactionLimit
//    Long balance
//
//}



class BankAccountModel {
    //Long AccountId
    String bankName
    Long id
    String accountNumber
    String upiPin
    BigDecimal transactionLimit
    BigDecimal balance = BigDecimal.valueOf(100000) // Default balance

    Long userId // This will be used to associate with UserDomainManagement
    Boolean isPrimary = false // Default to false

}
