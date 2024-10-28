# Rick and Morty Kotlin Multiplatform Project

This project is a Kotlin Multiplatform application that uses the [Rick and Morty GraphQL API](https://rickandmortyapi.com/graphql) to showcase characters, locations, and episodes from the *Rick and Morty* universe. It supports both **dark** and **light** themes and includes search and filtering functionalities across multiple screens. The project is designed with **Jetpack Compose** for shared UI components, making it cross-platform compatible.

## Table of Contents

- [Features](#features)
- [Screens](#screens)
- [Libraries and Dependencies](#libraries-and-dependencies)
  - [Routing](#routing)
  - [Dependency Injection (DI)](#dependency-injection-di)
  - [Networking](#networking)
  - [State Management](#state-management)
- [Dark and Light Theme Support](#dark-and-light-theme-support)
- [Screenshots](#screenshots)

## Features

- Cross-platform UI with **Jetpack Compose**.
- Integration with the **Rick and Morty GraphQL API** for live data.
- Search and filter options for characters, locations, and episodes.
- **Dark and light theme** compatibility.
- Detailed screens for each entity (character, location, episode).

## Screens

### 1. **Characters Page**
   - Displays a list of characters with search and filter capabilities.
   - Allows navigation to detailed views for each character.
   
### 2. **Locations Page**
   - Lists all locations with search and filter options.
   - Detailed information available on individual location screens.

### 3. **Episodes Page**
   - Shows all episodes with sorting and filtering.
   - Detailed episode screen for in-depth information.

### 4. **Character Details**
   - Provides additional information about a selected character, including origin, location, and episode appearances.

### 5. **Location Details**
   - Detailed insights into a specific location, including residents and episode appearances.

### 6. **Episode Details**
   - Showcases episode details, including characters that appeared in the episode.

## Libraries and Dependencies

### Routing
- **Navigation:** Handles screen navigation and flow between screens.

### Dependency Injection (DI)
- **Koin** (or another DI framework of your choice): For dependency management and injection, ensuring a modular and testable architecture.

### Networking
- **Apollo GraphQL**: For making API calls to the Rick and Morty GraphQL.

### State Management
- **StateFlow**: Utilized for reactive state management across all platforms.

## Dark and Light Theme Support

This application supports both **dark** and **light** themes, dynamically adjusting the UI based on the user’s system settings or manual preference. Each screen is designed to be visually cohesive in both themes.

## Screenshots

### Light Theme

- #### Characters Page
  ![Characters Page - Light](#)

- #### Locations Page
  ![Locations Page - Light](#)

- #### Episodes Page
  ![Episodes Page - Light](#)

- #### Character Details Page
  ![Character Details - Light](#)

### Dark Theme

- #### Characters Page
  ![Characters Page - Dark](#)

- #### Locations Page
  ![Locations Page - Dark](#)

- #### Episodes Page
  ![Episodes Page - Dark](#)

- #### Character Details Page
  ![Character Details - Dark](#)

---

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/rick-and-morty-kotlin-multiplatform.git
