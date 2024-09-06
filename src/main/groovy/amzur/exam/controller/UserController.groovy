package amzur.exam.controller

import amzur.exam.model.UserModel
import amzur.exam.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status

import javax.inject.Inject

@Controller("/users")
class UserController {
    @Inject
    UserService userService
    @Post
    def saveUsers(@Body UserModel userModel){
        userService.saveUsers(userModel)
        return userModel
        }
    @Get
    def getAllUsers(){
        return userService.getAllUsers()
    }
    @Get("/id/{id}")
    def getById(@PathVariable Long id){
        return userService.getById(id)
    }
    @Put("/update/{id}")

    def update(@PathVariable Long id, @Body UserModel userModel) {
            userService.updateUsers(id, userModel) // Updated method name
                return userModel
            }

    @Delete("/{id}")
    def deleteUser(@PathVariable Long id) {
        userService.deleteUser(id)

                return "deleted successfully"
    }


    @Post("/login")
    def login(@Body UserModel userModel){
             return userService.loginUser(userModel.mobileNumber,userModel.pin)
    }

    @Get("/startingname")
    def starts(){
        return userService.getNameStartsWith()
    }

}
