package com.github.doomsdayrs.apps.shosetsu.variables.ext

import android.widget.*
import androidx.core.view.get
import com.github.doomsdayrs.api.shosetsu.services.core.*
import com.github.doomsdayrs.api.shosetsu.services.core.Filter
import com.github.doomsdayrs.apps.shosetsu.backend.DownloadManager.getChapterText
import com.github.doomsdayrs.apps.shosetsu.backend.database.Database
import com.github.doomsdayrs.apps.shosetsu.variables.HandledReturns
import java.util.*
import kotlin.collections.ArrayList

/*
 * This file is part of shosetsu.
 *
 * shosetsu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * shosetsu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with shosetsu.  If not, see <https://www.gnu.org/licenses/>.
 * ====================================================================
 */

/**
 * shosetsu
 * 08 / 02 / 2020
 *
 * @author github.com/doomsdayrs
 */

/**
 * Gets the novel from local storage
 *
 * @param chapterID novelURL of the chapter
 * @return String of passage
 */
@Throws(MissingResourceException::class)
fun Database.DatabaseChapter.getSavedNovelPassage(chapterID: Int): HandledReturns<String> {
    return getChapterText(getSavedNovelPath(chapterID))
}

fun Array<Filter<*>>.defaultMap(): MutableMap<Int, Any> {
    val m = mutableMapOf<Int, Any>()
    forEach {
        m[it.id] = when (it) {
            is TextFilter -> ""
            is SwitchFilter -> true
            is DropdownFilter -> 0
            is RadioGroupFilter -> 0
            else -> {
            }
        }
    }
    return m
}

fun LinearLayout.findFilters(): Array<Any> {
    val a = ArrayList<Any>()
    for (i in 0 until childCount) {
        this[i].let {
            when (it) {
                is RadioGroup -> a.add(it.checkedRadioButtonId)
                is EditText -> a.add(it.text)
                is Spinner -> a.add(it.selectedItemPosition)
                is Switch -> a.add(it.isChecked)
                is LinearLayout -> a.addAll(listOf(it.findFilters()))
                else -> {
                }
            }
        }
    }
    return a.toArray()
}