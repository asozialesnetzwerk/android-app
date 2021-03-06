package com.github.doomsdayrs.apps.shosetsu.ui.reader.demarkActions

import com.github.doomsdayrs.apps.shosetsu.backend.Utilities
import com.github.doomsdayrs.apps.shosetsu.backend.Utilities.DeMarkAction
import com.github.doomsdayrs.apps.shosetsu.ui.reader.fragments.ChapterView

class TextSizeChange(private val chapterReader: ChapterView) : DeMarkAction {
    override fun action(spared: Int) {
        val a = intArrayOf(14, 17, 20)
        Utilities.setTextSize(a[spared])
        chapterReader.setUpReader()
    }

}