
package indg.com.cover2protect.util.swipe_util;

import android.view.MotionEvent;

public abstract class SimpleSwipeListener implements SwipeListener {
  @Override
  public void onSwipingLeft(MotionEvent event) {
  }

  @Override
  public boolean onSwipedLeft(MotionEvent event) {
    return false;
  }

  @Override
  public void onSwipingRight(MotionEvent event) {
  }

  @Override
  public boolean onSwipedRight(MotionEvent event) {
    return false;
  }

  @Override
  public void onSwipingUp(MotionEvent event) {
  }

  @Override
  public boolean onSwipedUp(MotionEvent event) {
    return false;
  }

  @Override
  public void onSwipingDown(MotionEvent event) {
  }

  @Override
  public boolean onSwipedDown(MotionEvent event) {
    return false;
  }
}