
// check and parse url parameters
function setLoginResultMessage (){
    const urlParams = new URLSearchParams(window.location.search);
    const loginResult = urlParams.get("login");

    if (loginResult == "usernamefail"){
        document.getElementById("message").textContent = "User doesn\'t exist";
    } else if (loginResult == "passwordfail"){
        document.getElementById("message").textContent = "Password incorrect!";
    } else if (loginResult == "created"){
        document.getElementById("message").textContent = "Login with new account";
        document.getElementById("message").style.color = "#0000ff";
    }
}

// attach the login result checker to its appropriate function
document.addEventListener('DOMContentLoaded', setLoginResultMessage);