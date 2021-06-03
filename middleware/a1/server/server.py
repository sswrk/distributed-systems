import generated.creator_platform_pb2 as creator_platform
import generated.creator_platform_pb2_grpc as creator_platform_grpc

from concurrent import futures
from datetime import datetime
from essential_generators import DocumentGenerator
import grpc
import random
import time


class ContentData:
    def __init__(self, title, categories, price, content_type):
        self.title = title
        self.categories = categories
        self.price = price
        self.content_type = content_type


def generate_creator_data():
    gen = DocumentGenerator()

    possible_content_types = (
        creator_platform.POST,
        creator_platform.PICTURE,
        creator_platform.VIDEO
    )

    possible_categories = (
        creator_platform.DOCUMENTARY,
        creator_platform.ARTISTIC,
        creator_platform.FUNNY,
        creator_platform.SERIOUS
    )

    title = gen.sentence().replace('\n', '')
    categories = random.sample(possible_categories, random.randint(1, len(possible_categories)))
    price = random.randint(1, 100)
    content_type = random.sample(possible_content_types, 1)[0]

    return ContentData(title, categories, price, content_type)


class CreatorPlatformServicer(creator_platform_grpc.CreatorPlatformInformatorServicer):
    def __init__(self):
        self.creators = ("John123", "Annie345", "XTRACREATOR23", "RacingFan")

    def observe(self, request, context):
        try:
            creator_name = request.creatorName
        except Exception as e:
            context.set_details(str(e))
            context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
            return creator_platform.ContentInfo()

        if creator_name not in self.creators:
            context.set_details("Creator " + creator_name + "doesn't exist")
            context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
            return creator_platform.ContentInfo()

        print("Added new " + creator_name + " observer")

        while True:
            time.sleep(random.randint(3, 6))
            creator_data = generate_creator_data()
            current_datetime = datetime.now().strftime("%d/%m/%Y, %H:%M:%S")
            content_info = creator_platform.ContentInfo(creatorName=creator_name,
                                                        timestamp=current_datetime,
                                                        title=creator_data.title,
                                                        categories=creator_data.categories,
                                                        price=creator_data.price,
                                                        contentType=creator_data.content_type)

            if not context.is_active():
                print("Lost connection with client")

            yield content_info
            print(creator_name + " has added new content: " + creator_data.title)

    def ping(self, request, context):
        return creator_platform.Empty()


def start_server():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    creator_platform_grpc.add_CreatorPlatformInformatorServicer_to_server(
        CreatorPlatformServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Server started")
    server.wait_for_termination()


if __name__ == '__main__':
    start_server()
