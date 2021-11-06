package indg.com.cover2protect.util.badge_count;

public interface BadgeCounter {
    void increment();
    void decrement();
    void setBadgeBackground(int color);
    void setCount(int count);
    void setCountWithAnimation(int count);
    void reset();
    int getCount();
}

