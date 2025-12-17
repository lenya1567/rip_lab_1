const root = document.getElementById("root");

root.addEventListener("mousedown", (event) => onCursorChange(event));
root.addEventListener("keydown", (event) => onKeyboardClick(event));

root.innerHTML = getHTMLPrefix("/");

moveCursorToEnd(root);