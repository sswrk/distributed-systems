package grpc.model;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: creator_platform.proto")
public final class CreatorPlatformInformatorGrpc {

  private CreatorPlatformInformatorGrpc() {}

  public static final String SERVICE_NAME = "creatorPlatform.CreatorPlatformInformator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.model.ObserveRequest,
      grpc.model.ContentInfo> getObserveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "observe",
      requestType = grpc.model.ObserveRequest.class,
      responseType = grpc.model.ContentInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.model.ObserveRequest,
      grpc.model.ContentInfo> getObserveMethod() {
    io.grpc.MethodDescriptor<grpc.model.ObserveRequest, grpc.model.ContentInfo> getObserveMethod;
    if ((getObserveMethod = CreatorPlatformInformatorGrpc.getObserveMethod) == null) {
      synchronized (CreatorPlatformInformatorGrpc.class) {
        if ((getObserveMethod = CreatorPlatformInformatorGrpc.getObserveMethod) == null) {
          CreatorPlatformInformatorGrpc.getObserveMethod = getObserveMethod =
              io.grpc.MethodDescriptor.<grpc.model.ObserveRequest, grpc.model.ContentInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "observe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.model.ObserveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.model.ContentInfo.getDefaultInstance()))
              .setSchemaDescriptor(new CreatorPlatformInformatorMethodDescriptorSupplier("observe"))
              .build();
        }
      }
    }
    return getObserveMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.model.Empty,
      grpc.model.Empty> getPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ping",
      requestType = grpc.model.Empty.class,
      responseType = grpc.model.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.model.Empty,
      grpc.model.Empty> getPingMethod() {
    io.grpc.MethodDescriptor<grpc.model.Empty, grpc.model.Empty> getPingMethod;
    if ((getPingMethod = CreatorPlatformInformatorGrpc.getPingMethod) == null) {
      synchronized (CreatorPlatformInformatorGrpc.class) {
        if ((getPingMethod = CreatorPlatformInformatorGrpc.getPingMethod) == null) {
          CreatorPlatformInformatorGrpc.getPingMethod = getPingMethod =
              io.grpc.MethodDescriptor.<grpc.model.Empty, grpc.model.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.model.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.model.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new CreatorPlatformInformatorMethodDescriptorSupplier("ping"))
              .build();
        }
      }
    }
    return getPingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CreatorPlatformInformatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CreatorPlatformInformatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CreatorPlatformInformatorStub>() {
        @java.lang.Override
        public CreatorPlatformInformatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CreatorPlatformInformatorStub(channel, callOptions);
        }
      };
    return CreatorPlatformInformatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CreatorPlatformInformatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CreatorPlatformInformatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CreatorPlatformInformatorBlockingStub>() {
        @java.lang.Override
        public CreatorPlatformInformatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CreatorPlatformInformatorBlockingStub(channel, callOptions);
        }
      };
    return CreatorPlatformInformatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CreatorPlatformInformatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CreatorPlatformInformatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CreatorPlatformInformatorFutureStub>() {
        @java.lang.Override
        public CreatorPlatformInformatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CreatorPlatformInformatorFutureStub(channel, callOptions);
        }
      };
    return CreatorPlatformInformatorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CreatorPlatformInformatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void observe(grpc.model.ObserveRequest request,
        io.grpc.stub.StreamObserver<grpc.model.ContentInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getObserveMethod(), responseObserver);
    }

    /**
     */
    public void ping(grpc.model.Empty request,
        io.grpc.stub.StreamObserver<grpc.model.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getObserveMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                grpc.model.ObserveRequest,
                grpc.model.ContentInfo>(
                  this, METHODID_OBSERVE)))
          .addMethod(
            getPingMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                grpc.model.Empty,
                grpc.model.Empty>(
                  this, METHODID_PING)))
          .build();
    }
  }

  /**
   */
  public static final class CreatorPlatformInformatorStub extends io.grpc.stub.AbstractAsyncStub<CreatorPlatformInformatorStub> {
    private CreatorPlatformInformatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CreatorPlatformInformatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CreatorPlatformInformatorStub(channel, callOptions);
    }

    /**
     */
    public void observe(grpc.model.ObserveRequest request,
        io.grpc.stub.StreamObserver<grpc.model.ContentInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getObserveMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ping(grpc.model.Empty request,
        io.grpc.stub.StreamObserver<grpc.model.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CreatorPlatformInformatorBlockingStub extends io.grpc.stub.AbstractBlockingStub<CreatorPlatformInformatorBlockingStub> {
    private CreatorPlatformInformatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CreatorPlatformInformatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CreatorPlatformInformatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<grpc.model.ContentInfo> observe(
        grpc.model.ObserveRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getObserveMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.model.Empty ping(grpc.model.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CreatorPlatformInformatorFutureStub extends io.grpc.stub.AbstractFutureStub<CreatorPlatformInformatorFutureStub> {
    private CreatorPlatformInformatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CreatorPlatformInformatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CreatorPlatformInformatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.model.Empty> ping(
        grpc.model.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_OBSERVE = 0;
  private static final int METHODID_PING = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CreatorPlatformInformatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CreatorPlatformInformatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_OBSERVE:
          serviceImpl.observe((grpc.model.ObserveRequest) request,
              (io.grpc.stub.StreamObserver<grpc.model.ContentInfo>) responseObserver);
          break;
        case METHODID_PING:
          serviceImpl.ping((grpc.model.Empty) request,
              (io.grpc.stub.StreamObserver<grpc.model.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CreatorPlatformInformatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CreatorPlatformInformatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.model.CreatorPlatform.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CreatorPlatformInformator");
    }
  }

  private static final class CreatorPlatformInformatorFileDescriptorSupplier
      extends CreatorPlatformInformatorBaseDescriptorSupplier {
    CreatorPlatformInformatorFileDescriptorSupplier() {}
  }

  private static final class CreatorPlatformInformatorMethodDescriptorSupplier
      extends CreatorPlatformInformatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CreatorPlatformInformatorMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CreatorPlatformInformatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CreatorPlatformInformatorFileDescriptorSupplier())
              .addMethod(getObserveMethod())
              .addMethod(getPingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
