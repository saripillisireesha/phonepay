package amzur.exam.domain
//
import grails.gorm.annotation.Entity
//
//import javax.persistence.OneToMany
//
//
//@Entity
//class UserDomain {
//    String firstName
//    String lastName
//    String email
//    String pin
//    Long mobileNumber
//static hasMany=[banks:BankAccountDomain]
//
//    static mapping = {
//        id Generator: 'increment'
//    }
//    static constraints = {
//        firstName nullable: false, blank: false
//        lastName nullable: false
//        email nullable: false, blank: false, unique: true
//        pin nullable: false, blank: false, unique: true
//        mobileNumber nullable: false
//    }
//}




@Entity
class UserDomain {
    String mobileNumber
    String pin
    String firstName
    String lastName
    String email

    static hasMany = [accounts: BankAccountDomain]

    static constraints = {
        mobileNumber unique: true, blank: false
        pin blank: false
        firstName blank: false
        lastName blank: false
        email email: true, blank: false
    }
}