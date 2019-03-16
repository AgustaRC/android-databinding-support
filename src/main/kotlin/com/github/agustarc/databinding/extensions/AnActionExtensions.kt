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

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile


/**
 * @author Leopold
 * https://github.com/AgustaRC
 * https://medium.com/@joongwon
 */

fun AnActionEvent.getVirtualFile(): VirtualFile? = getData(CommonDataKeys.VIRTUAL_FILE)

fun AnActionEvent.getPsiFile(): PsiFile? = getData(CommonDataKeys.PSI_FILE)