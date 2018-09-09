package kzt.project.rewardlist

import kzt.project.rewardlist.model.User

interface UserRepository {

    fun create(todoistId: Long): User

    fun findById(id: Long): User?
}