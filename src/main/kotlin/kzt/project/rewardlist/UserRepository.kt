package kzt.project.rewardlist

import kzt.project.rewardlist.model.User

interface UserRepository {

    fun create(todoistId: Long): User

    fun findById(todoistId: Long): User?
}