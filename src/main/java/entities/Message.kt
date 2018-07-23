package entities


data class  Message @JvmOverloads constructor(
        val subject: String = "",
        val body: Body = Body(),
        val from: Recipient = Recipient(),
        val sender: Recipient = Recipient(),
        val isRead: Boolean = false,
        val isDraft: Boolean = false,
        val id: String = "",
        val createdDateTime: String = "",
        val lastModifiedDateTime: String = "",
        val changeKey: String = "",
        val categories: List<Any> = listOf(),
        val receivedDateTime: String = "",
        val sentDateTime: String = "",
        val hasAttachments: Boolean = false,
        val bodyPreview: String = "",
        val importance: String = "normal",
        val conversationId: String = "",
        val toRecipients: List<Recipient> = listOf(),
        val ccRecipients: List<Recipient> = listOf(),
        val bccRecipients: List<Recipient> = listOf(),
        val replyTo: Recipient = Recipient()
)
