const uploader = document.getElementById("uploader");
const imageData = document.getElementById("imageData");
const textArea = document.getElementById("textArea");
const sender = document.getElementById("sender");

let sharedBase64;

const convertBase64 = (file) => {
    return new Promise((resolve, reject) => {
        const fileReader = new FileReader();
        fileReader.readAsDataURL(file);

        fileReader.onload = () => {
            resolve(fileReader.result);
        };

        fileReader.onerror = (error) => {
            reject(error);
        }
    })
}

const uploadImage = async (event) => {
    const file = event.target.files[0];
    const base64 = await convertBase64(file);
    imageData.src = base64;
    textArea.innerText = base64;
    sharedBase64 = base64;
    if (imageData) {
        console.log("image size: (", imageData.naturalWidth, ", ", imageData.naturalHeight, ")");
    }
};

const sendImage = async () => {
    // const endpointUrl = "http://localhost:5000/api/content/image"
    // const endpointUrl = "http://localhost:8081";
    const urlSplited = window.location.origin.split(":")
    const apiEndpoint = urlSplited[0] + ":" + urlSplited[1] + ":8081"

    const requestData = { 
        image: sharedBase64, 
        width: imageData.naturalWidth,
        height: imageData.naturalHeight,
        channel: 3,
    };

    try {
        const response = await fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })

        const json = await response.json();
        textArea.innerText = json.result;

        if (response.ok) {
            // Request was successful, handle the response if needed
            console.log('Image uploaded successfully.');
        } else {
            // Request failed, handle the error
            console.error('Image upload failed.');
        }
    } catch (err) {
        console.error("error occure", err);
    }
}


uploader.addEventListener("change", (e) => {
    uploadImage(e);
});

sender.addEventListener("click", () => {
    console.log("image");
    console.log(sharedBase64);
    sendImage();
});

