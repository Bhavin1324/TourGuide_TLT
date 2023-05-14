let resendButton = document.getElementById("btn-resend");
function startTimer(duration, display) {
    var timer = duration, minutes, seconds;

    let interval = setInterval(function () {
        minutes = parseInt(timer / 60);
        seconds = parseInt(timer % 60);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ':' + seconds;
        if (--timer < 0) {
            resendButton.style.display = "inline-block";
            clearInterval(interval);
        }
    }, 1000);
}
document.getElementById('ask-otp').addEventListener('click', () => {
    resendButton.style.display = "none";
    var duration = 10; // Timer duration in seconds
    var display = document.getElementById('otp-timer');
    startTimer(duration, display);
});

