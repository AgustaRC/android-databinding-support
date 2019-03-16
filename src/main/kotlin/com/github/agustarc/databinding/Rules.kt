/*
 * Copyright 2019 Leopold Baik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.agustarc.databinding

import com.github.agustarc.databinding.extensions.getFolderName
import com.github.agustarc.databinding.extensions.getRootTag
import com.github.agustarc.databinding.extensions.isDatabindingRootTag
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import java.util.regex.Pattern


/**
 * @author Leopold
 * https://github.com/AgustaRC
 * https://medium.com/@joongwon
 */

val ANDROID_LAYOUT_FOLDER_PATTERN: Pattern = Pattern.compile("^layout(-[a-zA-Z0-9]+)*$")

fun isCorrectSelectedFolder(file: VirtualFile?): Boolean {
    return if (file == null) false else file.parent.isDirectory && ANDROID_LAYOUT_FOLDER_PATTERN.matcher(file.parent.name).matches()
}

fun isNotDatabindingLayout(file: PsiFile?): Boolean {
    val rootTag = file.getRootTag() ?: return true
    return !rootTag.isDatabindingRootTag()
}

fun isInAndroidLayoutFolder(file: PsiFile?): Boolean = ANDROID_LAYOUT_FOLDER_PATTERN.matcher(file.getFolderName()).matches()