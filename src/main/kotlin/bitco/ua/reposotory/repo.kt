package bitco.ua.reposotory

import bitco.ua.reposotory.DataBaseFactory.dbQuery
import data.Tables.UserTable
import data.Tables.UserTable.email
import data.model.User
import jdk.nashorn.internal.objects.NativeDebug.map
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class repo {

    suspend fun addUser(user: User){
        dbQuery{
            UserTable.insert { ut->
                ut[UserTable.email] = user.email
                ut[UserTable.name] = user.UserName
                ut[UserTable.hpassword] = user.hashPassword

            }
        }
    }

    suspend fun findUserByEmail(email:String) =dbQuery { UserTable.select { UserTable.email.eq(email) }
        .map{RowtoUser(it)}
        .singleOrNull()
    }









     fun RowtoUser(row: ResultRow):User?{
        if(row==null){
            return null
        }
        return User(
            email = row[UserTable.email],
            UserName = row[UserTable.name],
            hashPassword = row[UserTable.hpassword]
        )
    }
}



