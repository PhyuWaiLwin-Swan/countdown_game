// Countdown Game Logic Script

// Constants
const totalSlots = 9; // Total number of letter slots
let currentLetters = []; // Holds the selected letters
let timerInterval; // Interval for the countdown timer
let timeLeft = 30; // Time remaining for the round
let currentRound = 1; // Start from round 1
const totalRounds = 4; // Total number of rounds
let totalPoints = 0;

// DOM Elements
const slotsContainer = document.getElementById('letter-slots'); // Container for letter slots
const vowelButton = document.getElementById('vowel-btn'); // Button to add vowels
const consonantButton = document.getElementById('consonant-btn'); // Button to add consonants
const timerDisplay = document.getElementById('time-left'); // Display for the timer
const wordInput = document.getElementById('word-input'); // Input field for the user's word
const submitButton = document.getElementById('submit-btn'); // Submit button
const playAgainBtn = document.getElementById('play-again-btn');// Play Again button
const result = document.getElementById('result');
const urlParams = new URLSearchParams(window.location.search);
const playerName = urlParams.get("playerName");
const greetingElement = document.getElementById("greeting");
const roundNumber = document.getElementById("round-number");
const points = document.getElementById('points');

// Update the greeting with the player's name
if (playerName) {
    greetingElement.textContent = `Hello, ${playerName}!`;
}
// Initialize 9 empty slots in the UI

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
function resetGame() {
    if (currentRound < totalRounds) {
        timeLeft = 30;
        vowelButton.disabled = false;
        consonantButton.disabled = false;
        playAgainBtn.disabled = true; // Initially disable the Play Again button
        submitButton.disabled = true;
        wordInput.disabled = true;
        slotsContainer.innerHTML = '';
        for (let i = 0; i < totalSlots; i++) {
            const slot = document.createElement('div');
            slot.classList.add('slot');
            slotsContainer.appendChild(slot);
        }
        wordInput.value = '';
        currentLetters = [];

        roundNumber.textContent = `Round ${currentRound}`;
        points.textContent = totalPoints;
    } else {
        window.location.href = "endGameScreen"
    }

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
        vowelButton.disabled = true;
        consonantButton.disabled = true;
        startTimer(); // Start the countdown timer
        submitButton.disabled = false;
        wordInput.disabled = false;
        playAgainBtn.disabled = false;

    }
}

function startGame() {
    resetGame();

}

// Start a new round
function startRound() {
    if (currentRound > totalRounds) {
        endGame();
        return;
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
            finishRound();
        }
    }, 1000); // Update the timer every second
}

// Retrieve player name from the URL parameters


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
submitButton.addEventListener('click', () => {
    const word = wordInput.value.trim(); // Get user input

    if (word === '') {
        result.textContent = 'Please enter a word!';
        return;
    }

    // Validate the word via the backend API
    fetch(`/api/game/validate?playerName=${encodeURIComponent(playerName)}&word=${encodeURIComponent(word)}&currentLetters=${encodeURIComponent(currentLetters.join(''))}`, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(data => {
            currentRound+=1;
            // Display the result of the validation
            if (data.isValid) {
                totalPoints += data.score;
                alert( `Valid word! Score: ${data.score}`);
                resetGame();

            } else {
                alert( `Invalid word!`);
                resetGame();
            }
        })
        .catch(error => {
            console.error('Error validating word:', error);
            alert('An error occurred while validating the word. Please try again.');}
        );
});
function finishRound() {
    clearInterval(timerInterval);
    currentRound+=1;
    resetGame();
}

function endGame() {
    document.getElementById('greeting').textContent = 'Game Over!';
    result.textContent = `Your total score is ${totalPoints} points.`;
    document.getElementById('play-again-btn').disabled = true;
    document.getElementById('vowel-btn').disabled = true;
    document.getElementById('consonant-btn').disabled = true;
    document.getElementById('word-input').disabled = true;
    document.getElementById('submit-btn').disabled = true;
}
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
window.onload = startGame;