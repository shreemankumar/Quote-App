# 🌟 Jetpack Compose User Quotes App

This is a modern Android application built with **Jetpack Compose**, designed to demonstrate user onboarding with persistent login state using **Jetpack DataStore**, animations, and rich UI components.

## 📱 Features

- 🖼 Splash screen with smooth transitions
- 📝 Onboarding form with input validation
- 💾 Persistent login state using DataStore
- 🌄 Asynchronous image loading using Coil
- 🎨 Material 3 Design with Jetpack Compose
- 🚀 Clean architecture & modular design
- 🧪 Preview support for UI testing

---

## 🧰 Tech Stack

### 👨‍💻 Languages & Frameworks
- **Kotlin**
- **Jetpack Compose** (Material 3)

### 🎨 UI & UX
- `androidx.compose.material3` – Material Design components
- `androidx.compose.foundation` – Layouts, gestures, scrolling
- `androidx.compose.animation` – Transitions and animation effects

### 🗂 State & Persistence
- `androidx.lifecycle` – ViewModel integration
- `androidx.datastore` – Data persistence for user state

### 📦 Image Loading
- [**Coil (v3)**](https://coil-kt.github.io/coil/) – For loading images asynchronously

### ⚙️ Utilities
- `androidx.activity` – Compose lifecycle
- `androidx.core.splashscreen` – For managing splash screen

---

## 📸 Screenshots

| Splash Screen | Onboarding | Home Screen |
|---------------|------------|-------------|
| ![Splash](splash.png) | ![Register](Register.png) | ![Home](home.png) |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Flamingo or later
- Kotlin 1.9+
- Android SDK 33+
- Minimum SDK 25

### Clone the Repository

```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```
  Run the App
1. Open in Android Studio.
2. Sync Gradle.
3. Run on an emulator or device.

🗃 Folder Structure

```
├── MainActivity.kt
├── ui/
│   ├── RegisterPageUI.kt
│   ├── HomePage.kt
│   └── components/
├── datastore/
│   └── DataStoreManager.kt
├── theme/
│   ├── Color.kt
│   ├── Theme.kt
│   └── ...
└── assets/
    └── Images and icons
```

✨ Contributing
Contributions are welcome! Feel free to open issues and submit pull requests.

📄 License
This project is licensed under the MIT License.

🙋‍♂️ Acknowledgements
1. Jetpack Compose
2. Coil
3. Google Material Design
4. 
```
---

### ✅ What You Should Customize

- Replace `your-username` and `your-repo-name` with your actual GitHub username and repository name.
- Add screenshots (`splash.png`, `register.png`, `home.png`) in a folder named `assets/` or remove the screenshot section.
- Add a real `LICENSE` file (MIT or Apache 2.0 is common).
- Add your name or team in the contributors or acknowledgments section if you'd like.
```

