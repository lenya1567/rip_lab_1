function initMega(prefs) {
    return `
<div class="m">
    <div>
        Редактор <span class="path">MEGA</span> (аналог nano)
    </div>
    <div>
        Выйти <span class="k">Ctrl+E</span>, выйти и сохранить <span class="k">Сtrl+S</span>
    </div>
</div>
<span class="p">&nbsp;</span>
<br>
<span>‎${prefs.megaValue ?? ""}</span>`;
}

async function runCommandInMega(prefs, event) {
    if (prefs.mega && event.ctrlKey && event.key === 's') {
        let isOk;
        if (prefs.megaValue !== undefined) {
            isOk = await updateFile(getPathResult(prefs.megaFileName, prefs.path), command);
        } else {
            isOk = await createFile(prefs.megaFileName, prefs.path, command);
        }
        let output;
        if (isOk !== true) {
            output = "Возникла ошибка во время сохранения файла: " + isOk;
        } else {
            output = "Файл " + prefs.megaFileName + " успешно сохранён!";
        }
        event.target.innerHTML = prefs.prevMega + "<br>";
        event.target.innerHTML += output + "<br>";
        event.target.innerHTML += getHTMLPrefix(prefs.path);

        prefs.mega = false;
        prefs.megaValue = undefined;

        nowLinePosition = 0;
        maxLinePosition = 0;
        command = "";
        moveCursorToEnd(event.target);
        event.target.scrollTo(0, event.target.scrollHeight);
        return true;
    }

    if (prefs.mega && event.ctrlKey && event.key === 'e') {
        event.target.innerHTML = prefs.prevMega + "<br>";
        event.target.innerHTML += getHTMLPrefix(prefs.path);

        prefs.mega = false;
        prefs.megaValue = undefined;

        nowLinePosition = 0;
        maxLinePosition = 0;
        command = "";
        moveCursorToEnd(event.target);
        event.target.scrollTo(0, event.target.scrollHeight);
        return true;
    }
}