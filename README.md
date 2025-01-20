# Countdown Game Application

The Countdown Game Application is an engaging web-based game inspired by the classic word game. The app challenges players to create words using a randomly generated set of letters (vowels and consonants) within a limited time. It tracks player scores, validates words using a dictionary API, and stores the scores in an H2 database for future reference.

## Features
- **Random Letter Selection:** Players can choose vowels or consonants to form a set of nine letters.
- **Countdown Timer:** A 30-second timer challenges players to form the best possible word.
- **Word Validation:** Words are validated against a dictionary list to ensure correctness.
- **Longest Possible Word:**  The longest possible word from the set of nine letters will be displayed in an alert when the user submits their answer, along with the score.
- **Score Tracking:** Player scores are calculated based on word length and saved in a local H2 database.
- **View Scores:** Player can view their scores at the end of the game.

## Technologies Used
- **Backend:** Java, Spring Boot
- **Frontend:** HTML, CSS, JavaScript
- **Database:** H2 (in-memory database)
- **Word Validation:** Uses a word list sourced from [dwyl/english-words](https://github.com/dwyl/english-words/blob/master/words_alpha.txt) for word validation.  
- **Build Tool:** Maven

## Build and Run the Application

### Prerequisites
- Java 17 installed
- Maven installed

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/PhyuWaiLwin-Swan/countdown_game.git
   cd countdown_game
2. Build and run the application:
   ```bash
   mvn spring-boot:run
3. Access the application: Open your browser and navigate to:
   ```bash
   http://localhost:8080

### How to Play
1. Enter your name on the home screen and click Play.
2. Select vowels or consonants to fill the nine-letter slots.
3. When all slots are filled, the timer starts.
4. Enter a word using the given letters.
5. The word is submitted either manually by clicking "Submit" or automatically when the timer ends.
6. The app validates your word, calculates your score, and alerts the result together with the longest possible word.
7. Players can play four rounds of the game or end it early.
8. The score will be displayed at the end of the game.


#### **Design Patterns**

**Model-View-Presenter (MVP) Architecture**
The Countdown Game Application follows the MVP design pattern, separating the application logic into three distinct layers:
- **Model:** Handles data and business logic (e.g., database, entities, services).
- **View:** Manages the user interface (HTML, CSS, JavaScript).
- **Presenter:** Acts as a bridge between the View and Model (Spring Controllers).

This structure ensures modularity, maintainability, and scalability.

#### **Backend Layers**

1. **Database Layer**
   - **Description:** Stores data, such as player scores.
   - **Technology:** H2 Database (in-memory).
   - **Entities:** Represent the structure of the data in the database.

2. **Repository Layer**
   - **Description:** Acts as the Data Access Layer (DAL).
   - **Technology:** Spring Data JPA.
   - **Functionality:** Handles CRUD operations on the database.

3. **Service Layer**
   - **Description:** Encapsulates the business logic.
   - **Technology:** Spring Service.
   - **Functionality:** Implements reusable logic for managing scores.

4. **Controller Layer**
   - **Description:** Handles HTTP requests and connects the frontend with the backend.
   - **Technology:** Spring RestController.
   - **Functionality:** Manages operations like saving and retrieving scores.

---

#### **Frontend Layers**

**HTML**
- Represents the UI components of the application.
- Includes features like:
  - A home page with a name input and a play button.
  - A game page with slots, timer, and action buttons.

**CSS**
- Provides styling for better usability and aesthetics.
- Features include:
  - Centralized layout using a container.
  - Buttons with hover effects and disabled states.

**JavaScript**
- Handles dynamic interactions on the web page.
- Includes functionality like:
  - Fetching letters via API.
  - Validating words via the backend.
  - Timer functionality.

#### **Flow of Data**

1. **Frontend**
   - Users interact with the web pages.
   - Requests vowels/consonants, submits words.

2. **Backend**
   - Controllers handle requests.
   - Services validate words and calculate scores.
   - Repositories handle database interactions.

3. **Frontend**
   - UI updates based on backend responses.

---
