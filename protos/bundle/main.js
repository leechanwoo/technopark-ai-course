
const {HelloRequest, HelloResponse} = require('./grpc_test_pb.js');
const {HelloServiceClient} = require('./grpc_test_grpc_web_pb.js');
const XMLHttpRequest = require('xhr2');


export const grpcTest = () => {
    var helloService = new HelloServiceClient('http://172.20.0.3:50051', { transport: XMLHttpRequest });
    helloService.sayHello(request, {}, function (err, response) {
        console.log(response)
    });

    var request = new HelloRequest();
    request.setName('Lee chanwoo');
}
