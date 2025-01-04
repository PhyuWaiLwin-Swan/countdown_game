// Countdown Game Logic Script

// Constants
const totalSlots = 9; // Total number of letter slots
let currentLetters = []; // Holds the selected letters
let timerInterval; // Interval for the countdown timer
let timeLeft = 30; // Time remaining for the round
let currentRound = 1; // Start from round 1
const totalRounds = 5; // Total number of rounds
let totalPoints = 0;

// DOM Elements
const slotsContainer = document.getElementById('letter-slots'); // Container for letter slots
const vowelButton = document.getElementById('vowel-btn'); // Button to add vowels
const consonantButton = document.getElementById('consonant-btn'); // Button to add consonants
const timerDisplay = document.getElementById('time-left'); // Display for the timer
const wordInput = document.getElementById('word-input'); // Input field for the user's word
const submitButton = document.getElementById('submit-btn'); // Submit button


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

/**
 * Start the game for each round reset all the slot and the button
 */
function startGame() {
    if (currentRound < totalRounds) {
        clearInterval(timerInterval);
        timeLeft = 30;
        timerDisplay.textContent = timeLeft;
        vowelButton.disabled = false;
        consonantButton.disabled = false;
        // Initially disable the Play Again button
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
        endGame()
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
            clearInterval(timerInterval);
            submitWord();
        }
    }, 1000); // Update the timer every second
}

// Redirect to home if playerName is not found
if (!playerName) {
    alert("Player name is missing. Redirecting to the home page.");
    window.location.href = "index.html";
}

// Event listener for vowel button
vowelButton.addEventListener('click', () => {
    fetchLetter('vowels');
});

// Event listener for consonant button
consonantButton.addEventListener('click', () => {
    fetchLetter('consonants');
});

wordInput.addEventListener("input", function (e) {
    // Remove any non-alphabetic characters and spaces
    this.value = this.value.replace(/[^a-zA-Z]/g, "");
});


/**
 * Handles word submission for validation.
 */
submitButton.addEventListener('click', () => {
    submitButton.disabled = true;
    const word = wordInput.value.trim(); // Get user input

    if (word === '') {
        result.textContent = 'Please enter a word!';
        return;
    } else {
        submitWord();
    }


});

/**
 * Submit the word when the timer is off or when user click the submit button
 */
function submitWord(){
    const word = wordInput.value.trim(); // Get user input
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
                startGame();

            } else {
                alert( `Invalid word!`);
                startGame();
            }
        })
        .catch(error => {
            console.error('Error validating word:', error);
            alert('An error occurred while validating the word. Please try again.');}
        );
}

/**
 * When the user click the end game button or the player have play four round of game
 */
function endGame() {
    window.location.href = `endScreen.html?playerName=${encodeURIComponent(playerName)}`
}

/**
 * pop a confirmation alert if the user want to terminate the game
 */
function handleEndGame() {
    let userConfirmed = confirm("Are you sure you want to end the game?");
    if (userConfirmed) {
        endGame();
    } else {
    }
}
window.onload = startGame;