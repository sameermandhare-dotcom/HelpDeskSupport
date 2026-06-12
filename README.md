# HelpDeskSupport - Android IT Help Desk Application

An Android application for IT help desk support that provides telephonic assistance with AI-generated feedback for Smart3 Android application users.

## Features

- 📞 **Phone Call Management** - Initiate, receive, and manage support calls
- 🤖 **AI Agent Integration** - Real-time AI-generated feedback during calls
- 📱 **MVVM Architecture** - Clean, maintainable code structure
- 💾 **Call History** - Detailed logs of all support interactions
- 🔄 **Real-time Status** - Live call status tracking
- 📊 **Dashboard** - Support statistics and quick access

## Architecture

- **MVVM Pattern** with ViewModel and LiveData
- **Repository Pattern** for data access
- **Dependency Injection** using Hilt
- **Kotlin Coroutines** for async operations
- **Room Database** for local persistence
- **Retrofit** for API communications

## Technologies Used

- Kotlin
- Android Jetpack (ViewModel, LiveData, Room, WorkManager)
- Hilt Dependency Injection
- Retrofit & OkHttp
- Material Design 3
- Coroutines

## Project Structure

```
app/
├── data/                 # Data layer (Models, DAOs, Repositories)
├── domain/              # Domain layer (Use cases, interfaces)
├── presentation/        # Presentation layer (ViewModels, UI)
├── features/
│   ├── phone/          # Phone call management
│   ├── call_history/   # Call history & records
│   ├── ai_feedback/    # AI feedback integration
│   ├── dashboard/      # Dashboard screens
│   └── settings/       # Settings & preferences
└── di/                 # Dependency injection modules
```

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Build and run on emulator or device

## License

MIT License
