package org.autotdd.eclipse2

import org.eclipse.swt.SWTException
import org.eclipse.swt.widgets.Display

class InvocationException(cause: Throwable) extends Exception(cause)

trait NeedsSwtThread {
  def display: Display
  def execute(runnable: Runnable) {
    if (!display.isDisposed())
      if (Thread.currentThread() == display.getThread)
        runnable.run
      else {
        try {
          display.syncExec(runnable)
        } catch {
          case e: SWTException => throw new InvocationException(e.getCause);
        }
      }
  }
}


