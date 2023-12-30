package com.wizard.xiver


import java.io.Serializable

class CategoryItem(name: String, shortName: String, vararg subCategories: CategoryItem?) : Serializable {
    @JvmField
    val shortName: String
    @JvmField
    val subCategories: Array<CategoryItem?>
    @JvmField
    var name: String

    init {
        var subCategories = subCategories
        this.name = name
        this.shortName = shortName
        this.subCategories = subCategories as Array<CategoryItem?>
    }

    val catKey: String
        get() = if (name == "All") "all" else "cat"
}