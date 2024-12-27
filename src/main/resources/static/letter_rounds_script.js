
    const totalSlots = 9;
    let currentLetters = [];
    let timerInterval;
    let timeLeft = 30;

    const slotsContainer = document.getElementById('letter-slots');
    const vowelButton = document.getElementById('vowel-btn');
    const consonantButton = document.getElementById('consonant-btn');
    const timerDisplay = document.getElementById('time-left');
    const wordInput = document.getElementById('word-input');
    const submitButton = document.getElementById('submit-btn');
    // Initialize 9 empty slots
    for (let i = 0; i < totalSlots; i++) {
    const slot = document.createElement('div');
    slot.classList.add('slot');
    slotsContainer.appendChild(slot);
}

    // Fetch letter from the backend
    function fetchLetter(type) {
    fetch(`/api/game/${type}?count=1`) // Fetch one letter at a time
        .then(response => response.json())
        .then(letter => {
            addLetter(letter[0]); // Add the letter to the slots
        })
        .catch(error => console.error('Error fetching letter:', error));
}

    // Add letter to the next empty slot
    function addLetter(letter) {
    if (currentLetters.length >= totalSlots) return;

    currentLetters.push(letter);

    // Update the UI slot
    const slot = slotsContainer.children[currentLetters.length - 1];
    slot.textContent = letter;

    // Disable buttons if all slots are filled
    if (currentLetters.length === totalSlots) {
    startTimer();
    vowelButton.classList.add('disabled');
    consonantButton.classList.add('disabled');
    vowelButton.disabled = true;
    consonantButton.disabled = true;
}
}

    // Start countdown timer
    function startTimer() {
    timeLeft = 30;
    timerDisplay.textContent = timeLeft;

    clearInterval(timerInterval);

    timerInterval = setInterval(() => {
    timeLeft--;
    timerDisplay.textContent = timeLeft;

    if (timeLeft <= 0) {
    wordInput.disabled = true;
    submitButton.disabled = true;
    clearInterval(timerInterval);
    alert('Time is up! Submit your word.');
}
}, 1000);
}

    // Event Listeners for buttons
    vowelButton.addEventListener('click', () => {
    fetchLetter('vowels');
});

    consonantButton.addEventListener('click', () => {
    fetchLetter('consonants');
});
    // Submit word for validation
    document.getElementById('submit-btn').addEventListener('click', () => {
    const word = document.getElementById('word-input').value.trim();
    if (word === '') {
    document.getElementById('result').textContent = 'Please enter a word!';
    return;
}

    fetch(`/api/game/validate?word=${encodeURIComponent(word)}&currentLetters=${encodeURIComponent(currentLetters.join(''))}`, {
    method: 'POST'
})
    .then(response => response.json())
    .then(data => {
    if (data.isValid) {
    document.getElementById('result').textContent = `Valid word! Score: ${data.score}`;
} else {
    document.getElementById('result').textContent = 'Invalid word!';
}
})
    .catch(error => console.error('Error validating word:', error));
});

