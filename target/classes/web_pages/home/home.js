
let oldestMessageId = 2147483640;
let newestMessageId = -1;

let isLoading = false;

function generateMessageHTML(username, message, timestamp){
    return `
            <hr>
            <div class = "messageUsername">@${username}</div>
            <div class = "messageText">${message}</div>
            <div class = "messageTimestamp">D/T: ${timestamp}</div>
            <hr>
            `;
}

function displayMessage(msg, prepend){
    let messagesDiv = document.getElementById("messages");
    let messageBlock = document.createElement("div");
    messageBlock.className = "messageBlock";
    messageBlock.innerHTML = generateMessageHTML(msg.username, msg.message, msg.timestamp);
    
    if (prepend) {
        messagesDiv.prepend(messageBlock);
    } else {
        messagesDiv.append(messageBlock);
    }
}

async function loadOlderMessages(){
    if (isLoading) return;

    try {
        isLoading = true;
        let response = await fetch(`/oldchat?oldestMessageId=${oldestMessageId}`);
        if (response.status != 200) throw "no more content"

        let newMessages = await response.json();

        let scrollTopBefore = window.scrollY;
        let prevDocHeight = document.body.scrollHeight;

        newMessages.forEach(msg => {
            displayMessage(msg, true);

            oldestMessageId = Math.min(oldestMessageId, msg.id);
            newestMessageId = Math.max(newestMessageId, msg.id);
        });

        window.scrollTo(0, scrollTopBefore + document.body.scrollHeight - prevDocHeight);

    } catch (error){
        console.log(error);
    }

    isLoading = false;
}

async function loadNewerMessages(){
    if (isLoading) return;

    try {
        isLoading = true;
        let response = await fetch(`/newchat?newestMessageId=${newestMessageId}`);
        if (response.status != 200) throw "no content yet"

        let newMessages = await response.json();

        newMessages.forEach(msg => {
            displayMessage(msg, false);

            oldestMessageId = Math.min(oldestMessageId, msg.id);
            newestMessageId = Math.max(newestMessageId, msg.id);
        });

        window.scrollTo(0, document.body.scrollHeight);

    } catch (error){
        console.log(error);
    }

    isLoading = false;
}

async function sendMessage(){
    try {
        let chatbox = document.getElementById("chatbox");

        await fetch("/sendmessage", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `message=${chatbox.value}`
        });

        chatbox.value = "";
        loadNewerMessages();
    } catch (error) {
        console.error("Error sending message:", error);
    }
}

async function onWebpageLoad(){
    await loadOlderMessages();

    setTimeout(function () {
        window.scrollTo(0, 3000000);
    },2);

    document.body.onscroll = async function(){
        if (window.scrollY < 200){
            await loadOlderMessages();
        }
    };

    var chatbox = document.getElementById("chatbox");

    chatbox.addEventListener("keydown", function (e) {
        if (e.code === "Enter") {
            sendMessage();
        }
    });
}

document.addEventListener("DOMContentLoaded", onWebpageLoad);

// fetch new messages periodically
setInterval(loadNewerMessages, 1000);