

function handleSubmit(event) {
            event.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const error = document.getElementById('error');
            localStorage.setItem("userName", username)

            /*
            // Simple client-side validation (for demo purposes)
            if (username === "admin" && password === "password123") {
                alert("Login successful!");
                error.style.display = "none";
                 // Redirect or perform action here (e.g., window.location.href = "/dashboard");
            } else {
                error.style.display = "block";
            }
            */

        }