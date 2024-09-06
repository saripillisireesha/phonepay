package amzur.exam.domain

import grails.gorm.annotation.Entity

@Entity
//class BankAccountDomain {
//
//    String bankName
//    String accountNumber
//    String upiPin
//    Long transactionLimit
//    Long balance
//    static belongsTo=[user:UserDomain]
//
//}

import grails.gorm.annotation.Entity

@Entity
class BankAccountDomain {
        String bankName

    String accountNumber
    String upiPin // Store PINs securely (e.g., hashed)
    BigDecimal balance = BigDecimal.valueOf(100000) // Default balance
    Long transactionLimit
    Boolean isPrimary = false // Default to false

    static belongsTo = [user: UserDomain] // Foreign key relationship
 //   static fetchMode = [user: 'eager']

    static hasMany = [transactions: TransactionDomain]

    static constraints = {
        accountNumber unique: true, blank: false
        upiPin blank: false
        transactionLimit min: 0L
    }

}