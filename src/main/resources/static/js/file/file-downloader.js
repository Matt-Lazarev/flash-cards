export function downloadFile(response) {
    const filename = response.headers.get('Content-Disposition').split("=")[1];
    response.blob()
        .then(blob => {
            const href = URL.createObjectURL(blob);

            const a = Object.assign(document.createElement('a'),
                {
                    href,
                    style: 'display:none',
                    download: filename
                })
            document.body.appendChild(a);
            a.click();
            URL.revokeObjectURL(href);
            a.remove();
        })
}