// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: creator_platform.proto

package grpc.model;

public interface ContentInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:creatorPlatform.ContentInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string creatorName = 1;</code>
   * @return The creatorName.
   */
  java.lang.String getCreatorName();
  /**
   * <code>string creatorName = 1;</code>
   * @return The bytes for creatorName.
   */
  com.google.protobuf.ByteString
      getCreatorNameBytes();

  /**
   * <code>string timestamp = 2;</code>
   * @return The timestamp.
   */
  java.lang.String getTimestamp();
  /**
   * <code>string timestamp = 2;</code>
   * @return The bytes for timestamp.
   */
  com.google.protobuf.ByteString
      getTimestampBytes();

  /**
   * <code>string title = 3;</code>
   * @return The title.
   */
  java.lang.String getTitle();
  /**
   * <code>string title = 3;</code>
   * @return The bytes for title.
   */
  com.google.protobuf.ByteString
      getTitleBytes();

  /**
   * <code>repeated .creatorPlatform.Category categories = 4;</code>
   * @return A list containing the categories.
   */
  java.util.List<grpc.model.Category> getCategoriesList();
  /**
   * <code>repeated .creatorPlatform.Category categories = 4;</code>
   * @return The count of categories.
   */
  int getCategoriesCount();
  /**
   * <code>repeated .creatorPlatform.Category categories = 4;</code>
   * @param index The index of the element to return.
   * @return The categories at the given index.
   */
  grpc.model.Category getCategories(int index);
  /**
   * <code>repeated .creatorPlatform.Category categories = 4;</code>
   * @return A list containing the enum numeric values on the wire for categories.
   */
  java.util.List<java.lang.Integer>
  getCategoriesValueList();
  /**
   * <code>repeated .creatorPlatform.Category categories = 4;</code>
   * @param index The index of the value to return.
   * @return The enum numeric value on the wire of categories at the given index.
   */
  int getCategoriesValue(int index);

  /**
   * <code>uint32 price = 5;</code>
   * @return The price.
   */
  int getPrice();

  /**
   * <code>.creatorPlatform.ContentType contentType = 6;</code>
   * @return The enum numeric value on the wire for contentType.
   */
  int getContentTypeValue();
  /**
   * <code>.creatorPlatform.ContentType contentType = 6;</code>
   * @return The contentType.
   */
  grpc.model.ContentType getContentType();
}
