

/**
 * Navigates the user to the game page (letter_rounds.html) while passing the player's name as a query parameter.
 * If the player's name is not provided, an alert is displayed.
 */
function navigateToGame() {
    const playerName = document.getElementById("player-name").value.trim(); // Get the player's name
    if (playerName) {
        // Redirect to the game page with player name in query parameters
        window.location.href = `letter_rounds.html?playerName=${encodeURIComponent(playerName)}`;
    } else {
        alert("Please enter your name before proceeding!");
    }
}

/**
 * Populates the player name in the input field from the URL query parameters.
 * If a `playerName` parameter exists in the URL, it sets the value in the input field and validates the name.
 */
function populatePlayerName() {
    const urlParams = new URLSearchParams(window.location.search); // Parse query parameters
    const playerName = urlParams.get("playerName"); // Retrieve the `playerName` parameter

    if (playerName) {
        const playerNameInput = document.getElementById("player-name");
        playerNameInput.value = decodeURIComponent(playerName); // Decode and set the value in the input field
        validateName(); // Enable the play button if the name is valid
    }
}

/**
 * Validates the player's name input.
 * Enables or disables the "Play" button based on input validity.
 */
function validateName() {
    const nameInput = document.getElementById('player-name');
    const playButton = document.getElementById('play-button');
    const nameRegex = /^[A-Za-z\s]+$/; // Only allows alphabets and spaces

    // Enable the Play button if the input is valid
    if (nameRegex.test(nameInput.value) && nameInput.value.trim().length > 0) {
        playButton.disabled = false;
    } else {
        playButton.disabled = true;
    }
}
function resetAll(){
    fetch('/api/scores/resetAll', {
        method: 'POST',
    })
        .then(response => {
            if (response.ok) {
                console.log('All game data deleted successfully.');
                // Redirect to home page after deletion
            } else {
                console.error('Failed to delete all game data.');
            }
        })
        .catch(error => {
            console.error('Error deleting all game data:', error);
        });
}
populatePlayerName();
resetAll();