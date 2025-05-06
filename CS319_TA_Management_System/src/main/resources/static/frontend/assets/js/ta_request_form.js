function showTARequestForm() {
    const form = document.getElementById("taRequestForm");
    form.style.display = form.style.display === "none" ? "block" : "none";
}

document.getElementById("taRequest")?.addEventListener("submit", function(e) {
    e.preventDefault();
    const selectedTA = document.getElementById("ta").value;
    const hours = document.getElementById("weeklyHours").value;

    alert(`TA ${selectedTA} requested for ${hours} hours.`);
    
    // İleride backend'e veri gönderme yapılabilir
});
