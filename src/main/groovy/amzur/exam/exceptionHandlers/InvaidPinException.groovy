package amzur.exam.exceptionHandlers

import grails.gorm.transactions.Transactional

@Transactional
class InvalidPinException extends RuntimeException {
    InvalidPinException(String message) {
        super(message)
    }
}
