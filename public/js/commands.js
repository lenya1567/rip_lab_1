async function runCommand(prefs, command) {
    const args = command.split(" ");
    const commandName = args[0];

    if (commandName === "cd") {
        const newPath = getPathResult(args[1], prefs.path);

        if (await isFolderExist(newPath)) {
            prefs.path = newPath;
        } else {
            return "Не найдена папка: " + newPath;
        }
        return false;
    }

    if (commandName === "ls") {
        const hasAFlag = args.find((arg) => arg === "-a") !== undefined;
        const list = await getFolderList(prefs.path);

        if (hasAFlag) {
            return getNormalList(list);
        } else {
            return getList(list);
        }
    }

    if (commandName === "tree") {
        const list = await getFolderTree(prefs.path);
        return getPrintTree(prefs.path === "/" ? "/" : getLastSegment(prefs.path), list, "");
    }

    if (commandName === "mkdir") {
        if (!args[1]) {
            return "Не указано имя папки!";
        }
        return (await createFolder(args[1], prefs.path)) ? "Что-то пошло не так :(" : false;
    }

    if (commandName === "mega") {
        if (!args[1]) {
            return "Не указано имя файла!";
        }

        const { value, error } = await readFile(getPathResult(args[1], prefs.path));

        if (error !== false) {
            return error;
        }

        prefs.mega = true;
        prefs.megaFileName = args[1];

        if (value !== false) {
            prefs.megaValue = value;
        }

        return false;
    }

    return "Неизвестная команда: " + command;
}