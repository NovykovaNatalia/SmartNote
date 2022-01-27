package com.nnnd.smartnote

import android.content.Context

class LanguageSupportUtils  {
    companion object {
        fun castToLangEvent(context: Context, s: String): String? {
            var s = s.replace("Date", context.getString(R.string.date_string))
            s = s.replace("Time", context.getString(R.string.time_string))
            s = s.replace("Event", context.getString(R.string.event_string))
            return s
        }
        fun castToLangBank(context: Context, s: String): String? {
            var s = s.replace("Account", context.getString(R.string.account_string))
            s = s.replace("Bank Name", context.getString(R.string.bank_name_string))
            s = s.replace("Account Number", context.getString(R.string.account_number_string))
            s = s.replace("Name, Surname", context.getString(R.string.name_sur_string))
            return s
        }
        fun castToLangCredentials(context: Context, s: String): String? {
            var s = s.replace("Credential", context.getString(R.string.credential_string))
            s = s.replace("Reference", context.getString(R.string.reference_string))
            return s
        }
        fun castToLangSales(context: Context, s: String): String? {
            var s = s.replace("Brend", context.getString(R.string.brend_string))
            s = s.replace("Thing", context.getString(R.string.thing_string))
            s = s.replace("Discount", context.getString(R.string.sale_string))
            s = s.replace("True price", context.getString(R.string.true_price_string))
            s = s.replace("Economy", context.getString(R.string.economy_string))
            s = s.replace("Percentage", context.getString(R.string.percentage_string))
            return s
        }

        fun castToLangNotes(context: Context, s: String): String? {
            var s = s.replace("Title", context.getString(R.string.title))
            s = s.replace("Note", context.getString(R.string.note_string))
            return s
        }

        fun castToLangDraws(context: Context, s: String): String? {
            var s = s.replace("Title", context.getString(R.string.title))
            return s
        }
    }
}