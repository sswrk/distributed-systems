// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: creator_platform.proto

package grpc.model;

/**
 * Protobuf enum {@code creatorPlatform.ContentType}
 */
public enum ContentType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>PICTURE = 0;</code>
   */
  PICTURE(0),
  /**
   * <code>VIDEO = 1;</code>
   */
  VIDEO(1),
  /**
   * <code>POST = 2;</code>
   */
  POST(2),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>PICTURE = 0;</code>
   */
  public static final int PICTURE_VALUE = 0;
  /**
   * <code>VIDEO = 1;</code>
   */
  public static final int VIDEO_VALUE = 1;
  /**
   * <code>POST = 2;</code>
   */
  public static final int POST_VALUE = 2;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static ContentType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static ContentType forNumber(int value) {
    switch (value) {
      case 0: return PICTURE;
      case 1: return VIDEO;
      case 2: return POST;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<ContentType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      ContentType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<ContentType>() {
          public ContentType findValueByNumber(int number) {
            return ContentType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return grpc.model.CreatorPlatform.getDescriptor().getEnumTypes().get(1);
  }

  private static final ContentType[] VALUES = values();

  public static ContentType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private ContentType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:creatorPlatform.ContentType)
}

