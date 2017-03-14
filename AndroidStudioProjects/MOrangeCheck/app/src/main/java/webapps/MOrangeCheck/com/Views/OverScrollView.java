package webapps.MOrangeCheck.com.Views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import webapps.MOrangeCheck.com.Tool.RefreshProcessor;


/**
 * 具有回弹功能的ScrollView
 * .,:,,,                                        .::,,,::.
 * .::::,,;;,                                  .,;;:,,....:i:
 * :i,.::::,;i:.      ....,,:::::::::,....   .;i:,.  ......;i.
 * :;..:::;::::i;,,:::;:,,,,,,,,,,..,.,,:::iri:. .,:irsr:,.;i.
 * ;;..,::::;;;;ri,,,.                    ..,,:;s1s1ssrr;,.;r,
 * :;. ,::;ii;:,     . ...................     .;iirri;;;,,;i,
 * ,i. .;ri:.   ... ............................  .,,:;:,,,;i:
 * :s,.;r:... ....................................... .::;::s;
 * ,1r::. .............,,,.,,:,,........................,;iir;
 * ,s;...........     ..::.,;:,,.          ...............,;1s
 * :i,..,.              .,:,,::,.          .......... .......;1,
 * ir,....:rrssr;:,       ,,.,::.     .r5S9989398G95hr;. ....,.:s,
 * ;r,..,s9855513XHAG3i   .,,,,,,,.  ,S931,.,,.;s;s&BHHA8s.,..,..:r:
 * :r;..rGGh,  :SAG;;G@BS:.,,,,,,,,,.r83:      hHH1sXMBHHHM3..,,,,.ir.
 * ,si,.1GS,   sBMAAX&MBMB5,,,,,,:,,.:&8       3@HXHBMBHBBH#X,.,,,,,,rr
 * ;1:,,SH:   .A@&&B#&8H#BS,,,,,,,,,.,5XS,     3@MHABM&59M#As..,,,,:,is,
 * .rr,,,;9&1   hBHHBB&8AMGr,,,,,,,,,,,:h&&9s;   r9&BMHBHMB9:  . .,,,,;ri.
 * :1:....:5&XSi;r8BMBHHA9r:,......,,,,:ii19GG88899XHHH&GSr.      ...,:rs.
 * ;s.     .:sS8G8GG889hi.        ....,,:;:,.:irssrriii:,.        ...,,i1,
 * ;1,         ..,....,,isssi;,        .,,.                      ....,.i1,
 * ;h:               i9HHBMBBHAX9:         .                     ...,,,rs,
 * ,1i..            :A#MBBBBMHB##s                             ....,,,;si.
 * .r1,..        ,..;3BMBBBHBB#Bh.     ..                    ....,,,,,i1;
 * :h;..       .,..;,1XBMMMMBXs,.,, .. :: ,.               ....,,,,,,ss.
 * ih: ..    .;;;, ;;:s58A3i,..    ,. ,.:,,.             ...,,,,,:,s1,
 * .s1,....   .,;sh,  ,iSAXs;.    ,.  ,,.i85            ...,,,,,,:i1;
 * .rh: ...     rXG9XBBM#M#MHAX3hss13&&HHXr         .....,,,,,,,ih;
 * .s5: .....    i598X&&A&AAAAAA&XG851r:       ........,,,,:,,sh;
 * . ihr, ...  .         ..                    ........,,,,,;11:.
 * ,s1i. ...  ..,,,..,,,.,,.,,.,..       ........,,.,,.;s5i.
 * .:s1r,......................       ..............;shs,
 * . .:shr:.  ....                 ..............,ishs.
 * .,issr;,... ...........................,is1s;.
 * .,is1si;:,....................,:;ir1sr;,
 * ..:isssssrrii;::::::;;iirsssssr;:..
 * .,::iiirsssssssssrri;;:.
 */
public class OverScrollView extends ScrollView {

    // 拖动的距离 size = 4 的意思 只允许拖动屏幕的1/4
    private static final int size = 3;
    private View inner;
    private float y;
    private Rect normal = new Rect();
    private float mTouchX, mTouchY;
    private RefreshProcessor refreshProcessor;

