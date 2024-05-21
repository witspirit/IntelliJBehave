package com.github.kumaraman21.intellijbehave

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.ModificationTracker
import com.intellij.openapi.util.SimpleModificationTracker

/**
 * Tracks the modification count of JBehave step definition files in the given project.
 */
@Service(Service.Level.PROJECT)
class JBehaveStepDefClassesModificationTracker(val project: Project) : ModificationTrackerBase() {
    companion object {
        @JvmStatic
        fun getInstance(project: Project) = project.service<JBehaveStepDefClassesModificationTracker>()
    }
}

abstract class ModificationTrackerBase : ModificationTracker {
    private val modificationTracker = SimpleModificationTracker()

    override fun getModificationCount() = modificationTracker.modificationCount

    fun increaseModificationCount() = modificationTracker.incModificationCount()
}