package org.akira.otuskotlin.ads.repo.postgresql

object SqlFields {
    const val ID = "id"
    const val TITLE = "title"
    const val AUTHORS = "authors"
    const val PUBLISHING = "publishing"
    const val YEAR = "year"
    const val AD_TYPE = "ad_type"
    const val PRICE = "price"

    const val LOCK = "lock"
    const val LOCK_OLD = "lock_old"
    const val OWNER_ID = "owner_id"

    const val AD_TYPE_TYPE = "ad_types_type"
    const val AD_TYPE_DEMAND = "demand"
    const val AD_TYPE_PROPOSAL = "PROPOSAL"

    const val FILTER_TITLE = TITLE
    const val FILTER_OWNER_ID = OWNER_ID
    const val FILTER_AD_TYPE = AD_TYPE

    const val DELETE_OK = "DELETE_OK"

    fun String.quoted() = "\"$this\""
    val allFields = listOf(
        ID, TITLE, AUTHORS, PUBLISHING, YEAR, AD_TYPE, PRICE, LOCK, OWNER_ID
    )
}