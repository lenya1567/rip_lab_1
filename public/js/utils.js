function getHTMLPrefix(path) {
    return `[<span class="path">${path}</span>]:&nbsp`
}

function getFullPath(path, name) {
    return path === "/" ? (path + name) : (path + "/" + name);
}

function getNormalList(data) {
    let output = "";
    data.forEach((e, index) => {
        const lineBreak = index !== data.length - 1 ? "<br>" : "";
        const fullPath = getFullPath(e.path, e.name);
        output += `- [<span class="path">${e.name}</span>]: Создано ` + new Date(e.created).toLocaleString() + lineBreak;
    });
    return output;
}

function getList(data) {
    return data.map((e) => `<span class="b">${e.name}</span>`).join(" ");
}

function getPathResult(newPath, originPath) {
    const splitPath = newPath.split("/");
    let splitOriginPath = originPath.split("/");

    splitPath.forEach((pathElement, index) => {
        if (pathElement === "") {
            splitOriginPath = [];
        } else if (pathElement === "..") {
            splitOriginPath.pop();
        } else if (pathElement !== ".") {
            splitOriginPath.push(pathElement);
        }
    });

    return "/" + splitOriginPath.filter((e) => e).join("/");
}

function getLastSegment(path) {
    return path.split("/").at(-1);
}

function getPrintTree(treeName, treeElement) {
    return `<span class="t">${printTree("┏ " + treeName, treeElement, "")}</span>`
}

function printTree(treeName, treeElement, prefix) {
    let content = treeName + "<br>";
    Object.keys(treeElement).forEach((element, index, array) => {
        content += prefix + "┃<br>";
        if (typeof treeElement[element] === "string") {
            content += prefix + (index !== array.length - 1 ? "┣━━━ " : "┗━━━ ") + element + "<br>";
        } else {
            content += prefix + (index !== array.length - 1 ? "┣━━┳ " : "┗━━┳ ") + printTree(
                element,
                treeElement[element],
                prefix + (index === array.length - 1 ? "&nbsp&nbsp&nbsp" : "┃&nbsp&nbsp"),
            );
        }
    });
    return content;
}