    public OverScrollView(Context context) {
        super(context, null, 0);
        if (refreshProcessor == null) {
            refreshProcessor = new RefreshProcessor(context);
        }
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        if (refreshProcessor == null) {
            refreshProcessor = new RefreshProcessor(context);
        }    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (refreshProcessor == null) {
            refreshProcessor = new RefreshProcessor(context);
        }    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = refreshProcessor.interceptTouchEvent(ev);
        return intercept || super.onInterceptTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                y = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    // Log.v("mlguitar", "will up and animation");
                    animation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
                /**
                 * size=4 表示 拖动的距离为屏幕的高度的1/4
                 */
                int deltaY = (int) (preY - nowY) / size;
                // 滚动
                // scrollBy(0, deltaY);

                y = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                        return;
                    }
                    int yy = inner.getTop() - deltaY;

                    // 移动布局
                    inner.layout(inner.getLeft(), yy, inner.getRight(),
                            inner.getBottom() - deltaY);
                }
                break;
            default:
                break;
        }
    }

    // 开启动画移动

    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
        ta.setDuration(200);
        inner.startAnimation(ta);
        // 设置回到正常的布局位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    // 是否需要移动布局
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

//    public class CoProcessor {
//        private RefreshProcessor refreshProcessor;
//
//
//        private final static int PULLING_TOP_DOWN = 0;
//        private final static int PULLING_BOTTOM_UP = 1;
//        private int state = PULLING_TOP_DOWN;
//
//        private static final int EX_MODE_NORMAL = 0;
//        private static final int EX_MODE_FIXED = 1;
//        private int exHeadMode = EX_MODE_NORMAL;
//
//
//        public CoProcessor() {
//
//
//        }
//
//
//        public boolean interceptTouchEvent(MotionEvent ev) {
//            return refreshProcessor.interceptTouchEvent(ev);
//        }
//
//
//        /**
//         * 在越界时阻止再次进入这个状态而导致动画闪烁。  Prevent entering the overscroll-mode again on animating.
//         */
//        private boolean isOverScrollTopLocked = false;
//
//        public void lockOsTop() {
//            isOverScrollTopLocked = true;
//        }
//
//        public void releaseOsTopLock() {
//            isOverScrollTopLocked = false;
//        }
//
//        public boolean isOsTopLocked() {
//            return isOverScrollTopLocked;
//        }
//
//        private boolean isOverScrollBottomLocked = false;
//
//        public void lockOsBottom() {
//            isOverScrollBottomLocked = true;
//        }
//
//        public void releaseOsBottomLock() {
//            isOverScrollBottomLocked = false;
//        }
//
//        public boolean isOsBottomLocked() {
//            return isOverScrollBottomLocked;
//        }
//
//        /**
//         * 在添加附加Header前锁住，阻止一些额外的位移动画
//         */
//        private boolean isExHeadLocked = true;
//
//        public boolean isExHeadLocked() {
//            return isExHeadLocked;
//        }
//
//        private void unlockExHead() {
//            isExHeadLocked = false;
//        }
//
//
//        public void setExHeadNormal() {
//            exHeadMode = EX_MODE_NORMAL;
//        }
//
//        public void setExHeadFixed() {
//            exHeadMode = EX_MODE_FIXED;
//        }
//
//        public boolean isExHeadNormal() {
//            return exHeadMode == EX_MODE_NORMAL;
//        }
//
//        public boolean isExHeadFixed() {
//            return exHeadMode == EX_MODE_FIXED;
//        }
//
//
//        public void setStatePTD() {
//            state = PULLING_TOP_DOWN;
//        }
//
//        public void setStatePBU() {
//            state = PULLING_BOTTOM_UP;
//        }
//
//        public boolean isStatePTD() {
//            return PULLING_TOP_DOWN == state;
//        }
//
//        public boolean isStatePBU() {
//            return PULLING_BOTTOM_UP == state;
//        }
//    }

}
// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