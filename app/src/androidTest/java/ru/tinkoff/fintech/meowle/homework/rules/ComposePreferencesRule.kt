package ru.tinkoff.fintech.meowle.homework.rules

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class ComposePreferencesRule: TestRule {
    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                mode()
                base.evaluate()
            }
        }
    }

    private fun mode() {
        InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("meowle", Context.MODE_PRIVATE)
            .edit()
            .putString("launch_mode", "COMPOSE")
            .commit()
    }

}