from pymongo import MongoClient
from bson.objectid import ObjectId
from pprint import pprint


class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, username, password, dbname):
        # Please change the port to the localhost.
        port = 27017
        # Initializing the MongoClient. This helps to
        # access the MongoDB databases and collections.
        self.client = MongoClient(
            'mongodb://%s:%s@localhost:%d/?authMechanism=DEFAULT&authSource=%s' % (username, password, port, dbname))

        # Set database and list collection in database
        self.database = self.client[dbname]

        # Set collection to use
        collname = 'animals'
        self.collection = self.database[collname]

# Create method to implement the C in CRUD.
    def create(self, data):
        if data is not None:
            # data should be a list of one or more dictionary
            return self.collection.insert_many(data)
        else:
            raise Exception(
                "[-] ERROR: Nothing to save, because data parameter is empty.")

# Read method to implement the R in CRUD.
    def read(self, data):
        if data is not None:
            # data should be dictionary
            return self.collection.find(data, {"_id": False})
        else:
            raise Exception(
                "[-] ERROR: Nothing to read, because data parameter is empty.")

# Update method to implement the U in CRUD.
    def update(self, data):
        if data is not None:
            # data should be dictionary
            return self.collection.update_many(data)
        else:
            raise Exception(
                "[-] ERROR: Nothing to update, because data parameter is empty.")

# Delete method to implement the D in CRUD.
    def delete(self, data):
        if data is not None:
            return self.collection.delete_many(data)  # data should be dictionary
        else:
            raise Exception(
                "[-] ERROR: Nothing to delete, because data parameter is empty.")
