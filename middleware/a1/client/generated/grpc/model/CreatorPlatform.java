// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: creator_platform.proto

package grpc.model;

public final class CreatorPlatform {
  private CreatorPlatform() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_creatorPlatform_Empty_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_creatorPlatform_Empty_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_creatorPlatform_ObserveRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_creatorPlatform_ObserveRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_creatorPlatform_ContentInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_creatorPlatform_ContentInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026creator_platform.proto\022\017creatorPlatfor" +
      "m\"\007\n\005Empty\"%\n\016ObserveRequest\022\023\n\013creatorN" +
      "ame\030\001 \001(\t\"\265\001\n\013ContentInfo\022\023\n\013creatorName" +
      "\030\001 \001(\t\022\021\n\ttimestamp\030\002 \001(\t\022\r\n\005title\030\003 \001(\t" +
      "\022-\n\ncategories\030\004 \003(\0162\031.creatorPlatform.C" +
      "ategory\022\r\n\005price\030\005 \001(\r\0221\n\013contentType\030\006 " +
      "\001(\0162\034.creatorPlatform.ContentType*A\n\010Cat" +
      "egory\022\014\n\010ARTISTIC\020\000\022\t\n\005FUNNY\020\001\022\013\n\007SERIOU" +
      "S\020\002\022\017\n\013DOCUMENTARY\020\003*/\n\013ContentType\022\013\n\007P" +
      "ICTURE\020\000\022\t\n\005VIDEO\020\001\022\010\n\004POST\020\0022\237\001\n\031Creato" +
      "rPlatformInformator\022J\n\007observe\022\037.creator" +
      "Platform.ObserveRequest\032\034.creatorPlatfor" +
      "m.ContentInfo0\001\0226\n\004ping\022\026.creatorPlatfor" +
      "m.Empty\032\026.creatorPlatform.EmptyB\037\n\ngrpc." +
      "modelB\017CreatorPlatformP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_creatorPlatform_Empty_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_creatorPlatform_Empty_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_creatorPlatform_Empty_descriptor,
        new java.lang.String[] { });
    internal_static_creatorPlatform_ObserveRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_creatorPlatform_ObserveRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_creatorPlatform_ObserveRequest_descriptor,
        new java.lang.String[] { "CreatorName", });
    internal_static_creatorPlatform_ContentInfo_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_creatorPlatform_ContentInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_creatorPlatform_ContentInfo_descriptor,
        new java.lang.String[] { "CreatorName", "Timestamp", "Title", "Categories", "Price", "ContentType", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
