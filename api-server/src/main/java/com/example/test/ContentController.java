
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
import com.example.test.model.ImageData;
import com.example.GrpcTest;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.StatusRuntimeException;

import com.example.HelloServiceGrpc;
import com.example.HelloServiceGrpc.HelloServiceBlockingStub;
import com.example.ThisIsGeneratedJavaServiceGrpc;
import com.example.GrpcTest;
import com.example.GrpcTest.HelloResponse;
import com.example.GrpcTest.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
// import io.grpc.Server;
// import io.grpc.ServerBuilder;

@RestController
@RequestMapping("/api/content")
@CrossOrigin(origins="*")
public class ContentController {

    private final ContentCollectionRepository repository;
    private final HelloClient client; 
    private final String target;
    private final ManagedChannel channel;

    public ContentController(ContentCollectionRepository repository) { 
        this.repository = repository;

        // grpc 
        this.target = "172.20.0.4:50051";
        this.channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        this.client = new HelloClient(channel);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image")
    public void create(@RequestBody ImageData image) {
        System.out.println("image recieved!");
        try {
            System.out.println("grpc sent");
            client.greet("lee chanwoo");
            System.out.println("grpc recieved");
        } finally {
            channel.shutdownNow(); // .awaitTermination();
        }
        // System.out.println(image);
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




// @RestController
// @RequestMapping("/api/content")
// @CrossOrigin(origins="*")
// public class ContentController {

//     private final ContentCollectionRepository repository;

//     public ContentController(ContentCollectionRepository repository) {
// 	this.repository = repository;
//     }

//     @ResponseStatus(HttpStatus.CREATED)
//     @PostMapping("/image")
//     public void create(@RequestBody ImageData image) {
//         System.out.println(image);
//     }

//     @GetMapping("")
//     public List<Content> findAll() {
// 	return repository.findAll();
//     }

//     @GetMapping("/{id}")
//     public Content findById(@PathVariable Integer id) {
// 	return repository
// 	    .findById(id)
// 	    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));
//     }

//     @ResponseStatus(HttpStatus.CREATED)
//     @PostMapping("")
//     public void create(@RequestBody Content content) {
//         System.out.println(content);
// 	repository.save(content);
//     }


//     @ResponseStatus(HttpStatus.NO_CONTENT)
//     @PutMapping("/{id}")
//     public void update(@RequestBody Content content, @PathVariable Integer id) {
// 	if(!repository.existsById(id))  {
// 	    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found");
// 	}

// 	repository.save(content);

//     }
//     @ResponseStatus(HttpStatus.NO_CONTENT)
//     @DeleteMapping("/{id}")
//     public void delete(@PathVariable Integer id) {
// 	repository.delete(id);
//     }
// }
