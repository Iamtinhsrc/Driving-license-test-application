# 🚗 Driving License Test Application

Ứng dụng thi bằng lái xe ô tô được phát triển bằng **Kotlin** theo kiến trúc **MVVM** và sử dụng **multi-module architecture** để tách biệt logic từng phần, giúp dễ dàng mở rộng và bảo trì.

---

## 🧩 Cấu trúc module

Dự án được chia thành nhiều module nhỏ, đảm nhiệm các vai trò riêng biệt:

* **app** — Điểm khởi đầu của ứng dụng, quản lý `Navigation Graph`.
* **core-database** — Quản lý cơ sở dữ liệu bằng **Room Database** gồm các DAO
* **core-di** — Cấu hình **Hilt Dependency Injection**.
* **core-model** — Định nghĩa các model dùng chung giữa các module.
* **core-navigation** — Xử lý điều hướng giữa các màn hình bằng **Navigation Component (Safe Args)**.
* **core-network** — Dùng Retrofit.
* **core-resources** — Quản lý string, color, dimension, drawable chung.
* **core-utils** — Các hàm tiện ích (extension,....).
* **feature-category / feature-exam / feature-home / feature-question / feature-history / feature-guide** — Mỗi module là một tính năng riêng biệt.

---

## ⚙️ Công nghệ & Kỹ thuật sử dụng

* **Ngôn ngữ:** Kotlin
* **Kiến trúc:** MVVM (Model - View - ViewModel)
* **Dependency Injection:** Dagger Hilt
* **Database:** Room
* **Navigation:** Navigation Component (Safe Args)
* **Asynchronous:** Kotlin Coroutines + Flow
* **Testing:** Unit Test & UI Test
* **UI:** ViewBinding, RecyclerView, ConstraintLayout
* **Pattern:** Repository Pattern, Clean Architecture, Modularization

---

## 📱 Tính năng chính

* Thi sát hạch lý thuyết (Exam feature)
* Xem lại lịch sử làm bài (History)
* Danh mục câu hỏi (Category)
* Câu hỏi ngẫu nhiên, trắc nghiệm (Question)
* Hướng dẫn thi, mẹo thi (Guide)
* Giao diện trực quan, dễ sử dụng
* Hỗ trợ đa module giúp dễ mở rộng

---

## 🧠 Kiến thức & kỹ năng đạt được

* Xây dựng ứng dụng Android theo mô hình **MVVM**
* Thiết lập **multi-module Gradle project**
* Sử dụng **Room + LiveData + ViewModel + Hilt** 
* Thiết kế **UI có khả năng tái sử dụng và dễ bảo trì**
* Hiểu rõ vòng đời Fragment, ViewModel và Navigation

---

## 🚀 Hướng phát triển

* Tích hợp **API online** cho bộ câu hỏi và kết quả thi.
* Thêm **chức năng đăng nhập/đăng ký** với Firebase Authentication.
* Đồng bộ dữ liệu với **Cloud Firestore hoặc Realtime Database**.
* Áp dụng **Paging 3** để tải danh sách câu hỏi lớn.
* Cải thiện **UI/UX** với animation và theme dark mode.

---

## 📎 Link dự án

GitHub: [Driving License Test Application](https://github.com/Iamtinhsrc/Driving-license-test-application)

---

## 👨‍💻 Tác giả

**Ngô Chí Tình** - 
Intern Android Developer - 
Ngôn ngữ: Kotlin - 
Liên hệ: [GitHub](https://github.com/Iamtinhsrc)
