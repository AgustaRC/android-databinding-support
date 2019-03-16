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

package com.github.agustarc.databinding.extensions

import com.github.agustarc.databinding.DATA_TAG
import com.github.agustarc.databinding.LAYOUT_TAG
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlTag


/**
 * @author Leopold
 * https://github.com/AgustaRC
 * https://medium.com/@joongwon
 */

fun XmlTag.isDatabindingRootTag(): Boolean = isLayoutTag() && hasNamespaceAndroid()

fun XmlTag.isLayoutTag(): Boolean = name == LAYOUT_TAG

fun XmlTag.hasNamespaceAndroid(): Boolean = getAttribute("xmlns:android") != null

fun XmlTag.findFirstDataTag(): XmlTag? = findFirstSubTag(DATA_TAG)

fun XmlTag.findLastSubTag(tagName: String): XmlTag? {
    val tags = findSubTags(tagName)
    return if (tags.isNotEmpty()) tags[tags.lastIndex] else null
}

fun XmlAttribute.isLayoutTag(): Boolean = LAYOUT_TAG == PsiTreeUtil.getParentOfType(this, XmlTag::class.java)?.name

fun XmlAttribute.hasDatabindingExpression(): Boolean {
    val value = value ?: return false
    return value.startsWith("@{") && value.endsWith("}")
}

fun XmlAttribute.has2WayDatabindingExpression(): Boolean {
    val value = value ?: return false
    return value.startsWith("@={") && value.endsWith("}")
}