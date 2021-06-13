import queue

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


queues = {}


class CreatorPlatformServicer(creator_platform_grpc.CreatorPlatformInformatorServicer):
    def __init__(self):
        self.creators = ("John123", "Annie345", "XTRACREATOR23", "RacingFan")

    def observe(self, request, context):
        try:
            creator_name = request.creatorName
            client_id = request.clientId
        except Exception as e:
            context.set_details(str(e))
            context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
            return creator_platform.ContentInfo()

        if creator_name not in self.creators:
            context.set_details("Creator " + creator_name + "doesn't exist")
            context.set_code(grpc.StatusCode.INVALID_ARGUMENT)
            return creator_platform.ContentInfo()

        print("Added new " + creator_name + " observer: " + client_id)

        if client_id not in queues:
            queues[client_id] = {
                "John123": queue.Queue(),
                "Annie345": queue.Queue(),
                "XTRACREATOR23": queue.Queue(),
                "RacingFan": queue.Queue()
            }

        while True:
            time.sleep(random.randint(3, 6))
            if not queues[client_id][creator_name].empty():
                creator_data = queues[client_id][creator_name].get()
            else:
                creator_data = generate_creator_data()
                print(creator_name + " has added new content: " + creator_data.title)
            current_datetime = datetime.now().strftime("%d/%m/%Y, %H:%M:%S")
            content_info = creator_platform.ContentInfo(creatorName=creator_name,
                                                        timestamp=current_datetime,
                                                        title=creator_data.title,
                                                        categories=creator_data.categories,
                                                        price=creator_data.price,
                                                        contentType=creator_data.content_type)

            retries = 0
            while not context.is_active() and retries < 10:
                retries += 1
                time.sleep(3)

            if not context.is_active():
                queues[client_id][creator_name].put(creator_data)
                break

            yield content_info
            print("Sent notification about " + creator_name + ": " + creator_data.title)

        print("Client has closed the connection")

    def ping(self, request, context):
        return creator_platform.Empty()


def start_server():
    server = grpc.server(
        futures.ThreadPoolExecutor(max_workers=10),
        options=(
            ('grpc.keepalive_time_ms', 10000),
            ('grpc.keepalive_timeout_ms', 5000),
            ('grpc.keepalive_permit_without_calls', True),
            ('grpc.http2.max_pings_without_data', 0),
            ('grpc.http2.min_time_between_pings_ms', 10000),
            ('grpc.http2.min_ping_interval_without_data_ms', 5000),
        )
    )
    creator_platform_grpc.add_CreatorPlatformInformatorServicer_to_server(
        CreatorPlatformServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Server started")
    server.wait_for_termination()


if __name__ == '__main__':
    start_server()
