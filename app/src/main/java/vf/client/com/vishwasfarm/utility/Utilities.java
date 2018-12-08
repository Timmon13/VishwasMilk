package vf.client.com.vishwasfarm.utility;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by ESB36UC on 08-05-2017.
 */

public class Utilities {

    /**
     * Swipe in animation for recycler view Row item
     *
     * @param fLayout
     * @param fDistance : Distance for animation
     */
    public static void swipeInAnimation(final RelativeLayout fLayout, final float fDistance) {

        TranslateAnimation lAnim = new TranslateAnimation(0, fDistance, 0, 0);
        lAnim.setFillAfter(true);
        lAnim.setDuration(200);

        fLayout.startAnimation(lAnim);
    }


    /**
     * Swipe out animation for recycler view row item
     *
     * @param fLayout
     * @param fDistance
     */

    public static void swipeOutAnimation(final RelativeLayout fLayout, final float fDistance) {
        Animation lAnim = new TranslateAnimation(0, 0, 0, 0);
        lAnim.setAnimationListener(new TranslateAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fLayout.layout((int) -fDistance, 0, fLayout.getWidth() - ((int) fDistance), fLayout.getHeight());
            }
        });

        fLayout.startAnimation(lAnim);
    }
}