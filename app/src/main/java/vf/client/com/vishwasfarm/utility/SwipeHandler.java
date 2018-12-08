package vf.client.com.vishwasfarm.utility;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import vf.client.com.vishwasfarm.R;


/**
 * Class to handle swipe gesture for view, handles left to right and right to left swipe.
 */
public class SwipeHandler implements View.OnTouchListener {

    private RecyclerView mRecyclerView;
    private View mDownView;

    private OnRecyclerListClickListener mOnRecyclerListClickListener;
    private GestureDetectorCompat mGestureDetector;

    private boolean mIsScrolling;

    private float mDownX;
    private float mDownY;

    private int mDownPosition;
    private int mDeleteButtonStatus = 0;
    private int mSwipeDistance = 0;

    private final int DELETE_VIEW_INVISIBLE = 0;
    private final int DELETE_VIEW_VISIBLE = 1;
    private final int DELETE_VIEW_INVALIDATE = 2;
    private final int MAX_Y_AXIS_SWIPE_VALUE = 120;
    private final int MIN_Y_AXIS_SWIPE_VALUE = -120;

    private final int MAIN_ITEM_VIEW_INDEX=1;
    private final int OPTION_ITEM_VIEW_INDEX=0;

    /**
     * Handles callback for adapter main view and hidden option item clicks
     */
    public interface OnRecyclerListClickListener {
        /**
         * Handles click event of main list view
         * @param fData : provides Tag attached with main view
         */
        void onRecyclerListMainItemClick(Object fData);

        /**
         * Handles click event of hidden option view
         * @param fData : provides Tag attached with option view
         */
        void onRecyclerListOptionItemClick(Object fData);
    }

    /**
     * Constructor to initialise callback
     * @param fCallback
     */
    public SwipeHandler(OnRecyclerListClickListener fCallback) {
        mOnRecyclerListClickListener = fCallback;
    }


