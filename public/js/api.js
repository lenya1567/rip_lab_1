const PATH = "http://localhost:7777/api";

async function isFolderExist(path) {
    try {
        const res = await fetch(PATH + "/dir/exist?path=" + encodeURIComponent(path));
        return (await res.text()) === "true";
    } catch {
        return false;
    }
}

async function getFolderList(path) {
    try {
        const res = await fetch(PATH + "/dir/ls?path=" + encodeURIComponent(path));
        const data = await res.json();
        return data;
    } catch (err) {
        return false;
    }
}

async function getFolderTree(path) {
    try {
        const res = await fetch(PATH + "/dir/tree?path=" + encodeURIComponent(path));
        const data = await res.json();
        return data;
    } catch (err) {
        return false;
    }
}

async function createFolder(name, path) {
    try {
        await fetch(PATH + "/dir/create?name=" + encodeURIComponent(name) + "&path=" + encodeURIComponent(path), { method: "POST" });
        return data;
    } catch (err) {
        return false;
    }
}

async function createFile(name, path, value) {
    try {
        const headers = new Headers();
        headers.set("Content-Type", "application/json");
        const response = await fetch(PATH + "/file/create", {
            method: "POST",
            headers,
            body: JSON.stringify({
                name,
                path,
                value
            })
        });

        return response.ok ? true : await response.text();
    } catch (err) {
        return false;
    }
}

async function updateFile(path, value) {
    try {
        const headers = new Headers();
        headers.set("Content-Type", "application/json");
        const response = await fetch(PATH + "/file/update", {
            method: "PUT",
            headers,
            body: JSON.stringify({
                path,
                value
            })
        });
        return response.ok ? true : await response.text();
    } catch (err) {
        console.log(err);
        return false;
    }
}

async function readFile(path) {
    try {
        const response = await fetch(PATH + "/file/read?path=" + encodeURIComponent(path));
        return {
            value: !response.ok ? false : await response.text(),
            error: (response.ok || response.status === 404) ? false : await response.text(),
        }
    } catch (err) {
        return false;
    }
}