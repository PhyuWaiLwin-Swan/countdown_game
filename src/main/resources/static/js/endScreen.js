const urlParams = new URLSearchParams(window.location.search);
const playerName = urlParams.get("playerName");


/**
 * fetch the data from the backend and update the table
 */
function loadScores() {
    // Use a playerName dynamically, or set it manually
    // Replace with actual player's name dynamically or pass as a parameter

    // Fetch the score data from the backend
    fetch(`/api/scores/endScreen?playerName=${playerName}`)
        .then(response => response.json())
        .then(scores => {
            const totalPoints = scores.reduce((sum, score) => sum + score.scoreValue, 0);

            // Generate the HTML content dynamically
            let content = `
            <h2>Scores for ${playerName}</h2>
            <p>Total Rounds: ${scores.length}</p>
            <p>Total Points: ${totalPoints}</p>
            <table class="score-table">
                <thead>
                    <tr>
                        <th>Round</th>
                        <th>Selected Alphabet</th>
                        <th>Answered Word</th>
                        <th>Score</th>
                    </tr>
                </thead>
                <tbody>
                    ${scores.map((score, index) => `
                        <tr>
                            <td>${index + 1}</td>
                            <td>${score.selectedAlphabet}</td>
                            <td>${score.answered}</td>
                            <td>${score.scoreValue}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;

            // Insert the generated content into the end screen div
            document.getElementById("end-screen-content").innerHTML = content;
        })
        .catch(error => {
            console.error('Error fetching score data:', error);
        });
}


/**
 * Redirects the user back to the home page, preserving the player name.
 */
function goBack() {
    window.location.href = `index.html?playerName=${encodeURIComponent(playerName)}`;

}

loadScores();