    /**
     * Builder to create SwipeListener and handle on Touch on recycler View
     * @param fView : RecyclerView to handle on Touch event on it
     * @return : provides instance of {@link SwipeHandler}
     */
    public SwipeHandler build(View fView){
        if(fView!=null && fView instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) fView;
            CustomGridLayoutManager lLayoutManager = new CustomGridLayoutManager(mRecyclerView.getContext());
            lLayoutManager.setmIsScrollEnabled(true);
            mRecyclerView.setLayoutManager(lLayoutManager);

            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setOnTouchListener(this);

            mGestureDetector = new GestureDetectorCompat(fView.getContext(), mGestureListener);
            mSwipeDistance = (mRecyclerView.getContext()).getResources().getDimensionPixelSize(R.dimen.dimen_60dp);
            return this;
        }
        return null;
    }

    /**
     * Sets distance of left and right animation on view
     * @param fDimen : R.dimen.___ value to be provided to set the distance
     */
    public void setDimensionSpaceForSwipingView(int fDimen){
        try {
            mSwipeDistance = (mRecyclerView.getContext()).getResources().getDimensionPixelSize(fDimen);
        }
        catch (Exception e){
            mSwipeDistance= (mRecyclerView.getContext()).getResources().getDimensionPixelSize(R.dimen.dimen_60dp);
        }
    }

    /**
     * Sets Touch Event to {@link GestureDetector}
     * @param fView : touched view
     * @param fMotionEvent : MotionEvent of touch
     * @return
     */
    @Override
    public boolean onTouch(View fView, MotionEvent fMotionEvent) {
        mGestureDetector.onTouchEvent(fMotionEvent);
        return false;
    }


    /**
     * Hides the visible portion of swiped view
     */
    public void hideSwipedView() {
        if (mDeleteButtonStatus == DELETE_VIEW_VISIBLE && mDownView != null) {
            mDeleteButtonStatus = DELETE_VIEW_INVALIDATE;
            mDownX = 0;
            mDownY = 0;
            updateScroll(false);
            onSwipeRight(mRecyclerView, mDownPosition);
        }
    }

    /**
     * Swipes the main view to left
     *
     * @param fView
     * @param fItemPosition
     */
    private void onSwipeLeft(View fView, int fItemPosition) {
        View lChildItem;
        RelativeLayout lMainRowRelativeLayout;
        int lFirstPosition;
        int lExpectedChildView;
        lFirstPosition = ((CustomGridLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        lExpectedChildView = fItemPosition - lFirstPosition;
        lChildItem = ((RecyclerView) fView).getChildAt(lExpectedChildView);

        if (lChildItem != null) {

            //lMainRowRelativeLayout = (RelativeLayout) lChildItem.findViewById(R.id.rl_main_adapter_view);
            lMainRowRelativeLayout=(RelativeLayout)((ViewGroup)lChildItem).getChildAt(MAIN_ITEM_VIEW_INDEX);
            vf.client.com.vishwasfarm.ServiceListener.Utilities.swipeOutAnimation(lMainRowRelativeLayout, mSwipeDistance);
        }
    }


    /**
     * SWipes the license plate right
     *
     * @param lView
     * @param lItemPosition
     */
    private void onSwipeRight(View lView, int lItemPosition) {
        View lChildItem;
        RelativeLayout lMainRowRelativeLayout;
        int firstPosition = ((CustomGridLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int wantedChild = lItemPosition - firstPosition;
        lChildItem = ((RecyclerView) lView).getChildAt(wantedChild);

        if (lChildItem != null) {
            lMainRowRelativeLayout=(RelativeLayout)((ViewGroup)lChildItem).getChildAt(MAIN_ITEM_VIEW_INDEX);
            vf.client.com.vishwasfarm.ServiceListener.Utilities.swipeInAnimation(lMainRowRelativeLayout, mSwipeDistance);
        }
    }

    /**
     * It enable or disable the recycler view scroll
     *
     * @param fScrollStatus
     */
    private void updateScroll(boolean fScrollStatus) {
        ((CustomGridLayoutManager) mRecyclerView.getLayoutManager()).setmIsScrollEnabled(fScrollStatus);
    }

    /**
     * Handles gesture listener for Touch down, fling , single Tap
     */
    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent fMotionEvent) {
            if (mDeleteButtonStatus == DELETE_VIEW_VISIBLE && mDownView != null) {
                //handles the swipe right
                mDownX = 0;
                mDownY = 0;
                updateScroll(false);

                mDeleteButtonStatus = DELETE_VIEW_INVALIDATE;
                onSwipeRight(mRecyclerView, mDownPosition);
            } else {
                mDeleteButtonStatus = DELETE_VIEW_INVISIBLE;
                updateScroll(true);
                mDownY = fMotionEvent.getRawY();
                mIsScrolling = false;

                mDownView = getChild(mRecyclerView, fMotionEvent, false);

                if ((mRecyclerView != null) && mDownView != null) {
                    mDownX = fMotionEvent.getRawX();
                    mDownY = fMotionEvent.getRawY();
                    mDownPosition = mRecyclerView.getChildAdapterPosition(mDownView);
                }
            }
            return false;
        }


        @Override
        public boolean onFling(MotionEvent fFirstDownMotionEvent, MotionEvent fMotionMoveEvent, float fVelocityX, float fVelocityY) {
            if (mDeleteButtonStatus == DELETE_VIEW_INVISIBLE || mDeleteButtonStatus == DELETE_VIEW_INVALIDATE) {
                float lDeltaX = 0;
                float lDeltaY = 0;

                if (fMotionMoveEvent.getAction() == MotionEvent.ACTION_UP) {
                    lDeltaX = fMotionMoveEvent.getRawX() - mDownX;
                    lDeltaY = fMotionMoveEvent.getRawY() - mDownY;
                }

                if (lDeltaX < 0 && ((lDeltaY < MAX_Y_AXIS_SWIPE_VALUE && lDeltaY > 0) || (lDeltaY > MIN_Y_AXIS_SWIPE_VALUE && lDeltaY < 0))) {
                    updateScroll(false);
                    mIsScrolling = false;
                    mDeleteButtonStatus = DELETE_VIEW_VISIBLE;
                    onSwipeLeft(mRecyclerView, mDownPosition);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent fMotionEvent) {
            View lRowTopmostView = getChild(mRecyclerView, fMotionEvent, false);
            if (mDownView != null && mDeleteButtonStatus == DELETE_VIEW_INVISIBLE) {
                mOnRecyclerListClickListener.onRecyclerListMainItemClick(((ViewGroup)mDownView).getChildAt(MAIN_ITEM_VIEW_INDEX).getTag());

            } else if (mDownView != null) {
                //handle delete button click
                mDeleteButtonStatus = DELETE_VIEW_INVALIDATE;
                mDownX = 0;
                mDownY = 0;
                updateScroll(false);

                View lDeleteView;
                lDeleteView = getChild(lRowTopmostView, fMotionEvent, true);

                if (lDeleteView != null) {
                    Object lTag=null;
                    View lChild=getChild(lDeleteView, fMotionEvent, false);
                    if(lChild!=null){
                        lTag=lChild.getTag();
                    }
                    else{
                        lTag= lDeleteView.getTag();
                    }
                    mOnRecyclerListClickListener.onRecyclerListOptionItemClick(lTag);
                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return !mIsScrolling;
        }
    };

    /**
     * It gives the child of view
     * @param fChild
     * @param fMotionEvent
     * @param fDeleteActionFlag
     * @return
     */
    private View getChild(View fChild, MotionEvent fMotionEvent, boolean fDeleteActionFlag) {

        int[] lViewCoordinates;
        Rect lViewRect;
        int lXPosition, lYPosition, lChildCount;
        View lChildView;

        // Find the child view that was touched (perform a hit test)
        lViewRect = new Rect();
        lChildCount = ((ViewGroup) fChild).getChildCount();
        lViewCoordinates = new int[2];

        fChild.getLocationOnScreen(lViewCoordinates);
        lXPosition = (int) fMotionEvent.getRawX() - lViewCoordinates[0];
        lYPosition = (int) fMotionEvent.getRawY() - lViewCoordinates[1];

        for (int i = 0; i < lChildCount; i++) {
            lChildView = ((ViewGroup) fChild).getChildAt(i);
            lChildView.getHitRect(lViewRect);

            if ((fDeleteActionFlag && lViewRect.contains(lXPosition, lYPosition)) || (lViewRect.contains(lXPosition, lYPosition))) {
                    return lChildView;
            }
        }
        return null;
    }

    /**
     * Custom Layout manager to handle scroll on recycler view
     */
    public class CustomGridLayoutManager extends LinearLayoutManager {
        private boolean mIsScrollEnabled = true;

        public CustomGridLayoutManager(Context fContext) {
            super(fContext);
        }

        /**
         * Update the scrolling property og recycler view
         *
         * @param fFlag
         */
        public void setmIsScrollEnabled(boolean fFlag) {
            this.mIsScrollEnabled = fFlag;
        }

        @Override
        public boolean canScrollVertically() {
            return mIsScrollEnabled && super.canScrollVertically();
        }
    }


}