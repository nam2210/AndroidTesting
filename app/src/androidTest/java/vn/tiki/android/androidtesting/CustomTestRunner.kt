package vn.tiki.android.androidtesting

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import vn.tiki.android.androidtesting.testing.TestApp

/**
 * Custom runner to disable dependency injection.
 */
class CustomTestRunner : AndroidJUnitRunner() {
  override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
    return super.newApplication(cl, TestApp::class.java.name, context)
  }
}
