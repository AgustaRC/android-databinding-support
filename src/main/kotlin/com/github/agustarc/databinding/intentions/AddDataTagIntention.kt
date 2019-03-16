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

import com.github.agustarc.databinding.DATA_TAG
import com.github.agustarc.databinding.extensions.getRootTag
import com.github.agustarc.databinding.extensions.isDatabindingRootTag
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.XmlElementFactory
import com.intellij.psi.xml.XmlFile

/**
 * @author Leopold
 * https://github.com/AgustaRC
 * https://medium.com/@joongwon
 */
class AddDataTagIntention : IntentionAction {
    override fun startInWriteAction(): Boolean = true
    override fun getFamilyName(): String = "Add <$DATA_TAG> tag"
    override fun getText(): String = "Add <$DATA_TAG> tag"

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        val rootTag = file.getRootTag() ?: return false
        return rootTag.isDatabindingRootTag() && rootTag.findSubTags(DATA_TAG).isEmpty()
    }

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        if (file is XmlFile) {
            val newTag = file.rootTag?.addSubTag(XmlElementFactory.getInstance(project).createTagFromText("<$DATA_TAG></$DATA_TAG>", XMLLanguage.INSTANCE), true)
            newTag?.value?.textRange?.startOffset?.run { editor?.caretModel?.moveToOffset(this) }
        }
    }
}