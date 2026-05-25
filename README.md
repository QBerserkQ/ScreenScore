# 🎬 ScreenScore

> A desktop application for tracking, rating, and reviewing movies, series, and anime — built with JavaFX and powered by a local SQLite database and Stream API.

---

## Table of Contents

- [About](#about)
- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Build from Source](#build-from-source)
- [Project Structure](#project-structure)

---

## About

ScreenScore is a personal media review tracker for desktop. It allows users to keep a local collection of their movie, series, and anime reviews — complete with ratings, descriptions, and cover images. All data is stored locally using SQLite, so no internet connection or account is required.

---

## Features

- ✅ Create, edit, and delete reviews
- ✅ Attach a cover image via Stream API
- ✅ Rate each title from 0 to 10
- ✅ Filter reviews by type — Movies, Series, or Anime
- ✅ Search reviews by title in real time
- ✅ Sort alphabetically (A→Z / Z→A) or by rating (high→low / low→high)
- ✅ Live statistics panel with:
  - Pie chart — reviews by type
  - Bar chart — rating distribution
- ✅ Dark theme UI
---

## Screenshots

<img width="1917" height="1016" alt="image" src="https://github.com/user-attachments/assets/9eebb6e8-7c8c-41d9-ac2e-b21c84d64964" />

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| JavaFX 21 | UI framework |
| SQLite + sqlite-jdbc | Local database |
| Maven | Build & dependency management |
| jpackage | Windows MSI packaging |

---

## Build from Source

### Requirements

- Java 17 or later
- Maven 3.8 or later

### Steps

```bash
# Clone the repository
git clone https://github.com/your-username/ScreenScore.git
cd ScreenScore

# Build the project
mvn clean package

# Run the application
mvn javafx:run
```

## Project Structure

```
src/
├── main/
│   ├── java/org/example/screenscore/
│   │   ├── controllers/     # JavaFX controllers
│   │   ├── dao/             # Database access objects
│   │   ├── models/          # Data models
│   │   ├── services/        # Business logic
│   │   └── Launcher.java
│   └── resources/
│       └── org/example/screenscore/
│           ├── views/       # FXML layout files
│           ├── styles.css   # App stylesheet
│           └── ScreenScoreIcon.ico
```
