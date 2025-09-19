package com.example.money_manager.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class CategoryIcons {
    Restaurant,
    DirectionsCar,
    ShoppingCart,
    Home,
    LocalHospital,
    FitnessCenter,
    LocalCafe,
    AttachMoney,
    Movie,
    School,
    TrendingUp,

    AccountBalance,
    CreditCard,
    Savings,
    Payments,
    Receipt,

    DirectionsBus,
    Flight,
    Train,
    DirectionsBike,
    LocalTaxi,

    ShoppingBag,
    Store,
    LocalMall,
    TagFaces,
    Loyalty,

    LocalBar,
    LocalPizza,
    Cake,
    FreeBreakfast,
    WineBar,

    Kitchen,
    Chair,
    Yard,
    CleaningServices,
    Build,

    HealthAndSafety,
    Medication,
    Spa,
    SelfImprovement,
    Coronavirus,

    Face,
    Style,
    Palette,
    Brush,

    SportsEsports,
    MusicNote,
    TheaterComedy,
    SportsBaseball,
    Casino,

    ReceiptLong,
    Description,
    RequestQuote,
    Calculate,
    Assignment,

    SportsSoccer,
    SportsBasketball,
    SportsTennis,
    GolfCourse,
    Pool,

    BeachAccess,
    LocalFlorist,
    Terrain,
    Park,
    OutdoorGrill,

    Book,
    MenuBook,
    HistoryEdu,
    Science,
    Computer,

    FamilyRestroom,
    ChildCare,
    ChildFriendly,
    Toys,
    CakeBirthday,

    Help,
    Public,
    Star,
    Favorite,
    Work,
    CardGiftcard,
    Pets,
    ElectricalServices,
    LocalGroceryStore,
    Laptop,
    MovieFilter,
    BikeScooter
}

