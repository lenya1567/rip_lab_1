function onCursorChange(event) {
    event.preventDefault();
}

function moveCursorToEnd(root) {
    const range = document.createRange();
    const selection = window.getSelection();
    range.selectNodeContents(root);
    range.collapse(false);
    selection.removeAllRanges();
    selection.addRange(range);
}

let nowLinePosition = 0;
let maxLinePosition = 0;
let command = "";

let prefs = {
    path: "/",
    mega: false,
    megaFileName: "",
    megaValue: undefined,
    prevMega: "",
};

async function onKeyboardClick(event) {
    if (await runCommandInMega(prefs, event)) {
    } else if (event.metaKey || event.ctrlKey) {
        if (!"cv".includes(event.key)) {
            event.preventDefault();
        }
    } else if (event.key.length === 1) {
        command = command.slice(0, nowLinePosition) + event.key + command.slice(nowLinePosition);
        maxLinePosition += 1;
        nowLinePosition += 1;
    } else if (event.key === "ArrowLeft" && nowLinePosition > 0) {
        nowLinePosition -= 1;
    } else if (event.key === "ArrowRight" && nowLinePosition < maxLinePosition) {
        nowLinePosition += 1;
    } else if (event.key === "Backspace" && nowLinePosition > 0) {
        command = command.slice(0, nowLinePosition - 1) + command.slice(nowLinePosition);
        nowLinePosition -= 1;
        maxLinePosition -= 1;
    } else if (event.key === "Enter") {
        if (prefs.mega) {
            nowLinePosition += 1;
            maxLinePosition += 1;
            command += "\n";
            return;
        }

        event.preventDefault();

        let output;
        try {
            output = await runCommand(prefs, command);
        } catch (err) {
            output = "Ошибка!" + err;
        }
        if (prefs.mega) {
            prefs.prevMega = event.target.innerHTML;
            nowLinePosition = (prefs.megaValue ?? "").length;
            maxLinePosition = (prefs.megaValue ?? "").length;
            command = prefs.megaValue ?? "";
            event.target.innerHTML = initMega(prefs);
            moveCursorToEnd(event.target);
            return;
        }

        event.target.innerHTML += "<br>"
        if (output) {
            event.target.innerHTML += output + "<br>";
        }

        event.target.innerHTML += getHTMLPrefix(prefs.path);

        nowLinePosition = 0;
        maxLinePosition = 0;
        command = "";

        moveCursorToEnd(event.target);
        event.target.scrollTo(0, event.target.scrollHeight);
    } else {
        event.preventDefault();
    }
}