function sendText() {
    let textValue = document.getElementById("search").value;
    let url = "?book_name=" + encodeURIComponent(textValue);

    window.location.href = url;
    showSearchedBooks();
}

window.onload = () => {
    showSearchedBooks();
}

function showSearchedBooks() {
    let searched = document.getElementById("searchedBooks");
    searched.style.display = "block";
}