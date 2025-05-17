package abstract_interface;

/**
 * Shottable 介面代表具有射擊能力的物件。
 * 所有實作此介面的類別必須提供射擊行為。
 */
public interface Shottable {

    /**
     * 執行射擊動作。
     */
    void shot();
}
