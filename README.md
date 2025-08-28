# 📰 NewsApp
Welcome to **NewsApp** — a modern Android application built with **Jetpack Compose** that brings you the latest headlines from around the world. Stay updated with real-time news, browse by categories, and enjoy a secure authentication system powered by **Firebase Authentication**.

---
## ✨ Features
- 🔐 **Firebase Authentication**: Sign up, Sign in, and Sign out securely with email/password
- 🗞️ **Top Headlines**: Get the latest breaking news in real-time
- 📂 **News Categories**: Browse by topics: General, Business, Technology, Sports, Health, Science, Entertainment
- 🔍 **WebView Reader**: Read full articles inside the app with smooth navigation
- 🎨 **Modern UI**: Clean, customizable interface with **Material 3** design
- 🌙 **Dark/Light Theme Support** for a better reading experience
- ❤️ **Session Handling** (sign-out, re-login logic present in ViewModel)
- 🔄 **Pull-To-Refresh** for news feed
- 🚀 **Composable Architecture** using **ViewModels & State Management**

---
## 🛠️ Tech Stack
- Kotlin
- Jetpack Compose
- Material 3 (Material You)
- Firebase Authentication
- Retrofit + Gson (REST API, JSON parsing)
- NewsAPI.org (News Provider)
- Coil (Async image loading)
- AndroidX Lifecycle + ViewModel + Coroutine

---
## 📦 Setup Instructions

1. **Clone this repository**

2. **Open in Android Studio** (latest stable).

3. **Add API Key** for NewsAPI:
- Get your API key from [NewsAPI.org](https://newsapi.org).
- Add it inside your `NewsViewModel.kt` or in `local.properties`:
  ```
  INSERT YOUR NEWS API HERE = your_api_key_here
  ```

4. **Configure Firebase Authentication**:
- Connect your project to Firebase via the **Firebase Console**.
- Enable **Email/Password Authentication** under Authentication → Sign-in Methods.

5. **Sync Gradle** to download dependencies.

6. **Run the app** on an emulator or device.

---
## 📲 Download APK

[![Download NewsApp](https://img.shields.io/badge/Download-APK-brightgreen)](https://github.com/ItsDeadlyProgrammer/NewsApp/releases/download/v1.0.0/news.apk)

---

## 🧑‍💻 Author
**Harshvardhan Singh**  
[![GitHub](https://img.shields.io/badge/GitHub-ItsDeadlyProgrammer-blue)](https://github.com/ItsDeadlyProgrammer)

---
❤️ Whether you’re exploring **Firebase Auth**, **Retrofit + Compose integration**, or just love beautiful reading apps — feel free to fork, contribute, and share! PRs are always welcome. ❤️

