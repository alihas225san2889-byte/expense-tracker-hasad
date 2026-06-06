# 📊 نظام إدارة المخزون والكاشير - دليل شامل

## نظرة عامة على البنية

هذا المشروع يوفر نظام متكامل لإدارة المخزون ونقطة البيع (POS) مع جلسات الكاشير.

### المكونات الرئيسية:

## 1️⃣ **Entities (الكيانات)**

### InventoryBox
```kotlin
@Entity(tableName = "inventory_boxes")
data class InventoryBox(
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val location: String = "",
    val totalCapacity: Double = 0.0,
    val currentQuantity: Double = 0.0
)
```

### Product
```kotlin
@Entity(tableName = "products")
data class Product(
    val id: Long = 0,
    val name: String,
    val barcode: String = "",
    val purchasePrice: Double = 0.0,
    val sellingPrice: Double = 0.0,
    val quantity: Double = 0.0,
    val inventoryBoxId: Long? = null
)
```

### StoreBox (صندوق البيع)
```kotlin
@Entity(tableName = "store_boxes")
data class StoreBox(
    val id: Long = 0,
    val name: String,
    val currentBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0
)
```

### Sale (المبيعة)
```kotlin
@Entity(tableName = "sales")
data class Sale(
    val id: Long = 0,
    val storeBoxId: Long,
    val totalAmount: Double = 0.0,
    val paymentMethod: String = "نقد"
)
```

### SaleItem (عنصر البيع)
```kotlin
@Entity(tableName = "sale_items")
data class SaleItem(
    val id: Long = 0,
    val saleId: Long,
    val productId: Long,
    val quantity: Double = 0.0,
    val unitPrice: Double = 0.0,
    val totalPrice: Double = 0.0
)
```

### InventoryTransaction (حركات المخزون)
```kotlin
@Entity(tableName = "inventory_transactions")
data class InventoryTransaction(
    val id: Long = 0,
    val productId: Long,
    val transactionType: String = "إضافة",
    val quantity: Double = 0.0
)
```

### CashierSession (جلسة الكاشير)
```kotlin
@Entity(tableName = "cashier_sessions")
data class CashierSession(
    val id: Long = 0,
    val storeBoxId: Long,
    val cashierName: String = "",
    val openingBalance: Double = 0.0,
    val totalSales: Double = 0.0,
    val status: String = "مفتوح"
)
```

## 2️⃣ **DAOs (Data Access Objects)**

كل DAO توفر عمليات CRUD كاملة:

```kotlin
@Dao
interface InventoryBoxDao {
    @Insert suspend fun insert(box: InventoryBox): Long
    @Update suspend fun update(box: InventoryBox)
    @Delete suspend fun delete(box: InventoryBox)
    @Query("SELECT * FROM inventory_boxes") fun getAll(): Flow<List<InventoryBox>>
}
```

## 3️⃣ **Repositories**

### InventoryRepository
- إدارة الصناديق والمنتجات
- تتبع حركات المخزون

### StoreRepository
- إدارة صناديق البيع
- معالجة المبيعات وعناصرها

### CashierRepository
- إدارة جلسات الكاشير
- فتح وإغلاق الجلسات

## 4️⃣ **ViewModels**

### InventoryViewModel
```kotlin
class InventoryViewModel(private val repository: InventoryRepository) : ViewModel() {
    val allProducts: StateFlow<List<Product>>
    val lowStockProducts: StateFlow<List<Product>>
    val totalInventoryValue: StateFlow<Double>
    
    fun addProduct(product: Product)
    fun updateProduct(product: Product)
    fun deleteProduct(product: Product)
}
```

### StoreViewModel
```kotlin
class StoreViewModel(private val repository: StoreRepository) : ViewModel() {
    val allStoreBoxes: StateFlow<List<StoreBox>>
    val currentSaleItems: StateFlow<List<SaleItem>>
    val currentSaleTotal: StateFlow<Double>
    
    fun addSaleItem(item: SaleItem)
    fun completeSale(sale: Sale)
}
```

### CashierViewModel
```kotlin
class CashierViewModel(private val repository: CashierRepository) : ViewModel() {
    val currentSession: StateFlow<CashierSession?>
    val isSessionOpen: StateFlow<Boolean>
    
    fun openSession(session: CashierSession)
    fun closeSession(sessionId: Long)
}
```

## 5️⃣ **UI Screens**

### InventoryScreen
- عرض قائمة المنتجات
- إضافة منتجات جديدة
- عرض المنتجات برصيد منخفض

### StoreScreen
- واجهة نقطة البيع (POS)
- إضافة عناصر للبيع
- إكمال المبيعة

### CashierScreen
- فتح جلسة الكاشير
- عرض الرصيد والمبيعات
- إغلاق الجلسة

## 6️⃣ **قاعدة البيانات**

### AppDatabase
```kotlin
@Database(
    entities = [
        InventoryBox::class,
        Product::class,
        StoreBox::class,
        Sale::class,
        SaleItem::class,
        InventoryTransaction::class,
        CashierSession::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase()
```

## 📋 خطوات الاستخدام

### 1. إنشاء منتج
```kotlin
val product = Product(
    name = "أرز",
    purchasePrice = 50.0,
    sellingPrice = 80.0,
    quantity = 100.0
)
inventoryViewModel.addProduct(product)
```

### 2. فتح جلسة كاشير
```kotlin
val session = CashierSession(
    cashierName = "أحمد",
    openingBalance = 500.0,
    storeBoxId = 1
)
cashierViewModel.openSession(session)
```

### 3. إضافة عنصر للبيع
```kotlin
val saleItem = SaleItem(
    productName = "أرز",
    quantity = 5.0,
    unitPrice = 80.0,
    totalPrice = 400.0
)
storeViewModel.addSaleItem(saleItem)
```

### 4. إكمال البيع
```kotlin
val sale = Sale(
    storeBoxId = 1,
    totalAmount = 400.0
)
storeViewModel.completeSale(sale)
```

## 🔄 تدفق البيانات

```
UI Screen
    ↓
ViewModel
    ↓
Repository
    ↓
DAO
    ↓
Room Database
```

## 🛠️ الميزات الرئيسية

✅ **إدارة المخزون**
- تتبع المنتجات
- تنبيهات الرصيد المنخفض
- حساب قيمة المخزون

✅ **نقطة البيع**
- واجهة بيع سهلة
- حساب الإجمالي تلقائياً
- تسجيل المبيعات

✅ **جلسات الكاشير**
- فتح وإغلاق جلسات
- تتبع الرصيد
- تقارير يومية

## 📊 قاعدة البيانات

العلاقات بين الجداول:
- **Product** ← **InventoryBox** (علاقة واحد لعديد)
- **Sale** ← **StoreBox** (علاقة واحد لعديد)
- **SaleItem** ← **Sale** (علاقة واحد لعديد)
- **SaleItem** ← **Product** (علاقة واحد لعديد)
- **CashierSession** ← **StoreBox** (علاقة واحد لعديد)

## 🚀 التثبيت والتشغيل

1. استنساخ المستودع
2. فتح في Android Studio
3. تشغيل التطبيق على محاكي أو جهاز

## 📝 ملاحظات

- التطبيق يستخدم **Kotlin Coroutines** للعمليات غير المتزامنة
- **StateFlow** يوفر تحديثات مباشرة للواجهة
- **Room Database** توفر تخزيناً محلياً آمناً

---

**تم بناء هذا النظام لتوفير حل متكامل لإدارة المحلات والمخازن! 🎉**
