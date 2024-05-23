package com.github.kumaraman21.intellijbehave.kotlin

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId

/**
 * Created by Rodrigo Quesada on 20/09/15.
 */
private const val kotlinPluginId = "org.jetbrains.kotlin"

val pluginIsEnabled = PluginManagerCore.getPlugin(PluginId.getId(kotlinPluginId))?.isEnabled ?: false