fun CategoryIcons.toImageVector(): ImageVector = when (this) {
    CategoryIcons.Restaurant -> Icons.Filled.Restaurant
    CategoryIcons.DirectionsCar -> Icons.Filled.DirectionsCar
    CategoryIcons.ShoppingCart -> Icons.Filled.ShoppingCart
    CategoryIcons.Home -> Icons.Filled.Home
    CategoryIcons.LocalHospital -> Icons.Filled.LocalHospital
    CategoryIcons.FitnessCenter -> Icons.Filled.FitnessCenter
    CategoryIcons.LocalCafe -> Icons.Filled.LocalCafe
    CategoryIcons.AttachMoney -> Icons.Filled.AttachMoney
    CategoryIcons.Movie -> Icons.Filled.Movie
    CategoryIcons.School -> Icons.Filled.School

    CategoryIcons.AccountBalance -> Icons.Filled.AccountBalance
    CategoryIcons.CreditCard -> Icons.Filled.CreditCard
    CategoryIcons.Savings -> Icons.Filled.Savings
    CategoryIcons.Payments -> Icons.Filled.Payments
    CategoryIcons.Receipt -> Icons.Filled.Receipt

    CategoryIcons.DirectionsBus -> Icons.Filled.DirectionsBus
    CategoryIcons.Flight -> Icons.Filled.Flight
    CategoryIcons.Train -> Icons.Filled.Train
    CategoryIcons.DirectionsBike -> Icons.AutoMirrored.Filled.DirectionsBike
    CategoryIcons.LocalTaxi -> Icons.Filled.LocalTaxi

    CategoryIcons.ShoppingBag -> Icons.Filled.ShoppingBag
    CategoryIcons.Store -> Icons.Filled.Store
    CategoryIcons.LocalMall -> Icons.Filled.LocalMall
    CategoryIcons.TagFaces -> Icons.Filled.TagFaces
    CategoryIcons.Loyalty -> Icons.Filled.Loyalty

    CategoryIcons.LocalBar -> Icons.Filled.LocalBar
    CategoryIcons.LocalPizza -> Icons.Filled.LocalPizza
    CategoryIcons.Cake -> Icons.Filled.Cake
    CategoryIcons.FreeBreakfast -> Icons.Filled.FreeBreakfast
    CategoryIcons.WineBar -> Icons.Filled.WineBar

    CategoryIcons.Kitchen -> Icons.Filled.Kitchen
    CategoryIcons.Chair -> Icons.Filled.Chair
    CategoryIcons.Yard -> Icons.Filled.Yard
    CategoryIcons.CleaningServices -> Icons.Filled.CleaningServices
    CategoryIcons.Build -> Icons.Filled.Build

    CategoryIcons.HealthAndSafety -> Icons.Filled.HealthAndSafety
    CategoryIcons.Medication -> Icons.Filled.Medication
    CategoryIcons.Spa -> Icons.Filled.Spa
    CategoryIcons.SelfImprovement -> Icons.Filled.SelfImprovement
    CategoryIcons.Coronavirus -> Icons.Filled.Coronavirus

    CategoryIcons.Face -> Icons.Filled.Face
    CategoryIcons.Style -> Icons.Filled.Style
    CategoryIcons.Palette -> Icons.Filled.Palette
    CategoryIcons.Brush -> Icons.Filled.Brush

    CategoryIcons.SportsEsports -> Icons.Filled.SportsEsports
    CategoryIcons.MusicNote -> Icons.Filled.MusicNote
    CategoryIcons.TheaterComedy -> Icons.Filled.TheaterComedy
    CategoryIcons.SportsBaseball -> Icons.Filled.SportsBaseball
    CategoryIcons.Casino -> Icons.Filled.Casino

    CategoryIcons.ReceiptLong -> Icons.AutoMirrored.Filled.ReceiptLong
    CategoryIcons.Description -> Icons.Filled.Description
    CategoryIcons.RequestQuote -> Icons.Filled.RequestQuote
    CategoryIcons.Calculate -> Icons.Filled.Calculate
    CategoryIcons.Assignment -> Icons.AutoMirrored.Filled.Assignment

    CategoryIcons.SportsSoccer -> Icons.Filled.SportsSoccer
    CategoryIcons.SportsBasketball -> Icons.Filled.SportsBasketball
    CategoryIcons.SportsTennis -> Icons.Filled.SportsTennis
    CategoryIcons.GolfCourse -> Icons.Filled.GolfCourse
    CategoryIcons.Pool -> Icons.Filled.Pool

    CategoryIcons.BeachAccess -> Icons.Filled.BeachAccess
    CategoryIcons.LocalFlorist -> Icons.Filled.LocalFlorist
    CategoryIcons.Terrain -> Icons.Filled.Terrain
    CategoryIcons.Park -> Icons.Filled.Park
    CategoryIcons.OutdoorGrill -> Icons.Filled.OutdoorGrill

    CategoryIcons.Book -> Icons.Filled.Book
    CategoryIcons.MenuBook -> Icons.AutoMirrored.Filled.MenuBook
    CategoryIcons.HistoryEdu -> Icons.Filled.HistoryEdu
    CategoryIcons.Science -> Icons.Filled.Science
    CategoryIcons.Computer -> Icons.Filled.Computer

    CategoryIcons.FamilyRestroom -> Icons.Filled.FamilyRestroom
    CategoryIcons.ChildCare -> Icons.Filled.ChildCare
    CategoryIcons.ChildFriendly -> Icons.Filled.ChildFriendly
    CategoryIcons.Toys -> Icons.Filled.Toys
    CategoryIcons.CakeBirthday -> Icons.Filled.Cake

    CategoryIcons.Help -> Icons.AutoMirrored.Filled.Help
    CategoryIcons.Public -> Icons.Filled.Public
    CategoryIcons.Star -> Icons.Filled.Star
    CategoryIcons.Favorite -> Icons.Filled.Favorite
    CategoryIcons.Work -> Icons.Filled.Work
    CategoryIcons.CardGiftcard -> Icons.Filled.CardGiftcard
    CategoryIcons.Pets -> Icons.Filled.Pets
    CategoryIcons.ElectricalServices -> Icons.Filled.ElectricalServices
    CategoryIcons.LocalGroceryStore -> Icons.Filled.LocalGroceryStore
    CategoryIcons.Laptop -> Icons.Filled.Laptop
    CategoryIcons.MovieFilter -> Icons.Filled.MovieFilter
    CategoryIcons.BikeScooter -> Icons.Filled.BikeScooter
    CategoryIcons.TrendingUp -> Icons.AutoMirrored.Filled.TrendingUp

}

fun iconsForType(type: TransactionType): List<CategoryIcons> = when (type) {
    TransactionType.INCOME -> listOf(
        CategoryIcons.AttachMoney,
        CategoryIcons.AccountBalance,
        CategoryIcons.CreditCard,
        CategoryIcons.Savings,
        CategoryIcons.Payments,
        CategoryIcons.TrendingUp,
        CategoryIcons.Work,
        CategoryIcons.Receipt,
        CategoryIcons.CardGiftcard,
        CategoryIcons.RequestQuote,
        CategoryIcons.Calculate,
        CategoryIcons.Assignment,
        CategoryIcons.Star,
        CategoryIcons.Help
    )

    TransactionType.EXPENSE -> listOf(
        CategoryIcons.Restaurant,
        CategoryIcons.DirectionsCar,
        CategoryIcons.ShoppingCart,
        CategoryIcons.Home,
        CategoryIcons.LocalHospital,
        CategoryIcons.FitnessCenter,
        CategoryIcons.LocalCafe,
        CategoryIcons.AttachMoney,
        CategoryIcons.Movie,
        CategoryIcons.School,
        CategoryIcons.Pool,
        CategoryIcons.Toys,
        CategoryIcons.GolfCourse
    )
}

