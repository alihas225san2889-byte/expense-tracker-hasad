# 📊 تطبيق دفتر الحسابات حاشد فون الذكي

تطبيق اندرويد حديث لإدارة المصاريف والدخل بسهولة وفعالية.

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)
![Android](https://img.shields.io/badge/Android-24%2B-brightgreen.svg)

## ✨ المميزات الرئيسية

- ✅ **إضافة المصاريف والدخل** - واجهة سهلة وسريعة
- ✅ **تصنيفات ذكية** - طعام، مواصلات، راتب، وغيرها
- ✅ **لوحة تحكم شاملة** - عرض الرصيد والدخل والمصاريف
- ✅ **تقارير تفصيلية** - شهرية وسنوية مع رسوم بيانية
- ✅ **البحث والتصفية** - جد معاملاتك بسهولة
- ✅ **واجهة حديثة** - تصميم Material Design 3
- ✅ **قاعدة بيانات آمنة** - حفظ محلي آمن

## 🛠️ التقنيات المستخدمة

| التقنية | الإصدار |
|--------|---------|
| Kotlin | Latest |
| Jetpack Compose | 1.5.4 |
| Room Database | 2.6.1 |
| MVVM Architecture | - |
| Coroutines | 1.7.3 |
| Navigation Compose | 2.7.5 |

## 📱 متطلبات النظام

- Android 7.0 (API 24) أو أعلى
- 5 MB من مساحة التخزين
- الاتصال بالإنترنت (اختياري)

## 🚀 البدء السريع

### 1️⃣ استنساخ المستودع
```bash
git clone https://github.com/alihas225san2889-byte/expense-tracker-hasad.git
cd expense-tracker-hasad
```

### 2️⃣ فتح في Android Studio
```bash
android-studio .
```

### 3️⃣ تشغيل التطبيق
- اختر محاكي أو جهاز
- اضغط Run

## 📁 هيكل المشروع

```
expense-tracker-hasad/
├── app/
│   ├── src/main/
│   │   ├── java/com/hasad/expensetracker/
│   │   │   ├── data/
│   │   │   │   ├── dao/
│   │   │   │   ├── database/
│   │   │   │   ├── entity/
│   │   │   │   └── repository/
│   │   │   ├── ui/
│   │   │   │   ├── screens/
│   │   │   │   ├── theme/
│   │   │   │   ├── utils/
│   │   │   │   └── viewmodel/
│   │   │   └── MainActivity.kt
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
├── LICENSE
└── CONTRIBUTING.md
```

## 🎯 الشاشات الرئيسية

### 🏠 الشاشة الرئيسية
- عرض الرصيد الحالي
- إحصائيات الدخل والمصاريف
- قائمة المعاملات بالأحدث أولاً
- تصفية حسب النوع (دخل/مصروف/الكل)

### ➕ إضافة معاملة
- اختيار نوع (مصروف/دخل)
- اختيار فئة مناسبة
- إدخال المبلغ والتاريخ
- ملاحظات إضافية

### 📊 التقارير
- ملخص شهري للمصاريف
- توزيع المصاريف حسب الفئات
- رسوم بيانية بصرية
- فترات زمنية مختلفة

### ⚙️ الإعدادات
- عن التطبيق
- اللغة والإعدادات
- النسخ الاحتياطي
- حذف البيانات

## 🎓 أمثلة الاستخدام

### إضافة معاملة جديدة
```kotlin
viewModel.addTransaction(
    title = "شراء مواد غذائية",
    amount = 150.0,
    category = "الطعام",
    type = TransactionType.EXPENSE,
    description = "من السوق المركزي"
)
```

### الحصول على المعاملات
```kotlin
viewModel.allTransactions.collect { transactions ->
    // تحديث الواجهة بقائمة المعاملات
}
```

## 🔄 دورة حياة المشروع

```
Requirement → Planning → Development → Testing → Release → Maintenance
```

## 🤝 المساهمة

نرحب بجميع أنواع المساهمات! اقرأ [CONTRIBUTING.md](CONTRIBUTING.md) للتفاصيل.

### خطوات سريعة:
1. Fork المستودع
2. أنشئ فرع: `git checkout -b feature/اسم-الميزة`
3. اكتب الكود واختبره
4. اتجه: `git commit -m "الوصف"`
5. ادفع: `git push origin feature/اسم-الميزة`
6. افتح Pull Request

## 🐛 إبلاغ عن الأخطاء

وجدت خطأ؟ [افتح Issue جديد](../../issues/new) مع:
- وصف المشكلة
- خطوات إعادة الإنتاج
- الجهاز والنسخة المستخدمة

## 📈 خارطة الطريق المستقبلية

- [ ] 📊 رسوم بيانية متقدمة
- [ ] ☁️ نسخ احتياطية سحابية
- [ ] 🔔 إشعارات ذكية
- [ ] 📤 تصدير PDF/Excel
- [ ] 👥 مشاركة الأسرة
- [ ] 🌐 نسخة الويب

## 📞 التواصل والدعم

- 📧 البريد: support@hasad.com
- 🐛 الأخطاء: [Issues](../../issues)
- 💬 النقاش: [Discussions](../../discussions)

## 📄 الترخيص

هذا المشروع تحت ترخيص [MIT License](LICENSE)

## 👨‍💻 الفريق

**Hasad Phone Developer Team** ❤️

تم بناء هذا المشروع بـ ❤️ لتسهيل إدارة أموالك

---

> إذا أعجبك المشروع، لا تنسى إضافة ⭐ على GitHub!
