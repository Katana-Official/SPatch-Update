package com.github.topbottomsnackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

class TBSnackbarManager {
  private static final int LONG_DURATION_MS = 2750;
  
  static final int MSG_TIMEOUT = 0;
  
  private static final int SHORT_DURATION_MS = 1500;
  
  private static TBSnackbarManager sSnackbarManager;
  
  private SnackbarRecord mCurrentSnackbar;
  
  private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(Message param1Message) {
          if (param1Message.what != 0)
            return false; 
          TBSnackbarManager.this.handleTimeout((TBSnackbarManager.SnackbarRecord)param1Message.obj);
          return true;
        }
      });
  
  private final Object mLock = new Object();
  
  private SnackbarRecord mNextSnackbar;
  
  private boolean cancelSnackbarLocked(SnackbarRecord paramSnackbarRecord, int paramInt) {
    Callback callback = paramSnackbarRecord.callback.get();
    if (callback != null) {
      this.mHandler.removeCallbacksAndMessages(paramSnackbarRecord);
      callback.dismiss(paramInt);
      return true;
    } 
    return false;
  }
  
  static TBSnackbarManager getInstance() {
    if (sSnackbarManager == null)
      sSnackbarManager = new TBSnackbarManager(); 
    return sSnackbarManager;
  }
  
  private boolean isCurrentSnackbarLocked(Callback paramCallback) {
    SnackbarRecord snackbarRecord = this.mCurrentSnackbar;
    return (snackbarRecord != null && snackbarRecord.isSnackbar(paramCallback));
  }
  
  private boolean isNextSnackbarLocked(Callback paramCallback) {
    SnackbarRecord snackbarRecord = this.mNextSnackbar;
    return (snackbarRecord != null && snackbarRecord.isSnackbar(paramCallback));
  }
  
  private void scheduleTimeoutLocked(SnackbarRecord paramSnackbarRecord) {
    if (paramSnackbarRecord.duration == -2)
      return; 
    int i = 2750;
    if (paramSnackbarRecord.duration > 0) {
      i = paramSnackbarRecord.duration;
    } else if (paramSnackbarRecord.duration == -1) {
      i = 1500;
    } 
    this.mHandler.removeCallbacksAndMessages(paramSnackbarRecord);
    Handler handler = this.mHandler;
    handler.sendMessageDelayed(Message.obtain(handler, 0, paramSnackbarRecord), i);
  }
  
  private void showNextSnackbarLocked() {
    SnackbarRecord snackbarRecord = this.mNextSnackbar;
    if (snackbarRecord != null) {
      this.mCurrentSnackbar = snackbarRecord;
      this.mNextSnackbar = null;
      Callback callback = snackbarRecord.callback.get();
      if (callback != null) {
        callback.show();
        return;
      } 
      this.mCurrentSnackbar = null;
    } 
  }
  
  public void cancelTimeout(Callback paramCallback) {
    synchronized (this.mLock) {
      if (isCurrentSnackbarLocked(paramCallback))
        this.mHandler.removeCallbacksAndMessages(this.mCurrentSnackbar); 
      return;
    } 
  }
  
  public void dismiss(Callback paramCallback, int paramInt) {
    synchronized (this.mLock) {
      if (isCurrentSnackbarLocked(paramCallback)) {
        cancelSnackbarLocked(this.mCurrentSnackbar, paramInt);
      } else if (isNextSnackbarLocked(paramCallback)) {
        cancelSnackbarLocked(this.mNextSnackbar, paramInt);
      } 
      return;
    } 
  }
  
  void handleTimeout(SnackbarRecord paramSnackbarRecord) {
    synchronized (this.mLock) {
      if (this.mCurrentSnackbar == paramSnackbarRecord || this.mNextSnackbar == paramSnackbarRecord)
        cancelSnackbarLocked(paramSnackbarRecord, 2); 
      return;
    } 
  }
  
  public boolean isCurrent(Callback paramCallback) {
    synchronized (this.mLock) {
      return isCurrentSnackbarLocked(paramCallback);
    } 
  }
  
  public boolean isCurrentOrNext(Callback paramCallback) {
    synchronized (this.mLock) {
      if (isCurrentSnackbarLocked(paramCallback) || isNextSnackbarLocked(paramCallback))
        return true; 
    } 
    boolean bool = false;
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_3} */
    return bool;
  }
  
  public void onDismissed(Callback paramCallback) {
    synchronized (this.mLock) {
      if (isCurrentSnackbarLocked(paramCallback)) {
        this.mCurrentSnackbar = null;
        if (this.mNextSnackbar != null)
          showNextSnackbarLocked(); 
      } 
      return;
    } 
  }
  
  public void onShown(Callback paramCallback) {
    synchronized (this.mLock) {
      if (isCurrentSnackbarLocked(paramCallback))
        scheduleTimeoutLocked(this.mCurrentSnackbar); 
      return;
    } 
  }
  
  public void restoreTimeout(Callback paramCallback) {
    synchronized (this.mLock) {
      if (isCurrentSnackbarLocked(paramCallback))
        scheduleTimeoutLocked(this.mCurrentSnackbar); 
      return;
    } 
  }
  
  public void show(int paramInt, Callback paramCallback) {
    synchronized (this.mLock) {
      if (isCurrentSnackbarLocked(paramCallback)) {
        this.mCurrentSnackbar.duration = paramInt;
        this.mHandler.removeCallbacksAndMessages(this.mCurrentSnackbar);
        scheduleTimeoutLocked(this.mCurrentSnackbar);
        return;
      } 
      if (isNextSnackbarLocked(paramCallback)) {
        this.mNextSnackbar.duration = paramInt;
      } else {
        this.mNextSnackbar = new SnackbarRecord(paramInt, paramCallback);
      } 
      if (this.mCurrentSnackbar != null && cancelSnackbarLocked(this.mCurrentSnackbar, 4))
        return; 
      this.mCurrentSnackbar = null;
      showNextSnackbarLocked();
      return;
    } 
  }
  
  static interface Callback {
    void dismiss(int param1Int);
    
    void show();
  }
  
  private static class SnackbarRecord {
    final WeakReference<TBSnackbarManager.Callback> callback;
    
    int duration;
    
    SnackbarRecord(int param1Int, TBSnackbarManager.Callback param1Callback) {
      this.callback = new WeakReference<TBSnackbarManager.Callback>(param1Callback);
      this.duration = param1Int;
    }
    
    boolean isSnackbar(TBSnackbarManager.Callback param1Callback) { return (param1Callback != null && this.callback.get() == param1Callback); }
  }
}


/* Location:              C:\User\\user\Desktop\classes-dex2jar.jar!\com\github\topbottomsnackbar\TBSnackbarManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.1
 */