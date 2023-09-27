/**
 * @fileoverview gRPC-Web generated client stub for com.example
 * @enhanceable
 * @public
 */

// Code generated by protoc-gen-grpc-web. DO NOT EDIT.
// versions:
// 	protoc-gen-grpc-web v1.4.2
// 	protoc              v3.12.4
// source: grpc_test.proto


/* eslint-disable */
// @ts-nocheck



const grpc = {};
grpc.web = require('grpc-web');

const proto = {};
proto.com = {};
proto.com.example = require('./grpc_test_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.com.example.HelloServiceClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options.format = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname.replace(/\/+$/, '');

};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.com.example.HelloServicePromiseClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options.format = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname.replace(/\/+$/, '');

};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.com.example.HelloRequest,
 *   !proto.com.example.HelloResponse>}
 */
const methodDescriptor_HelloService_SayHello = new grpc.web.MethodDescriptor(
  '/com.example.HelloService/SayHello',
  grpc.web.MethodType.UNARY,
  proto.com.example.HelloRequest,
  proto.com.example.HelloResponse,
  /**
   * @param {!proto.com.example.HelloRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.com.example.HelloResponse.deserializeBinary
);


/**
 * @param {!proto.com.example.HelloRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.RpcError, ?proto.com.example.HelloResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.com.example.HelloResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.com.example.HelloServiceClient.prototype.sayHello =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/com.example.HelloService/SayHello',
      request,
      metadata || {},
      methodDescriptor_HelloService_SayHello,
      callback);
};


/**
 * @param {!proto.com.example.HelloRequest} request The
 *     request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.com.example.HelloResponse>}
 *     Promise that resolves to the response
 */
proto.com.example.HelloServicePromiseClient.prototype.sayHello =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/com.example.HelloService/SayHello',
      request,
      metadata || {},
      methodDescriptor_HelloService_SayHello);
};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.com.example.ThisIsGeneratedJavaServiceClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options.format = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname.replace(/\/+$/, '');

};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.com.example.ThisIsGeneratedJavaServicePromiseClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options.format = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname.replace(/\/+$/, '');

};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.com.example.HelloRequest,
 *   !proto.com.example.HelloResponse>}
 */
const methodDescriptor_ThisIsGeneratedJavaService_TestRPC = new grpc.web.MethodDescriptor(
  '/com.example.ThisIsGeneratedJavaService/TestRPC',
  grpc.web.MethodType.UNARY,
  proto.com.example.HelloRequest,
  proto.com.example.HelloResponse,
  /**
   * @param {!proto.com.example.HelloRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.com.example.HelloResponse.deserializeBinary
);


/**
 * @param {!proto.com.example.HelloRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.RpcError, ?proto.com.example.HelloResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.com.example.HelloResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.com.example.ThisIsGeneratedJavaServiceClient.prototype.testRPC =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/com.example.ThisIsGeneratedJavaService/TestRPC',
      request,
      metadata || {},
      methodDescriptor_ThisIsGeneratedJavaService_TestRPC,
      callback);
};


/**
 * @param {!proto.com.example.HelloRequest} request The
 *     request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.com.example.HelloResponse>}
 *     Promise that resolves to the response
 */
proto.com.example.ThisIsGeneratedJavaServicePromiseClient.prototype.testRPC =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/com.example.ThisIsGeneratedJavaService/TestRPC',
      request,
      metadata || {},
      methodDescriptor_ThisIsGeneratedJavaService_TestRPC);
};


module.exports = proto.com.example;

