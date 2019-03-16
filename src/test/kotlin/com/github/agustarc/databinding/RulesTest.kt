package com.github.agustarc.databinding

import org.junit.Test


/**
 * @author Leopold
 * @Github https://github.com/AgustaRC
 * @Blog https://medium.com/@joongwon
 */
class RulesTest {

    @Test
    fun testDefaultLayoutFolderName() {
        assert(ANDROID_LAYOUT_FOLDER_PATTERN.matcher("layout").matches())
    }

    @Test
    fun testLayoutWithQualifier() {
        assert(ANDROID_LAYOUT_FOLDER_PATTERN.matcher("layout-large").matches())
    }
}