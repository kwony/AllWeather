package kwony.allweather.data.asset

enum class AssetType(val code: Int) {
    NONE(-1),
    DOMESTIC_STOCK(0),
    DOMESTIC_BOND(1),
    ADVANCED_STOCK(2),
    ADVANCED_BOND(3),
    EMERGING_STOCK(4),
    EMERGING_BOND(5),
    GOLD(6),
    SILVER(7),
    COMMODITY(8),
    MIXED(100);

    companion object {
        @JvmStatic
        fun toEnum(value: Int) = values().first { it.code == value }
    }
}