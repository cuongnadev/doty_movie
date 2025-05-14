# Project Structure

Dự án này được tổ chức theo kiến trúc MVVM kết hợp với Clean Architecture. Dưới đây là mô tả chi tiết các thư mục và chức năng của chúng.

## **1. data/**
📌 **Chứa dữ liệu ứng dụng, bao gồm API, cơ sở dữ liệu, và repository.**

- **`repository/`**: Chứa các class triển khai repository, gọi API hoặc DB để lấy dữ liệu.
- **`remote/`**: Chứa API service (Retrofit hoặc Firebase).
- **`local/`**: Chứa database nếu bạn dùng Room hoặc SharedPreferences.
- **`model/`**: Chứa các DTO (Data Transfer Object) hoặc Entity.

Ví dụ:
```
data/
 ├── repository/
 │    ├── MovieRepositoryImpl.kt
 │    ├── UserRepositoryImpl.kt
 ├── remote/
 │    ├── MovieApiService.kt
 ├── local/
 │    ├── MovieDao.kt
 ├── model/
 │    ├── MovieDto.kt
```

---

## **2. domain/**
📌 **Chứa logic nghiệp vụ (Business Logic), độc lập với UI và nguồn dữ liệu.**

- **`model/`**: Chứa các Entity (lớp dữ liệu cốt lõi).
- **`repository/`**: Định nghĩa interface repository, giúp tách biệt logic xử lý và nguồn dữ liệu.
- **`usecase/`**: Chứa các UseCase, mỗi class xử lý một tác vụ cụ thể.

Ví dụ:
```
domain/
 ├── model/
 │    ├── Movie.kt
 ├── repository/
 │    ├── MovieRepository.kt
 ├── usecase/
 │    ├── GetMoviesUseCase.kt
```

---

## **3. di/**
📌 **Chứa cấu hình Dependency Injection (Hilt, Dagger, Koin).**

- **`AppModule.kt`**: Định nghĩa các dependency chung như database, shared preferences.
- **`NetworkModule.kt`**: Định nghĩa các dependency liên quan đến API.
- **`RepositoryModule.kt`**: Bind các repository vào Hilt/Dagger.

Ví dụ:
```
di/
 ├── AppModule.kt
 ├── NetworkModule.kt
 ├── RepositoryModule.kt
```

---

## **4. ui/**
📌 **Chứa tất cả thành phần liên quan đến giao diện người dùng.**

- **`component/`**: Chứa các thành phần UI có thể tái sử dụng.
- **`navigation/`**: Chứa logic điều hướng (Navigation Component hoặc Jetpack Compose Navigation).
- **`screen/`**: Chứa các màn hình chính của ứng dụng.
- **`viewmodel/`**: Chứa các ViewModel để quản lý trạng thái UI.

Ví dụ:
```
ui/
 ├── component/
 │    ├── CustomButton.kt
 ├── navigation/
 │    ├── NavigationGraph.kt
 ├── screen/
 │    ├── MovieListScreen.kt
 ├── viewmodel/
 │    ├── MovieViewModel.kt
```

---

## **5. utils/**
📌 **Chứa các class tiện ích chung có thể tái sử dụng.**

Ví dụ:
```
utils/
 ├── DateFormatter.kt
 ├── NetworkUtils.kt
```

---

## **6. MainActivity.kt**
📌 **Điểm bắt đầu của ứng dụng, nơi khởi tạo UI và điều hướng.**

---

📌 **Lưu ý:**
- Nếu dự án nhỏ, bạn có thể đơn giản hóa thư mục.
- Nếu dự án lớn, tổ chức theo kiến trúc này giúp bảo trì dễ dàng hơn.

🚀 **Happy coding!**

