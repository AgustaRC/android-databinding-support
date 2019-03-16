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

package com.github.agustarc.databinding.utils

import com.github.agustarc.databinding.DATA_TAG
import com.github.agustarc.databinding.TYPE_ATTRIBUTE
import com.github.agustarc.databinding.VARIABLE_TAG
import com.github.agustarc.databinding.extensions.isLayoutTag
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag

/**
 * @author Leopold
 * https://github.com/AgustaRC
 * https://medium.com/@joongwon
 */

fun getPointingElement(editor: Editor?, file: PsiFile?): PsiElement? {
    val offset = editor?.caretModel?.offset ?: return null
    return file?.findElementAt(offset)
}

fun getPointingXmlAttribute(editor: Editor?, file: PsiFile?) = getPointingParentElement<XmlAttribute>(editor, file)

inline fun <reified T : PsiElement> getPointingParentElement(editor: Editor?, file: PsiFile?): T? {
    val psiElement = getPointingElement(editor, file) ?: return null
    return PsiTreeUtil.getParentOfType(psiElement, T::class.java)
}

fun collectLayoutVariableTypesOf(project: Project, qualifiedName: String?): List<XmlAttribute>? {
    val psiManager = PsiManager.getInstance(project)
    return FileTypeIndex.getFiles(XmlFileType.INSTANCE, ProjectScope.getProjectScope(project)).filterNot {
        it.path.contains("/.idea/")
    }.mapNotNull {
        (psiManager.findFile(it) as? XmlFile)?.rootTag
    }.filter(XmlTag::isLayoutTag).flatMap {
        it.findSubTags(DATA_TAG).asIterable()
    }.filterNotNull().flatMap {
        it.findSubTags(VARIABLE_TAG).asIterable()
    }.mapNotNull {
        it.getAttribute(TYPE_ATTRIBUTE)
    }.filter {
        it.value == qualifiedName
    }
}
