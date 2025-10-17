# Money Manager 💰

[![Kotlin](https://img.shields.io/badge/Kotlin-Android-blue?style=flat&logo=kotlin)]()  
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()

> Android-приложение для управления личными финансами: учёт доходов и расходов, работа с категориями, хранение данных в Firebase и авторизация через Google.  
> Построено на **MVVM + Clean Architecture** ✨

---

## 🔍 О проекте

Money Manager позволяет:

- вести учёт доходов и расходов,  
- авторизоваться через Google,  
- хранить и обновлять данные в **Firebase Realtime Database**,  
- использовать категории для операций,  
- просматривать сводки и аналитику.  

---

## 🎯 Основные возможности

- 📊 Учёт доходов и расходов  
- 🗂 Категоризация операций  
- 🔑 Авторизация через Google  
- ☁ Хранение и обновление данных в Firebase  
- ⏱ Асинхронная работа с данными (Coroutines)
- 🌐💱 Мультивалютность 

---

## 🛠 Технологии

Проект построен на следующих технологиях:

| Компонент | Технология / инструмент |
|-----------|--------------------------|
| Язык | Kotlin (Android) |
| Архитектура | MVVM + Clean Architecture |
| DI | Dagger Hilt |
| БД (локальная) | Room |
| Асинхронность | Kotlin Coroutines |
| Облачные сервисы | Firebase (Auth, Realtime Database) |
| UI | Android Jetpack (ViewModel, LiveData / StateFlow и др.) |

---

## 🚀 Установка и запуск

1. Склонируйте репозиторий:  
   ```bash
   git clone https://github.com/boomhaa/money-manager.git
   ```
2. Откройте проект в Android Studio.
3. Дождитесь синхронизации Gradle.
4. Запустите проект на эмуляторе или реальном устройстве (API 24+).

## 🏗 Архитектура / структура проекта
Код организован по слоям внутри модуля app:

app/

├── data/          ← доступ к данным (Room, Firebase, репозитории)

├── domain/        ← бизнес-логика, use-cases

├── presentation/  ← UI слой, ViewModels

├── util/          ← утилиты и вспомогательные классы

├── di/            ← модули Dagger Hilt

└── build.gradle.kts

## 🤝 Участие и вклад

Буду рад вашим пулл-реквестам и предложениям!

1. Форкните репозиторий
2. Создайте ветку feature/ваша-идея
3. Внесите изменения
4. Откройте Pull Request

Спасибо за интерес к проекту! 🚀
