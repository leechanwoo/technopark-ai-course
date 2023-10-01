const uploader = document.getElementById("uploader");
const imageData = document.getElementById("imageData");
const textArea = document.getElementById("textArea");
const grpcTest = document.getElementById("grpcTest");

import * as pb from './proto/grpc_test_pb_bundle.js';
import * as grpc from './proto/grpc_test_grpc_web_pb_bundle.js';


const { HelloServiceClient } = grpc;
console.log(grpc);

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
};


uploader.addEventListener("change", (e) => {
    uploadImage(e);
});


grpcTest.addEventListener("click", () => {
    console.log("test")
    var helloService = new HelloServiceClient('http://172.20.0.3:50051');

    var request = new HelloRequest();
    request.setName('Lee chanwoo');

    helloService.sayHello(request, {}, function (err, response) {
        console.log(response)
    });
});

