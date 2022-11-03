package bitco.ua.reposotory

import authentication.hash
import bitco.ua.reposotory.DataBaseFactory.dbQuery
import data.Tables.UserTable
import data.Tables.UserTable.email
import data.Tables.UserTable.name
import data.model.User
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

    suspend fun changeUserName(old_name: String,newName:String) = dbQuery { UserTable.update (
        where = {UserTable.name.eq(old_name)},
        ){nt->nt[UserTable.name]=newName}
    }

    suspend fun changeUserEmail(old_email: String,newEmail:String) = dbQuery { UserTable.update (
        where = {UserTable.email.eq(old_email)},
    ){nt->nt[UserTable.email]=newEmail}
    }

    suspend fun changeUserPassword(old_pass: String,newPass:String) = dbQuery { UserTable.update (
        where = {UserTable.hpassword.eq(hash(old_pass))},
    ){nt->nt[UserTable.hpassword]= hash(newPass)}
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



