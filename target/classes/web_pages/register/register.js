
// check and parse url parameters
function setRegisterResultMessage (){
    const urlParams = new URLSearchParams(window.location.search);
    const loginResult = urlParams.get("register");

    if (loginResult == "alreadypresent"){
        document.getElementById("message").textContent = "User already exists!";
    } else if (loginResult == "failed"){
        document.getElementById("message").textContent = "User creation failed";
    } else if (loginResult == "lengthlow"){
        document.getElementById("message").textContent = "Username or password too short!";
    }
}

// attach the login result checker to its appropriate function
document.addEventListener("DOMContentLoaded", setRegisterResultMessage);