package org.example.domain.logs

data class audit_logs (
    val id_audit_logs: String,
    val timestamp: String,
    val user_id: String,
    val action_type: String,
    val entity: String,
//    val entity_id: String,
    val status: String
)

data class CreateAudit_logsRequest(
    val timestamp: String,
    val user_id: String,
    val action_type: String,
    val entity: String,
//    val entity_id: String,
    val status: String
)