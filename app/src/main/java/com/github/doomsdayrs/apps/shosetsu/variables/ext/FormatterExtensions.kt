package com.github.doomsdayrs.apps.shosetsu.variables.ext

import com.github.doomsdayrs.api.shosetsu.services.core.Formatter
import com.github.doomsdayrs.apps.shosetsu.backend.Utilities
import com.github.doomsdayrs.apps.shosetsu.backend.Utilities.FormatterPrefKeys.Listing

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
 * 01 / 03 / 2020
 *
 * @author github.com/doomsdayrs
 */

val Formatter.defaultListing: Int
    get() = Utilities.formatterPreferences.getInt("$formatterID:$Listing", 0)


fun Formatter.setDefaultListing(int: Int): Boolean = when {
    int >= listings.size || int < 0 -> false
    else -> {
        Utilities.formatterPreferences.edit().putInt("$formatterID:$Listing", int).apply()
        true
    }
}


fun Formatter.getListing(): Formatter.Listing = listings[defaultListing]