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

package com.github.agustarc.databinding.intentions

import com.github.agustarc.databinding.LAYOUT_TAG
import com.github.agustarc.databinding.extensions.getRootTag
import com.github.agustarc.databinding.extensions.isDatabindingRootTag
import com.github.agustarc.databinding.isInAndroidLayoutFolder
import com.github.agustarc.databinding.isNotDatabindingLayout
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.XmlElementFactory


/**
 * @author Leopold
 * https://github.com/AgustaRC
 * https://medium.com/@joongwon
 */
class ConvertToDatabindingIntention : IntentionAction {
    override fun startInWriteAction(): Boolean = true
    override fun getText(): String = "Wrap with data binding layout"
    override fun getFamilyName(): String = "Wrap with data binding layout"

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        return isInAndroidLayoutFolder(file) && isNotDatabindingLayout(file)
    }

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        val rootTag = file?.getRootTag() ?: return
        if (rootTag.isDatabindingRootTag()) {
            return
        }

        val xmlnsAttributes = rootTag.attributes.filter { it.name.startsWith("xmlns:") }
        val attributeText = xmlnsAttributes.mapNotNull { it.text }.joinToString(separator = " ")
        val factory = XmlElementFactory.getInstance(project)

        xmlnsAttributes.forEach { it.delete() }
        rootTag.replace(factory.createTagFromText("<$LAYOUT_TAG $attributeText>${rootTag.text}</$LAYOUT_TAG>", XMLLanguage.INSTANCE))
    }
}