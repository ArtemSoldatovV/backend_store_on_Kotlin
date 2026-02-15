package org.example.repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.ZoneOffset

object audit_logsDTO:Table("audit_logs"){
    val id = varchar("id_audit_logs", 50)
    val timestamp = varchar("timestamp", 150)
    val user_id = varchar("user_id", 50)
    val action_type= varchar("action_type", 50)
    val entity = varchar("entity", 50)
    val status = varchar("status", 50)

    override val primaryKey = PrimaryKey(OrdersDTO.id)
}

class Audit_logsRepository{
    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/store_db",
            driver = "org.postgresql.Driver",
            user = "store_user",
            password = "store123"
        )

        transaction {
            SchemaUtils.create(audit_logsDTO)
        }
    }

    fun createAudit_logs(user_id: String, action_type: String, entity: String, status: String): Boolean {
        return transaction {
            audit_logsDTO.insert {
                it[audit_logsDTO.timestamp] = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)
                it[audit_logsDTO.user_id] = user_id
                it[audit_logsDTO.action_type] = action_type
                it[audit_logsDTO.entity] = entity
//                it[audit_logsDTO.entity_id] = entity_id
                it[audit_logsDTO.status] = status
            }
            true
        }
    }
}