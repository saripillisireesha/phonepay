package amzur.exam.service

import amzur.exam.domain.UserDomain
import amzur.exam.exceptionHandlers.InvalidPinException
import amzur.exam.exceptionHandlers.UserNotFoundException
import amzur.exam.model.UserModel
import grails.gorm.transactions.Transactional

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService {

    // Method to validate PIN
    private boolean isValidPin(String pin) {
        return pin?.length() == 6 && pin.isNumber()
    }

    @Transactional
    def saveUsers(UserModel userModel) {
        if (!isValidPin(userModel.pin)) {
            throw new InvalidPinException("PIN must be exactly 6 digits long.")
        }

        UserDomain userDomain = new UserDomain()
        userDomain.id = userModel.id
        userDomain.firstName = userModel.firstName
        userDomain.lastName = userModel.lastName
        userDomain.email = userModel.email
        userDomain.pin = userModel.pin
        userDomain.mobileNumber = userModel.mobileNumber
        userDomain.save(flush: true) // Ensure the save operation is immediately flushed to the database
        return userDomain
    }

    @Transactional
    def getAllUsers() {
        List<UserDomain> userDomainList = UserDomain.findAll()
        userDomainList.each { user ->
            user.accounts.size()  // Force initialization of the accounts collection
        }
        return userDomainList
    }


    @Transactional
    def getById(Long id) {
        UserDomain userDomain = UserDomain.get(id)
        if (userDomain) {
            userDomain.accounts.size()  // Force initialization of the accounts collection
            return userDomain
        } else {
            throw new UserNotFoundException("User with id ${id} is not found")
        }
    }

    @Transactional
    def updateUsers(Long id, UserModel userModel) {
        if (!isValidPin(userModel.pin)) {
            throw new InvalidPinException("PIN must be exactly 6 digits long.")
        }

        UserDomain userDomain = UserDomain.get(id)
        if (userDomain) {
            userDomain.firstName = userModel.firstName
            userDomain.lastName = userModel.lastName
            userDomain.email = userModel.email
            userDomain.pin = userModel.pin
            userDomain.mobileNumber = userModel.mobileNumber
            userDomain.save(flush: true) // Ensure the save operation is immediately flushed to the database
            return userDomain
        } else {
            throw new UserNotFoundException("User with id ${id} is not found")
        }
    }

    @Transactional
    def deleteUser(Long id) {
        UserDomain userDomain = UserDomain.findById(id)
        if (userDomain) {
            userDomain.delete(flush: true) // Ensure the delete operation is immediately flushed to the database
            return true
        } else {
            return false
        }
    }

    @Transactional
    UserModel loginUser(String mobileNumber, String pin) {
        UserDomain userDomain = UserDomain.findByMobileNumberAndPin(mobileNumber, pin)
        if (userDomain) {
           return new UserModel(
                    id: userDomain.id,
                    firstName: userDomain.firstName,
                    lastName: userDomain.lastName,
                    email: userDomain.email,
                    mobileNumber: userDomain.mobileNumber
            )
        } else {
            return "Invalid Credentials"

        }
    }


}
