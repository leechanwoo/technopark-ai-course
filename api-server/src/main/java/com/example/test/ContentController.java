
package com.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.test.model.Content;
import com.example.test.model.Type;
import com.example.test.model.Status;
import com.example.test.model.ImageJson;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.StatusRuntimeException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import com.example.GrpcTest.HelloResponse;
import com.example.GrpcTest.HelloRequest;
import com.example.HelloServiceGrpc;
import com.example.HelloServiceGrpc.HelloServiceBlockingStub;

import com.example.Prediction.ImageData;
import com.example.Prediction.CategoricalResult;

import com.example.PredictionServiceGrpc;
import com.example.PredictionServiceGrpc.PredictionServiceBlockingStub;

import java.util.Base64;
import com.google.protobuf.ByteString;



@RestController
@RequestMapping("/api/content")
@CrossOrigin(origins="*")
public class ContentController {

    private final ContentCollectionRepository repository;
    // private final HelloClient client; 
    private final PredictionClient client; 
    private final HelloClient testClient; 
    private final String target;
    private final ManagedChannel channel;

    public ContentController(ContentCollectionRepository repository) { 
        this.repository = repository;

        // grpc 
        this.target = "172.20.0.4:50051";
        this.channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        this.testClient = new HelloClient(channel);
        this.client = new PredictionClient(channel);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/hello")
    public void helloHandler(@RequestBody String body) {
        System.out.println("request recieved!");

        try {
            System.out.println("grpc sent");
            testClient.greet("lee chanwoo");
            System.out.println("grpc recieved");
        } finally {
            channel.shutdownNow(); // .awaitTermination();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/image")
    public void imageHandler(@RequestBody ImageJson image) {
        System.out.println("image recieved!");

        try {
            System.out.println("grpc sent");
            client.predict(image.image().split(",")[1]);
            System.out.println("grpc recieved");
        } finally {
            // channel.shutdownNow(); // .awaitTermination();
        }
    }
}

class PredictionClient {
    private final PredictionServiceBlockingStub blockingStub;
  
    /** Construct client for accessing HelloWorld server using the existing channel. */
    public PredictionClient(Channel channel) {
      // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
      // shut it down.
  
      // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
      blockingStub = PredictionServiceGrpc.newBlockingStub(channel);
    }
  

    public void predict(String base64) {

        ImageData request = ImageData.newBuilder().setData(ByteString.copyFromUtf8(base64)).build();
        CategoricalResult response;

        try {
            System.out.println("try prediction");
            response = blockingStub.imagePrediction(request);

            for (float r : response.getResultList()) {
                System.out.println(r);
            }
        } catch (StatusRuntimeException e) {
          // logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
          
            System.out.println("error: " + e);
            return;
        }
    }
}


class HelloClient {
    private final HelloServiceBlockingStub blockingStub;
  
    /** Construct client for accessing HelloWorld server using the existing channel. */
    public HelloClient(Channel channel) {
      // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
      // shut it down.
  
      // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
      blockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }
  

    public void greet(String name) {
        // logger.info("Will try to greet " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloResponse response;

        try {
            System.out.println("try sayHello");
            response = blockingStub.sayHello(request);
            System.out.println(response.getGreeting());
        } catch (StatusRuntimeException e) {
          // logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
          
            System.out.println("error: " + e);
            return;
        }
    }
}