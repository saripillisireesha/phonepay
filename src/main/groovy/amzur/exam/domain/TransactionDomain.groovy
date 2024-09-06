package amzur.exam.domain

//
//import grails.gorm.annotation.Entity
//import groovy.transform.ToString
//import java.math.BigDecimal
//import java.time.LocalDateTime
//
//@Entity
//class TransactionDomain {
//    String senderMobileNumber
//    String transactionId
//    BigDecimal amount
//    LocalDateTime transactionDate
//    String receiverMobileNumber
//
//    static belongsTo = [account: BankAccountDomain]
//
//    static constraints = {
//        transactionId unique: true, blank: false
//        amount min: 0.0
//        recipientMobileNumber blank: false
//    }

import grails.gorm.annotation.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString



@Entity
@ToString(includeNames = true)
@EqualsAndHashCode
class TransactionDomain {

    Long id
    Date date = new Date() // Automatically sets to current date and time by default
    BigDecimal amount
    String recipient

    static belongsTo = [user:UserDomain ,account:BankAccountDomain]
    static constraints = {
        date nullable: false

        amount nullable: false, min: 0.0G // Amount must be non-negative
        recipient nullable: false, blank: false
    }

    static mapping = {
        id generator: 'identity'
        amount scale: 2 // Store amount with two decimal places
    }



}