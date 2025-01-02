// Countdown Game Logic Script

// Constants
const totalSlots = 9; // Total number of letter slots
let currentLetters = []; // Holds the selected letters
let timerInterval; // Interval for the countdown timer
let timeLeft = 30; // Time remaining for the round

// DOM Elements
const slotsContainer = document.getElementById('letter-slots'); // Container for letter slots
const vowelButton = document.getElementById('vowel-btn'); // Button to add vowels
const consonantButton = document.getElementById('consonant-btn'); // Button to add consonants
const timerDisplay = document.getElementById('time-left'); // Display for the timer
const wordInput = document.getElementById('word-input'); // Input field for the user's word
const submitButton = document.getElementById('submit-btn'); // Submit button
const playAgainBtn = document.getElementById('play-again-btn'); // Play Again button
playAgainBtn.disabled = true; // Initially disable the Play Again button
submitButton.disabled = true;
wordInput.disabled = true;
// Initialize 9 empty slots in the UI
for (let i = 0; i < totalSlots; i++) {
    const slot = document.createElement('div');
    slot.classList.add('slot');
    slotsContainer.appendChild(slot);
}

/**
 * Fetches a letter (vowel or consonant) from the backend API.
 * @param {string} type - The type of letter to fetch ('vowels' or 'consonants').
 */
function fetchLetter(type) {
    fetch(`/api/game/${type}?count=1`) // API call to fetch one letter
        .then(response => response.json())
        .then(letter => {
            addLetter(letter[0]); // Add the fetched letter to the UI
        })
        .catch(error => console.error('Error fetching letter:', error));
}

/**
 * Adds a letter to the next available slot in the UI.
 * @param {string} letter - The letter to add.
 */
function addLetter(letter) {
    if (currentLetters.length >= totalSlots) return; // Prevent adding more than 9 letters

    currentLetters.push(letter); // Add the letter to the currentLetters array

    // Update the corresponding slot in the UI
    const slot = slotsContainer.children[currentLetters.length - 1];
    slot.textContent = letter;

    // Disable letter buttons and enable Play Again button when all slots are filled
    if (currentLetters.length === totalSlots) {
        startTimer(); // Start the countdown timer

        vowelButton.disabled = true;
        consonantButton.disabled = true;
        submitButton.disabled = false;
        wordInput.disabled = false;
        playAgainBtn.disabled = false;
        vowelButton.classList.add('disabled');
        consonantButton.classList.add('disabled');
    }
}

/**
 * Starts the countdown timer for the round.
 */
function startTimer() {
    timeLeft = 30; // Reset the timer
    timerDisplay.textContent = timeLeft; // Display initial time

    clearInterval(timerInterval); // Clear any existing timer

    timerInterval = setInterval(() => {
        timeLeft--;
        timerDisplay.textContent = timeLeft;

        if (timeLeft <= 0) {
            // Disable input and submit button when time runs out
            wordInput.disabled = true;
            submitButton.disabled = true;
            clearInterval(timerInterval); // Stop the timer
            alert('Time is up! Submit your word.');
        }
    }, 1000); // Update the timer every second
}

// Retrieve player name from the URL parameters
const urlParams = new URLSearchParams(window.location.search);
const playerName = urlParams.get("playerName");

if (!playerName) {
    alert("Player name is missing. Redirecting to the home page.");
    window.location.href = "index.html"; // Redirect to home if playerName is not found
}

// Event listener for vowel button
vowelButton.addEventListener('click', () => {
    fetchLetter('vowels');
});

// Event listener for consonant button
consonantButton.addEventListener('click', () => {
    fetchLetter('consonants');
});

/**
 * Handles word submission for validation.
 */
document.getElementById('submit-btn').addEventListener('click', () => {
    const word = wordInput.value.trim(); // Get user input

    if (word === '') {
        document.getElementById('result').textContent = 'Please enter a word!';
        return;
    }

    // Validate the word via the backend API
    fetch(`/api/game/validate?playerName=${encodeURIComponent(playerName)}&word=${encodeURIComponent(word)}&currentLetters=${encodeURIComponent(currentLetters.join(''))}`, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(data => {
            // Display the result of the validation
            if (data.isValid) {
                document.getElementById('result').textContent = `Valid word! Score: ${data.score}`;
            } else {
                document.getElementById('result').textContent = 'Invalid word!';
            }
        })
        .catch(error => {
            console.error('Error validating word:', error);
            alert('An error occurred while validating the word. Please try again.');}
        );
});

/**
 * Reloads the current page to restart the game.
 */
function playAgain() {
    window.location.reload();
}

/**
 * Redirects the user back to the home page, preserving the player name.
 */
function goBack() {
    window.location.href = `index.html?playerName=${encodeURIComponent(playerName)}`;
}
