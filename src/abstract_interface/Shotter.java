package abstract_interface;

import java.util.List;

import gameObject.Bullet;

/**
 * 抽象類別 Shotter，表示具有射擊能力的敵人。
 * 繼承自 Enemy 並實作 Shottable 介面。
 */
public abstract class Shotter extends Enemy implements Shottable {

    /** 管理此類敵人所發射的所有子彈列表 */
    public static List<Bullet> bullets;

}
